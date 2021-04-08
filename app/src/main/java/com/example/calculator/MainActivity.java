
package com.example.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }
    public void ButtonClicked(android.view.View view)
    {

        Button btn=(Button)view;
        String text=btn.getText().toString();
        TextView output= findViewById(R.id.Output2);
        if(!(output.getText().equals("Некорректное выражение")||output.getText().equals("Не число")||output.getText().equals("∞")||output.getText().equals("-∞")))
            output.setText(output.getText()+text);

    }
    public void ButtonClearClicked(android.view.View view)
    {
        TextView output2= findViewById(R.id.Output2);
        TextView output1= findViewById(R.id.Output1);
        output1.setText("");
        output2.setText("");
    }
    public void ButtonDeleteSymbolClicked(android.view.View view)
    {
        TextView output2= findViewById(R.id.Output2);
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
            } else if (text.length() > 1 && text.charAt(text.length() - 1) == 'i' && text.charAt(text.length() - 2) == 'p')
                output2.setText(text.substring(0, text.length() - 2));
            else
                output2.setText(text.substring(0, text.length() - 1));
        }
        }
    }
    public void ButtonGCDClicked(android.view.View view)
    {
    }
    public void ButtonCalculateClicked(android.view.View view) {
        TextView output2 = findViewById(R.id.Output2);
        if (!(output2.getText().equals("Некорректное выражение") || output2.getText().equals("Не число") || output2.getText().equals("∞") || output2.getText().equals("-∞"))) {
            String exp = output2.getText().toString();
            SimpleExpression simpleExpression = new SimpleExpression();
            TextView output1 = findViewById(R.id.Output1);
            output1.setText(output2.getText());
            try {
                ComplexNumber number = simpleExpression.GetAnswer(exp);
                if (number.Re == Double.POSITIVE_INFINITY)
                    output2.setText("∞");
                else if (number.Re == Double.NEGATIVE_INFINITY)
                    output2.setText("-∞");
                else if (Double.toString(number.Re) == "NaN")
                    output2.setText("Не число");
                else {
                    if (!(number.Re * 10000 < 1 && number.Im != 0)) {
                        if (number.Re * 10000 < 1 || number.Re % 1 == 0)
                            output2.setText(Long.toString(Math.round(number.Re)));
                        else {
                            if (number.Re * 1000 % 1 == 0)
                                output2.setText(Double.toString(number.Re));
                            else
                                output2.setText(String.format("%.4f", number.Re));
                        }
                    } else
                        output2.setText("");
                    if (number.Im * 10000 < 1 || number.Im % 1 == 0) {
                        if (number.Im > 0) {
                            if (output2.getText().equals(""))
                                output2.setText(output2.getText() + Long.toString(Math.round(number.Im)) + "i");
                            else
                                output2.setText(output2.getText() + "+" + Math.round(number.Im) + "i");
                        } else if (number.Im < 0)
                            output2.setText(output2.getText() + Long.toString(Math.round(number.Im)) + "i");
                    } else {
                        if (number.Im > 0) {
                            if (number.Im * 1000 % 1 == 0) {
                                if (output2.getText().equals(""))
                                    output2.setText(output2.getText() + Double.toString(number.Im) + "i");
                                else
                                    output2.setText(output2.getText() + "+" + number.Im + "i");
                            } else {
                                if (output2.getText().equals(""))
                                    output2.setText(output2.getText() + String.format("%.4f", number.Im) + "i");
                                else
                                    output2.setText(output2.getText() + "+" + String.format("%.4f", number.Im) + "i");
                            }
                        } else {
                            if (number.Im * 1000 % 1 == 0)
                                output2.setText(output2.getText() + Double.toString(number.Im) + "i");
                            else
                                output2.setText(output2.getText() + String.format("%.4f", number.Im) + "i");

                        }
                    }
                }
            } catch (Exception e) {
                output2.setText("Некорректное выражение");
            }
        }
    }
}