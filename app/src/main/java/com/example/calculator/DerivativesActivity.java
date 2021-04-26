package com.example.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

@SuppressWarnings("DuplicateExpressions")
public class DerivativesActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derivatives);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }
    @SuppressLint("SetTextI18n")
    public void ButtonClicked(android.view.View view)
    {
        Button btn=(Button)view;
        String text=btn.getText().toString();
        TextView output= findViewById(R.id.OutputBottom);
        if(!(output.getText().equals("Некорректное выражение")||output.getText().equals("Не число")||output.getText().equals("∞")||output.getText().equals("-∞")))
            output.setText(output.getText()+text);
    }
    public void ButtonClearClicked(android.view.View view)
    {
        TextView output2= findViewById(R.id.OutputBottom);
        TextView output1= findViewById(R.id.OutputTop);
        output1.setText("");
        output2.setText("");
    }
    public void ButtonDeleteSymbolClicked(android.view.View view)
    {
        TextView output2= findViewById(R.id.OutputBottom);
        if(!(output2.getText().equals("Некорректное выражение")||output2.getText().equals("Не число")||output2.getText().equals("∞")||output2.getText().equals("-∞"))){
            if(!output2.getText().equals("")) {
                String text = output2.getText().toString();
                if (text.charAt(text.length() - 1) == 's')
                    output2.setText(text.substring(0, text.length() - 3));
                else if (text.charAt(text.length() - 1) == 't')
                    output2.setText(text.substring(0, text.length() - 4));
                else if (text.charAt(text.length() - 1) == 'n') {
                    if (text.charAt(text.length() - 2) == 'l')
                        output2.setText(text.substring(0, text.length() - 2));
                    else
                        output2.setText(text.substring(0, text.length() - 3));
                } else if (text.charAt(text.length() - 1) == 'g') {
                    if (text.length() > 2 && text.charAt(text.length() - 3) == 'c')
                        output2.setText(text.substring(0, text.length() - 3));
                    else
                        output2.setText(text.substring(0, text.length() - 2));
                }
                else
                    output2.setText(text.substring(0, text.length() - 1));
            }
        }
    }
    public void ButtonCalculateClicked(android.view.View view) {
        TextView output2 = findViewById(R.id.OutputBottom);
        if (!(output2.getText().equals("Некорректное выражение") || output2.getText().equals("Не число") || output2.getText().equals("∞") || output2.getText().equals("-∞"))) {
            String exp = output2.getText().toString();
            Derivatives der=new Derivatives();
            TextView output1 = findViewById(R.id.OutputTop);
            output1.setText(output2.getText());
            try {
                StringBuilder answer=new StringBuilder();
                for(String symbol:der.GetReversePolishNotation(exp))
                {
                    answer.append(symbol);
                    answer.append(' ');
                }
                output2.setText(answer);
            } catch (Exception e) {
                output2.setText("Некорректное выражение");
            }
        }
    }
}