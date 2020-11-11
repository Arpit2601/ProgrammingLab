import Data.List ()
import System.IO ()
import Data.List ( permutations ) 
import Text.Printf
--  Why I'm not able to use this import
{-
    sudo apt-get install cabal
    cabal update
    cabal install random
    ghci Q2.hs
-}
-- import System.Random

teams :: [String]
teams = ["BS", "CM", "CH", "CV", "CS", "DS", "EE", "HU", "MA", "ME", "PH", "ST"]

dates :: [Int]
dates = [1, 1, 2, 2, 3, 3]

times :: [Float]
times = [9.5, 19.5, 9.5, 19.5, 9.5, 19.5]

seed :: Int
seed = 12

permuted_teams :: [String]
permuted_teams = permutations teams  !! seed


removeItem :: Eq a => a -> [a] -> [a]
removeItem _ []                 = []
removeItem x (y:ys) 
    | x == y    = removeItem x ys
    | otherwise = y : removeItem x ys


getDraws :: [String] -> [[String]]
getDraws [] = []
getDraws all_teams = draws where
    team1 = all_teams !! 0
    team2 = all_teams !! 1
    draw = [team1, team2]
    temp_teams_1 = tail all_teams
    temp_teams_2 = tail temp_teams_1
    draws = draw: getDraws temp_teams_2


getSchedule :: [[String]] -> [Int] -> [Float] -> [(String, String, Int, Float)]
getSchedule draws dates times = schedule where
    teams = head draws
    date = head dates
    time = head times
    match = (head teams, teams !! 1, date, time)
    temp_draws =  tail draws
    temp_dates = tail dates
    temp_times = tail times
    schedule = match: getSchedule temp_draws temp_dates temp_times

fixture :: String -> IO()
fixture input = do
    let draws = getDraws permuted_teams
    let schedule = getSchedule draws dates times
    if (input == "all") 
        then printSchedule schedule
    else
        if (input `elem` teams)
            then printFixture input schedule
        else
            print "This team does not exist. Provide correct input"
    

printSchedule :: [(String, String, Int, Float)] -> IO()
printSchedule [] = do print "abcd"
printSchedule matches = do
    let match = head matches
    let (team1, team2, date, time) = match
    -- print match
    if (time == 9.5)
        then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
    else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date 
    printSchedule(tail matches) -- TODO why is this giving error Exception: Prelude.head: empty list


printFixture :: String -> [(String, String, Int, Float)] -> IO()
printFixture team (fixture: rest) = do
    let (team1, team2, date, time) = fixture
    if (team == team1 || team == team2)
        then 
            if (time == 9.5)
               then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
            else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
        else
            printFixture team rest 


nextMatch :: Int -> Float -> IO()
nextMatch date time = do
    let draws = getDraws permuted_teams
    let schedule = getSchedule draws dates times
    -- Sanity check for time and date
    if ( date > 3)
        then print "Wrong date provided. No matches after this date."
    else 
        if  (date == 3 && time >=19.5)
            then print "Wrong date and time combination provided, No match after this date and time."
        else 
            if (time <0 || time >24)
                then print "Time not given in correct format"
            else findNextFixture schedule date time


findNextFixture :: [(String, String, Int, Float)] -> Int -> Float -> IO()
findNextFixture (fixture:rest) givenDate givenTime = do
    let (team1, team2, date, time) = fixture
    if (givenDate < date)
        then 
            if (time == 9.5)
               then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
            else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
    else 
        if (givenDate == date && givenTime <time)
            then 
                 if (time == 9.5)
                    then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
                else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
        else findNextFixture rest givenDate givenTime