package Q1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;

public class Main {

    private static int NumRobotArms;
    private static int NumWhiteSocks;
    private static int NumBlackSocks;
    private static int NumBlueSocks;
    private static int NumGreySocks;
    public static int TotalNumSocks;
    public static int CountSocksTaken;
    public static int CountSocksMatched;
    public static int[] Socks;
    // Locks for each sock so that only one robot can pick a sock at a time
    public static Lock[] SockLocks;

    // Two buffers one between robot and matching machine and other between matching machine and shelf manager
    // Each buffer is a array with 4 elements and since we can't lock individual element of array so we have 2 lock array as well
    public static int[] BufferSockMatching = new int[4];
    public static int[] BufferMatchingShelf = new int[4];
    public static final Object[] BufferSockMatchingLocks = new Object[4];
    public static final Object[] BufferMatchingShelfLocks = new Object[4];

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of robot arms:\n");
        NumRobotArms = scanner.nextInt();
        System.out.println("Enter the number of white arms:\n");
        NumWhiteSocks = scanner.nextInt();
        System.out.println("Enter the number of black arms:\n");
        NumBlackSocks = scanner.nextInt();
        System.out.println("Enter the number of blue arms:\n");
        NumBlueSocks = scanner.nextInt();
        System.out.println("Enter the number of grey arms:\n");
        NumGreySocks = scanner.nextInt();

        Initialize();
        Start();
    }

    public static void Initialize()
    {
        // Initialise the Heap from the given number of socks
        TotalNumSocks = NumBlueSocks+NumGreySocks+NumBlackSocks+NumWhiteSocks;
        Socks = new int[TotalNumSocks];
        for(int i=0;i<TotalNumSocks;i++)
        {
            Socks[i]=(i%4)+1;
        }

        // Initialising the locks array
        SockLocks = new Lock[TotalNumSocks];

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

    public static void Start()
    {
        // Make NumRobotArms and start them
        for(int i=0;i<NumRobotArms;i++)
        {
            RoboticArm roboticArm = new RoboticArm("RobotArm" + Integer.toString(i));
            roboticArm.start();
        }

        // Matching machine
        MatchingMachine matchingMachine = new MatchingMachine("MatchingMachine");
        matchingMachine.start();

        // Shelf Manager
        ShelfManager shelfManager = new ShelfManager("ShelfManager");
        shelfManager.start();
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
