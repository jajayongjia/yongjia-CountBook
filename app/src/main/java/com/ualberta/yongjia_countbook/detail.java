/*
 * Detail
 *
 * Version 1.0
 *
 * 2017 9 22
 *
 * Copyright 2017 yongjia CMPUT301, University of Alberta, All Rights Reserved.
 * You may use distribut, or modify this code under terms and conditions of the ode of Student Behavior at University of Alberta
 * You may find a copy of the license in this project. Otherwise please contact jajayongjia@gmail.com
 */
package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * This class shows the detail information about this Counter and allows user to either do the increment or decrement to the current value
 * <br>
 *  Also this activty allows user to reset the current value to the init value, and update the date in the same time;
 * <br>
 * @author Yongjia Huang
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class detail extends AppCompatActivity {
    public static final int REQUEST_CODE_THREE = 3;
    Counter counterName;
    TextView name;
    TextView initV;
    TextView currV;
    TextView dateV;
    TextView commV;


    /**
     * Detail actitivy onCreate method;
     * @param savedInstanceState onCreate method param.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Create TextViews:
        counterName = getIntent().getParcelableExtra("COUNTER");
        name = (TextView) findViewById(R.id.nameDetail);
        initV= (TextView) findViewById(R.id.initDetail);
        currV = (TextView) findViewById(R.id.currentDetail);
        dateV = (TextView) findViewById(R.id.dateDetail);
        commV = (TextView) findViewById(R.id.commentDetail);

        name.setText(counterName.getName());
        initV.setText(Integer.toString(counterName.getInitialValue()));
        currV.setText(Integer.toString(counterName.getCurrentValue()));
        dateV.setText(counterName.getDate());
        commV.setText(counterName.getComment());
        //Create Buttons:
        configuredeleteButton();
        configureplusButton();
        configureminsButton();
        configureeditButton();
        configureresetButton();
    }

    /**
     * Create a delete Button on detail page
     * <br>
     * If user click delete button, send delete message to main page and the main activity will delete it
     * <br>
     * go main page if you need more imformation
     */
    private void configuredeleteButton(){
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setResult(RESULT_OK);
                //close the detail page;
                finish();
            }

        });
    }

    /**
     * Create a Plus Button on Detail page
     * <br>
     * The Plue Button let user to increase the current Value and get date up-to-date.
     */
    private void configureplusButton(){
        Button plusButton = (Button) findViewById(R.id.increment);
        plusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                counterName.setCurrentValue(counterName.getCurrentValue()+1);
                counterName.setDate();
                //set Date to current value if the current value get changed
                currV.setText(Integer.toString(counterName.getCurrentValue()));
                dateV.setText(counterName.getDate());
                //update the current page information

            }
        });
    }

    /**
     * Create a minus Button on Detail page
     * <br>
     *     The minus Button allows user to decrease the current Value and get date up-to-date
     */
    private void configureminsButton(){
        Button minsButton = (Button) findViewById(R.id.decrement);
        minsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int currentValue = counterName.getCurrentValue() - 1;
                // here we need to check the user's input
                if (currentValue<0){
                    Toast.makeText(detail.this, "current value can not be negative!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //if user's input is valid
                    counterName.setCurrentValue(currentValue);
                    counterName.setDate();
                    //set the date to current date
                    currV.setText(Integer.toString(counterName.getCurrentValue()));
                    dateV.setText(counterName.getDate());
                    //update the current value in this page
                }
            }

        });

    }

    /**
     * This method allows user to save the changes when going back to the last activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("CHANGED",counterName);
        setResult( RESULT_FIRST_USER, intent);
        // send RESULT_FIRST_USER message to main page
        //save all the changes to the current counter

        super.onBackPressed();
    }

    /**
     * This method allows user to jump into edit page with the selected counter information on page
     */
    private void configureeditButton(){
        Button editButton = (Button) findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(detail.this, edit_page.class);
                intent.putExtra("EDITCOUNTER",counterName);
                startActivityForResult((intent),REQUEST_CODE_THREE);
                // get the result of edit counter and push the result to onActivityResult method
            }
        });
    }

    /**
     * This method allows user to reset current value to init value and get date up-to-date
     */
    private void configureresetButton(){
        //configure the reset Button and update the current value
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                counterName.setCurrentValue(counterName.getInitialValue());
                //update the current value;
                counterName.setDate();
                //update the date
                currV.setText(Integer.toString(counterName.getCurrentValue()));
                dateV.setText(counterName.getDate());
            }
        });
    }


    /**
     * This method recives result from other activites and handle them base on specific request and result code
     * @param requestCode ID of activity
     * @param resultCode  ID of result
     * @param data         the intent
     */
    protected void  onActivityResult(int requestCode,int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_THREE) {
            //the activity suppose the get result from the edit page
            //and send it to the main page;
            if (resultCode == RESULT_OK) {
                Counter changedCounter;
                changedCounter = data.getParcelableExtra("EDITEDCOUNTER");
                Intent intent = new Intent();
                intent.putExtra("CHANGED",changedCounter);
                setResult( RESULT_FIRST_USER, intent);
                finish();
            }
        }
    }



}
