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
 * GPS Position Deviation Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/uart1.h
 * @author Claas Anders "CaScAdE" Rathje
 */
public class GPS_PosDev_t extends c_int {

    public u16 Distance;        // in 1E-7 deg
    public s16 Bearing;        // in 1E-7 deg

    public GPS_PosDev_t(String prefix) {
        Distance = new u16(prefix + " Distance");
        Bearing = new s16(prefix + " Bearing");
        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Distance);
        allAttribs.add(Bearing);

    }

    @Override
    public int[] getAsInt() {
        return c_int.concatArray(Distance.getAsInt(), Bearing.getAsInt());
    }
}
