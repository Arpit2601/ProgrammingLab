package Q3;


public class Main {

    public static void main(String[] args)
    {
        Interface.CreateInterface();
        HighlightNumPad highlightNumPad = new HighlightNumPad();
        highlightNumPad.start();
        HighlightFunctionPad highlightFunctionPad = new HighlightFunctionPad();
        highlightFunctionPad.start();
    }

}
