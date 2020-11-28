## Q1) Implement Haskell Functions for Basic Set Operations

To run the program `ghci Q1.hs`

All the sets are taken as list in input

A) Empty set: 

`*Main> isEmpty set`

E.g. isEmpty [1] returns False and isEmpty [] returns True

B) Union:

`*Main> union set1 set2`

E.g. union [1] [2] returns [1,2] and union [] [1,2,3] returns [1,2,3]

C) Intersection:

`*Main> intersection set1 set2`

E.g. intersection [1] [1,2] returns [1] and intersection [] [1,2] returns []

D) Subtraction:

`*Main> subtraction set1 set2`

E.g. subtraction [1] [1] returns [] and subtraction [1,2] [1] returns [2]

E) Addition:

`*Main> addition set1 set2`

E.g. addition [1] [2] returns [3] and addition [1,2] [3,4] returns [4,5,6]

## Q2) IITG Football League

First install random library using following steps:

`sudo apt-get install ghc`

`sudo apt-get install libghc-random-dev`

To run the program use `ghci Q2.hs`

A) To generate all fixtures:

`*Main> fixture "all"`

E.g. 

    CM vs CH    1-12-2020  9:30

    CS vs CV    1-12-2020  7:30

    DS vs BS    2-12-2020  9:30

    EE vs HU    2-12-2020  7:30

    MA vs ME    3-12-2020  9:30

    PH vs ST    3-12-2020  7:30

To get random fixtures run the above step multiple times

B) To get fixture for a particular team:

`*Main> fixture "team_name"`

E.g. `fixture "DS"` 

    DS vs BS    2-12-2020  9:30

C) To get next match:

`*Main> nextMatch date time`

E.g. `nextMatch 1 13.25` will give

    CS vs CV    1-12-2020  7:30

Part B and C will give answers corresponding to latest fixtures generated from Step A

## Q3) House Planner

To run the program use `ghci Q3.hs`

To generate designs use `*Main> design space numBedrooms numHalls`

E.g.1 `design 1000 3 2`  will give

    Bedroom: 3 (10, 10)
    Hall: 2 (15, 10)
    Kitchen: 1 (7, 5)
    Bathroom: 4 (4, 5)
    Garden: 1 (12, 17)
    Balcony: 1 (9, 9)
    Unused Space: 0

E.g.2 `design 10000 12 14` will give

    Bedroom: 12 (15, 15)
    Hall: 14 (20, 15)
    Kitchen: 4 (15, 13)
    Bathroom: 13 (8, 9)
    Garden: 1 (20, 20)
    Balcony: 1 (10, 10)
    Unused Space: 884

Program might about 1 min to run large test cases such as above E.g.2