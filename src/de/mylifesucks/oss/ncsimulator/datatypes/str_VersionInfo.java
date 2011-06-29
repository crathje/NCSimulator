/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.util.LinkedList;

/**
 * version Info Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=/tags/V0.82a/uart.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class str_VersionInfo extends c_int {

    public u8 SWMajor;
    public u8 SWMinor;
    public u8 ProtoMajor;
    public u8 ProtoMinor;
    public u8 SWPatch;
    public u8 HardwareError[] = new u8[5];
    String prefix;

    public str_VersionInfo(String prefix) {
        this.prefix = prefix;
        SWMajor = new u8(prefix + " " + "SWMajor");
        SWMinor = new u8(prefix + " " + "SWMinor");
        ProtoMajor = new u8(prefix + " " + "ProtoMajor");
        ProtoMinor = new u8(prefix + " " + "ProtoMinor");
        SWPatch = new u8(prefix + " " + "SWPatch");
        for (int i = 0; i < HardwareError.length; i++) {
            HardwareError[i] = new u8(prefix + " " + "HardwareError " + i);
        }

        allAttribs = new LinkedList<c_int>();
        allAttribs.add(SWMajor);
        allAttribs.add(SWMinor);
        allAttribs.add(ProtoMajor);
        allAttribs.add(ProtoMinor);
        allAttribs.add(SWPatch);
        allAttribs.add(HardwareError[0]);
        allAttribs.add(HardwareError[1]);
        allAttribs.add(HardwareError[2]);
        allAttribs.add(HardwareError[3]);
        allAttribs.add(HardwareError[4]);


        for (c_int c : allAttribs) {
            DataStorage.addToSerializePool(c);
        }
    }
};
