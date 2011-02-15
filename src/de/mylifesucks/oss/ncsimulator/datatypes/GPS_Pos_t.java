/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.LinkedList;

/**
 * GPS-Data struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/ubx.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class GPS_Pos_t extends c_int {

    public s32 Longitude;// in 1E-7 deg
    public s32 Latitude;// in 1E-7 deg
    public s32 Altitude; // in mm
    public u8Flags Status; // validity of data

    public static final String[] GPS_FLAGS = {"INVALID", "NEWDATA", "PROCESSED", "", "", "", "", ""};

    public GPS_Pos_t(String prefix) {
        super();
        name = prefix;
        Longitude = new s32(prefix + " Longitude");// in 1E-7 deg
        Latitude = new s32(prefix + " Latitude");// in 1E-7 deg
        Altitude = new s32(prefix + " Altitude"); // in mm
        Status = new u8Flags(prefix + " Status", GPS_FLAGS); // validity of data
        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Longitude);
        allAttribs.add(Latitude);
        allAttribs.add(Altitude);
        allAttribs.add(Status);
    }

    @Override
    public int[] getAsInt() {
        int[] ret = c_int.concatArray(Longitude.getAsInt(), Latitude.getAsInt());
        ret = c_int.concatArray(ret, Altitude.getAsInt());
        ret = c_int.concatArray(ret, Status.getAsInt());
        return ret;
    }
}
