package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Derivatives {
    @SuppressWarnings("SimplifiableBooleanExpression")
    private List<String> GetOperationsAndOperands(String expression)
    {
        List<String> operationsAndOperands = new ArrayList<>();
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
        Stack<String> stack = new Stack<>();
        List<String> rpn = new ArrayList<>();

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
//    public String GetAnswer(String exp)
//    {
//        List<String> rpn = GetReversePolishNotation(exp);
//        Stack<String> stack = new Stack<>();
//        for(String symbol : rpn)
//        {
//            if(Priority(symbol)==-1)
//                stack.push(symbol);
//            else
//            {
//                String rValue = stack.pop();
//                String lValue;
//                switch (symbol) {
//                    case "~":
//                        stack.push('-'+rValue);
//                        break;
//                    case "sqrt":
//                        stack.push("1/2sqrt"+rValue);
//                        break;
//                    case "sin":
//                        stack.push("cos"+rValue);
//                        break;
//                    case "cos":
//                        stack.push("-sin"+rValue);
//                        break;
//                    case "tg":
//                        stack.push("1/cos^2"+rValue);
//                        break;
//                    case "ctg":
//                        stack.push("-1/sin^2"+rValue);
//                        break;
//                    case "ln":
//                        stack.push("1/"+rValue);
//                        break;
//                    default:
//                        lValue = stack.pop();
//                        switch (symbol) {
//                            case "+":
//                                stack.push(GetAnswer(lValue)+'+'+GetAnswer(rValue));
//                                break;
//                            case "-":
//                                stack.push(GetAnswer(lValue)+'-'+GetAnswer(rValue));
//                                break;
////                            case "*":
////                                stack.push(ComplexNumber.Multiply(lValue, rValue));
////                                break;
////                            case "/":
////                                stack.push(ComplexNumber.Divide(lValue, rValue));
////                                break;
////                            case "^":
////                                if (rValue.Im != 0)
////                                    throw new IllegalArgumentException();
////                                n = rValue.Re;
////                                if (lValue.Re > 0)
////                                    arg = Math.atan(lValue.Im / lValue.Re);
////                                else
////                                    arg = Math.atan(lValue.Im / lValue.Re) + Math.PI;
////                                stack.push(new ComplexNumber(Math.pow(lValue.GetAbs(), n) * Math.cos(n * arg), Math.pow(lValue.GetAbs(), n) * Math.sin(n * arg)));
////                                break;
//                        }
//                        break;
//                }
//            }
//        }
//        if(stack.size()!=1)
//            throw new IllegalArgumentException();
//        return stack.pop();
//    }

    public String GetAnswer(String exp)
    {
        List<String> rpn = GetReversePolishNotation(exp);
        return SimpleDerivatives(rpn);
    }
    public String SimpleDerivatives(List<String> rpn)
    {
        switch (rpn.get(rpn.size()-1))
        {
            case "+": {
                Stack<List<String>> stack = new Stack<>();
                for (int i=0;i<rpn.size()-1;i++) {
                    String symbol=rpn.get(i);
                    if (Priority(symbol) == -1) {
                        List<String> list = new ArrayList<String>();
                        list.add(symbol);
                        stack.push(list);
                    } else if (Priority(symbol) == 6) {
                        List<String> list = new ArrayList<String>(stack.pop());
                        list.add(symbol);
                        stack.push(list);
                    } else {
                        List<String> list = new ArrayList<String>();
                        List<String> second = stack.pop();
                        List<String> first = stack.pop();
                        list.addAll(first);
                        list.addAll(second);
                        list.add(symbol);
                        stack.push(list);
                    }
                }
                List<String> second = stack.pop();
                List<String> first = stack.pop();
                return SimpleDerivatives(first) +'+'+ SimpleDerivatives(second);
            }
            case "-": {
                Stack<List<String>> stack = new Stack<>();
                for (int i=0;i<rpn.size()-1;i++) {
                    String symbol=rpn.get(i);
                    if (Priority(symbol) == -1) {
                        List<String> list = new ArrayList<String>();
                        list.add(symbol);
                        stack.push(list);
                    } else if (Priority(symbol) == 6) {
                        List<String> list = new ArrayList<String>(stack.pop());
                        list.add(symbol);
                        stack.push(list);
                    } else {
                        List<String> list = new ArrayList<String>();
                        List<String> second = stack.pop();
                        List<String> first = stack.pop();
                        list.addAll(first);
                        list.addAll(second);
                        list.add(symbol);
                        stack.push(list);
                    }
                }
                List<String> second = stack.pop();
                List<String> first = stack.pop();
                return SimpleDerivatives(first) +'-'+ SimpleDerivatives(second);
            }
            case "*": {
                Stack<List<String>> stack = new Stack<>();
                for (int i=0;i<rpn.size()-1;i++) {
                    String symbol=rpn.get(i);
                    if (Priority(symbol) == -1) {
                        List<String> list = new ArrayList<String>();
                        list.add(symbol);
                        stack.push(list);
                    } else if (Priority(symbol) == 6) {
                        List<String> list = new ArrayList<String>(stack.pop());
                        list.add(symbol);
                        stack.push(list);
                    } else {
                        List<String> list = new ArrayList<String>();
                        List<String> second = stack.pop();
                        List<String> first = stack.pop();
                        list.addAll(first);
                        list.addAll(second);
                        list.add(symbol);
                        stack.push(list);
                    }
                }
                List<String> second = stack.pop();
                List<String> first = stack.pop();
                return SimpleDerivatives(first) +'*'+ Answer(second)+'+'+SimpleDerivatives(second) +'*'+ Answer(first);
            }

            case "x":
                return "1";
            default: return "0";

        }
    }
    private String Answer(List<String> rpn)
    {
        Stack<String> stack = new Stack<>();
        for(String symbol : rpn)
        {
            if(Priority(symbol)==-1)
                stack.push(symbol);
            else
            {
                String rValue = stack.pop();
                String lValue;
                switch (symbol) {
                    case "~":
                        stack.push('-'+rValue);
                        break;
                    case "sqrt":
                        stack.push("sqrt("+rValue+")");
                        break;
                    case "sin":
                        stack.push("sin("+rValue+")");
                        break;
                    case "cos":
                        stack.push("cos("+rValue+")");
                        break;
                    case "tg":
                        stack.push("tg("+rValue+")");
                        break;
                    case "ctg":
                        stack.push("ctg("+rValue+")");
                        break;
                    case "ln":
                        stack.push("ln("+rValue+")");
                        break;
                    default:
                        lValue = stack.pop();
                        switch (symbol) {
                            case "+":
                                stack.push(lValue+'+'+rValue);
                                break;
                            case "-":
                                stack.push(lValue+'-'+rValue);
                                break;
                            case "*":
                                stack.push(lValue+'*'+rValue);
                                break;
                            case "/":
                                stack.push(lValue+'/'+rValue);
                                break;
                            case "^":
                                stack.push(lValue+'^'+rValue);

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
