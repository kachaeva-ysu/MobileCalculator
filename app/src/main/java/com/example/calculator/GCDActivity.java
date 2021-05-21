package com.example.calculator;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Objects;
import static java.lang.String.*;

public class GCDActivity extends AppCompatActivity {
    int inputNumber=1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_c_d);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    public void Input1Clicked(android.view.View view)
    {
        inputNumber=1;
    }
    public void Input2Clicked(android.view.View view)
    {
        inputNumber=2;
    }
    @SuppressLint("SetTextI18n")
    public void ButtonClicked(android.view.View view)
    {
        Button btn=(Button)view;
        String text=btn.getText().toString();
        if(inputNumber==1)
        {
            EditText input1=findViewById(R.id.Input1);
            input1.setText(input1.getText()+text);
        }
        else
        {
            EditText input2=findViewById(R.id.Input2);
            input2.setText(input2.getText()+text);
        }
    }
    public void ButtonClearClicked(android.view.View view)
    {
        TextView output= findViewById(R.id.OutputGCD);
        output.setText("");
        TextView output2=findViewById(R.id.OutputBezu);
        output2.setText("");
        EditText input1=findViewById(R.id.Input1);
        input1.setText("");
        EditText input2=findViewById(R.id.Input2);
        input2.setText("");
        inputNumber=1;
    }
    public void ButtonDeleteSymbolClicked(android.view.View view)
    {
        if(inputNumber==1)
        {
            EditText input1=findViewById(R.id.Input1);
            String text=input1.getText().toString();
            if(text.length()!=0)
                input1.setText(text.substring(0,text.length()-1));
        }
        else
        {
            EditText input2=findViewById(R.id.Input2);
            String text=input2.getText().toString();
                if(text.length()!=0)
                    input2.setText(text.substring(0,text.length()-1));
        }
    }
    @SuppressLint("DefaultLocale")
    public void ButtonCalculateClicked(android.view.View view)
    {
        GCD gcd=new GCD();
        EditText input1=findViewById(R.id.Input1);
        EditText input2=findViewById(R.id.Input2);
        TextView output= findViewById(R.id.OutputGCD);
        TextView output2=findViewById(R.id.OutputBezu);
        try
        {
            int[] answer=gcd.FindNOD(Integer.parseInt(input1.getText().toString()),Integer.parseInt(input2.getText().toString()));
            output.setText(String.format("НОД:  %d", answer[0]));
            output2.setText(String.format("Коэффициенты Безу:  %d  %d", answer[1], answer[2]));
        }
        catch (Exception e)
        {
            try
            {
                ComplexNumber[]answer=gcd.FindNOD(ComplexNumber.parse(input1.getText().toString()),ComplexNumber.parse(input2.getText().toString()));
                output.setText(format("НОД:  %s", answer[0].toString()));
                output2.setText(format("Коэффициенты Безу:  %s  %s", answer[1].toString(), answer[2].toString()));
            }
            catch (Exception e2)
            {
                output.setText("Некорректное выражение");
            }
        }
    }
}