package Q3.a;

public class Main {

    /*
        Pad: indicates which Pad is currently active i.e. Numpad (0) or FunctionPad (1)
        NumPadLock: used as a lock in synchronised block to start and stop numpad
        FunctionPadLock: used as a lock in synchronised block to start and stop numpad
     */

    public static int Pad;
    public static final Object NumPadLock = new Object();
    public static final Object FunctionPadLock = new Object();

    public static void main(String[] args)
    {
        // Initially numpad will start
        Pad = 0;

        /*
            Create the interface and start highlighting of numpad and function pad as separate threads
         */
        Interface.CreateInterface();
        HighlightNumPad highlightNumPad = new HighlightNumPad();
        highlightNumPad.start();
        HighlightFunctionPad highlightFunctionPad = new HighlightFunctionPad();
        highlightFunctionPad.start();
    }

}
