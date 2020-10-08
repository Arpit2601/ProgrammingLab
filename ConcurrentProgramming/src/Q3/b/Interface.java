package Q3.b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Interface {

    /*
        jFrame: contains all the components of UI
        jTextArea: used to display queries and their answers
        JPanel: contains buttons for numbers and functions separately
     */

    static JFrame jFrame;
    static JTextArea jTextArea;
    static JPanel numPad, functionPad;
    static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, add, sub, mul, div, stop, clear;
    static JButton[] numPadButtons = new JButton[10];
    static JButton[] functionPadButtons = new JButton[6];

    // Function to add components to the window
    public  static void AddComponents()
    {
         /*
               Initial code for window i.e. setting is size, name and exit by clicking on x
         */

        jFrame = new JFrame("Calculator");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300, 500);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        numPad = new JPanel();
        functionPad = new JPanel();

        /*
            Setting the position, color, font size and text area size.
         */
        jTextArea = new JTextArea();
        jTextArea.setCaretColor(Color.WHITE);
        jTextArea.setPreferredSize(new Dimension(300, 100));
        Font font = jTextArea.getFont();
        float size = font.getSize() + 5.0f;
        jTextArea.setFont(font.deriveFont(size));
        jTextArea.setLineWrap(true);
        jFrame.add(jTextArea, BorderLayout.NORTH);  // Placing the text area on the top of window

        /*
            Making buttons for numpad and placing them in numPadButtons array
         */
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

        
        /*
            Key event listener for whole window
        */
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            /*
                On key pressed:
                    1) if Enter key is pressed and currently numpad is active:
                        a) check which numpad button has green color i.e. is highlighted
                        b) send this number to Calculate class which will display it and do further computation

                     2) if Space key is pressed and currently functionpad is active:
                        a) check which functionPad button has orange color i.e. is highlighted
                        b) send this function to Calculate class which will display it and do further computation

            */
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int key = keyEvent.getKeyCode();
                if(key == KeyEvent.VK_ENTER)
                {
                    // Check which numpad button has green background color
                    for(int i=0;i<10;i++)
                    {
                        if(numPadButtons[i].getBackground() == Color.GREEN)
                        {
                            Calculate.display(numPadButtons[i].getText());
                            numPadButtons[i].doClick();
                            break;
                        }
                    }

                }
                else if(key == KeyEvent.VK_SPACE)
                {
                    // Check which function button has orange background color
                    for(int i=0;i<6;i++)
                    {
                        if(functionPadButtons[i].getBackground() == Color.ORANGE)
                        {
                            Calculate.display(functionPadButtons[i].getText());
                            functionPadButtons[i].doClick();
                            break;
                        }
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        // Adding number buttons to numPad
        for(int i=0;i<10;i++)
        {
            numPad.add(numPadButtons[i]);
        }
        numPad.setLayout(new GridLayout(4, 3));
        jFrame.add(numPad, BorderLayout.CENTER);    // Place numPad at the center of window

        add = new JButton("+");functionPadButtons[0]=add;
        sub = new JButton("-");functionPadButtons[1]=sub;
        mul = new JButton("*");functionPadButtons[2]=mul;
        div = new JButton("/");functionPadButtons[3]=div;
        stop = new JButton("Stop");functionPadButtons[4]=stop;
        clear = new JButton("Clear");functionPadButtons[5]=clear;

        // adding function buttons to functionPad
        for(int i=0;i<6;i++)
        {
            functionPad.add(functionPadButtons[i]);
        }


        functionPad.setLayout(new GridLayout(3, 2));
        jFrame.add(functionPad, BorderLayout.SOUTH);    // Place functionPad at the bottom of window

        jFrame.setFocusable(true);
        jFrame.setVisible(true);
    }

    // Function to add create interface
    public static void CreateInterface()
    {
        AddComponents();
    }

}
