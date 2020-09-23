package Q3.a;

import java.awt.*;

public class HighlightFunctionPad extends Thread{

    @Override
    public void run() {
        int i=0;
        while(true)
        {
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
            if(i==0)
            {
                Interface.functionPadButtons[5].setBackground(Color.GRAY);
            }
            else
            {
                Interface.functionPadButtons[i-1].setBackground(Color.GRAY);
            }
            Interface.functionPadButtons[i].setBackground(Color.ORANGE);
            try
            {
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
