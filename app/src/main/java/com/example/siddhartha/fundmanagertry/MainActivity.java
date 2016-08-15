package com.example.siddhartha.fundmanagertry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView plus;
    Button details;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plus = (ImageView)findViewById(R.id.ivPlus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog newFund = new Dialog(MainActivity.this);
                newFund.setContentView(R.layout.newfund);
                newFund.setTitle("New Fund");
                newFund.show();
                final EditText fName, fAmount;
                Button submit;
                fName = (EditText)newFund.findViewById(R.id.etFundName);
                fAmount = (EditText)newFund.findViewById(R.id.etFundAmount);
                submit = (Button)newFund.findViewById(R.id.bSubmitFund);
            }
        });
        details = (Button)findViewById(R.id.bDetails1);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FundDetails.class);
                startActivity(i);
            }
        });
    }
}
