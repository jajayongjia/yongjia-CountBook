/*
 * Edit_page
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
import android.widget.EditText;
import android.widget.Toast;


/**
 *This class is the edit page  of this application,
 * <br>
 *This activity allows user to edit on the Counter instance except for the date
 * <br>
 * This activity also shows the detail information about the selected Counter
 *
 * @author Yongjia Huang
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class Edit_page extends AppCompatActivity {
    private EditText editName;
    private EditText editInit;
    private EditText editCurrent;
    private EditText editDate;
    private EditText editComment;
    private Button saveE;
    private Counter editCounter;
    private String name;
    private String comment;
    private int init;
    private int current;
    private String initString;
    private String currentString;


    /**
     * onCreate method of this activity
     * @param savedInstanceState onCreate instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        //this activity is very similar to the new_counter activity
        //edittext  field;
        editName = (EditText) findViewById(R.id.editName);
        editInit = (EditText) findViewById(R.id.editInit);
        editCurrent = (EditText)findViewById(R.id.editCurrent);
        editDate = (EditText) findViewById(R.id.editDate);
        editComment = (EditText)findViewById(R.id.editComment);
        editDate.setEnabled(false);
        saveE = (Button) findViewById(R.id.saveE);

        // get the counter passed by the detail activity
        editCounter = getIntent().getParcelableExtra("EDITCOUNTER");

        //get the detail information of the passed counter and show them on the page
        editName.setText(editCounter.getName());
        editInit.setText(Integer.toString(editCounter.getInitialValue()));
        editCurrent.setText(Integer.toString(editCounter.getCurrentValue()));
        editDate.setText(editCounter.getDate());
        editComment.setText(editCounter.getComment());


        saveE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = editName.getText().toString();
                comment = editComment.getText().toString();
                initString = editInit.getText().toString();
                currentString = editCurrent.getText().toString();
                //need to check the format of the user's input

                if (initString.matches("")||name.matches("")||currentString.matches("")){
                    Toast.makeText(Edit_page.this, "You must enter name , init value and current value!", Toast.LENGTH_SHORT).show();
                }
                else if (!isInteger(initString) || !isInteger(currentString)){
                    Toast.makeText(Edit_page.this, "Please Enter a integer number for init and current!", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.valueOf(initString)<0||Integer.valueOf(currentString)<0) {
                    Toast.makeText(Edit_page.this, "Please Enter a non-negative number for init and current!", Toast.LENGTH_SHORT).show();
                }
                //Question#1
//                else if (Integer.valueOf(initString)>Integer.valueOf(currentString)){
//                    Toast.makeText(edit_page.this, "current value must bigger than or equal to the init value!", Toast.LENGTH_SHORT).show();
//                }
                else {
                    init = Integer.valueOf(initString);
                    current = Integer.valueOf(currentString);
                    //get user's input
                    editCounter.setName(name);
                    //Question: Do we need to reset the date to current date when the init value is changed?
                    if (current!=editCounter.getCurrentValue()) {
                        editCounter.setDate();
                    }

                    editCounter.setInitialValue(init);
                    editCounter.setCurrentValue(current);
                    editCounter.setComment(comment);
                    Intent intent = new Intent();
                    intent.putExtra("EDITEDCOUNTER", editCounter);
                    setResult(RESULT_OK, intent);
                    finish();
                    //shut down this activity
                }


            }
        });

    }

    /**
     * A simple method that check the user's input is integer or not
     * @param s user's input;
     * @return boolean;
     */
    private static boolean isInteger( String s ) {

        try {
            Integer.parseInt(s) ;
            return true;
        }
        catch(Exception e ){
            return false;
        }


    }

}