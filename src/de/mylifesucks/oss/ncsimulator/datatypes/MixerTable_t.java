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
 * Mixer Table Data Struct
 * adapted from: http://svn.mikrokopter.de/filedetails.php?repname=FlightCtrl&path=/tags/V0.82a/eeprom.h
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class MixerTable_t extends c_int {

    u8 MixerRevision = new u8("Mixer Revision");
    u8 Name[] = new u8[12];
    u8 MixerTable[][] = new u8[16][4];

    public MixerTable_t() {
        allAttribs = new LinkedList<c_int>();
        allAttribs.add(MixerRevision);
        MixerRevision.setValue(1, false);
        for (int i = 0; i < Name.length; i++) {
            u8 c = new u8("Name " + i);
            Name[i] = c;
            allAttribs.add(c);
        }
        for (int i = 0; i < MixerTable.length; i++) {
            for (int j = 0; j < MixerTable[i].length; j++) {
                u8 c = new u8("MixerTable " + i + "/" + j);
                MixerTable[i][j] = c;
                allAttribs.add(c);
            }

        }
    }
}
