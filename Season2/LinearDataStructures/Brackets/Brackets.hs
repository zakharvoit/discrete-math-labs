data List a = Node a (List a)
            | Nil

newtype Stack a = Stack (List a)

createStack :: Stack a
createStack = Stack Nil

push :: Stack a -> a -> Stack a
push (Stack top) val = Stack (Node val top)

pop :: Stack a -> (a, Stack a)
pop (Stack (Node val top)) = (val, Stack top)

top :: Stack a -> a
top (Stack (Node val _)) = val

isEmpty :: Stack a -> Bool
isEmpty (Stack Nil) = True
isEmpty _ = False

-- Solution --

isClose :: Char -> Bool
isClose ']' = True
isClose ')' = True
isClose _ = False

isOpen :: Char -> Bool
isOpen = not . isClose

equals :: Char -> Char -> Bool
equals '[' ']' = True
equals '(' ')' = True
equals _ _ = False

check :: String -> Bool
check s = check' s createStack
  where
    check' :: String -> Stack Char -> Bool
    check' "" (Stack Nil) = True
    check' "" _ = False
    check' (c:cs) s | isOpen c = check' cs (push s c)
                    | isClose c = if not (isEmpty s) && equals (top s) c then
                                    check' cs (snd $ pop s)
                                  else
                                    False

showBool :: Bool -> String
showBool True = "YES"
showBool False = "NO"

fileName :: String
fileName = "brackets"

interactFiles :: (String -> String) -> IO()
interactFiles f = do
  inp <- readFile $ fileName ++ ".in"
  writeFile (fileName ++ ".out") (f inp)
    
main :: IO()
main = interactFiles (unlines . (map (showBool . check)) . lines)
