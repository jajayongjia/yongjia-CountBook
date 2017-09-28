/*
 * Counter
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

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class describes a Counter object that are used through Counter Book application
 * this object Counter implements a spectial interface called Parcelable,
 * this object can be passed between different activities since it implements Parcelable interface
 * the example I followed : https://developer.android.com/reference/android/os/Parcelable.html
 * <br>
 * @author Yongjia Huang
 * @version 1.0
 * @see Parcelable
 * @since 1.0
 */

public class Counter implements Parcelable {

    /**
     *
     */
    private String name;
    private Date date;
    private int currentValue;
    private int initialValue;
    private String comment;

    /**
     *generate a Counter object with no parameter
     */
    public Counter(){
        date = new java.util.Date();
    }


    /**
     * Constructs a Counter object
     * @param name Counter's name
     * @param initialValue Counter's initial value
     */
    public Counter(String name, int initialValue){
        this.name = name;
        this.initialValue = initialValue;
        date =  new java.util.Date();
        currentValue = initialValue;
        comment = "";
    }
    /**
     * Constructs a Counter object
     * @param name Counter's name
     * @param initialValue Counter's initial value
     * @param comment Counter's comment
     */

    public Counter(String name, int initialValue,String comment){
        this.name = name;
        this.initialValue = initialValue;
        date =  new java.util.Date();
        currentValue = initialValue;
        this.comment = comment;

    }

    /**
     * Constructs a Counter object
     * @param parcel interface parcel that allows pass counter between activities
     */
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
    /**
     * Constructs a Counter object;
     * @param parcel interface parcel that allows pass counter between activities
     */
    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(this.name);
        parcel.writeString(this.comment);
        parcel.writeInt(this.initialValue);
        parcel.writeInt(this.currentValue);
        parcel.writeLong(date != null ? date.getTime() : -1);
    }

    /**
     * interface Parceable;
     */

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

    /**
     * Getter method;
     * @return name - Counter's name;
     */
    public String getName(){
        return name;
    }

    /**
     * Setter method;
     * @param name Counter's name;
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter method;
     * @return date - Counter's date;
     */
    public String getDate(){
        //return date.toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        return format;
    }

    /**
     * Setter method;
     * set date to current date;
     */
    public void setDate(){
        this.date = new java.util.Date();
    }

    /**
     * Getter method;
     * @return currentValue - Counter's counter value;
     */
    public int getCurrentValue(){
        return currentValue;
    }

    /**
     * Setter method;
     * @param currentValue Counter's current Value;
     */
    public void setCurrentValue(int currentValue){
        this.currentValue = currentValue;
    }

    /**
     * Getter method;
     * @return initialValue - Counter's initial Value;
     */
    public int getInitialValue(){
        return initialValue;
    }

    /**
     * Setter method;
     * @param initialValue Counter's initial Value;
     */
    public void setInitialValue(int initialValue){
        this.initialValue = initialValue;
    }

    /**
     * Getter method;
     * @return comment - Counter's comment;
     */
    public String getComment(){
        return comment;
    }

    /**
     * Setter method;
     * @param comment Counter's comment;
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Let Counter be Printable;
     * @return String - Counter's Name + date + current Value;
     */
    @Override
    public String toString(){
        return name+" | "+this.getDate()+" | Count:"+String.valueOf(currentValue);
    }

}
