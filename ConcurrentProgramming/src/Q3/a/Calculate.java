package Q3.a;

import java.util.Stack;
import java.util.Vector;

import static java.lang.Character.isDigit;

public class Calculate {

    public static boolean SyntaxError = false;
    public static boolean DivisionByZeroError = false;
    public static Vector<String> preProcessedData = new Vector<>();
    public static Vector<String> processedData =  new Vector<>();
    public static String finalString = "";
    public static Stack<Character> operators = new Stack<>();
    public static Stack<Integer> operands = new Stack<>();

    public static void display(String text)
    {
        // TODO: At the moment you have to clear after current calculation to move to next calculation
        if(text.equals("Stop"))
        {
            preComputation();
            if(SyntaxError)
            {
                Interface.jTextArea.setText("Syntax Error Found in query.");
            }
            else
            {
                Compute();
                if(DivisionByZeroError)
                {
                    Interface.jTextArea.setText("Division by zero not allowed");
                }
                else
                {
                    Interface.jTextArea.append("= " + operands.peek().toString());
                }
            }
        }
        else if(text.equals("Clear"))
        {
            ClearVariables();
            Interface.jTextArea.setText("");
        }
        else
        {
            Interface.jTextArea.append(text);
            preProcessedData.add(text);
        }
    }

    public static void ClearVariables()
    {
        SyntaxError = false;
        DivisionByZeroError = false;
        preProcessedData.clear();
        processedData.clear();
        operands.clear();
        operators.clear();
        finalString = "";
    }
    public static boolean isOperator(String text)
    {
        return text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/");
    }
    public static void preComputation()
    {
        int size = preProcessedData.size();
        // Find syntax errors
        // For first character it can be a number or if its a operator then
            // cannot be a * or /
            // If + ignore it
            // If - then add
        for(int i=0;i<size;i++)
        {
            System.out.println(preProcessedData.get(i));
            // First character in expression cannot be a * or /
            if(i==0)
            {
                if(preProcessedData.get(i).equals("*") || preProcessedData.get(i).equals("/"))
                {
                    SyntaxError = true;
                    break;
                }
            }
            // Last character cannot be a operator
            if(i==(size-1))
            {
                if(isOperator(preProcessedData.get(i)))
                {
                    SyntaxError = true;
                    break;
                }
            }
            // If current character is a operator then next cannot be a operator unless it is a "-" and its next is a digit
            if(isOperator(preProcessedData.get(i)))
            {
                if((i+1<size) && preProcessedData.get(i+1).equals("+") || preProcessedData.get(i+1).equals("*") ||preProcessedData.get(i+1).equals("/"))
                {
                    SyntaxError = true;
                    break;
                }
                if((i+1<size) && preProcessedData.get(i+1).equals("-"))
                {
                    if(i + 2 >= size || isOperator(preProcessedData.get(i + 2)))
                    {
                        SyntaxError = true;
                        break;
                    }
                }
            }

        }

        // Do this only is no Syntax Error
        if(!SyntaxError)
        {
            // Now convert multiple digits to a single number and push all data to a new vector
            int i=0;
            while(i<size)
            {
                if(isOperator(preProcessedData.get(i)))
                {
                    processedData.add(preProcessedData.get(i));
                    i++;
                }
                else
                {
                    int val = Integer.parseInt(preProcessedData.get(i));
                    i++;
                    while(i<size && !isOperator(preProcessedData.get(i)))
                    {
                        val = val * 10 + Integer.parseInt(preProcessedData.get(i));
                        i++;
                    }
                    processedData.add(String.valueOf(val));
                }
            }

            // Now convert processedData to a string, for e.g.
            // "2+3*5" or -2*-4 --> (0-2)*(0-4)
            // if we encounter a "-" then convert it into (0-next num)

            i=0;
            while(i<size)
            {
                if(processedData.get(i).equals("+") || processedData.get(i).equals("*") || processedData.get(i).equals("/"))
                {
                    finalString+=processedData.get(i);
                    i++;
                }
                else if(processedData.get(i).equals("-"))
                {
                    // If previous character is digit then don't do anything
                    // Else convert to (0-num)
                    if(i>=1 && isOperator(processedData.get(i-1)))
                    {
                        finalString = finalString + "(" + "0-" + processedData.get(i+1) + ")";
                        i+=2;
                    }
                    else
                    {
                        finalString+=processedData.get(i);
                        i++;
                    }

                }
                else
                {
                    finalString+=processedData.get(i);
                    i++;
                }
            }

            System.out.println(finalString);
        }

    }

    //  Now just compute the value using finalString
    public static void Compute()
    {
        int i=0;
        while(i<finalString.length())
        {
            char temp = finalString.charAt(i);

            // If temp is a digit
            if(isDigit(temp))
            {
                // This is the first digit of number, there can be multiple, so calculate the number
                int val = temp-'0';
                i++;
                while(i<finalString.length() && isDigit(finalString.charAt(i)))
                {
                    val = val*10+finalString.charAt(i);
                    i++;
                }

                operands.push(val);
            }
            else if(temp == '(')
            {
                operators.push(temp);
                i++;
            }
            else if(isOperator(Character.toString(temp)))
            {
                while(!operators.isEmpty() && precedence(temp)<=precedence(operators.peek()))
                {
                    // Take two numbers from operand stack and perform operation using temp as operation
                    char operation = operators.pop();
                    int num1 = operands.pop();
                    int num2 = operands.pop();
                    Operate(num1, num2, operation);

                }
                operators.push(temp);
                i++;
            }
            else if(temp == ')')
            {
                while(operators.peek() != '(')
                {
                    char operation = operators.pop();
                    int num1 = operands.pop();
                    int num2 = operands.pop();
                    Operate(num1, num2, operation);
                }
                // Pop '('
                operators.pop();
                i++;
            }

            // If we get DivisionByZero error in between then stop the calculation
            if(DivisionByZeroError)
            {
                break;
            }

        }
        if(!DivisionByZeroError)
        {
            while(!operators.isEmpty())
            {
                char operation = operators.pop();
                int num1 = operands.pop();
                int num2 = operands.pop();
                Operate(num1, num2, operation);
            }
        }

    }

    public static int precedence(char operator)
    {
        if(operator == '-' || operator == '+')
        {
            return 1;
        }
        if(operator == '*' || operator == '/')
        {
            return 2;
        }
        return 0;
    }

    public static void Operate(int num1, int num2, char operator)
    {
        if(operator == '+')
        {
            operands.push(num1+num2);
        }
        if(operator == '-')
        {
            operands.push(num2-num1);
        }
        if(operator == '*')
        {
            operands.push(num1*num2);
        }
        if(operator == '/')
        {
            if(num1 == 0)
            {
                DivisionByZeroError = true;
            }
            else operands.push(num2/num1);
        }

    }
}
