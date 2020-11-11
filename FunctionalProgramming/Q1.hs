import qualified Data.Set as Set

-- TODO: what does check the possibility of each operation mean
{-
    takes list as argument ->converts to set and check if it is empty or not   
-}
isEmpty :: [Int] -> Bool
isEmpty (list)  = Set.null(Set.fromList(list))

{-
    takes two lists as arguments ->converts them to sets -> takes their union -> convert it to list and return
    TODO: check how to return set data type
-}
union :: [Int] -> [Int] -> [Int]
union list1 list2 = list3 where
   s1 = Set.fromList(list1)
   s2 = Set.fromList(list2)
   s3 = Set.union s1 s2
   list3 = Set.toList s3

{-
    takes two lists as arguments ->converts them to sets -> takes their intersection > convert it to list and return 
-}
intersection :: [Int] -> [Int] ->[Int]
intersection list1 list2 = list3 where
    s1 = Set.fromList list1
    s2 = Set.fromList list2
    s3 = Set.intersection s1 s2
    list3 = Set.toList s3

{-
    TODO: check what does subtraction exactly mean
    removes elements present in 2nd set from first set and returns first set
-}
subtraction :: [Int] -> [Int] -> [Int]
subtraction list1 list2 = list3 where
    s1 = Set.fromList list1
    s2 = Set.fromList list2
    s3 = Set.difference s1 s2
    list3 = Set.toList s3

{-
    takes two lists as arguments then adds them in nested order and returns a list with unique elements
-}
addition :: [Int] -> [Int] -> [Int]
addition list1 list2 = list3 where
    list = [x + y | x <- list1, y <- list2]
    set = Set.fromList list
    list3 = Set.toList set    
