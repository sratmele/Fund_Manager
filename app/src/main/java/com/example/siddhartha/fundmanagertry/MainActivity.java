package com.example.siddhartha.fundmanagertry;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView plus;
    LinearLayout fundList;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plus = (ImageView)findViewById(R.id.ivPlus);
        fundList = (LinearLayout)findViewById(R.id.llFundScroll);
        databaseHandler = new DatabaseHandler(getApplicationContext());
        display();
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog newFund = new Dialog(MainActivity.this);
                newFund.setContentView(R.layout.newfund);
                newFund.setTitle("New Fund");
                newFund.show();

                final EditText fName, fAmount;
                final Spinner day, month, year;
                Button submit;

                fName = (EditText)newFund.findViewById(R.id.etFundName);
                fAmount = (EditText)newFund.findViewById(R.id.etFundAmount);
                submit = (Button)newFund.findViewById(R.id.bSubmitFund);
                day = (Spinner)newFund.findViewById(R.id.sDayF);
                month = (Spinner)newFund.findViewById(R.id.sMonF);
                year = (Spinner)newFund.findViewById(R.id.sYearF);

                Date date = new Date();
                CharSequence s = android.text.format.DateFormat.format("dd/MM/yyyy",date);
                String dateString = String.valueOf(s);

                String days[] = new String[31];
                for(int i=0;i<31;i++)
                    if(i+1<10)
                        days[i]="0"+String.valueOf(i+1);
                    else
                        days[i]=String.valueOf(i+1);
                ArrayAdapter<String> dAdapt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,days);
                day.setAdapter(dAdapt);

                String months[] = new String[12];
                for(int i=0;i<12;i++)
                    if(i+1<10)
                        months[i]="0"+String.valueOf(i+1);
                    else
                        months[i]=String.valueOf(i+1);
                ArrayAdapter<String> mAdapt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,months);
                month.setAdapter(mAdapt);

                String years[] = new String[50];
                for(int i=2001;i<=2050;i++)
                    years[i-2001]=String.valueOf(i);
                ArrayAdapter<String> yAdapt = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,years);
                year.setAdapter(yAdapt);

                day.setSelection(Integer.valueOf(dateString.substring(0, 2)) - 1);
                month.setSelection(Integer.valueOf(dateString.substring(3, 5)) - 1);
                year.setSelection(Integer.valueOf(dateString.substring(6)) - 2001);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String dateFormat = String.valueOf(year.getSelectedItem())+"-"+String.valueOf(month.getSelectedItem())+"-"+String.valueOf(day.getSelectedItem());
                        databaseHandler.insertFund(fName.getText().toString(),Long.valueOf(fAmount.getText().toString()),dateFormat);
                        display();
                    }
                });
            }
        });
    }

    private void display() {
        databaseHandler = new DatabaseHandler(getApplicationContext());
        fundList.removeAllViews();
        int numberOfFunds = databaseHandler.getNumberOfFunds();
        List<String> funds = databaseHandler.getFunds();
        List<String> amounts = databaseHandler.getFundAmounts();
        List<String> dates = databaseHandler.getFundDates();
        for (int i = 0; i < numberOfFunds; i++) {
            setFundCard(funds.get(i),amounts.get(i),dates.get(i));
        }
    }

    private void setFundCard(final String name, String amount, String date) {
        databaseHandler = new DatabaseHandler(getApplicationContext());
        RelativeLayout fundCard = new RelativeLayout(this);
        int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
        fundCard.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dimension));
        dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        fundCard.setPadding(dimension, dimension, dimension, dimension);
        fundCard.setBackgroundResource(R.drawable.card);

        TextView tName = new TextView(this);
        TextView tDate = new TextView(this);
        LinearLayout bottomLine = new LinearLayout(this);
        Button details = new Button(this);
        Spinner amounts = new Spinner(this);

        dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        tName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dimension));
        tName.setText(name);
        tName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);
        tName.setTextColor(Color.BLACK);
        tName.setLongClickable(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dimension);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tDate.setLayoutParams(params);
        String dateFormatted = date.substring(8)+"/"+date.substring(5,7)+"/"+date.substring(2,4);
        tDate.setText(dateFormatted);
        tDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        tDate.setPadding(dimension, dimension, dimension, dimension);

        dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dimension);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomLine.setLayoutParams(params);
        bottomLine.setOrientation(LinearLayout.HORIZONTAL);
        bottomLine.setWeightSum(100);

        dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,dimension);
        linearParams.weight=60;
        linearParams.gravity=Gravity.CENTER_VERTICAL;
        details.setLayoutParams(linearParams);
        details.setText("Details");
        details.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
        details.setTextColor(Color.BLACK);
        details.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,FundDetails.class);
                i.putExtra("fundname",name);
                startActivity(i);
            }
        });

        linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,dimension);
        linearParams.weight=40;
        linearParams.gravity=Gravity.CENTER_VERTICAL;
        amounts.setLayoutParams(linearParams);
        List<String> fundBillAmounts = databaseHandler.getBillAmounts(name);
        long balance = Long.valueOf(amount);
        for(String x:fundBillAmounts)
        balance-=Long.valueOf(x);
        fundBillAmounts.add(0,String.valueOf(balance));
        fundBillAmounts.add(1,String.valueOf(amount));
        ArrayAdapter<String> fbaAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,fundBillAmounts);
        amounts.setAdapter(fbaAdapter);

        bottomLine.addView(details);
        bottomLine.addView(amounts);

        fundCard.addView(tName);
        fundCard.addView(tDate);
        fundCard.addView(bottomLine);

        fundList.addView(fundCard);
    }
}
