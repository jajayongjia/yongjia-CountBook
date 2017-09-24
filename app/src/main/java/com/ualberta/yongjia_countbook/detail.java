package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;

public class detail extends AppCompatActivity {
    Counter counterName;
    TextView name;
    TextView initV;
    TextView currV;
    TextView dateV;
    TextView commV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        configuredeleteButton();
    }

    private void configuredeleteButton(){
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setResult(RESULT_OK);
                finish();
            }

        });
    }



}
