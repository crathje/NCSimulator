/*
 *  Copyright (C) 2013 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Struct to put the deviations somewhere
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=%2Ftags%2FV0.30g%2Ftimer1.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class DateTime_t extends c_int {

    public u16 Year;
    public u8 Month;
    public u8 Day;
    public u8 Hour;
    public u8 Min;
    public u8 Sec;
    public u16 mSec;
    public u8 valid;

    public DateTime_t() {
        super();
        Year = new u16("Year");
        Month = new u8("Month");
        Day = new u8("Day");
        Hour = new u8("Hour");
        Min = new u8("Min");
        Sec = new u8("Sec");
        mSec = new u16("Secmp");
        valid = new u8("valid");

        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Year);
        allAttribs.add(Month);
        allAttribs.add(Day);
        allAttribs.add(Hour);
        allAttribs.add(Sec);
        allAttribs.add(mSec);
        allAttribs.add(valid);
        
        set(Calendar.getInstance());
    }

    public void set(Calendar c) {
        Year.setValue(c.get(Calendar.YEAR), true);
        Month.setValue(c.get(Calendar.MONTH), true);
        Day.setValue(c.get(Calendar.DAY_OF_WEEK), true);
        Hour.setValue(c.get(Calendar.HOUR_OF_DAY), true);
        Sec.setValue(c.get(Calendar.SECOND), true);
        mSec.setValue(c.get(Calendar.MILLISECOND), true);
        valid.setValue(1, true);
    }
}
