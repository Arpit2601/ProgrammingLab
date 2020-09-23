package Q1;

public class ShelfManager extends Thread {

    private static String Name;
    private static Boolean running = true;
    public ShelfManager(String name)
    {
        Name = name;
    }

    @Override
    public void run() {
        // Shelf manager robot can take any number of pairs from BufferMatchingShelf and put them on the shelf
        while(running)
        {
            for(int i=0;i<4;i++)
            {
                synchronized (Main.BufferMatchingShelfLocks[i])
                {
                    if(Main.BufferMatchingShelf[i]>0)
                    {
                        System.out.println(Name + " placed " + Main.BufferMatchingShelf[i] + " pair(s) of " + Main.GetSockColor(i+1) + " socks on shelf.");

                        Main.BufferMatchingShelf[i]=0;
                        Main.Shelf[i]+=1;
                        Main.CountSocksPlaced+=2;

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
