package Q2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Main {

    // To start the evaluators (CC/TA1/TA2) all the same time to show concurrency
    public static final CountDownLatch latch = new CountDownLatch(1);

    // Records of type: Roll No. --> <Name, email, marks, updated by>
    public static HashMap<Integer, ArrayList<String>> StudentRecords = new HashMap<>();

    // Each Record of type: <Roll No., marks, updated by>
    // This cannot be hashmap because same teachers may want to update marks of same student multiple times
    // The records basically represent the updates that evaluators might have made at the same time
    // Thus we will be able to show concurrency
    public static ArrayList<ArrayList<String>> UpdationBuffer = new ArrayList<>();

    public static void main(String[] args)
    {
        // Read the current Stud_info.txt file
        ReadStudInfo();

        Scanner scanner = new Scanner(System.in);
        while(true)
        {

            System.out.println("\n------------Choose one of the options:--------------\n" + "1) Update marks\n" + "2) Perform updates\n" +
                      "3) Exit");
            int option = scanner.nextInt();
            if(option == 1)
            {
                Utils.updateMarks();
            }
            else if(option == 2)
            {
                Utils.performUpdates();
            }
            else if(option == 3)
            {
               return;
            }
            else
            {
                System.out.println("INVALID INPUT: Try again.");
            }
        }
    }

    private static void ReadStudInfo() {

        File file = new File("../../resources/Q2/Stud_Info.txt");
        try {
            Scanner scannerFile = new Scanner(file);
            // Each line of format: Roll No,name,email,marks,updated by
            while(scannerFile.hasNextLine())
            {
                String studentRecord = scannerFile.nextLine();
                String[] record = studentRecord.split(",");
                ArrayList<String> data = new ArrayList<>(Arrays.asList(record[1], record[2], record[3], record[4]));
                StudentRecords.put(Integer.parseInt(record[0]), data);
            }
        } catch (Exception e)
        {
            System.out.println("ERROR:Student info file not found.");
            return;
        }

    }
}

