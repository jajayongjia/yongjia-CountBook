package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_page extends AppCompatActivity {
    EditText editName;
    EditText editInit;
    EditText editCurrent;
    EditText editDate;
    EditText editComment;
    Button saveE;
    Counter editCounter;
    String name;
    String comment;
    int init;
    int current;
    String initString;
    String currentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        editName = (EditText) findViewById(R.id.editName);
        editInit = (EditText) findViewById(R.id.editInit);
        editCurrent = (EditText)findViewById(R.id.editCurrent);
        editDate = (EditText) findViewById(R.id.editDate);
        editComment = (EditText)findViewById(R.id.editComment);
        editDate.setEnabled(false);
        saveE = (Button) findViewById(R.id.saveE);
        editCounter = getIntent().getParcelableExtra("EDITCOUNTER");
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

                if (initString.matches("")||name.matches("")||currentString.matches("")){
                    Toast.makeText(edit_page.this, "You must enter name , init value and current value!", Toast.LENGTH_SHORT).show();
                }
                else if (!isInteger(initString) || !isInteger(currentString)){
                    Toast.makeText(edit_page.this, "Please Enter a integer number for init and current!", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.valueOf(initString)<0||Integer.valueOf(currentString)<0) {
                    Toast.makeText(edit_page.this, "Please Enter a non-negative number for init and current!", Toast.LENGTH_SHORT).show();
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
                    editCounter.setInitialValue(init);
                    editCounter.setCurrentValue(current);
                    editCounter.setComment(comment);

                    //Question#2
                    editCounter.setDate();

                    Intent intent = new Intent();
                    intent.putExtra("EDITEDCOUNTER", editCounter);
                    setResult(RESULT_OK, intent);
                    finish();
                    //shut down this activity
                }


            }
        });

    }
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