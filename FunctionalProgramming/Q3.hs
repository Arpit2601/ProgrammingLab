import Data.Set as Set ( fromList, toList )
import Text.Printf ( printf )


-- contains all possible Dimensions of Bedroom
possibleDimensionBedroom :: [(Int, Int)]
possibleDimensionBedroom = Set.toList (Set.fromList [(x , y) | x <- [10..15], y <- [10..15]])

-- contains all possible Dimensions of Hall
possibleDimensionHall :: [(Int, Int)]
possibleDimensionHall = Set.toList (Set.fromList [(x , y) | x <- [15..20], y <- [10..15]])

-- contains all possible Dimensions of Kitchen
possibleDimensionKitchen :: [(Int, Int)]
possibleDimensionKitchen = Set.toList (Set.fromList [(x , y) | x <- [7..15], y <- [5..13]])

-- contains all possible Dimensions of Bathroom
possibleDimensionBathroom :: [(Int, Int)]
possibleDimensionBathroom = Set.toList (Set.fromList [(x , y) | x <- [4..8], y <- [5..9]])

-- contains all possible Dimensions of Balcony
possibleDimensionBalcony :: [(Int, Int)]
possibleDimensionBalcony = Set.toList (Set.fromList [(x , y) | x <- [5..10], y <- [5..10]])

-- contains all possible Dimensions of Garden
possibleDimensionGarden :: [(Int, Int)]
possibleDimensionGarden = Set.toList (Set.fromList [(x , y) | x <- [10..20], y <- [10..20]])

-- Tuples of bedroom, Hall and Kitchen dimensions
bedroomHallKitchenTuples :: [((Int, Int), (Int, Int), (Int, Int))]
bedroomHallKitchenTuples = Set.toList (Set.fromList [(x, y, z) | x <- possibleDimensionBedroom, y <- possibleDimensionHall, z <- possibleDimensionKitchen])

-- Tuples of bedroom, Hall, Kitchen and Bathroom dimensions
bedroomHallKitchenBathroomTuples :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int))]
bedroomHallKitchenBathroomTuples = Set.toList (Set.fromList [(x, y, z, a) | x <- possibleDimensionBedroom, y <- possibleDimensionHall, z <- possibleDimensionKitchen, a <- possibleDimensionBathroom])


-- combines a list with three tuple and one tuple into four tuple
computeTuplesOf4 :: [((Int, Int), (Int, Int), (Int, Int))] -> [(Int, Int)] -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int))] 
computeTuplesOf4 xs ys = [(xs1, xs2, xs3, y) | (xs1, xs2, xs3) <- xs, y <- ys]

-- combines a list with four tuple and one tuple into five tuple
computeTuplesOf5 :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [(Int, Int)] -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] 
computeTuplesOf5 xs ys = [(xs1, xs2, xs3, xs4, y) | (xs1, xs2, xs3, xs4) <- xs, y <- ys]

-- combines a list with five tuple and one tuple into six tuple
computeTuplesOf6 :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [(Int, Int)] -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] 
computeTuplesOf6 xs ys = [(xs1, xs2, xs3, xs4, xs5, y) | (xs1, xs2, xs3, xs4, xs5) <- xs, y <- ys]



--------------------------------------Tuple Generators----------------------------------------

-- removes tupes which give same area with bedroom and hall
b_h_k__ba_Generator :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k__ba_Generator possibleDimensions = b_h_k_ba_GeneratorUtil possibleDimensions [] 

-- Util function for above task
b_h_k_ba_GeneratorUtil :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [Int] -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k_ba_GeneratorUtil [] _ _ _ _ _= []
b_h_k_ba_GeneratorUtil ((xs1,xs2,xs3,xs4):xs) list2 numBedrooms numHalls numKitchens numBathrooms
    | uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 *numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms  `elem` list2 = b_h_k_ba_GeneratorUtil xs list2 numBedrooms numHalls numKitchens numBathrooms
    | otherwise = (xs1,xs2,xs3,xs4) : b_h_k_ba_GeneratorUtil xs ((uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 *numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms):list2) numBedrooms numHalls numKitchens numBathrooms

-- removes tupes which give same area with bedroom and hall
b_h_k__ba_g_Generator :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]  -> Int -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k__ba_g_Generator possibleDimensions = b_h_k_ba_g_GeneratorUtil possibleDimensions []

-- Util function for above task
b_h_k_ba_g_GeneratorUtil :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [Int] -> Int -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k_ba_g_GeneratorUtil [] _ _ _ _ _ _= []
b_h_k_ba_g_GeneratorUtil ((xs1,xs2,xs3,xs4,xs5):xs) list2 numBedrooms numHalls numKitchens numBathrooms numGarden
    | uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 * numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden  `elem` list2 = b_h_k_ba_g_GeneratorUtil xs list2 numBedrooms numHalls numKitchens numBathrooms numGarden
    | otherwise = (xs1,xs2,xs3,xs4,xs5) : b_h_k_ba_g_GeneratorUtil xs ((uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 * numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden ):list2) numBedrooms numHalls numKitchens numBathrooms numGarden
    
-- removes tupes which give same area with bedroom and hall
b_h_k__ba_g_bal_Generator :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]  -> Int -> Int -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k__ba_g_bal_Generator possibleDimensions = b_h_k_ba_g_bal_GeneratorUtil possibleDimensions []

-- Util function for above task
b_h_k_ba_g_bal_GeneratorUtil :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [Int] -> Int -> Int -> Int -> Int -> Int -> Int -> [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))]
b_h_k_ba_g_bal_GeneratorUtil [] _ _ _ _ _ _ _= []
b_h_k_ba_g_bal_GeneratorUtil ((xs1,xs2,xs3,xs4,xs5,xs6):xs) list2 numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony
    | uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 * numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden + uncurry (*) xs6 * numBalcony  `elem` list2 = b_h_k_ba_g_bal_GeneratorUtil xs list2 numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony
    | otherwise = (xs1,xs2,xs3,xs4,xs5,xs6) : b_h_k_ba_g_bal_GeneratorUtil xs ((uncurry (*) xs1 * numBedrooms + uncurry (*) xs2 * numHalls + uncurry (*) xs3 * numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden + uncurry (*) xs6 * numBalcony):list2) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony


-------------------------------Functions to get maximum area and info related to it----------------------
-- Returns Dimensions tuple which gives maximum area
getMaximumArea :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> Int -> Int -> Int -> Int -> Int -> Int -> Int
getMaximumArea = getMaximumAreaUtil

-- Util funciton for above task
getMaximumAreaUtil :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> Int -> Int -> Int -> Int -> Int -> Int -> Int
getMaximumAreaUtil [] _ _ _ _ _ _= 0
getMaximumAreaUtil ((xs1,xs2,xs3,xs4,xs5,xs6):xs) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony = maximum [computeArea (xs1,xs2,xs3,xs4,xs5,xs6) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony , getMaximumAreaUtil xs numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony]
 

------------------------Functions to check constraints--------------------------
-- condition to check if area with current dimension is less than max possible area and kitchen dimensions are less than that of bathroom and dimension of kitchen <= dimension bedroom/hall
condition1 :: ((Int, Int), (Int, Int), (Int, Int), (Int, Int)) -> Int -> Int -> Int -> Int -> Int -> Bool
condition1 (xs1,xs2,xs3, xs4) maxAreaPossible numBedrooms numHalls numKitchens numBathrooms = do
    uncurry (*) xs1 *numBedrooms + uncurry (*) xs2 *numHalls + uncurry (*) xs3 *numKitchens + uncurry (*) xs4 * numBathrooms <= maxAreaPossible && fst xs4 <= fst xs3 && snd xs4  <= snd xs3 && fst xs3 <= fst xs1 && snd xs3  <= snd xs1 &&  fst xs3 <= fst xs2 && snd xs3 <= snd xs2

-- condition to check if area with current dimension is less than max possible area
condition2 :: ((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int)) -> Int -> Int -> Int -> Int -> Int -> Int -> Bool
condition2 (xs1,xs2,xs3,xs4,xs5) maxAreaPossible numBedrooms numHalls numKitchens numBathrooms numGarden= do
    uncurry (*) xs1 *numBedrooms + uncurry (*) xs2 *numHalls + uncurry (*) xs3 *numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden <= maxAreaPossible 

-- Function to compute area with given dimensions
computeArea :: ((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int)) -> Int -> Int -> Int -> Int -> Int -> Int -> Int
computeArea (xs1,xs2,xs3,xs4,xs5,xs6) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony = do
    uncurry (*) xs1 *numBedrooms + uncurry (*) xs2 *numHalls + uncurry (*) xs3 *numKitchens + uncurry (*) xs4 * numBathrooms + uncurry (*) xs5 * numGarden + uncurry (*) xs6 * numBalcony


---------------------------------------main function to call and print answer--------------------------------
{-
    @params: maximum area possible, number of bedrooms and number of halls
-}
design :: Int -> Int -> Int -> IO()
design maxAreaPossible numBedrooms numHalls = do
    
    -- Number of different rooms calculated as given by constraints in assignment
    let numKitchens = ceiling(fromIntegral numBedrooms/3)
    let numBathrooms = numBedrooms + 1
    let numGarden = 1
    let numBalcony = 1
    
    -- Remove bedroom, hall, kitchen and bathroom tuples which give same area after removing tuples which don't constraints given in question
    let b_h_k_ba_tuples = b_h_k__ba_Generator (filter (\(xs1,xs2,xs3,xs4) -> condition1 (xs1,xs2,xs3,xs4) maxAreaPossible numBedrooms numHalls numKitchens numBathrooms) bedroomHallKitchenBathroomTuples) numBedrooms numHalls numKitchens numBathrooms  
    
    -- Remove bedroom, hall, kitchen, bathroom and garden tuples which give same area after removing tuples which don't constraints given in question
    let b_h_k_ba_g_tuples = b_h_k__ba_g_Generator (filter (\(xs1,xs2,xs3,xs4,xs5) -> condition2 (xs1,xs2,xs3,xs4,xs5) maxAreaPossible numBedrooms numHalls numKitchens numBathrooms numGarden) (computeTuplesOf5 b_h_k_ba_tuples possibleDimensionGarden)) numBedrooms numHalls numKitchens numBathrooms numGarden    
    
    -- Remove bedroom, hall, kitchen, bathroom, garden and balcony tuples which give same area after removing tuples which don't constraints given in question
    let b_h_k_ba_g_bal_tuples = b_h_k__ba_g_bal_Generator (filter (\(xs1,xs2,xs3,xs4,xs5,xs6) -> computeArea (xs1,xs2,xs3,xs4,xs5,xs6) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony <= maxAreaPossible) (computeTuplesOf6 b_h_k_ba_g_tuples possibleDimensionBalcony)) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony
    
    -- Get the maximum possible area with dimension tuples which follow all constraints
    let maxAreaComputed = getMaximumArea b_h_k_ba_g_bal_tuples numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony

    -- Filter out dimensions which give maximum area, there can be many such possibilities so we take head result to print in printOptimum function
    let finalDimensions = filter (\(xs1,xs2,xs3,xs4,xs5,xs6) -> computeArea (xs1,xs2,xs3,xs4,xs5,xs6) numBedrooms numHalls numKitchens numBathrooms numGarden numBalcony == maxAreaComputed) b_h_k_ba_g_bal_tuples
    
    -- Store all the number of rooms in a list
    let numList = [numBedrooms, numHalls, numKitchens, numBathrooms, numGarden, numBalcony]

    -- Print the optimum answer
    printOptimumAnswer finalDimensions numList maxAreaPossible maxAreaComputed

-- Function to print the computed answer i.e. dimension and count of each room and left over area
printOptimumAnswer :: [((Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int), (Int, Int))] -> [Int] -> Int -> Int-> IO()
printOptimumAnswer result numList maxAreaPossible area = do
    if null result
    then printf "No design possible for the given constraints\n"
    else do 
        let (xs1,xs2,xs3,xs4,xs5,xs6) = head result -- Get only the first ans
        let leftarea = maxAreaPossible - area   -- Area left is max possible - max computed and is >=0
        printf "Bedroom: %d (%d, %d)\nHall: %d (%d, %d)\nKitchen: %d (%d, %d)\nBathroom: %d (%d, %d)\nGarden: %d (%d, %d)\nBalcony: %d (%d, %d)\nUnused Space: %d\n" (numList !! 0) (fst xs1) (snd xs1) (numList !! 1) (fst xs2) (snd xs2) (numList !! 2) (fst xs3) (snd xs3) (numList !! 3) (fst xs4) (snd xs4) (numList !! 4) (fst xs5) (snd xs5) (numList !! 5) (fst xs6) (snd xs6) leftarea
