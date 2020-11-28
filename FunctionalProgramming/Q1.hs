import qualified Data.Set as Set

{-
    takes list as argument ->converts to set and check if it is empty or not   
-}
isEmpty :: (Ord a) => [a]  -> Bool
isEmpty list  = Set.null(Set.fromList list)

{-
    takes two lists as arguments ->converts them to sets -> takes their union -> convert it to list and return
-}
union :: (Ord a) => [a] -> [a] -> [a]
union list1 list2 = list3 where
   s1 = Set.fromList list1
   s2 = Set.fromList list2
   s3 = Set.union s1 s2
   list3 = Set.toList s3

{-
    takes two lists as arguments ->converts them to sets -> takes their intersection > convert it to list and return 
-}
intersection :: (Ord a) => [a] -> [a] -> [a]
intersection list1 list2 = list3 where
    s1 = Set.fromList list1
    s2 = Set.fromList list2
    s3 = Set.intersection s1 s2
    list3 = Set.toList s3

{-
    removes elements present in 2nd set from first set and returns first set
-}
subtraction :: (Ord a) => [a] -> [a] -> [a]
subtraction list1 list2 = list3 where
    s1 = Set.fromList list1
    s2 = Set.fromList list2
    s3 = Set.difference s1 s2
    list3 = Set.toList s3

{-
    takes two lists as arguments then adds them in nested order and returns a list with unique elements
-}
addition :: (Num a, Ord a) => [a] -> [a] -> [a]
addition list1 list2 = list3 where
    list = [x + y | x <- list1, y <- list2]
    set = Set.fromList list
    list3 = Set.toList set    

