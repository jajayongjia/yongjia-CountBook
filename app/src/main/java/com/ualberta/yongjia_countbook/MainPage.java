/* MainPage
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


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

/**
 *This class is the main page (main activity) of this application,
 * <br>
 *this activity shows the Counters list and a new button which allows user to jump into new_counter activity and create a new counter
 *on top of this page has  a total number of exist counter;
 *the counters list contains counter's name , counter's date  and counter's current value
 *the view list of this page are clickable and it will jump into the detail activity
 *
 * @author Yongjia Huang
 * @version 1.0
 * @see AppCompatActivity
 * @see AdapterView
 * @since 1.0
 */

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

    /**
     * Activity onCreate method;
     * @param savedInstanceState activity oncreate instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Start with the main page
        counterviewList = (ListView) findViewById(R.id.counterList); //Create listView reference counterList Which contains all of the counters that user has created;
        counterviewList.setOnItemClickListener(this);
        configureNewButton(); //New Button jump to a create new Counter activity

    }

    /**
     * Override AdapterView.OnItemClickListener method
     * @param parent adapter param
     * @param view   listView
     * @param position list index
     * @param id     Long
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        //on view list item click
         Counter countersname;
         indexRemoved = position;
         countersname = (Counter)(parent.getItemAtPosition(position)); //get the counter selected
         Intent i = new Intent(getApplicationContext(), detail.class); //get ready to jump into detail activity.
         i.putExtra("COUNTER", countersname);
         startActivityForResult(i,REQUEST_CODE_TWO); // Start the detail activity and wait for the result of delete or change with a specific request code.
            }

    /**
     *Create Button reference addButton
     * <br>
     *add button is a link that jump to another layout to add a new counter
     */
    private void configureNewButton(){
        Button addNewButton = (Button) findViewById(R.id.add);
        addNewButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivityForResult(new Intent(MainPage.this, add_counter.class),REQUEST_CODE_ONE);//Let new buttion jump to add_counter activity page.
            }

        });
    }

    /**
     * This method allows the main activity gets the data that transfromed from  sub-activity
     * @param requestCode the code that passed from the above method,it depends on which sub-activity passed the value
     * @param resultCode the code that depends on sub-activity's purpose,
     * @param data the intent link
     */
    @Override
    protected void  onActivityResult(int requestCode,int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_ONE){
            //is request activity result code is the same as 1, which is the activity that create a new counter
            if(resultCode == RESULT_OK){
                name = data.getExtras().getString("NAME");
                comment = data.getExtras().getString("COMMENT");
                init = data.getExtras().getInt("INIT");
                newCounter = new Counter(name,init,comment);//create a new Counter with current == init
                countersList.add(newCounter);//add the new Counter into countersList
                saveInFile();//save the updata new Counter into file
            }
        }
        if (requestCode == REQUEST_CODE_TWO){//if mainActivity jump to a detail activity.
            if(resultCode == RESULT_OK){//if detail activity ask for a delete action
                countersList.remove(indexRemoved);//delete the selected counter
                adapter.notifyDataSetChanged();//notify the data changed
                saveInFile();
            }
            else if (resultCode == RESULT_FIRST_USER){//if detail activity ask for a edit actitivy or increment or decrement or reset all those actions require a update to the selected counter
                Counter changedCounter;
                changedCounter = data.getParcelableExtra("CHANGED");//get the changed counter from edit activity or the detail activity
                countersList.remove(indexRemoved);
                countersList.add(indexRemoved,changedCounter);//update the changed counter and add it to the same position as the removed old counter
                saveInFile();
            }
        }
    }

    /**
     * Main Actitivy onStart method;
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
       //load the data from the file
        total = (TextView) findViewById(R.id.total);
        total.setText("Total is : " + Integer.toString(countersList.size()));
        //add the total counter  number on the top of the main activity
        adapter = new ArrayAdapter<Counter>(this,
                R.layout.list_item, countersList);
        counterviewList.setAdapter(adapter);
        //set adapter as a countersLit array adapter.
    }


    /**
     * load the date from a file;
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            countersList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            countersList = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    /**
     * save all the changes into a file
     */
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
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}