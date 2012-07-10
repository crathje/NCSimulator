/*
 *  Copyright (C) 2012 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.LinkedList;

/**
 * BLDataStruct
 * adapted from: http://www.mikrokopter.de/ucwiki/en/SerialCommands/BLDataStruct
 * @author Claas Anders "CaScAdE" Rathje
 */
public class BLData_t extends c_int {

    public u8 Index;
    public u8 Current;
    public u8 Temperature;
    public u8 MaxPWM;
    public u8Flags Status;

    public BLData_t(int index) {

        Index = new u8(index + "Index");
        Current = new u8(index + "Current");
        Temperature = new u8(index + "Temperature");
        MaxPWM = new u8(index + "MaxPWM");
        Status = new u8Flags(index + "Status", new String[]{"?", "?", "?", "?", "?", "?", "?", "?"});

        Index.setValue(index, false);
        Status.setValue(128, false); // enable report?

        allAttribs = new LinkedList<c_int>();
        allAttribs.add(Index);
        allAttribs.add(Current);
        allAttribs.add(Temperature);
        allAttribs.add(MaxPWM);
        allAttribs.add(Status);

//
//        for (c_int c : allAttribs) {
//            DataStorage.addToSerializePool(c);
//        }
    }
}
