package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GCDActivity extends AppCompatActivity {
    int inputNumber=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_c_d);
    }
    public void Input1Clicked(android.view.View view)
    {
        inputNumber=1;
    }
    public void Input2Clicked(android.view.View view)
    {
        inputNumber=2;
    }

    public void ButtonClicked(android.view.View view)
    {
        Button btn=(Button)view;
        String text=btn.getText().toString();
        if(inputNumber==1){
        EditText input1=findViewById(R.id.Input1);
        input1.setText(input1.getText()+text);}
        else{
            EditText input2=findViewById(R.id.Input2);
            input2.setText(input2.getText()+text);
        }
    }
    public void ButtonClearClicked(android.view.View view)
    {
        TextView output= findViewById(R.id.Output);
        output.setText("");
        EditText input1=findViewById(R.id.Input1);
        input1.setText("");
        EditText input2=findViewById(R.id.Input2);
        input1.setText("");
    }
    public void ButtonDeleteSymbolClicked(android.view.View view)
    {

    }
    public void ButtonCalculateClicked(android.view.View view)
    {

    }

}