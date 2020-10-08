package Q3.a;

import java.awt.*;

public class HighlightNumPad extends Thread{

    /*
        This thread is used for highlighting number pad
    */
    @Override
    public void run() {
        int i=0;
        while(true)
        {
            // If currently FunctionPad is getting highlighted then this thread will wait
            // This thread will resume only after getting notify() from Interface class
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

            // Setting the appropriate colors for buttons
            if(i==0)
            {
                Interface.numPadButtons[9].setBackground(Interface.numPadButtons[i].getBackground());
            }
            else
            {
                Interface.numPadButtons[i-1].setBackground(Interface.numPadButtons[i].getBackground());
            }
            Interface.numPadButtons[i].setBackground(Color.GREEN);
            try
            {
                // Wait for 1.5 sec before changing the background color of button
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
