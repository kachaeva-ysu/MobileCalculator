package com.example.calculator;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SimpleExpression
{
    private List<String> GetOperationsAndOperands(String expression)
    {
        List<String> operationsAndOperands = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        boolean previousSymbolIsANumber = false;
        for(int i=0;i<expression.length();i++)
        {
            if (Priority(Character.toString(expression.charAt(i))) == -1 && Character.isLetter(expression.charAt(i))&&expression.charAt(i)!='i')
            {
                if(previousSymbolIsANumber)
                {
                    operationsAndOperands.add(number.toString());
                    number=new StringBuilder();
                    previousSymbolIsANumber=false;
                    operationsAndOperands.add("*");
                }
                if (expression.charAt(i) == 'π')
                    operationsAndOperands.add(Double.toString(Math.PI));
                else if (expression.charAt(i) == 'e')
                    operationsAndOperands.add(Double.toString(Math.E));
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
                    i++;
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
        Stack<String> stack = new Stack<>();
        List<String> rpn = new ArrayList<>();
        for (String symbol : operationsAndOperands)
        {
            int priority = Priority(symbol);
            if (priority == -1)
                rpn.add(symbol);
            else
            {
                if (symbol.equals("(") || stack.size() == 0 || priority > Priority(stack.peek())&&!symbol.equals(")"))
                    stack.push(symbol);
                else
                {
                    while (stack.size() > 0 && priority <= Priority(stack.peek()))
                        rpn.add(stack.pop());
                    if (symbol.equals(")"))
                    {
                        while (Priority(stack.peek()) != 0)
                            rpn.add(stack.pop());
                        stack.pop();
                    }
                    else
                        stack.push(symbol);
                }
            }
        }
        while(stack.size()!=0)
            rpn.add(stack.pop());
        return rpn;
    }
    public ComplexNumber GetAnswer(String exp)
    {
        List<String> rpn = GetReversePolishNotation(exp);
        Stack<ComplexNumber> stack = new Stack<>();
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
                switch (symbol) {
                    case "~":
                        stack.push(ComplexNumber.Multiply(rValue, new ComplexNumber(-1,0)));
                        break;
                    case "abs":
                        stack.push(new ComplexNumber(rValue.GetAbs(), 0));
                        break;
                    case "sqrt":
                        double n = 0.5;
                        double arg;
                        if (rValue.Re > 0)
                            arg = Math.atan(rValue.Im / rValue.Re);
                        else
                            arg = Math.atan(rValue.Im / rValue.Re) + Math.PI;
                        stack.push(new ComplexNumber(Math.pow(rValue.GetAbs(), n) * Math.cos(n * arg), Math.pow(rValue.GetAbs(), n) * Math.sin(n * arg)));
                        break;
                    case "sin":
                        stack.push(new ComplexNumber(Math.sin(rValue.Re) * Math.cosh(rValue.Im), Math.cos(rValue.Re) * Math.sinh(rValue.Im)));
                        break;
                    case "cos":
                        stack.push(new ComplexNumber(Math.cos(rValue.Re) * Math.cosh(rValue.Im), -Math.sin(rValue.Re) * Math.sinh(rValue.Im)));
                        break;
                    case "tg":
                        stack.push(new ComplexNumber(Math.sin(rValue.Re * 2) / (Math.cos(rValue.Re * 2) + Math.cosh(rValue.Im * 2)), Math.sinh(rValue.Im * 2) / (Math.cos(rValue.Re * 2) + Math.cosh(rValue.Im * 2))));
                        break;
                    case "ctg":
                        stack.push(new ComplexNumber(-Math.sin(rValue.Re * 2) / (Math.cos(rValue.Re * 2) - Math.cosh(rValue.Im * 2)), Math.sinh(rValue.Im * 2) / (Math.cos(rValue.Re * 2) - Math.cosh(rValue.Im * 2))));
                        break;
                    case "ln":
                        stack.push(new ComplexNumber(Math.log(Double.parseDouble(rValue.toString())), 0));
                        break;
                    default:
                        lValue = stack.pop();
                        switch (symbol) {
                            case "+":
                                stack.push(ComplexNumber.Add(lValue, rValue));
                                break;
                            case "-":
                                stack.push(ComplexNumber.Subtract(lValue, rValue));
                                break;
                            case "*":
                                stack.push(ComplexNumber.Multiply(lValue, rValue));
                                break;
                            case "/":
                                stack.push(ComplexNumber.Divide(lValue, rValue));
                                break;
                            case "^":
                                if (rValue.Im != 0)
                                    throw new IllegalArgumentException();
                                n = rValue.Re;
                                if (lValue.Re > 0)
                                    arg = Math.atan(lValue.Im / lValue.Re);
                                else
                                    arg = Math.atan(lValue.Im / lValue.Re) + Math.PI;
                                stack.push(new ComplexNumber(Math.pow(lValue.GetAbs(), n) * Math.cos(n * arg), Math.pow(lValue.GetAbs(), n) * Math.sin(n * arg)));
                                break;
                        }
                        break;
                }
            }
        }
        if(stack.size()!=1)
            throw new IllegalArgumentException();
        return stack.pop();
    }
}
