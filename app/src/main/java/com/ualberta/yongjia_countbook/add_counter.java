package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_counter extends AppCompatActivity {
    String name,comment;
    int init;
//    Counter newCounter;
    EditText nameInput;
    EditText initInput;
    EditText commentInput;
    Button createButton;
    String initString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        nameInput = (EditText) findViewById(R.id.new_name);
        initInput = (EditText) findViewById(R.id.new_init);
        commentInput = (EditText) findViewById(R.id.new_comment);
        createButton = (Button) findViewById(R.id.decrement);
        //create instance field
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = nameInput.getText().toString();
                comment = commentInput.getText().toString();
                initString = initInput.getText().toString();
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
                    init = Integer.valueOf(initString);

                    Intent intent = new Intent();
                    intent.putExtra("NAME",name);
                    intent.putExtra("INIT",init);
                    intent.putExtra("COMMENT",comment);
//                else{
                    setResult(RESULT_OK,intent);
                    finish();
                }
                //get user's input
                    //shut down this activity

//                }


            }
        });
    }
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

