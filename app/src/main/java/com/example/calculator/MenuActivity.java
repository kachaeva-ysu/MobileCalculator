package com.example.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }
    public void ButtonCalculatorClicked(android.view.View view)
    {
        android.content.Intent intent=new android.content.Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void ButtonGCDClicked(android.view.View view)
    {
        android.content.Intent intent=new android.content.Intent(MenuActivity.this,GCDActivity.class);
        startActivity(intent);
    }
    public void ButtonDerivativesClicked(android.view.View view)
    {
        android.content.Intent intent=new android.content.Intent(MenuActivity.this,DerivativesActivity.class);
        startActivity(intent);
    }

}