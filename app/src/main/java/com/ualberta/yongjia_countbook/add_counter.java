package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_counter extends AppCompatActivity {
    String name,comment;
    int init;
//    Counter newCounter;
    EditText nameInput;
    EditText initInput;
    EditText commentInput;
    Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        nameInput = (EditText) findViewById(R.id.new_name);
        initInput = (EditText) findViewById(R.id.new_init);
        commentInput = (EditText) findViewById(R.id.new_comment);
        createButton = (Button) findViewById(R.id.create);
        //create instance field
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                name = nameInput.getText().toString();
                comment = commentInput.getText().toString();
                init = Integer.valueOf(initInput.getText().toString());
                //get user's input

                Intent intent = new Intent();
                intent.putExtra("NAME",name);
                intent.putExtra("INIT",init);
                intent.putExtra("COMMENT",comment);

                setResult(RESULT_OK,intent);
                finish();
                //shut down this activity


            }
        });
    }
}

