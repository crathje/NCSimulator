/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Waypoint Data Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=/tags/V0.22a/waypoints.h
 *
 * @author Frank Blumenberg
 */
public class Waypoint_t extends c_int implements Observer {

    public GPS_Pos_t Position;             // the gps position of the waypoint, see ubx.h for details
    public s16 Heading;                    // orientation, future implementation
    public u8 ToleranceRadius;    // in meters, if the MK is within that range around the target, then the next target is triggered
    public u8 HoldTime;                   // in seconds, if the was once in the tolerance area around a WP, this time defines the delay before the next WP is triggered
    public u8 Event_Flag;                 // future implementation
    public u8 Index;              // to indentify different waypoints, workaround for bad communications PC <-> NC
    public u8 Type;                               // typeof Waypoint
    public u8 WP_EventChannelValue;  //
    public u8 AltitudeRate;
    public u8 Speed;
    public u8 CameraAngle;
    public u8 reserve[];             // reserve
    public static final int INVALID = 0x00;
    public static final int NEWDATA = 0x01;
    public static final int PROCESSED = 0x02;
    public static final int POINT_TYPE_INVALID = 255;
    public static final int POINT_TYPE_WP = 0;
    public static final int POINT_TYPE_POI = 1;

    public Waypoint_t(String prefix, int idx) {
        this(prefix);
        Index.value = idx;
    }

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
        AltitudeRate = new u8(prefix + " AltitudeRate");
        Speed = new u8(prefix + " Speed");
        CameraAngle = new u8(prefix + " CameraAngle");
        reserve = new u8[6];
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
        allAttribs.add(AltitudeRate);
        allAttribs.add(Speed);
        allAttribs.add(CameraAngle);
        allAttribs.addAll(Arrays.asList(reserve));

        for (c_int c : allAttribs) {
            c.addObserver(this);
        }
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers();
    }

    public void clearData() {
        Position.Status.value = Waypoint_t.INVALID;
        Position.Latitude.value = 0;
        Position.Longitude.value = 0;
        Position.Altitude.value = 0;
        Heading.value = 361;
        ToleranceRadius.value = 0;
        HoldTime.value = 0;
        Type.value = Waypoint_t.POINT_TYPE_INVALID;
        Event_Flag.value = 0;
        AltitudeRate.value = 0;
        Speed.value = 30;
        CameraAngle.value = 0;
    }

    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {
        if (nameLabel == null) {
            initComponent();
            Position.Longitude.initComponent();
            Position.Latitude.initComponent();
            Position.Altitude.initComponent();
            Heading.initComponent();
            ToleranceRadius.initComponent();
            HoldTime.initComponent();
            Type.initComponent();
            Event_Flag.initComponent();
            AltitudeRate.initComponent();
            Speed.initComponent();
            CameraAngle.initComponent();

        }

        gbc.gridx = 0;
        panel.add(Position.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Position.Longitude.valueField, gbc);
        gbc.gridx++;
        panel.add(Position.Latitude.valueField, gbc);
        gbc.gridx++;
        panel.add(Position.Altitude.valueField, gbc);
        gbc.gridx++;
        panel.add(Heading.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Heading.valueField, gbc);
        gbc.gridx++;
        panel.add(ToleranceRadius.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(ToleranceRadius.valueField, gbc);
        gbc.gridx++;

        gbc.gridy++;
        gbc.gridx = 0;

        panel.add(HoldTime.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(HoldTime.valueField, gbc);
        gbc.gridx++;

        panel.add(Type.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Type.valueField, gbc);
        gbc.gridx++;

        panel.add(Event_Flag.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Event_Flag.valueField, gbc);
        gbc.gridx++;

        panel.add(AltitudeRate.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(AltitudeRate.valueField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;

        panel.add(Speed.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(Speed.valueField, gbc);
        gbc.gridx++;

        panel.add(CameraAngle.getNameLabel(), gbc);
        gbc.gridx++;
        panel.add(CameraAngle.valueField, gbc);
        gbc.gridx++;

/*
//                System.out.println(getNameLabel());

            allAttribs.add(Position);
            allAttribs.add(Heading);
            allAttribs.add(ToleranceRadius);
            allAttribs.add(HoldTime);
            allAttribs.add(Event_Flag);
            allAttribs.add(Index);
            allAttribs.add(Type);
            allAttribs.add(WP_EventChannelValue);
            allAttribs.add(AltitudeRate);
            allAttribs.add(Speed);
            allAttribs.add(CameraAngle);
            allAttribs.addAll(Arrays.asList(reserve));


            gbc.gridx++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(valueField, gbc);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx++;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(slider, gbc);
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            */
        gbc.gridy++;
    }

}
