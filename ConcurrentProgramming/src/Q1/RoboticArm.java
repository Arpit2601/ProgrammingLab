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

    @Override
    public void run() {
        // Basically pick a sock and pass it to BufferSockMatching
        // Now how to pick a sock
        // Easiest choice is to pick a sock at random and keep on searching until you find one
        // If there are no socks then terminate the thread
        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        Random random = new Random();
        int Index;
        while(running)
        {
            // Pick a sock pass it and update CountSocksTaken
            Index = random.nextInt(Main.TotalNumSocks);
            // Locked -> A robot is currently passing this robot
            if(!Main.SockLocks[Index].tryLock())
            {
                continue;
            }
            else
            {
                // This sock has already been passed
                if(Main.SockTakenByRobot[Index]==1)
                {
                    continue;
                }
                else
                {
                    // Take this sock and pass it
                    // For passing we wait for synchronised block on BufferSockMatching of the corresponding color
                    int i = Main.Socks[Index]-1;
                    System.out.println(Name + " has picked a sock.");
                    synchronized (Main.BufferSockMatchingLocks[i])
                    {
                        Main.BufferSockMatching[i]++;
                        Main.CountSocksTaken++;
                        System.out.println(Name + " has passed sock of color " + Main.GetSockColor(Main.Socks[Index]) + " to Matching machine.");
                        Main.SockTakenByRobot[Index]=1;
                        Main.SockLocks[Index].unlock();
                    }
                }

            }
            if(Main.CountSocksTaken == Main.TotalNumSocks)
            {
                this.Stop();
                System.out.println("Stopping " + Name);
            }
        }

    }

    public void Stop()
    {
        running = false;
    }
}
