package Q1;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RoboticArm extends Thread {

    private static Boolean running = true;
    private final String Name;
    private static CountDownLatch latch;

    public RoboticArm(String name, CountDownLatch latch)
    {
        Name = name;
        RoboticArm.latch = latch;
    }

    /*
       First try to pick a sock at random. If lock is available and no one has this sock then pass it to next buffer.
       Else keep on trying until you find a sock or all the socks have been taken (check using CountSocksTaken)
    */

    @Override
    public void run() {

        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println(Name + " started.");
        Random random = new Random();
        int Index;

        while(running)
        {
            // Generate a random index to pick a sock from
            Index = random.nextInt(Main.TotalNumSocks);

            // If lock is available then trylock will return true else false
            if(!Main.SockLocks[Index].tryLock())
            {
                continue;
            }
            else
            {
                // Now we have the lock for this index but someone has already taken this sock so continue
                if(Main.SockTakenByRobot[Index]==1)
                {
                    continue;
                }
                else
                {
                    // Take this sock and pass it
                    // For passing we wait for synchronised block on BufferSockMatching of the corresponding color

                    // i is the index in buffer we have to place it
                    int i = Main.Socks.get(Index) -1;
                    synchronized (Main.OutputLock)
                    {
                        System.out.println(Name + " : picked a sock of color " + Main.GetSockColor(Main.Socks.get(Index)) + ".");
                    }
                    synchronized (Main.BufferSockMatchingLocks[i])
                    {
                        // We now  have the permission to update BufferSockMatching
                        synchronized (Main.OutputLock){System.out.println(Name + " : passed sock of color " + Main.GetSockColor(Main.Socks.get(Index)) + " to Matching machine.");}
                        Main.BufferSockMatching[i]++;
                        Main.CountSocksTaken.incrementAndGet();
                        Main.SockTakenByRobot[Index]=1;
                        Main.SockLocks[Index].unlock();
                    }
                }

            }
            // Ending condition
            if(Main.CountSocksTaken.get() == Main.TotalNumSocks)
            {
                this.Stop();
                synchronized (Main.OutputLock){System.out.println("Stopping " + Name);}
            }
        }

    }

    private void Stop()
    {
        running = false;
    }
}
