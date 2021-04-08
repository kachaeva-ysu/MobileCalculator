package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SimpleExpression
{
    private List<String> GetOperationsAndOperands(String expression)
    {
        List<String> operationsAndOperands = new ArrayList<String>();
        StringBuilder number = new StringBuilder();
        boolean previousSymbolIsANumber = false;
        for(int i=0;i<expression.length();i++)
        {
            if (Priority(Character.toString(expression.charAt(i))) == -1 && Character.isLetter(expression.charAt(i))&&expression.charAt(i)!='i')
            {
                if(previousSymbolIsANumber)
                    operationsAndOperands.add("*");
                if (expression.charAt(i) == 'p' && expression.charAt(i+1) == 'i')
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
            case ";": return 7;
            default: return -1;
        }
    }
    private List<String> GetReversePolishNotation(String exp)
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
    public ComplexNumber GetAnswer(String exp)
    {
        exp = exp.replaceAll("\\s+","");
        List<String> rpn = GetReversePolishNotation(exp);
        Stack<ComplexNumber> stack = new Stack<ComplexNumber>();
        for(String symbol : rpn)
        {
            try
            {
                stack.push(ComplexNumber.parse(symbol));
            }
            catch(Exception e)
            {
                ComplexNumber rValue = stack.pop();
                ComplexNumber lValue;
                if (symbol.equals("~"))
                    stack.push(ComplexNumber.Multiply(rValue, -1));
                else if (symbol.equals("abs"))
                {
                    stack.push(new ComplexNumber(rValue.GetAbs(), 0));
                }
                else if (symbol.equals("sqrt"))
                {
                    stack.push(new ComplexNumber(Math.sqrt(Double.parseDouble(rValue.toString())), 0));
                }
                else if (symbol.equals("sin"))
                {
                    stack.push(new ComplexNumber(Math.sin(Double.parseDouble(rValue.toString())), 0));
                }
                else if (symbol.equals("cos"))
                {
                    stack.push(new ComplexNumber(Math.cos(Double.parseDouble(rValue.toString())), 0));
                }
                else if (symbol.equals("tg"))
                {
                    double rV=Double.parseDouble(rValue.toString());
                    stack.push(new ComplexNumber(Math.sin(rV)/Double.parseDouble(String.format("%.3f",Math.cos(rV))), 0));
                }
                else if (symbol.equals("ctg"))
                {
                    double rV=Double.parseDouble(rValue.toString());
                    stack.push(new ComplexNumber(Math.cos(rV)/Double.parseDouble(String.format("%.3f",Math.sin(rV))), 0));
                }
                else if (symbol.equals("ln"))
                {
                    stack.push(new ComplexNumber(Math.log(Double.parseDouble(rValue.toString())), 0));
                }
                else
                {
                    lValue = stack.pop();
                    if (symbol.equals("+"))
                        stack.push(ComplexNumber.Add(lValue , rValue));
                    else if (symbol.equals("-"))
                        stack.push(ComplexNumber.Subtract(lValue ,rValue));
                    else if (symbol.equals("*"))
                        stack.push(ComplexNumber.Multiply(lValue , rValue));
                    else if (symbol.equals("/"))
                        stack.push(ComplexNumber.Divide(lValue , rValue));
                    else if (symbol.equals("^"))
                    {
                        stack.push(new ComplexNumber(Math.pow(Double.parseDouble(lValue.toString()), Double.parseDouble(rValue.toString())), 0));
                    }
                }
            }
        }
        if(stack.size()!=1)
            throw new IllegalArgumentException();
        return stack.pop();
    }
}
