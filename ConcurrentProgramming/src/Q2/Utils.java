package Q2;

import java.util.*;

public class Utils {

    private static final Scanner scanner = new Scanner(System.in);

    /*
        Function allows you to take evaluator's name, roll no and marks to be updated by as input
        And then add these fields in UpdationBuffer
     */
    public static void updateMarks()
    {
        String evaluatorName = GetEvaluatorName();
        Integer rollNo = GetRollNumber();
        Integer updateMarksBy = GetUpdateMarksBy(rollNo);

        // Enter this data in Updation buffer to be processed simultaneously later
        Main.UpdationBuffer.add(new ArrayList<>(Arrays.asList(rollNo.toString(), updateMarksBy.toString(), evaluatorName)));
    }

    // Get the evaluator's name
    public static String GetEvaluatorName()
    {
        while(true)
        {
            System.out.println("ACTION: Enter evaluator's name (CC/TA1/TA2):");
            String evaluatorName = scanner.next();
            if(evaluatorName.equals("CC") || evaluatorName.equals("TA1") || evaluatorName.equals("TA2"))
            {
                return evaluatorName;
            }
            else
            {
                System.out.println("INVALID INPUT: Enter valid evaluator i.e. one of CC/TA1/TA2.");
            }
        }

    }

    public static Integer GetRollNumber()
    {
        System.out.println("ACTION: Enter Roll number of student whose marks are to be updated.");
        while(true)
        {
            while(!scanner.hasNextInt())
            {
                System.out.println("INVALID INPUT: Roll no. has to be a integer. Enter valid Roll No.");
                scanner.next();
            }

            Integer rollNumber = scanner.nextInt();
            if(!Main.StudentRecords.containsKey(rollNumber))
            {
                System.out.println("INVALID INPUT: " + rollNumber + " not present in data. Enter valid roll no.");
            }
            else
            {
                return rollNumber;
            }
        }
    }

    public static Integer GetUpdateMarksBy(Integer rollNumber)
    {
        int currentMarks = Integer.parseInt(Main.StudentRecords.get(rollNumber).get(2));
        System.out.println("Current marks of this student are: " + currentMarks);
        System.out.println("ACTION: Enter by how much you want to update the marks (to decrease enter negative number):");

        while(!scanner.hasNextInt())
        {
            System.out.println("INVALID INPUT: marks has to be an integer");
            scanner.next();
        }
        Integer marksToBeUpdatedBy = scanner.nextInt();
        return marksToBeUpdatedBy;

    }

    /*
        Distribute the updation buffer into 3 categories on the basis of evaluator type and start the three threads
        Since CC requires more priority it has been given so but still to be sure we first wait for CC to finish its execution
        because  just setting priority to MAX does not work
        Ref: (https://stackoverflow.com/questions/29374755/thread-execution-ordering-by-setting-priority)
     */
    public static void performUpdates()
    {
        // Distribute the updation buffer into individual buffers of each evaluator
        // Each buffer is of form: Roll No. --> update marks
        ArrayList<ArrayList<String>> CCBuffer = new ArrayList<>(), TA1Buffer = new ArrayList<>(), TA2Buffer = new ArrayList<>();
        for (ArrayList<String> entry: Main.UpdationBuffer)
        {
            switch (entry.get(2)) {
                case "CC" -> CCBuffer.add(new ArrayList<>(Arrays.asList(entry.get(0), entry.get(1))));
                case "TA1" -> TA1Buffer.add(new ArrayList<>(Arrays.asList(entry.get(0), entry.get(1))));
                case "TA2" -> TA2Buffer.add(new ArrayList<>(Arrays.asList(entry.get(0), entry.get(1))));
            }
        }

        // Make three evaluators as threads and start them
        Thread CC = new Evaluator("CC", CCBuffer, Main.latch);
        Thread TA1 = new Evaluator("TA1", TA1Buffer, Main.latch);
        Thread TA2 = new Evaluator("TA2", TA2Buffer, Main.latch);

        // Setting the priorities for threads
        CC.setPriority(10); // MAX_PRIORITY
        TA1.setPriority(5); // Same priority as that of TA2
        TA2.setPriority(5);

        System.out.println("##############################################");
        CC.start();
        try {
            CC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TA1.start();
        TA2.start();

        // Wait for all TAs to finish
        try {
            TA1.join();
            TA2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GenerateFiles.UpdateStudInfoFile();
        GenerateFiles.SortByName();
        GenerateFiles.SortByRoll();
        System.out.println("##############################################");

        // After processing this batch empty the updation buffer
        Main.UpdationBuffer.clear();
    }

}
