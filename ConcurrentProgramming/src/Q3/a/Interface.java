package Q3.a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Interface {

    static JFrame jFrame;
    static JTextArea jTextArea;
    static JPanel numPad, functionPad;
    static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, add, sub, mul, div, stop, clear;
    static JButton[] numPadButtons = new JButton[10];
    static JButton[] functionPadButtons = new JButton[6];
    public  static void AddComponents()
    {
        jFrame = new JFrame("Calculator");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300, 500);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        numPad = new JPanel();
        functionPad = new JPanel();

        jTextArea = new JTextArea();
        jTextArea.setCaretColor(Color.WHITE);
        jTextArea.setPreferredSize(new Dimension(300, 100));
        Font font = jTextArea.getFont();
        float size = font.getSize() + 5.0f;
        jTextArea.setFont(font.deriveFont(size));
        jFrame.add(jTextArea, BorderLayout.NORTH);

        b0 = new JButton("0");numPadButtons[0]=b0;
        b1 = new JButton("1");numPadButtons[1]=b1;
        b2 = new JButton("2");numPadButtons[2]=b2;
        b3 = new JButton("3");numPadButtons[3]=b3;
        b4 = new JButton("4");numPadButtons[4]=b4;
        b5 = new JButton("5");numPadButtons[5]=b5;
        b6 = new JButton("6");numPadButtons[6]=b6;
        b7 = new JButton("7");numPadButtons[7]=b7;
        b8 = new JButton("8");numPadButtons[8]=b8;
        b9 = new JButton("9");numPadButtons[9]=b9;

        

        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if(key == KeyEvent.VK_ENTER && Main.Pad == 0)
                {

                    // Check which numpad button has green background color
                    for(int i=0;i<10;i++)
                    {
                        if(numPadButtons[i].getBackground() == Color.GREEN)
                        {
                            Calculate.display(numPadButtons[i].getText());
                            break;
                        }
                    }
                    Main.Pad = 1;
                    synchronized (Main.FunctionPadLock){
                        Main.FunctionPadLock.notify();
                    }

                }
                else if(key == KeyEvent.VK_ENTER && Main.Pad == 1)
                {
                    String buttonText="";
                    // Check which function button has orange background color
                    for(int i=0;i<6;i++)
                    {
                        if(functionPadButtons[i].getBackground() == Color.ORANGE)
                        {
                            buttonText = functionPadButtons[i].getText();
                            Calculate.display(functionPadButtons[i].getText());
                            break;
                        }
                    }
                    // If button clicked was Stop then keep highlighting functionPad (for Clear) else start Numpad

                    if(!buttonText.equals("Stop"))
                    {
                        Main.Pad = 0;
                        synchronized (Main.NumPadLock) {
                            Main.NumPadLock.notify();
                        }
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        for(int i=0;i<10;i++)
        {
            numPadButtons[i].setBackground(Color.GRAY);
            numPad.add(numPadButtons[i]);
        }
        numPad.setLayout(new GridLayout(4, 3));
        jFrame.add(numPad, BorderLayout.CENTER);

        add = new JButton("+");functionPadButtons[0]=add;
        sub = new JButton("-");functionPadButtons[1]=sub;
        mul = new JButton("*");functionPadButtons[2]=mul;
        div = new JButton("/");functionPadButtons[3]=div;
        stop = new JButton("Stop");functionPadButtons[4]=stop;
        clear = new JButton("Clear");functionPadButtons[5]=clear;

        for(int i=0;i<6;i++)
        {
            functionPadButtons[i].setBackground(Color.GRAY);
            functionPad.add(functionPadButtons[i]);
        }


        functionPad.setLayout(new GridLayout(3, 2));
        jFrame.add(functionPad, BorderLayout.SOUTH);

        jFrame.setFocusable(true);
        jFrame.setVisible(true);
    }

    static void CreateInterface()
    {
        AddComponents();
    }

}
