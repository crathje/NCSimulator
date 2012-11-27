/*
* Copyright (C) 2012 by Claas Anders "CaScAdE" Rathje
* admiralcascade@gmail.com
* Licensed under: Creative Commons / Non Commercial / Share Alike
* http://creativecommons.org/licenses/by-nc-sa/2.0/de/
*
*/
package de.mylifesucks.oss.ncsimulator.datatypes;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.util.LinkedList;

/**
* 3D Data Struct
* adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=%2Ftags%2FV0.28o%2Fuart1.h
* @author Claas Anders "CaScAdE" Rathje
*/
public class Data3D_t extends c_int {

    public s16 AngleNick = new s16("3D NC AngleNick",-1800,1800);	// in 0.1 deg
    public s16 AngleRoll = new s16("3D NC AngleRoll",-1800,1800); // in 0.1 deg
    public s16 Heading = new s16("3D NC Heading",-1800,1800); // in 0.1 deg
    
    public u8 StickNick = new u8("3D NC StickNick");
    public u8 StickRoll = new u8("3D NC StickRoll");
    public u8 StickYaw = new u8("3D NC StickYaw");
    public u8 StickGas = new u8("3D NC StickGas");
    
    
    public u8 reserve[] = new u8[4];

    public Data3D_t() {
        allAttribs = new LinkedList<c_int>();
        allAttribs.add(AngleNick);
        allAttribs.add(AngleRoll);
        allAttribs.add(Heading);
        allAttribs.add(StickNick);
        allAttribs.add(StickRoll);
        allAttribs.add(StickYaw);
        allAttribs.add(StickGas);
        
        for (int i = 0; i < reserve.length; i++) {
            reserve[i] = new u8("3D NC reserve" + i);
            allAttribs.add(reserve[i]);
        }

        for (c_int c : allAttribs) {
            DataStorage.addToSerializePool(c);
        }
    }
}