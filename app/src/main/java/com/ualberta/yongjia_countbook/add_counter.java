/*
 * Add_counter activity
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
 * This activity allows user to add a new counter base on the user's inputs
 * <br>
 * The Counter created by this activity will be saved by the main page activity into a countersList and will be saved in a file for future puposes
 * <br>
 * This user interface will check whether user's input is vaild or not and will ask for a correct input
 * @author Yongjia Huang
 * @version 1.0
 * @see AppCompatActivity
 * @since 1.0
 */
public class add_counter extends AppCompatActivity {

    String name,comment;
    int init;
    EditText nameInput;
    EditText initInput;
    EditText commentInput;
    Button createButton;
    String initString;

    /**Activity onCreate method;
     * @param savedInstanceState create activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);
        nameInput = (EditText) findViewById(R.id.new_name);
        initInput = (EditText) findViewById(R.id.new_init);
        commentInput = (EditText) findViewById(R.id.new_comment);
        createButton = (Button) findViewById(R.id.decrement);


        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = nameInput.getText().toString();
                comment = commentInput.getText().toString();
                initString = initInput.getText().toString();

                //check the format of user's input:
                if (initString.matches("")||name.matches("")){
                    Toast.makeText(add_counter.this, "You must enter name and init value!", Toast.LENGTH_SHORT).show();
                }
                else if (!isInteger(initString)){
                    Toast.makeText(add_counter.this, "Please Enter a integer number!", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.valueOf(initString)<0) {
                    Toast.makeText(add_counter.this, "Please Enter a non-negative number!", Toast.LENGTH_SHORT).show();
                }

                else{
                    //if userinput is in correct format:
                    init = Integer.valueOf(initString);

                    Intent intent = new Intent();
                    intent.putExtra("NAME",name);
                    intent.putExtra("INIT",init);
                    intent.putExtra("COMMENT",comment);
                    setResult(RESULT_OK,intent);
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
            Integer.parseInt(s);
            return true;
        }
        catch(Exception e ){
                return false;
            }


    }
}

