package com.github.ignaciotcrespo.randomobject.otherpackage;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by crespo on 2/20/17.
 */
public class Pojo extends PojoParent {
    public String text;
    public boolean flag;
    public byte numByte;
    public short numShort;
    public int numInt;
    public long numLong;
    public float numFloat;
    public double numDouble;
    public Boolean flagObj;
    public Byte numByteObj;
    public Short numShortObj;
    public Integer numIntObj;
    public Long numLongObj;
    public Float numFloatObj;
    public Double numDoubleObj;
    public Date date;
    public Calendar calendar;
    public Pojo pojo;
    public PojoEnum enumObj;


    @Override
    public String toString() {
        return "Pojo{" + "\n" +
                "grandparent_textDefault='" + text + '\'' + "\n" +
                ", flag=" + flag + "\n" +
                ", numByte=" + numByte + "\n" +
                ", numShort=" + numShort + "\n" +
                ", numInt=" + numInt + "\n" +
                ", numLong=" + numLong + "\n" +
                ", numFloat=" + numFloat + "\n" +
                ", numDouble=" + numDouble + "\n" +
                ", flagObj=" + flagObj + "\n" +
                ", numByteObj=" + numByteObj + "\n" +
                ", numShortObj=" + numShortObj + "\n" +
                ", numIntObj=" + numIntObj + "\n" +
                ", numLongObj=" + numLongObj + "\n" +
                ", numFloatObj=" + numFloatObj + "\n" +
                ", numDoubleObj=" + numDoubleObj + "\n" +
                ", date=" + date + "\n" +
                ", calendar=" + calendar + "\n" +
                ", pojo=" + pojo + "\n" +
                ", pojoEnum=" + enumObj + "\n" +
                '}';
    }
}
