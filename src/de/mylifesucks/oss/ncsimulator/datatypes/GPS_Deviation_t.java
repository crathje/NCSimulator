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
 * Struct to put the deviations somewhere
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/GPS.c
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class GPS_Deviation_t extends c_int {

    public u8 Status; // invalid, newdata, processed
    public s32 North;		// in cm
    public s32 East;		// in cm
    public s32 Bearing;	// in deg
    public s32 Distance;	// in cm

    public GPS_Deviation_t() {
        super();
        Status = new u8("temp");
        North = new s32("temp");
        East = new s32("temp");
        Bearing = new s32("temp");
        Distance = new s32("temp");

        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Status);
        allAttribs.add(North);
        allAttribs.add(East);
        allAttribs.add(Bearing);
        allAttribs.add(Distance);
    }
};
