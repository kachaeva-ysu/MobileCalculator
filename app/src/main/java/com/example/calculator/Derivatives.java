package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Derivatives {
    private List<String> GetOperationsAndOperands(String expression)
    {
        List<String> operationsAndOperands = new ArrayList<String>();
        StringBuilder number = new StringBuilder();
        boolean previousSymbolIsANumber = false;
        for(int i=0;i<expression.length();i++)
        {
            if (Priority(Character.toString(expression.charAt(i))) == -1 && Character.isLetter(expression.charAt(i))&&expression.charAt(i)!='i')
            {
                if(previousSymbolIsANumber) {
                    operationsAndOperands.add(number.toString());
                    number=new StringBuilder();
                    previousSymbolIsANumber=false;
                    operationsAndOperands.add("*");
                }
                if(expression.charAt(i)=='x')
                {
                    operationsAndOperands.add("x");
                    previousSymbolIsANumber=true;
                }
                else if (expression.charAt(i) == 'p' && expression.charAt(i+1) == 'i')
                {
                    i +=1;
                    operationsAndOperands.add(Double.toString(Math.PI));
                }
                else if (expression.charAt(i) == 'e')
                {
                    operationsAndOperands.add(Double.toString(Math.E));
                }
                else if (expression.charAt(i) == 'a' && expression.charAt(i+1) == 'b' && expression.charAt(i+2) == 's')
                {
                    i +=2;
                    operationsAndOperands.add("abs");
                }
                else if (expression.charAt(i) == 's' && expression.charAt(i+1) == 'q' && expression.charAt(i+2) == 'r' && expression.charAt(i + 3) == 't')
                {
                    i +=3;
                    operationsAndOperands.add("sqrt");
                }
                else if (expression.charAt(i) == 's' && expression.charAt(i+1) == 'i' && expression.charAt(i+2) == 'n')
                {
                    i +=2;
                    operationsAndOperands.add("sin");
                }
                else if (expression.charAt(i) == 'c' && expression.charAt(i+1) == 'o' && expression.charAt(i+2) == 's')
                {
                    i +=2;
                    operationsAndOperands.add("cos");
                }
                else if (expression.charAt(i) == 't' && expression.charAt(i+1) == 'g')
                {
                    i++;
                    operationsAndOperands.add("tg");
                }
                else if (expression.charAt(i) == 'c' && expression.charAt(i+1) == 't' && expression.charAt(i+2) == 'g')
                {
                    i +=2;
                    operationsAndOperands.add("ctg");
                }
                else if (expression.charAt(i) == 'l' && expression.charAt(i+1) == 'n')
                {
                    i +=1;
                    operationsAndOperands.add("ln");
                }
                else
                    throw new IllegalArgumentException();
            }
            else if (Priority(Character.toString(expression.charAt(i))) == -1)
            {
                number.append(expression.charAt(i));
                previousSymbolIsANumber = true;
            }
            else
            {
                if (!number.toString().equals(""))
                {
                    operationsAndOperands.add(number.toString());
                    number=new StringBuilder();
                    if(expression.charAt(i)=='(')
                        operationsAndOperands.add("*");
                }
                if (expression.charAt(i) == '-' && !previousSymbolIsANumber && (i != 0 && expression.charAt(i-1) != ')' || i == 0))
                    operationsAndOperands.add("~");
                else
                    operationsAndOperands.add(Character.toString(expression.charAt(i)));
                previousSymbolIsANumber = false;
            }
        }
        if (!number.toString().equals(""))
            operationsAndOperands.add(number.toString());
        return operationsAndOperands;
    }
    private int Priority(String symbol)
    {
        switch (symbol)
        {
            case "(": return 0;
            case ")": return 1;
            case "+": case "-": return 2;
            case "*": case "/": return 3;
            case "~": return 4;
            case "^":  return 5;
            case "sqrt": case "sin": case"cos": case "tg": case "ctg": case "ln": case "abs": return 6;
            default: return -1;
        }
    }
    public List<String> GetReversePolishNotation(String exp)
    {
        List<String> operationsAndOperands = GetOperationsAndOperands(exp);
        Stack<String> stack = new Stack<String>();
        List<String> rpn = new ArrayList<String>();

        for (String symbol : operationsAndOperands)
        {
            int priority = Priority(symbol);
            if (priority == -1)
            {
                rpn.add(symbol);
            }
            else
            {
                if (symbol.equals("(") || stack.size() == 0 || priority > Priority(stack.peek())&&!symbol.equals(")"))
                {
                    stack.push(symbol);
                }
                else
                {
                    while (stack.size() > 0 && priority <= Priority(stack.peek()))
                    {
                        rpn.add(stack.pop());
                    }
                    if (symbol.equals(")"))
                    {
                        while (Priority(stack.peek()) != 0)
                        {
                            rpn.add(stack.pop());
                        }
                        stack.pop();
                    }
                    else
                    {
                        stack.push(symbol);
                    }

                }
            }
        }
        while(stack.size()!=0)
            rpn.add(stack.pop());
        return rpn;
    }
}
