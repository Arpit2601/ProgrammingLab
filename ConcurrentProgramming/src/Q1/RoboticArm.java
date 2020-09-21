package Q1;

import java.util.Random;

public class RoboticArm extends Thread {

    private static Boolean running = true;
    private static String Name;

    public RoboticArm(String name)
    {
        Name = name;
    }

    @Override
    public void run() {
        // Basically pick a sock and pass it to BufferSockMatching
        // Now how to pick a sock
        // Easiest choice is to pick a sock at random and keep on searching until you find one
        // If there are no socks then terminate the thread
        Random random = new Random();
        int Index;
        while(running)
        {
            // Pick a sock pass it and update CountSocksTaken
            Index = random.nextInt(Main.TotalNumSocks);
            // If locked then this sock has been taken continue
            if(Main.SockLocks[Index].tryLock())
            {
                continue;
            }
            else
            {
                // Take this sock and pass it
                // For passing we wait for synchronised block on BufferSockMatching of the corresponding color
                int i = Main.Socks[Index]-1;
                System.out.println(Name + " has picked a sock.");
                synchronized (Main.BufferSockMatchingLocks[i-1])
                {
                    Main.BufferSockMatching[i]++;
                    Main.CountSocksTaken++;
                    System.out.println(Name + " has passed sock of color" + Main.GetSockColor(Main.Socks[Index]) + "to Matching machine.");
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
