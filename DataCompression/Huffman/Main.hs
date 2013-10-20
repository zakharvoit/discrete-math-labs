import Data.List 
import Data.Ord

data Node = 
        List Integer
        | Node Node Node Integer
            deriving (Eq, Show)

instance Ord Node where
        compare = comparing freq

list val = List val

freq (List val) = val
freq (Node _ _ val) = val

combine left right = 
        Node left right (freq left + freq right)

getMin :: [Node] -> [Node] -> (Node, [Node], [Node])
getMin [] (x:xs) = (x, [], xs)
getMin (x:xs) [] = (x, xs, [])
getMin (x:xs) (y:ys)
        | x < y = (x, xs, y:ys)
        | y <= x = (y, x:xs, ys)

huffmanStep :: [Node] -> [Node] -> ([Node], [Node])
huffmanStep a b = 
        (a'', b'' ++ [combine min1 min2])
            where
                (min1, a', b') = getMin a b
                (min2, a'', b'') = getMin a' b'

huffman' :: [Node] -> [Node] -> Node
huffman' [] [res] = res
huffman' a b = 
        huffman' a' b'
            where
                (a', b') = huffmanStep a b


huffman :: [Node] -> Node
huffman = (`huffman'` [])

calculateSize :: Node -> Integer -> Integer
calculateSize (List val) depth = val * depth
calculateSize (Node left right val) depth = 
        (calculateSize left (depth + 1)) + (calculateSize right (depth + 1))

interactFile f = do
        inp <- readFile "huffman.in"
        writeFile "huffman.out" (f inp)
    
main = interactFile $ show.(`calculateSize` 0).huffman.sort.(map (list.read)).tail.words

