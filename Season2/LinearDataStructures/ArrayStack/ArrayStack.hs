import qualified Data.Array.IO as A
import qualified Data.ByteString.Char8 as B
import System.IO
import Data.Maybe

-- Vector implementation --
data Vector a = Vector { array :: A.IOArray Integer a
                       , size :: Integer
                       , capacity :: Integer
                       }

defaultCapacity :: Integer
defaultCapacity = 1
 
increasingCoefficient :: Integer
increasingCoefficient = 4

createVector :: IO (Vector a)
createVector = do
  arr <- A.newArray (0, defaultCapacity) undefined :: IO (A.IOArray Integer a)
  return $ Vector arr 0 defaultCapacity

arrayCopy :: Integer -> A.IOArray Integer a -> A.IOArray Integer a -> IO ()
arrayCopy idx v u = do
  (_, vSize) <- A.getBounds v
  if (vSize == idx) then
    return ()
  else do
    value <- A.readArray v idx
    A.writeArray u idx value
    arrayCopy (idx + 1) v u
  
  
increaseCapacity :: Vector a -> IO (Vector a)
increaseCapacity v = do
  let newCapacity = (capacity v) * increasingCoefficient
  arr <- A.newArray (0, newCapacity) undefined :: IO (A.IOArray Integer a)
  arrayCopy 0 (array v) arr
  return $ v { array = arr, capacity = newCapacity }
  
ensureCapacity :: Vector a -> Integer -> IO (Vector a)
ensureCapacity v newSize = do
  if newSize > (capacity v) then
    increaseCapacity v
  else
    return v
  
add :: Vector a -> a -> IO (Vector a)
add v x = do
  let newSize = (size v) + 1
  v' <- ensureCapacity v newSize
  A.writeArray (array v') (size v) x
  return $ v' { size = newSize }

get :: Vector a -> Integer -> IO (a)
get v x = return =<< A.readArray (array v) x
  
remove :: Vector a -> IO (Vector a)
remove v = do
  return $ v { size = (size v) - 1 }

-- Stack implementation --
newtype Stack a = Stack { impl :: Vector a }

createStack :: IO (Stack a)
createStack = (return . Stack) =<< createVector

push :: Stack a -> a -> IO (Stack a)
push s x = (return . Stack) =<< add (impl s) x

top :: Stack a -> IO (a)
top s = return =<< get (impl s) ((size $ impl s) - 1)

pop :: Stack a -> IO (Stack a)
pop s = (return . Stack) =<< remove (impl s)

-- Solution --
  
solve :: Handle -> [B.ByteString] -> Stack Integer -> IO ()
solve hOut [] _ = hClose hOut
solve hOut (x:xs) stack = do
  if (B.head x) == '+' then do
    let xs' = tail xs
    stack' <- push stack (fst $ fromJust $ B.readInteger $ head xs)
    solve hOut xs' stack'
  else do
    res <- top stack
    stack' <- pop stack
    hPutStrLn hOut (show res)
    solve hOut xs stack'

fileName :: String
fileName = "stack1"

main :: IO ()
main = do
  inp <- B.readFile $ fileName ++ ".in"
  hOut <- openFile (fileName ++ ".out") WriteMode
  solve'' <- solve' hOut
  solve'' $ tail $ B.words inp
  where
    solve' handle = (flip (solve handle)) `fmap` createStack

