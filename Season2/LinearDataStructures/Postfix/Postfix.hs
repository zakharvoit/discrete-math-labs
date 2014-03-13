data Stack a = Nil
           | Stack a (Stack a)

push :: Stack a -> a -> Stack a
push s x = Stack x s

pop :: Stack a -> (a, Stack a)
pop (Stack x s) = (x, s)

data Token = Number Integer
             | Operation Char
               
parseToken :: String -> Token
parseToken "+" = Operation '+'
parseToken "-" = Operation '-'
parseToken "*" = Operation '*'
parseToken x = Number $ read x

calc :: [Token] -> Integer
calc = (`calc'` Nil)
  where
    calc' :: [Token] -> Stack Integer -> Integer
    calc' [] (Stack x _) = x
    calc' ((Operation c):cs)
          (Stack y (Stack x s))
      | c == '+' = calc' cs (push s (x + y))
      | c == '-' = calc' cs (push s (x - y))
      | c == '*' = calc' cs (push s (x * y))
      | otherwise = error "No such operation" 
    calc' ((Number x):cs) s = calc' cs (push s x)

fileName :: String
fileName = "postfix"

interactFiles :: (String -> String) -> IO()
interactFiles f = do
  inp <- readFile $ fileName ++ ".in"
  writeFile (fileName ++ ".out") (f inp)
 
main = interactFiles $ show . calc . (map parseToken) . words
