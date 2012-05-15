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
 * 3D Data Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/waypoints.h
 * @author Claas Anders "CaScAdE" Rathje
 */
public class Motor_t extends c_int {

    public u8 Index;              // to indentify different waypoints, workaround for bad communications PC <-> NC
    public u8 Current;
    public u8 Temperature;
    public u8 MaxPWM;
    public u8 State;

    public Motor_t(int index) {

        allAttribs = new LinkedList<c_int>();
        Index = new u8(index + " Index");
        Index.value=index;
        
        Current = new u8(index + " Current");
        Temperature = new u8(index + " Temperature");
        MaxPWM = new u8(index + " MaxPWM");
        State = new u8(index + " State");

        allAttribs.add(Index);
        allAttribs.add(Current);
        allAttribs.add(Temperature);
        allAttribs.add(MaxPWM);
        allAttribs.add(State);
    }
}
