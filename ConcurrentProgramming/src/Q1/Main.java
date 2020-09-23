package Q1;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static int NumRobotArms;
    private static int NumWhiteSocks;
    private static int NumBlackSocks;
    private static int NumBlueSocks;
    private static int NumGreySocks;
    public static int TotalNumSocks;
    public static int CountSocksTaken;
    public static int CountSocksMatched;
    public static int CountSocksPlaced;
    public static int[] Socks;
    public static int[] SockTakenByRobot;
    // Locks for each sock so that only one robot can pick a sock at a time
    public static Lock[] SockLocks;

    // Two buffers one between robot and matching machine and other between matching machine and shelf manager
    // Each buffer is a array with 4 elements and since we can't lock individual element of array so we have 2 lock array as well
    public static int[] BufferSockMatching = new int[4];
    public static int[] BufferMatchingShelf = new int[4];   // Contains pairs of socks
    public static int[] Shelf = new int[4]; // Also has pairs of socks as its count
    public static Object[] BufferSockMatchingLocks = new Object[4];
    public static Object[] BufferMatchingShelfLocks = new Object[4];

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of robot arms:\n");
        NumRobotArms = scanner.nextInt();
        System.out.println("Enter the number of white socks:\n");
        NumWhiteSocks = scanner.nextInt();
        System.out.println("Enter the number of black socks:\n");
        NumBlackSocks = scanner.nextInt();
        System.out.println("Enter the number of blue socks:\n");
        NumBlueSocks = scanner.nextInt();
        System.out.println("Enter the number of grey socks:\n");
        NumGreySocks = scanner.nextInt();

        Initialize();
        Start();
    }

    public static void Initialize()
    {
        // Initialise the Heap from the given number of socks
        TotalNumSocks = NumBlueSocks+NumGreySocks+NumBlackSocks+NumWhiteSocks;
        Socks = new int[TotalNumSocks];
        int i=0;
        for(;i<NumWhiteSocks;i++){Socks[i]=1;}
        for(;i<NumWhiteSocks+NumBlackSocks;i++){Socks[i]=2;}
        for(;i<NumWhiteSocks+NumBlackSocks+NumBlueSocks;i++){Socks[i]=3;}
        for(;i<TotalNumSocks;i++){Socks[i]=4;}


        // Initialising the locks and SockTaken array
        SockLocks = new Lock[TotalNumSocks];
        SockTakenByRobot = new int[TotalNumSocks];
        for (i=0;i<TotalNumSocks;i++)
        {
            SockTakenByRobot[i]=0;
            SockLocks[i] = new ReentrantLock();
        }

        // Initialising the two buffer locks
        for(i=0;i<4;i++)
        {
            BufferSockMatchingLocks[i]=new Object();
        }
        for(i=0;i<4;i++)
        {
            BufferMatchingShelfLocks[i]=new Object();
        }
    }

    public static void Start() throws InterruptedException {
        // Make NumRobotArms and start them
        for(int i=0;i<NumRobotArms;i++)
        {
            RoboticArm roboticArm = new RoboticArm("RobotArm" + i, latch);
            roboticArm.start();
        }
        // To start all the RobotArms at same time
        latch.countDown();

        // Matching machine
        MatchingMachine matchingMachine = new MatchingMachine("MatchingMachine");
        matchingMachine.start();

        // Shelf Manager
        ShelfManager shelfManager = new ShelfManager("ShelfManager");
        shelfManager.start();

        // Wait shelf and matching machine to stop because robot arms stop before them
        matchingMachine.join();
        shelfManager.join();

        System.out.println("Statistics");



    }

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
}
