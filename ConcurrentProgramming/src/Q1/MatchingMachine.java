package Q1;

import java.util.concurrent.CountDownLatch;

public class MatchingMachine extends Thread {

    private static Boolean running = true;
    private static String Name;
    private static CountDownLatch latch;
    private static Boolean StoppingCondition = false;

    public MatchingMachine(String name, CountDownLatch latch)
    {
        Name = name;
        MatchingMachine.latch = latch;
    }

    /*
        Basically removes two socks (of same color) from BufferSockMatching (if available) and passes them to BufferMatchingShelf
     */
    @Override
    public void run()
    {
        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Name + " started.");

        while(running)
        {
            /*
                Try to acquire lock for each color, if acquired then if there are two socks of this color then pass them
                to next buffer (again after acquiring lock)
             */
            for(int i=0;i<4;i++)
            {
                synchronized (Main.BufferSockMatchingLocks[i])
                {
                    if(Main.BufferSockMatching[i] >= 2)
                    {
                        Main.BufferSockMatching[i]-=2;
                        synchronized (Main.BufferMatchingShelfLocks[i])
                        {
                            synchronized (Main.OutputLock){System.out.println(Name + " : placed a pair of socks of color " + Main.GetSockColor(i+1) + " on MatchingShelf buffer.");}
                            Main.BufferMatchingShelf[i]+=1;
                        }
                    }
                    // Check for stopping condition after every match
                    CheckStoppingCondition();
                }
            }

            if(StoppingCondition)
            {
                Stop();
                synchronized (Main.OutputLock){System.out.println("Stopping " + Name);}
            }
        }
    }

    private static void Stop()
    {
        running = false;
    }

    /*
        Stopping condition is when robots have passed all the socks to matching machine and this machine has less
        than 2 socks left in every container (of each color)
    */
    private static void CheckStoppingCondition()
    {
        boolean flag = true;
        if(Main.CountSocksTaken.get() == Main.TotalNumSocks)
        {
            for(int i=0;i<4;i++)
            {
                if(Main.BufferSockMatching[i]>=2){flag=false;break;}
            }
            if(flag)
            {
                StoppingCondition = true;
            }
        }
    }

    public static Boolean getStoppingCondition()
    {
        return StoppingCondition;
    }

}
