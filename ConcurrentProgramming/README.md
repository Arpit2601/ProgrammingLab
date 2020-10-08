All the commands are given from main directory of the submission

The source code is compiled used java 14 (Openjdk 14.0.1). You can check the version using:

`$java --version`

**1) Sock Matching Robot:**

To execute the program run these commands from terminal

`$cd src/Q1`

`$javac *.java -d .`

`$java Q1.Main ../../resources/Q1/Input.txt`

- After this program will take the heap from the specified file (given as argument), and you will be prompted to enter number of robotic arms, then press enter.
- The specified file has only a sequence of numbers where each number corresponds to a sock of appropriate color.
- 1, 2, 3 and 4 indicate White, Black, Blue and Grey color sock respectively.


****

**2) Data Modification in Distributed System:**


To execute the program run these commands from terminal

`$cd src/Q2`

`$javac *.java -d .`

`$java Q2.Main`

After this follow along the instructions from terminal:

Meaning of the commands that appear and steps to follow are:

-  "1) Update marks" -> use it to provide the updates you want to make. Since we provide updates in batch format keep on selecting this option to add more updates to the current batch.
-   After this asked to enter the name of evaluator which can be only CC/TA1/TA2, if you enter something else program will ask you again to enter right name.
-   Next step is to enter the roll no. of student whose marks the specified evaluator wants to update. If roll is wrong or not present in Stud_Info.txt then you will be asked to enter it again.
-   Next enter by how much you want to update this student's marks.
-   Since we have to show concurrency, we take updates in batches and then execute concurrently so if you want to add another update to this batch select option 1 and follow along.
-   If you want to execute all the updates you provided select option 2.
-   To exit select option 3.


Here is sample output and input format if CC wants to update 174101035's marks by 5 and TA1 wants to decrease this student's marks by 2 at the same time.


        ------------Choose one of the options:--------------
        1) Update marks
        2) Perform updates
        3) Exit
        1
        ACTION: Enter evaluator's name (CC/TA1/TA2):
        CC
        ACTION: Enter Roll number of student whose marks are to be updated.
        174101035
        Current marks of this student are: 109
        ACTION: Enter by how much you want to update the marks (to decrease enter negative number):
        5
        
        ------------Choose one of the options:--------------
        1) Update marks
        2) Perform updates
        3) Exit
        1
        ACTION: Enter evaluator's name (CC/TA1/TA2):
        TA1
        ACTION: Enter Roll number of student whose marks are to be updated.
        174101035
        Current marks of this student are: 109
        ACTION: Enter by how much you want to update the marks (to decrease enter negative number):
        -2
        
        ------------Choose one of the options:--------------
        1) Update marks
        2) Perform updates
        3) Exit
        2
        ##############################################
        INFO: CC updated 174101035's marks to 114
        INFO: TA1 cannot 174101035's marks as they are already updated by CC.
        INFO: Stud_Info.txt has been updated.
        INFO: Sorted_Name.txt has been written.
        INFO: Sorted_Roll.txt has been written.
        ##############################################
        
        ------------Choose one of the options:--------------
        1) Update marks
        2) Perform updates
        3) Exit
        3


You can view the Sorted_Roll.txt and Sorted_Name.txt files using (Assuming are in the main directory of submission)

`$cd resources/Q2`

`$cat Sorted_Roll.txt`

`$cat Sorted_Name.txt`
****

**3) Calculator for Differently Abled Persons:**

- *a) Using only ENTER KEY:*

     Run these commands from terminal
    
    `$cd src/Q3/a`
    
    `$javac *.java -d .`
    
    `$java Q3.a.Main`
    
    Use ENTER key to select number or function. The two portions 
    (number pad and function pad) will be alternatively highlighted.
    
    
- *b) Using ENTER AND SPACE keys:*
    
    Run these commands from terminal
    
    `$cd src/Q3/b`
    
    `$javac *.java -d .`
    
    `$java Q3.b.Main`
    
    Use ENTER key to select number and SPACE key to select function. The two portions 
    (number pad and function pad) will be simultaneously highlighted.

- For both the calculators use 'Stop' key to evaluate the expression.
- To move on to the next expression first 'Clear' the screen.
- If after pressing 'Stop' you press anything other than 'Clear' it will be neglected.
