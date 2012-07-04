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
 * ExternControl Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=%2Ftags%2FV0.84a%2Fuart.h
 * @author Claas Anders "CaScAdE" Rathje
 */
public class str_ExternControl extends c_int {

    public u8 Digital[] = new u8[2];
    public u8 RemoteTasten;
    public s8 Nick;
    public s8 Roll;
    public s8 Gier;
    public u8 Gas;
    public s8 Hight; // nice spelling :)
    public u8 free;
    public u8 Frame;
    public u8 Config;

    public str_ExternControl() {
        super();
        allAttribs = new LinkedList<c_int>();
        for (int i = 0; i < Digital.length; i++) {
            Digital[i] = new u8("Digital" + i);
            allAttribs.add(Digital[i]);
        }
        RemoteTasten = new u8("RemoteTasten");
        allAttribs.add(RemoteTasten);
        Nick = new s8("Nick");
        allAttribs.add(Nick);
        Roll = new s8("Roll");
        allAttribs.add(Roll);
        Gier = new s8("Gier");
        allAttribs.add(Gier);
        Gas = new u8("Gas");
        allAttribs.add(Gas);
        Hight = new s8("Hight");
        allAttribs.add(Hight);
        free = new u8("free");
        allAttribs.add(free);
        Frame = new u8("Frame");
        allAttribs.add(Frame);
        Config = new u8("Config");
        allAttribs.add(Config);
    }
}
