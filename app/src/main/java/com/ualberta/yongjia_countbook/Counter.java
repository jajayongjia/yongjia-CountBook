package com.ualberta.yongjia_countbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.data;

/**
 * Created by yongjiahuang on 2017-09-22.
 */

public class Counter implements Parcelable {

    /***************************************************************/
    //instances field;
    private String name;
    private Date date;
    private int currentValue;
    private int initialValue;
    private String comment;

    /***************************************************************/

    /*********************************************************************************/
    //constructor field;
    public Counter(){
        date = new java.util.Date();
    }

    public Counter(String name, int initialValue){
        this.name = name;
        this.initialValue = initialValue;
        date =  new java.util.Date();
        currentValue = initialValue;
        comment = "";
    }

    public Counter(String name, int initialValue,String comment){
        this.name = name;
        this.initialValue = initialValue;
        date =  new java.util.Date();
        currentValue = initialValue;
        this.comment = comment;

    }

    // parcel interface
    public Counter(Parcel parcel){
        this.name = parcel.readString();
        this.comment = parcel.readString();
        this.initialValue = parcel.readInt();
        this.currentValue = parcel.readInt();
        long tmpDate = parcel.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);


    }

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(this.name);
        parcel.writeString(this.comment);
        parcel.writeInt(this.initialValue);
        parcel.writeInt(this.currentValue);
        parcel.writeLong(date != null ? date.getTime() : -1);
    }

    public static final Parcelable.Creator<Counter> CREATOR = new Parcelable.Creator<Counter>(){
        @Override
        public Counter createFromParcel(Parcel parcel){
            return new Counter(parcel);
        }
        @Override
        public Counter[] newArray(int i){
            return new Counter[i];
        }

    };


    /**********************************************************************************/



    /****************************************************************************************************/
    //method field;

    //getter and setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDate(){
        return date.toString();
    }
    public void setDate(){

        this.date = new java.util.Date();
    }
    public int getCurrentValue(){
        return currentValue;
    }
    public void setCurrentValue(int currentValue){
        this.currentValue = currentValue;
    }
    public int getInitialValue(){
        return initialValue;
    }
    public void setInitialValue(int initialValue){
        this.initialValue = initialValue;
    }
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    /****************************************************************************************************/

    //other methods:

    @Override
    public String toString(){
        return name+" | "+date.toString()+" | Count:"+String.valueOf(currentValue);
    }

}
