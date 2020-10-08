package Q3.b;


public class Main {

    public static void main(String[] args)
    {
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
