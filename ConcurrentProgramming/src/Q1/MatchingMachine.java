package Q1;

public class MatchingMachine extends Thread {

    private static Boolean running = true;
    private static String Name;

    public MatchingMachine(String name)
    {
        Name = name;
    }
    @Override
    public void run()
    {
        while(running)
        {
            // Try for each block
            for(int i=0;i<4;i++)
            {
                synchronized (Main.BufferSockMatchingLocks[i])
                {
                    if(Main.BufferSockMatching[i] >= 2)
                    {
                        System.out.println(Name + "removed a pair of socks of color " + Main.GetSockColor(i+1) + " from SockMatching buffer.");
                        Main.BufferSockMatching[i]-=2;
                        synchronized (Main.BufferMatchingShelfLocks[i])
                        {
                            System.out.println(Name + " placed a pair of socks of color " + Main.GetSockColor(i+1) + " on MatchingShelf buffer.");
                            Main.BufferMatchingShelf[i]+=1;
                            // TODO ending condition has to be better as there can be odd no. of socks as well
                            Main.CountSocksMatched+=2;
                        }
                    }
                }
            }



            if(Main.CountSocksMatched == Main.TotalNumSocks)
            {
                Stop();
                System.out.println("Stopping " + Name);
            }
        }
    }

    public static void Stop()
    {
        running = false;
    }

}
