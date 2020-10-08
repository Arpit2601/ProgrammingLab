package Q1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    // Total number of robotic arms
    private static int NumRobotArms;

    // Respective Number of socks of each color
    private static int NumWhiteSocks;
    private static int NumBlackSocks;
    private static int NumBlueSocks;
    private static int NumGreySocks;

    // Total number of socks
    public static int TotalNumSocks;

    /*
        Two buffers:
        1) BufferSockMatching: between heap and matching machine, robots will place socks here and matching machine will
                               pick them from here to pass to next buffer.
        2) BufferMatchingShelf: between matching machine and shelf manager, matching machine will place a pair of sock here
                                which the shelf manager will pick to place it on Shelf

           BufferSockMatching[i] has number of socks of the corresponding color
           BufferMatchingShelf[i] has number of pairs of the corresponding color
     */
    public static int[] BufferSockMatching = new int[4];
    public static int[] BufferMatchingShelf = new int[4];   // Contains pairs of socks


    // Also has pairs of socks as its count
    public static int[] Shelf = new int[4];

    // Keeps the count of number of socks taken by robots i.e. placed on BufferSockMatching
    // Is is kept as atomic instead of integer as two robots might try to update this variable at same time so to keep consistency
    public static AtomicInteger CountSocksTaken = new AtomicInteger(0);

    // Heap of socks represented as arraylist
    public static ArrayList<Integer> Socks = new ArrayList<>();

    // Binary array to record which sock has already been taken by a robot
    // 0 if no robot has taken this sock else 1
    public static int[] SockTakenByRobot;

    // Locks for each sock so that only one robot can pick a sock at a time
    public static Lock[] SockLocks;

    // Locks for BufferSockMatching so that matching machine and robots can work synchronously
    public static Object[] BufferSockMatchingLocks = new Object[4];

    // Locks for BufferSockMatching so that matching machine and shelf manager can work synchronously
    public static Object[] BufferMatchingShelfLocks = new Object[4];

    // Lock for system out otherwise you might output in wrong order even though processing has been done in right order
    public static final Object OutputLock = new Object();

    // Latch is used to start all the robots and machines at same time
    private static final CountDownLatch latch = new CountDownLatch(1);

    /*
        Takes number of robots as input from command line and heap of socks from file in resources
     */
    public static void main(String[] args) throws InterruptedException{
        if(args.length==0)
        {
            System.out.println("ERROR: Provide the file name as argument.");
            return;
        }
        System.out.println("Taking input as heap form " + args[0] +  "file");
        File file = new File(args[0]);
        Scanner filescanner = null;
        try {
            filescanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return;
        }

        while(filescanner.hasNext())
        {
            try {
                Socks.add(filescanner.nextInt());
            } catch (InputMismatchException e)
            {
                System.out.println("Input file not in correct format (should only have integers).");
                return;
            }
            TotalNumSocks++;
            if(Socks.get(TotalNumSocks-1)<=0 || Socks.get(TotalNumSocks-1)>4)
            {
                System.out.println("Sock colors can be only between 1 and 4 (both included).");
                return;
            }
            if(Socks.get(TotalNumSocks-1)==1)
            {
                NumWhiteSocks++;
            }
            else if(Socks.get(TotalNumSocks-1)==2)
            {
                NumBlackSocks++;
            }
            else if(Socks.get(TotalNumSocks-1)==3)
            {
                NumBlueSocks++;
            }
            else if (Socks.get(TotalNumSocks-1)==4)
            {
                NumGreySocks++;
            }
            else
            {
                System.out.println("Sock of wrong color present in heap!!!.");
                return;
            }
        }
        System.out.println("Heap taken as input.");
        System.out.println("Color coding scheme: 1->White   2->Black    3->Blue 4->Grey");
        System.out.println("Total number of socks in heaps: " + TotalNumSocks);
        System.out.println("Total number of white socks in heap: "+ NumWhiteSocks);
        System.out.println("Total number of black socks in heap: "+ NumBlackSocks);
        System.out.println("Total number of blue socks in heap: "+ NumBlueSocks);
        System.out.println("Total number of grey socks in heap: "+ NumGreySocks);

        Scanner scanner = new Scanner(System.in);
        System.out.println("-----------------Enter the number of robot arms:--------------------");
        try {
            NumRobotArms = scanner.nextInt();
        } catch (InputMismatchException e)
        {
            System.out.println("Input not provided as integer.");
            return;
        }
        if(NumRobotArms<=0)
        {
            System.out.println("Number of Robotic arms has to be greater than 0.");
            return;
        }
        Initialize();
        Start();
    }

    /*
        Initialise various variables
     */
    private static void Initialize()
    {
        // Initialising the locks and SockTaken array
        SockLocks = new Lock[TotalNumSocks];
        SockTakenByRobot = new int[TotalNumSocks];
        for (int i=0;i<TotalNumSocks;i++)
        {
            SockTakenByRobot[i]=0;
            SockLocks[i] = new ReentrantLock();
        }

        // Initialising the two buffer locks
        for(int i=0;i<4;i++)
        {
            BufferSockMatchingLocks[i]=new Object();
        }
        for(int i=0;i<4;i++)
        {
            BufferMatchingShelfLocks[i]=new Object();
        }
    }

    /*
        Making robotic arms, matching machine and shelf manager and starting them
     */
    private static void Start() throws InterruptedException {
        ArrayList<RoboticArm> roboticArmArrayList = new ArrayList<>();
        for(int i=0;i<NumRobotArms;i++)
        {
            RoboticArm roboticArm = new RoboticArm("RobotArm_" + i, latch);
            roboticArmArrayList.add(roboticArm);
            roboticArm.start();
        }


        // Matching machine
        MatchingMachine matchingMachine = new MatchingMachine("Matching_Machine", latch);
        matchingMachine.start();

        // Shelf Manager
        ShelfManager shelfManager = new ShelfManager("Shelf_Manager", latch);
        shelfManager.start();

        // To start all the RobotArms, matching machine and shelf manager at same time
        latch.countDown();

        // Wait for all the robots and machines to stop
        for(int i=0;i<NumRobotArms;i++)
        {
            roboticArmArrayList.get(i).join();
        }
        matchingMachine.join();
        shelfManager.join();
        GenerateStats();
    }

    /*
        Used to sock color corresponding to number
        Different numbers to represent to socks are:
            1->White
            2->Black
            3->Blue
            4->Grey
     */
    public static String GetSockColor(int i)
    {
        if (i == 1)
        {
            return "White";
        }
        else if (i == 2)
        {
            return "Black";
        }
        else if (i == 3)
        {
            return "Blue";
        }
        else return "Grey";
    }

    /*
        Prints number of socks matched, pairs placed in shelf and socks left in buffers (if any)
     */
    private static void GenerateStats()
    {
        int TotalNumberSocksMatched = (Main.Shelf[0] + Main.Shelf[1] + Main.Shelf[2] + Main.Shelf[3])*2;
        System.out.println("--------------------------Statistics--------------------------");

        System.out.println("Total number of socks matched:" + TotalNumberSocksMatched);
        System.out.println("Number of Pairs in Shelf:");
        System.out.println("    White pairs: " + Main.Shelf[0]);
        System.out.println("    Black pairs: " + Main.Shelf[1]);
        System.out.println("    Blue pairs: " + Main.Shelf[2]);
        System.out.println("    Grey pairs: " + Main.Shelf[3]);

        if(TotalNumSocks == TotalNumberSocksMatched)
        {
            System.out.println("Matching machine buffer has no socks left.");
        }
        else
        {
            System.out.println("Matching machine buffer (between robotic arms and machine) has some socks left:");
            System.out.println("    White pairs: " + Main.BufferSockMatching[0]);
            System.out.println("    Black pairs: " + Main.BufferSockMatching[1]);
            System.out.println("    Blue pairs: " + Main.BufferSockMatching[2]);
            System.out.println("    Grey pairs: " + Main.BufferSockMatching[3]);
        }
    }
}
