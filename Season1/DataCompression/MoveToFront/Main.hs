import Data.List
import Data.Char

pureFind :: Show a => Eq a => a -> [a] -> Int
pureFind x s =
        case pos of
            Nothing -> undefined
            Just idx -> idx
        where
            pos = x `elemIndex` s

mtf' :: String -> String -> [Int] -> [Int]
mtf' [] _ acc = acc
mtf' (x:xs) dict acc =
        mtf' xs dict' (pos:acc)
        where 
            pos = x `pureFind` dict
            dict' = x:(filter (/=x) dict)


mtf :: String -> [Int]
mtf s = reverse $ mtf' s ['a' .. 'z'] []

interactFile f = do
        inp <- readFile "mtf.in"
        writeFile "mtf.out" (f inp)

trim = reverse.(dropWhile isSpace).reverse

main = interactFile $ unwords.(map show).(map (+1)).mtf.trim

