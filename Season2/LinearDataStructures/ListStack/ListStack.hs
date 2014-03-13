import System.IO
import Data.Char
import Data.Int
import Data.Maybe
import qualified Data.ByteString.Char8 as B

data List a = Node a (List a)
            | Nil

newtype Stack a = Stack (List a)

createStack = Stack Nil

push :: Stack a -> a -> Stack a
push (Stack top) val = Stack (Node val top)

pop :: Stack a -> (a, Stack a)
pop (Stack (Node val top)) = (val, Stack top)

-- Solution --
  
solve :: Handle -> [B.ByteString] -> Stack Integer -> IO ()
solve hOut [] _ = hClose hOut
solve hOut (x:xs) stack = do
  if (B.head x) == '+' then do
    let xs' = tail xs
    let stack' = push stack (fst $ fromJust $ B.readInteger $ head xs)
    solve hOut xs' stack'
  else do
    let (res, stack') = pop stack
    hPutStrLn hOut (show res)
    solve hOut xs stack'

fileName :: String
fileName = "stack2"

main :: IO ()
main = do
  inp <- B.readFile $ fileName ++ ".in"
  hOut <- openFile (fileName ++ ".out") WriteMode
  let solve'' = solve' hOut
  solve'' $ tail $ B.words inp
  where
    solve' handle = (flip (solve handle)) createStack

