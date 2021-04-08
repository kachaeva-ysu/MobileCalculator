package com.example.calculator;

public class ComplexNumber
{
    public double Re;
    public double Im;
    public double GetAbs()
    {
        return Math.sqrt(Re * Re + Im * Im);
    }
    public ComplexNumber(double re,double im)
    {
        Re = re;
        Im = im;
    }
    public String toString()
    {
        if (Re == 0 && Im == 0)
            return "0";
        if (Re == 0)
        {
            if (Im == 1)
                return "i";
            if (Im == -1)
                return "-i";
            return Im + "i";
        }
        if (Im == 0)
            return Double.toString(Re);
        if (Im == 1)
            return Re + "+i";
        if (Im == -1)
            return Re + "-i";
        if (Im > 0)
            return (Re + "+" + Im + "i");
        return (Re + "-" + Math.abs(Im) + "i");
    }
    public static ComplexNumber parse(String exp)
    {
        StringBuilder re = new StringBuilder();
        StringBuilder im = new StringBuilder();
        if (exp.equals("i"))
            return new ComplexNumber(0, 1);
        if (exp.equals("-i"))
            return new ComplexNumber(0, -1);
        re.append(exp.charAt(0));
        int index = 1;

        while (index<exp.length()&&(Character.isDigit(exp.charAt(index))||exp.charAt(index)=='.'))
        {
            re.append(exp.charAt(index));
            index++;
        }
        if(index==exp.length())
            return new ComplexNumber(Double.parseDouble(re.toString()), 0);
        if (exp.charAt(index) == 'i'&&index + 1 == exp.length())
            return new ComplexNumber(0, Double.parseDouble(re.toString()));
        if (exp.charAt(index) == '+' || exp.charAt(index) == '-')
        {
            if (exp.charAt(index) == '-')
                im.append('-');
            index++;
        }
        else
            throw new IllegalArgumentException();
        while (index<exp.length()&&(Character.isDigit(exp.charAt(index))||exp.charAt(index)=='.'))
        {
            im.append(exp.charAt(index));
            index++;
        }
        if (im.toString().equals("")|| im.toString().equals("-"))
            im.append(1);
        if (exp.charAt(index) == 'i'&& index + 1 == exp.length())
            return new ComplexNumber(Double.parseDouble(re.toString()), Double.parseDouble(im.toString()));
        else
            throw new IllegalArgumentException();
    }
    public static ComplexNumber Add(ComplexNumber a, ComplexNumber b)
    {
        return new ComplexNumber(a.Re+b.Re,a.Im+b.Im);
    }
    public static ComplexNumber Add(ComplexNumber a, Double b)
    {
        return new ComplexNumber(a.Re + b, a.Im);
    }
    public static ComplexNumber Subtract(ComplexNumber a, ComplexNumber b)
    {
        return new ComplexNumber(a.Re-b.Re,a.Im-b.Im);
    }
    public static ComplexNumber Subtract(ComplexNumber a, double b)
    {
        return new ComplexNumber(a.Re - b, a.Im);
    }
    public static ComplexNumber Multiply(ComplexNumber a, ComplexNumber b)
    {
        return new ComplexNumber(a.Re * b.Re + a.Im * b.Im * -1, a.Re * b.Im + a.Im * b.Re);
    }
    public static ComplexNumber Multiply(ComplexNumber a, double b)
    {
        return new ComplexNumber(a.Re * b, a.Im * b);
    }
    public static ComplexNumber Divide(ComplexNumber a, ComplexNumber b)
    {
        double divider = b.Re * b.Re + b.Im * b.Im;
        return new ComplexNumber((a.Re * b.Re + a.Im * b.Im)/divider, (a.Re * b.Im*-1 + a.Im * b.Re)/divider);
    }
    public static ComplexNumber Divide(ComplexNumber a, double b)
    {
        return new ComplexNumber(a.Re / b, a.Im / b);
    }
}
