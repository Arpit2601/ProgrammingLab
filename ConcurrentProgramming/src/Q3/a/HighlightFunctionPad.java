package Q3.a;

import java.awt.*;

public class HighlightFunctionPad extends Thread{

    /*
        This thread is used to highlight function pad
    */
    @Override
    public void run() {
        int i=0;
        while(true)
        {
            // If currently NumPad is getting highlighted then this thread will wait
            // This thread will resume only after getting notify() from Interface class
            if(Main.Pad == 0)
            {
                synchronized (Main.FunctionPadLock)
                {
                    try {
                        Main.FunctionPadLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Setting the appropriate colors for buttons
            if(i==0)
            {
                Interface.functionPadButtons[5].setBackground(Interface.functionPadButtons[i].getBackground());
            }
            else
            {
                Interface.functionPadButtons[i-1].setBackground(Interface.functionPadButtons[(i)].getBackground());
            }
            Interface.functionPadButtons[i].setBackground(Color.ORANGE);
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
            i = i%6;
        }
    }
}
