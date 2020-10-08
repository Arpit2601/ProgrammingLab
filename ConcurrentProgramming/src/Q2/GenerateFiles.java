package Q2;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GenerateFiles {

    /*
        Contains the sorted records of students
        SortedByRollRecords: has records of form <Roll No., Name, email, marks, updated by>
        SortedByNameRecords: has records of form <Name, Roll No., email, marks, updated by>
     */
    private static ArrayList<ArrayList<String>> SortedByRollRecords = new ArrayList<>(),
                                                SortedByNameRecords = new ArrayList<>()  ;

    /*
        Function to sort the records by Roll No.
     */
    public static void SortByRoll()
    {
        SortedByRollRecords.clear();
        // First convert StudentRecords which is a hashmap into an arraylist then sort it
        for(HashMap.Entry<Integer, ArrayList<String>> entry: Main.StudentRecords.entrySet())
        {
            SortedByRollRecords.add(
                    new ArrayList<String>(Arrays.asList(entry.getKey().toString(),
                            entry.getValue().get(0), entry.getValue().get(1),
                            entry.getValue().get(2),entry.getValue().get(3))));

        }

        // Now sort this arraylist
        SortedByRollRecords.sort(new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> a, ArrayList<String> b) {
                return a.get(0).compareTo(b.get(0));
            }
        });

        // Now generate the file
        MakeFile("Sorted_Roll.txt");
    }

    /*
        Function to sort the records by Name
     */
    public static void SortByName()
    {
        SortedByNameRecords.clear();
        for(HashMap.Entry<Integer, ArrayList<String>> entry: Main.StudentRecords.entrySet())
        {
            SortedByNameRecords.add(
                    new ArrayList<String>(Arrays.asList(entry.getValue().get(0),
                            entry.getKey().toString(),entry.getValue().get(1),
                            entry.getValue().get(2),entry.getValue().get(3))));

        }

        SortedByNameRecords.sort(new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> a, ArrayList<String> b) {
                    return a.get(0).compareTo(b.get(0));
                }
        });
        MakeFile("Sorted_Name.txt");
    }

    // Function to update stud info file after batch update
    public static void UpdateStudInfoFile()
    {
        MakeFile("Stud_Info.txt");
    }


    // Generic function to generate the Sorted_roll and Sorted_Name files.
    public static void MakeFile(String filename)
    {

        File file = new File("../../resources/Q2/" + filename);
        try
        {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            if(file.exists())
            {
                writer.write("");
            }
            else
            {
                file.createNewFile();
            }

            // Now enter data for each entry depending choosing arraylist depending on the name of file
            if(filename.equals("Sorted_Roll.txt"))
            {
                for(ArrayList<String> entry: SortedByRollRecords)
                {
                    writer.append(entry.get(0));
                    writer.append(",");
                    writer.append(entry.get(1));
                    writer.append(",");
                    writer.append(entry.get(2));
                    writer.append(",");
                    writer.append(entry.get(3));
                    writer.append(",");
                    writer.append(entry.get(4));
                    writer.append("\n");
                }
                System.out.println("INFO: Sorted_Roll.txt has been written.");
            }
            else if(filename.equals("Sorted_Name.txt"))
            {
                for(ArrayList<String> entry: SortedByNameRecords)
                {
                    writer.append(entry.get(0));
                    writer.append(",");
                    writer.append(entry.get(1));
                    writer.append(",");
                    writer.append(entry.get(2));
                    writer.append(",");
                    writer.append(entry.get(3));
                    writer.append(",");
                    writer.append(entry.get(4));
                    writer.append("\n");
                }
                System.out.println("INFO: Sorted_Name.txt has been written.");
            }
            else if(filename.equals("Stud_Info.txt"))
            {
                for(Map.Entry<Integer, ArrayList<String>> entry: Main.StudentRecords.entrySet())
                {
                    writer.append(entry.getKey().toString());
                    writer.append(",");
                    writer.append(entry.getValue().get(0));
                    writer.append(",");
                    writer.append(entry.getValue().get(1));
                    writer.append(",");
                    writer.append(entry.getValue().get(2));
                    writer.append(",");
                    writer.append(entry.getValue().get(3));
                    writer.append("\n");
                }
                System.out.println("INFO: Stud_Info.txt has been updated.");
            }
            writer.flush();
            writer.close();


        }
        catch (IOException e)
        {
            System.out.println("ERROR: File not found.");
        }

    }
}
