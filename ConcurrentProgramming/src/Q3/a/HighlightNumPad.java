package Q3.a;

import java.awt.*;

public class HighlightNumPad extends Thread{

    @Override
    public void run() {
        int i=0;
        while(true)
        {
            if(Main.Pad == 1)
            {
                synchronized (Main.NumPadLock)
                {
                    try {
                        Main.NumPadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(i==0)
            {
                Interface.numPadButtons[9].setBackground(Color.GRAY);
            }
            else
            {
                Interface.numPadButtons[i-1].setBackground(Color.GRAY);
            }
            Interface.numPadButtons[i].setBackground(Color.GREEN);
            try
            {
                Thread.sleep(1500);
            }
            catch (InterruptedException e)
            {
                System.out.println("Thread exception" + e);
            }
            i++;
            i = i%10;
        }
    }
}
