import System.IO ()
import Data.List ( permutations ) 
import Text.Printf ( printf )
import Data.IORef ( IORef, newIORef, writeIORef, readIORef )
import System.IO.Unsafe ( unsafePerformIO ) 
import System.Random (getStdRandom, randomR)


{-
    sudo apt-get install libghc-random-dev
    sudo apt-get install cabal
    cabal update
    cabal install random
-}

-- global variable to check if fixtures have been created or not before nextMatch is called
fixturesGenerated ::  IORef Int
{-# NOINLINE fixturesGenerated #-}
fixturesGenerated = unsafePerformIO (newIORef 0)

-- global variable for storing draws 
drawsGenerated :: IORef [[String]]
{-# NOINLINE drawsGenerated #-}
drawsGenerated = unsafePerformIO (newIORef [["NULL"]])

-- global variable for storing schedule
scheduleGenerated :: IORef [(String, String, Int, Float)]
{-# NOINLINE scheduleGenerated #-}
scheduleGenerated = unsafePerformIO (newIORef [("NULL", "NULL", 0, 0)])


-- will generate a new seed every time
newSeed :: Int -> Int -> IO Int 
newSeed x y = getStdRandom (randomR (x, y))

-- list containing team names
teams :: [String]
teams = ["BS", "CM", "CH", "CV", "CS", "DS", "EE", "HU", "MA", "ME", "PH", "ST"]

-- list containing dates on which matches happen
dates :: [Int]
dates = [1, 1, 2, 2, 3, 3]

-- list containing times of matches
times :: [Float]
times = [9.5, 19.5, 9.5, 19.5, 9.5, 19.5]

-- returns a particular permutation of teams list 
-- will give new permutation for each seed passed
permutedTeams :: Int -> [String]
permutedTeams seed = permutations teams  !! seed

-- returns the draws i.e. team1 vs team2 list
getDraws :: [String] -> [[String]]
getDraws [] = []
getDraws all_teams = draws where
    team1 = head all_teams 
    team2 = all_teams !! 1
    draw = [team1, team2]
    temp_teams_1 = tail all_teams
    temp_teams_2 = tail temp_teams_1
    draws = draw: getDraws temp_teams_2

-- take draws, dates and times as input and returns [team1 vs team2, date, time]
getSchedule :: [[String]] -> [Int] -> [Float] -> [(String, String, Int, Float)]
getSchedule [] [] [] = []
getSchedule draws dates times = schedule where
    teams = head draws
    date = head dates
    time = head times
    match = (head teams, teams !! 1, date, time)
    temp_draws =  tail draws
    temp_dates = tail dates
    temp_times = tail times
    schedule = match: getSchedule temp_draws temp_dates temp_times

-- function to generate fixtures for either all or for just one team
fixture :: String -> IO()
fixture input = do
    -- first generate a new random number which will work as seed
    seed <- newSeed 0 1000

    -- get new permutation based on seed
    let permuted_teams = permutedTeams seed

    -- Get draws from permuted list of teams
    let draws = getDraws permuted_teams

    -- Get schedule
    let schedule = getSchedule draws dates times

    -- If nput is fixture "all" then compute draws and schedule and then store them in global variables
    if input == "all" 
        then do
            writeIORef fixturesGenerated 1
            writeIORef drawsGenerated draws
            writeIORef scheduleGenerated schedule
            printSchedule schedule
    -- If input is of form fixture "team_name" then first check if team exists or not
    -- If not then return error with no such team otherwise check if fixtures have been generated or not and give output on its basis    
    else
        if input `elem` teams
            then do
                temp <- readIORef fixturesGenerated
                tempSchedule <- readIORef scheduleGenerated
                if temp ==0 then
                    print "Fixtures have not been generated yet"
                else
                    printFixture input tempSchedule
        else
            print "This team does not exist. Provide correct input"
    
-- Function to print the schedule
printSchedule :: [(String, String, Int, Float)] -> IO()
-- if only match then print it
printSchedule [match] = do 
    let (team1, team2, date, time) = match
    if time == 9.5
        then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
    else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date 

-- if more than one match
printSchedule matches = do
    let match = head matches
    let (team1, team2, date, time) = match
    if time == 9.5
        then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
    else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date 
    printSchedule(tail matches) 


-- print fexture of one team
printFixture :: String -> [(String, String, Int, Float)] -> IO()
printFixture team (fixture: rest) = do
    let (team1, team2, date, time) = fixture
    if team == team1 || team == team2
        then 
            if time == 9.5
               then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
            else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
        else
            printFixture team rest 

-- get next match given date and time
nextMatch :: Int -> Float -> IO()
nextMatch date time = do

    temp <- readIORef fixturesGenerated

    if temp == 0
        then
            print "Fixtures not created yet"
    else do
        tempSchedule <- readIORef scheduleGenerated
        -- Sanity check for time and date
        if date > 3
            then print "Wrong date provided. No matches after this date."
        else if date < 1 
            then print "Wrong date provided. No matches before this date."
        else 
            if  date == 3 && time >=19.5
                then print "Wrong date and time combination provided, No match after this date and time."
            else 
                if time <0 || time >24
                    then print "Time not given in correct format"
                else findNextFixture tempSchedule date time -- If all checks passed then find next match and print it

-- Function to find next match given date and time
findNextFixture :: [(String, String, Int, Float)] -> Int -> Float -> IO()
findNextFixture (fixture:rest) givenDate givenTime = do
    -- Recursive function 
    -- If current match hass less date or same date but less time then make recursive call 
    -- Else print the current match
    let (team1, team2, date, time) = fixture
    if givenDate < date
        then 
            if time == 9.5
               then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
            else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
    else 
        if givenDate == date && givenTime <time
            then 
                 if time == 9.5
                    then printf "%s vs %s    %d-12-2020  9:30\n" team1 team2 date 
                else printf "%s vs %s    %d-12-2020  7:30\n" team1 team2 date  
        else findNextFixture rest givenDate givenTime