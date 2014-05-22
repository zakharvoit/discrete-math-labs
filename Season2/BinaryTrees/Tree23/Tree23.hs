{-# LANGUAGE NoImplicitPrelude #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE BangPatterns #-}

import Prelude hiding (minimum,
                       maximum,
                       min,
                       Left,
                       Right)
import Data.Int
import Data.Maybe
import Data.Char
import Debug.Trace
import qualified Data.ByteString.Char8 as B

-- Util stuff

-- Two argument (.)
($$) = (.).(.)

-- Data declaration

data Tree a = Empty
            | Leaf
              { min  :: a 
              }
            | Two
              { keyRight :: a
              , left     :: Tree a
              , right    :: Tree a
              , min      :: a
              }
            | Three 
              { keyMid   :: a
              , keyRight :: a
              , left     :: Tree a
              , mid      :: Tree a
              , right    :: Tree a
              , min      :: a
              }

instance Show a => Show (Tree a) where
  show Empty                                 = "()"
  show (Leaf { min=val })                  = show val
  show (Two { left=l, right=r })           = "(" ++ show l ++ ", " ++ show r ++ ")"
  show (Three { left=l, mid=m, right=r }) = "[" ++ show l ++ ", " ++ show m ++ ", " ++ show r ++ "]"

data Query = Insert Int32
           | Delete Int32
           | Next Int32
           | Prev Int32
           | Exists Int32
             deriving Show

data Direction = Left
               | Mid
               | Right
               deriving Eq

-- Ugly functions for easy implementation of other code.

leaf x = Leaf x

two t1 t2
  | mn1 < mn2 = Two mn2 t1 t2 mn1
  | otherwise = Two mn1 t2 t1 mn2
  where
    mn1 = min t1
    mn2 = min t2

three t1 t2
  | k3 < k1   = Three k1 k2 t2 l r k3
  | k3 < k2   = Three k3 k2 l t2 r k1
  | otherwise = Three k2 k3 l r t2 k1
  where
    l  = left t1
    r  = right t1
    k1 = min $ left t1
    k2 = keyRight t1 
    k3 = min t2

isEmpty (Empty) = True
isEmpty _ = False

isLeaf (Leaf _) = True
isLeaf _ = False

isTwo (Two _ _ _ _) = True
isTwo _ = False

isThree (Three _ _ _ _ _ _) = True
isThree _ = False

-- Other code

getBranch :: Direction -> Tree a -> Tree a
getBranch Left  = left
getBranch Right = right
getBranch Mid   = mid

selectBranchTwo (Two k l r _) x
  | x < k     = Left
  | otherwise = Right

selectBranchThree (Three k1 k2 l m r _) x
  | x < k1    = Left
  | x < k2    = Mid
  | otherwise = Right
                    
selectBranch :: Ord a => Tree a -> a -> Direction
selectBranch t x
  | isTwo t   = selectBranchTwo t x
  | isThree t = selectBranchThree t x

findPath :: Ord a => [Tree a] -> Tree a -> a -> Maybe ([Tree a], Tree a)
findPath p t x
  | isLeaf t && min t == x = Just (p, t)
  | isTwo t || isThree t  = findPath (t:p) (getBranch (selectBranch t x) t) x
  | otherwise            = Nothing

findClosest :: Ord a => [Tree a] -> Tree a -> a -> ([Tree a], Tree a)
findClosest p t x
  | isLeaf t = (p, t)
  | otherwise = findClosest (t:p) (getBranch (selectBranch t x) t) x

find :: Ord a => Tree a -> a -> Maybe (Tree a)
find t x
  | isLeaf t && min t == x = Just t
  | isTwo t || isThree t    = find (getBranch (selectBranch t x) t) x
  | otherwise              = Nothing

contains :: Ord a => Tree a -> a -> Bool
contains Empty _ = False
contains (Leaf key) x = x == key
contains t x = contains (getBranch (selectBranch t x) t) x

next :: Ord a => Tree a -> a -> Maybe a
next Empty _ = Nothing
next t x
  | maximum t <= x  = Nothing
  | otherwise      = if min v' > x then Just $ min v'
                     else Just $ min $ nextLeaf p' v' (min v')
    where
      (p', v') = findClosest [] t x

nextLeaf :: Ord a => [Tree a] -> Tree a -> a -> Tree a
nextLeaf p t k 
  | isLeaf t && min t /= k   = t
  | isLeaf t                 = findInParent
  | selectBranch t k == Right = findInParent
  | selectBranch t k == Mid   = findIn right
  | selectBranch t k == Left  = if isTwo t then findIn right
                               else findIn mid
  where
    findIn f     = fromJust $ find t $ min $ f t
    findInParent = nextLeaf (tail p) (head p) k

prev :: Ord a => Tree a -> a -> Maybe a
prev Empty _ = Nothing
prev t x
  | min t >= x      = Nothing
  | otherwise      = if min v' < x then Just $ min v'
                     else Just $ min $ prevLeaf p' v' (min v')
  where
    (p', v') = findClosest [] t x

prevLeaf :: Ord a => [Tree a] -> Tree a -> a -> Tree a
prevLeaf p t k 
  | isLeaf t && min t /= k     = t
  | isLeaf t                   = findInParent
  | selectBranch t k == Left    = findInParent
  | selectBranch t k == Mid     = findIn left
  | selectBranch t k == Right   = if isTwo t then findIn left
                                 else findIn mid
  where
    findIn f     = fromJust $ find t $ maximum $ f t
    findInParent = prevLeaf (tail p) (head p) k

goToSelected :: Tree a -> (Tree a -> Tree a) -> a
goToSelected t f
  | isLeaf t  = min t
  | otherwise = goToSelected (f t) f 

maximum :: Ord a => Tree a -> a
maximum = (`goToSelected` right)

insert :: Ord a => Tree a -> a -> Tree a
insert t k
  | isEmpty t      = leaf k
  -- | t `contains` k = t
  | otherwise    = updateRoot t' u
    where
      (t', u) = insert' t k

delete :: Ord a => Tree a -> a -> Tree a
delete Empty _ = Empty
delete t@(Leaf x) k
  | x == k     = Empty
  | otherwise = t
delete t k =
  updateTree t dir (delete (getBranch dir t) k)
  where
    dir = selectBranch t k

updateTree :: Ord a => Tree a -> Direction -> Tree a -> Tree a
updateTree t d c
  | isEmpty c = removeBranch t d
  | otherwise = changeChild t d c

removeBranch :: Ord a => Tree a -> Direction -> Tree a
removeBranch t d
  | isTwo t = if d == Left then right t
              else left t
  | isThree t = case d of
                  Left  -> two (mid t) (right t)
                  Mid   -> two (left t) (right t)
                  Right -> two (left t) (mid t)

updateRoot :: Ord a => Tree a -> Maybe (Tree a) -> Tree a
updateRoot t u
  | isNothing u = t
  | isLeaf t    = two t u'
  | isTwo t     = three t u'
  | isThree t   = updateRoot t' u''
  where
    u'        = fromJust u
    (t', u'') = splitThree t u'

insert' :: Ord a => Tree a -> a -> (Tree a, Maybe (Tree a))
insert' t k
  | isLeaf t && min t == k = (t, Nothing)
  | isLeaf t  = (t, Just $ leaf k)
  | otherwise = mergedNode
    where
      dir                = selectBranch t k
      branch             = getBranch dir t
      mergedNode         = if isNothing update then (newNode, Nothing)
                           else mergeNode  newNode (fromJust update)
      newNode            = changeChild t dir newChild
      (newChild, update) = insert' branch k

changeChild :: Ord a => Tree a -> Direction -> Tree a -> Tree a
changeChild t Left c  = t { left = c, min = min c }
changeChild t Right c = t { keyRight = min c,right = c }
changeChild t Mid c   = t { keyMid = min c,mid = c }

mergeNode :: Ord a => Tree a -> Tree a -> (Tree a, Maybe (Tree a))
mergeNode t1 t2
  | isTwo t1 = (three t1 t2, Nothing)
  | isThree t1 = splitThree t1 t2

splitThree :: Ord a => Tree a -> Tree a -> (Tree a, Maybe (Tree a))
splitThree t1 t2 =
  if min t2 < keyMid t1 then
    (two (left t1) t2, Just $ two (mid t1) (right t1))
  else
    (two (left t1) (mid t1), Just $ two t2 (right t1))

-- Solution

showBool :: Bool -> B.ByteString
showBool True = "true"
showBool False = "false"

showRes :: Maybe Int32 -> B.ByteString
showRes (Just x) = B.pack $ show x
showRes Nothing  = "none"

parse :: B.ByteString -> [Query]
parse = parse' . B.words

parse' :: [B.ByteString] -> [Query]
parse' [] = []
parse' (s:x:xs)
  | B.head s == 'i' = Insert (fromInteger $ fst $ fromJust $ B.readInteger x) : parse' xs
  | B.head s == 'd' = Delete (fromInteger $ fst $ fromJust $ B.readInteger x) : parse' xs
  | B.head s == 'n' = Next (fromInteger $ fst $ fromJust $ B.readInteger x) : parse' xs
  | B.head s == 'p' = Prev (fromInteger $ fst $ fromJust $ B.readInteger x) : parse' xs
  | B.head s == 'e' = Exists (fromInteger $ fst $ fromJust $ B.readInteger x) : parse' xs

solveQuery :: Query -> Tree Int32 -> (B.ByteString, Tree Int32)
solveQuery (Insert x) t = ("", t `insert` x) 
solveQuery (Delete x) t = ("", t `delete` x) 
solveQuery (Next x) t   = (showRes $ t `next` x, t) 
solveQuery (Prev x) t   = (showRes $ t `prev` x, t) 
solveQuery (Exists x) t = (showBool $ t `contains` x, t) 

runner :: [Query] -> Tree Int32 -> [B.ByteString]
runner [] _ = []
runner (q:qs) t =
  (if res /= "" then (res:) else id)
  $ runner qs t'
  where
    (res, t') = solveQuery q t

solve :: B.ByteString -> B.ByteString 
solve = B.unlines . (`runner` Empty) . parse

fileName :: String
fileName = "bst"

interactFiles :: String -> (B.ByteString -> B.ByteString) -> IO()
interactFiles s f = do
  inp <- B.readFile (s ++ ".in")
  B.writeFile (s ++ ".out") (f inp)

main :: IO ()
main = interactFiles fileName solve

