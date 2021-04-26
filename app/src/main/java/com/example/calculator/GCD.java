package com.example.calculator;

public class GCD
{
    public int[] FindNOD(int a, int b)
    {
        int[] answer = new int[3];
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int[] stroka1 = new int[7];
        int[] stroka2 = new int[7];
        stroka1[1] = 1;
        stroka1[3] = max;
        stroka1[5] = 1;
        stroka1[6] = min;
        while (stroka1[6] != 0)
        {
            stroka2[0] = stroka1[3] / stroka1[6];
            stroka2[1] = stroka1[4];
            stroka2[2] = stroka1[5];
            stroka2[3] = stroka1[6];
            stroka2[4] = stroka1[1] - stroka2[0] * stroka2[1];
            stroka2[5] = stroka1[2] - stroka2[0] * stroka2[2];
            stroka2[6] = stroka1[3] - stroka1[6] * stroka2[0];
            System.arraycopy(stroka2, 0, stroka1, 0, 7);
        }
        answer[0] = stroka1[3];
        if (a > b)
        {
            answer[1] = stroka1[1];
            answer[2] = stroka1[2];
        }
        else
        {
            answer[1] = stroka1[2];
            answer[2] = stroka1[1];
        }
        if(answer[0]<0)
        {
            answer[0] *= -1;
            answer[1] *= -1;
            answer[2] *= -1;
        }
        return answer;
    }
    public ComplexNumber[] FindNOD(ComplexNumber a, ComplexNumber b)
    {
        ComplexNumber[] answer = new ComplexNumber[3];
        if (a.GetAbs() < b.GetAbs())
        {
            ComplexNumber temp = a;
            a = b;
            b = temp;
        }
        ComplexNumber[] stroka1 = new ComplexNumber[7];
        ComplexNumber[] stroka2 = new ComplexNumber[7];
        stroka1[1] = new ComplexNumber(1,0);
        stroka1[3] = a;
        stroka1[5] = new ComplexNumber(1, 0);
        stroka1[6] = b;
        stroka1[0] = stroka1[2] = stroka1[4] = new ComplexNumber(0, 0);
        while (stroka1[6].Re != 0 || stroka1[6].Im != 0)
        {
            ComplexNumber q = ComplexNumber.Divide(stroka1[3] , stroka1[6]);
            q.Re = Math.round(q.Re);
            q.Im = Math.round(q.Im);
            stroka2[0] = q;
            stroka2[1] = stroka1[4];
            stroka2[2] = stroka1[5];
            stroka2[3] = stroka1[6];
            stroka2[4] = ComplexNumber.Subtract(stroka1[1] , ComplexNumber.Multiply(stroka2[0] , stroka2[1]));
            stroka2[5] = ComplexNumber.Subtract(stroka1[2] , ComplexNumber.Multiply(stroka2[0] , stroka2[2]));
            stroka2[6] = ComplexNumber.Subtract(stroka1[3] , ComplexNumber.Multiply(stroka1[6] , stroka2[0]));
            System.arraycopy(stroka2, 0, stroka1, 0, 7);
        }
        answer[0] = stroka1[3];
        if (a.GetAbs() >= b.GetAbs())
        {
            answer[1] = stroka1[1];
            answer[2] = stroka1[2];
        }
        else
        {
            answer[1] = stroka1[2];
            answer[2] = stroka1[1];
        }
        if (answer[0].Re < 0||answer[0].Re==0&&answer[0].Im<0)
        {
            answer[0] = ComplexNumber.Multiply(answer[0],-1);
            answer[1] =ComplexNumber.Multiply(answer[1],-1);
            answer[2] =ComplexNumber.Multiply(answer[2],-1);
        }
        return answer;
    }
}
