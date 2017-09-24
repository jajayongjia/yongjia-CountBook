package com.ualberta.yongjia_countbook;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class MainPage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String FILENAME = "file.sav";
    private TextView total;
    private ListView counterviewList;
    private String name;
    private String comment;
    private int init;
    private Counter newCounter;
    private ArrayList<Counter> countersList = new ArrayList<Counter>();
    private ArrayAdapter<Counter> adapter;
    private static final int REQUEST_CODE_ONE = 1;
    private static final int REQUEST_CODE_TWO = 2;
    private int indexRemoved;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start with the main page
        counterviewList = (ListView) findViewById(R.id.counterList);
        //Create listView reference counterList
        //Which contains all of the counters that user has created;
        counterviewList.setOnItemClickListener(this);




        configureNewButton();



    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
         Counter countersname;
         indexRemoved = position;
         countersname = (Counter)(parent.getItemAtPosition(position));
         Intent i = new Intent(getApplicationContext(), detail.class);
         i.putExtra("COUNTER", countersname);
         startActivityForResult(i,REQUEST_CODE_TWO);
            }

    private void configureNewButton(){
        Button addNewButton = (Button) findViewById(R.id.add);
        //Create Button reference addButton
        //add button is a link that jump to another layout to add a new counter
        addNewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivityForResult(new Intent(MainPage.this, add_counter.class),REQUEST_CODE_ONE);

                //Let new buttion jump to add_counter activity page.
            }

        });
    }

    @Override
    protected void  onActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_ONE){
            //is request activity result code is the same as 1, which is the activity that create a new counter
            if(resultCode == RESULT_OK){
                name = data.getExtras().getString("NAME");
                comment = data.getExtras().getString("COMMENT");
                init = data.getExtras().getInt("INIT");
                newCounter = new Counter(name,init,comment);
                //create a new Counter
                countersList.add(newCounter);
                //add the new Counter into countersList
                saveInFile();
                //save the updata new Counter into file
            }
        }
        if (requestCode == REQUEST_CODE_TWO){
            if(resultCode == RESULT_OK){
                countersList.remove(indexRemoved);
                adapter.notifyDataSetChanged();
                saveInFile();
            }
        }
    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        total = (TextView) findViewById(R.id.total);
        total.setText("Total is : " + Integer.toString(countersList.size()));
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_item, countersList);
        counterviewList.setAdapter(adapter);
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//        loadFromFile();
//        adapter = new ArrayAdapter<Counter>(this,
//                R.layout.list_item, countersList);
//        counterviewList.setAdapter(adapter);
//    }
//    protected void onPause() {
//        super.onPause();
//    }




    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            countersList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            countersList = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(countersList, out);

            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}