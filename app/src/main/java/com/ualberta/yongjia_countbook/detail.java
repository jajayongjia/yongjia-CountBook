package com.ualberta.yongjia_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class detail extends AppCompatActivity {
    public static final int REQUEST_CODE_THREE = 3;
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
        configureplusButton();
        configureminsButton();
        configureeditButton();
        configureresetButton();
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
    private void configureplusButton(){
        Button plusButton = (Button) findViewById(R.id.increment);
        plusButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                counterName.setCurrentValue(counterName.getCurrentValue()+1);
                counterName.setDate();
                currV.setText(Integer.toString(counterName.getCurrentValue()));
                dateV.setText(counterName.getDate());

            }
        });
    }
    private void configureminsButton(){
        Button minsButton = (Button) findViewById(R.id.decrement);
        minsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int currentValue = counterName.getCurrentValue() - 1;

                //Question !, the size of current value and init value;
                if (currentValue<0){
                    Toast.makeText(detail.this, "current value can not be negative!", Toast.LENGTH_SHORT).show();
                }
                else {
                    counterName.setCurrentValue(currentValue);
                    counterName.setDate();
                    currV.setText(Integer.toString(counterName.getCurrentValue()));
                    dateV.setText(counterName.getDate());
                }
            }

        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("CHANGED",counterName);
        setResult( RESULT_FIRST_USER, intent);

        super.onBackPressed();
    }
    private void configureeditButton(){
        Button editButton = (Button) findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(detail.this, edit_page.class);
                intent.putExtra("EDITCOUNTER",counterName);
                startActivityForResult((intent),REQUEST_CODE_THREE);
            }
        });
    }
    private void configureresetButton(){
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                counterName.setCurrentValue(counterName.getInitialValue());
                counterName.setDate();
                currV.setText(Integer.toString(counterName.getCurrentValue()));
                dateV.setText(counterName.getDate());
            }
        });
    }



    protected void  onActivityResult(int requestCode,int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_THREE) {
            //is request activity result code is the same as 1, which is the activity that create a new counter
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
