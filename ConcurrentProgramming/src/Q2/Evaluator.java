package Q2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class Evaluator extends Thread {

    // Name of the evaluator
    private final String name;

    // This evaluator's buffer
    private ArrayList<ArrayList<String>> myBuffer;

    private final CountDownLatch latch;

    public Evaluator(String name, ArrayList<ArrayList<String>> myBuffer, CountDownLatch latch) {
        this.myBuffer = myBuffer;
        this.name = name;
        this.latch = latch;
    }

    @Override
    public void run()
    {

        while(myBuffer.size()!=0)
        {
            // Get the first element of myBuffer -> then wait for this roll no's lock ->check if you can actually change this data
            // -> change the marks
            ArrayList<String> firstElem = myBuffer.get(0);
            Integer RollNumber = Integer.parseInt(firstElem.get(0));
            Integer MarksToBeUpdatedBy = Integer.parseInt(firstElem.get(1));
            Integer currentMarks = Integer.parseInt(Main.StudentRecords.get(RollNumber).get(2));
            synchronized (Main.StudentRecords.get(RollNumber))
            {
                // CC can change any student's marks, they can be previously updated by TA1 or TA2 also
                if(this.name.equals("CC"))
                {
                    // Change the marks and also the name of evaluator
                    Main.StudentRecords.get(RollNumber).set(2, Integer.toString(currentMarks + MarksToBeUpdatedBy));
                    Main.StudentRecords.get(RollNumber).set(3, this.name);
                    System.out.println("INFO: " + this.name + " updated " + RollNumber + "'s marks to " + (currentMarks + MarksToBeUpdatedBy));
                }
                // TA cannot change marks updated by CC
                else
                {
                    if(Main.StudentRecords.get(RollNumber).get(3).equals("CC"))
                    {
                        // Cannot change this data so do nothing
                        myBuffer.remove(0);
                        System.out.println("INFO: " + this.name + " cannot " + RollNumber + "'s marks as they are already updated by CC.");
                        continue;
                    }
                    else
                    {
                        // Change the marks and also the Name of evaluator
                        Main.StudentRecords.get(RollNumber).set(2, Integer.toString(currentMarks + MarksToBeUpdatedBy));
                        Main.StudentRecords.get(RollNumber).set(3, this.name);
                        System.out.println("INFO: " + this.name + " updated " + RollNumber + "'s marks to " + (currentMarks + MarksToBeUpdatedBy));
                    }
                }
                myBuffer.remove(0);

            }
        }
    }
}
