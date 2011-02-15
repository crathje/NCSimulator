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
public class Waypoint_t extends c_int {
    public static LinkedList<Waypoint_t> waypointList = new LinkedList<Waypoint_t>();

    public GPS_Pos_t Position;             // the gps position of the waypoint, see ubx.h for details
    public s16 Heading;                    // orientation, future implementation
    public u8 ToleranceRadius;    // in meters, if the MK is within that range around the target, then the next target is triggered
    public u8 HoldTime;                   // in seconds, if the was once in the tolerance area around a WP, this time defines the delay before the next WP is triggered
    public u8 Event_Flag;                 // future implementation
    public u8 Index;              // to indentify different waypoints, workaround for bad communications PC <-> NC
    public u8 Type;                               // typeof Waypoint
    public u8 WP_EventChannelValue;  //
    public u8 reserve[];             // reserve
    public static final int INVALID = 0x00;
    public static final int NEWDATA = 0x01;
    public static final int PROCESSED = 0x02;

    public Waypoint_t(String prefix) {
        super();
        name = prefix;
        allAttribs = new LinkedList<c_int>();
        Position = new GPS_Pos_t(prefix + " Position");
        Heading = new s16(prefix + " Heading");
        ToleranceRadius = new u8(prefix + " ToleranceRadius");
        HoldTime = new u8(prefix + " HoldTime");
        Event_Flag = new u8(prefix + " Event_Flag");
        Index = new u8(prefix + " Index");
        Type = new u8(prefix + " Type");
        WP_EventChannelValue = new u8(prefix + " WP_EventChannelValue");
        reserve = new u8[9];
        for (int i = 0; i < reserve.length; i++) {
            reserve[i] = new u8(prefix + "" + i);
        }
        allAttribs.add(Position);
        allAttribs.add(Heading);
        allAttribs.add(ToleranceRadius);
        allAttribs.add(HoldTime);
        allAttribs.add(Event_Flag);
        allAttribs.add(Index);
        allAttribs.add(Type);
        allAttribs.add(WP_EventChannelValue);
        for (int i = 0; i < reserve.length; i++) {
            allAttribs.add(reserve[i]);
        }
    }

    public static void clearWP() {
        waypointList.clear();
    }
    public static void addWP(Waypoint_t wp) {
        waypointList.add(wp);
    }
}
