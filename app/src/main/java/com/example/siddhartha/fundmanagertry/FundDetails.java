package com.example.siddhartha.fundmanagertry;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FundDetails extends AppCompatActivity {

    Button initial,add;
    TextView remaining;
    EditText eInitial;
    ImageView tick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_details);
        initial = (Button)findViewById(R.id.bInitialAmount);
        add = (Button)findViewById(R.id.bAddBill);
        remaining = (TextView)findViewById(R.id.tvRemainingAmount);
        initial.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(FundDetails.this);
                dialog.setContentView(R.layout.changeinitial);
                dialog.setTitle("Update Initial Amount");
                eInitial = (EditText)dialog.findViewById(R.id.etInitialAmount);
                tick = (ImageView)dialog.findViewById(R.id.ivTick);
                dialog.show();
                tick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initial.setText(eInitial.getText().toString());
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog newBill = new Dialog(FundDetails.this);
                newBill.setContentView(R.layout.newbill);
                newBill.setTitle("New Bill");
                newBill.show();
            }
        });
    }
}
