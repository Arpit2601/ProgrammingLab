package Q1;

import java.util.concurrent.CountDownLatch;

public class ShelfManager extends Thread {

    private static String Name;
    private static Boolean running = true;
    private static CountDownLatch latch;
    private static Boolean StoppingCondition = false;

    public ShelfManager(String name, CountDownLatch latch)
    {
        Name = name;
        ShelfManager.latch = latch;
    }

    /*
        Basically removes all pair of socks (of same color) from BufferMatchingShelf (if available) and passes them to Shelf
     */
    @Override
    public void run() {
        // Shelf manager robot can take any number of pairs from BufferMatchingShelf and put them on the shelf
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
                Try to acquire lock for each color, if acquired then pass all the pairs to shelf
                to next buffer (again after acquiring lock)
             */
            for(int i=0;i<4;i++)
            {
                synchronized (Main.BufferMatchingShelfLocks[i])
                {
                    if(Main.BufferMatchingShelf[i]>0)
                    {
                        synchronized (Main.OutputLock){System.out.println(Name + " : placed " + Main.BufferMatchingShelf[i] + " pair(s) of " + Main.GetSockColor(i+1) + " socks on shelf.");}
                        Main.Shelf[i]+=Main.BufferMatchingShelf[i];
                        Main.BufferMatchingShelf[i]=0;

                    }
                }
                CheckStoppingCondition();

            }

            if(StoppingCondition)
            {
                Stop();
                System.out.println("Stopping " + Name);
            }
        }
    }

    // Stop when matching machine has placed all the socks in shelf manager i.e. it has stopped
    // and BufferMatchingShelf is empty i.e. shelf manager has done its work
    private void CheckStoppingCondition()
    {
        boolean flag = true;
        if(MatchingMachine.getStoppingCondition())
        {
            for(int i=0;i<4;i++)
            {
                if(Main.BufferMatchingShelf[i]>0){flag=false;break;}
            }
            if(flag)
            {
                StoppingCondition = true;
            }
        }
    }

    private static void Stop()
    {
        running = false;
    }
}
