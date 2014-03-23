solve :: [Integer] -> Bool
solve xs = solve' xs xs 1 1

solve' :: [Integer] -> [Integer] -> Int -> Int -> Bool
solve' _ [] _ _ = True
solve' xxs@(x:xs) (y:ys) pos1 pos2
  | pos2 `div` 2 == pos1 = x <= y && solve' xs ys (pos1 + 1) (pos2 + 1)
  | otherwise = solve' xxs ys pos1 (pos2 + 1)

boolYes :: Bool -> String
boolYes True = "YES"
boolYes False = "NO"
                
interactFiles :: String -> (String -> String) -> IO()
interactFiles file f = do
  inp <- readFile (file ++ ".in")
  writeFile (file ++ ".out") $ f inp

fileName :: String
fileName = "isheap"

main :: IO()
main = interactFiles fileName (boolYes . solve . (map read) . tail . words)
