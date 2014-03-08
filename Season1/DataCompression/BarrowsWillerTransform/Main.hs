import Data.Char
import Data.List

getCyclicShifts :: String -> [String]
getCyclicShifts s = map (getCyclicShift s) [1 .. length s]

getCyclicShift :: String -> Int -> String
getCyclicShift s x = (drop x s) ++ (take x s)

getLast = head.reverse

bwt :: String -> String
bwt = (map getLast).sort.getCyclicShifts

interactFile f = do
        inp <- readFile "bwt.in"
        writeFile "bwt.out" (f inp)

trim = reverse.(dropWhile isSpace).reverse
main = interactFile (bwt.trim)
