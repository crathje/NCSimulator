/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 3D Data Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/waypoints.h
 * @author Claas Anders "CaScAdE" Rathje
 */
public class Waypoint_t extends c_int {
    public static Waypoint_t[] waypointList = new Waypoint_t[31];

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

        Waypoint_t wp = new Waypoint_t("");
        wp.Position.Status.value = INVALID;
        wp.Position.Latitude.value = 0;
        wp.Position.Longitude.value = 0;
        wp.Position.Altitude.value = 0;
        wp.Heading.value = 361; 		// invalid value
        wp.ToleranceRadius.value = 0;	// in meters, if the MK is within that range around the target, then the next target is triggered
        wp.HoldTime.value = 0;			// in seconds, if the was once in the tolerance area around a WP, this time defines the delay before the next WP is triggered
        wp.Type.value = 255;
        wp.Event_Flag.value = 0;		// future implementation
        //wp.AltitudeRate.value = 0;		// no change of setpoint

        for (int i = 0; i < waypointList.length; i++) {
            wp.Index.value = i;
            waypointList[i]=wp;
        }
    }

    public static void addWP(Waypoint_t wp) {
        waypointList[(int) wp.Index.value-1]=wp;
    }
}
