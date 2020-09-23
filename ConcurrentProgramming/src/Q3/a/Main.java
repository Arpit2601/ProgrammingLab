package Q3.a;

public class Main {

    public static int Pad;
    public static final Object NumPadLock = new Object();
    public static final Object FunctionPadLock = new Object();
    public static void main(String[] args)
    {
        // 0 means numpad is getting highlighted and 1 means functionpad is getting highlighted
        Pad = 0;
        Interface.CreateInterface();
        HighlightNumPad highlightNumPad = new HighlightNumPad();
        highlightNumPad.start();
        HighlightFunctionPad highlightFunctionPad = new HighlightFunctionPad();
        highlightFunctionPad.start();
    }

}
