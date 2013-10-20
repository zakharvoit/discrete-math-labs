import Data.List
import Data.Char

pureFind :: Show a => Eq a => a -> [a] -> Int
pureFind x s =
        case pos of
            Nothing -> undefined
            Just idx -> idx
        where
            pos = x `elemIndex` s

lzw' :: String -> [String] -> String -> [Int] -> [Int]
lzw' "" dict buf acc = (pureFind buf dict):acc
lzw' (x:xs) dict buf acc
        | (x:buf) `elem` dict = lzw' xs dict (x:buf) acc
        | otherwise           = lzw' xs (dict ++ [x:buf]) [x] ((pureFind buf dict):acc)

list :: c -> [c]
list c = [c]

lzw :: String -> [Int]
lzw s = reverse $ lzw' s (map list ['a'..'z']) "" []

interactFile f = do
        inp <- readFile "lzw.in"
        writeFile "lzw.out" (f inp)

trim = reverse.(dropWhile isSpace).reverse

main = interactFile $ unwords.(map show).lzw.trim

