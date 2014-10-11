import Control.Monad
import Control.Monad.ST
import Data.Array.MArray
import Data.Array.ST
import Data.Functor
import Data.Int

data Heap s = Heap { arr :: STUArray s Int32 Int32
                   , size :: Int32
                   }

inf :: Int32
inf = 10^9 + 7

pushDown :: Heap s -> Int32 -> ST s ()
pushDown h v = do
  let left = 2 * v
  when (left <= size h) $ do
  let right = 2 * v + 1
  our <- readArray (arr h) v
  lv <- readArray (arr h) left
  rv <- if right <= size h
        then readArray (arr h) right
        else return $ -inf
  let (cur, cv) = if lv > rv
                  then (left, lv)
                  else (right, rv)
  when (cv > our) $ do
  swap (arr h) v cur
  pushDown h cur

swap :: STUArray s Int32 Int32 -> Int32 -> Int32 -> ST s ()
swap a i j = do
  buf <- readArray a i
  readArray a j >>= writeArray a i 
  writeArray a j buf

heapSort :: [Int32] -> [Int32]
heapSort a = runST $ do
  let size = fromIntegral $ length a
  arr <- newListArray (1, size) a :: ST s (STUArray s Int32 Int32)
  let heap = Heap arr size
  mapM_ (pushDown heap) (reverse [1 .. size `div` 2])
  mapM_ (\pos ->
          swap arr 1 pos >> pushDown (heap { size = pos - 1 }) 1
        ) (reverse [1 .. size])
  getElems arr

interactFiles :: String -> (String -> String) -> IO ()
interactFiles name f = do
  inp <- readFile (name ++ ".in")
  writeFile (name ++ ".out") $ f inp

main = interactFiles "sort"
       $ unwords
       . map show
       . heapSort
       . map read
       . tail
       . words
