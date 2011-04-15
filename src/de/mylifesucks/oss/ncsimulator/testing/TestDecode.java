/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.PPMArray;
import de.mylifesucks.oss.ncsimulator.datatypes.Waypoint_t;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.datatypes.paramset_t;
import de.mylifesucks.oss.ncsimulator.protocol.Encode;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class TestDecode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String in = "#bQ>RQ@=]AA>MUD?=aG?mngD^x|@my=?A==?>=C?KU[]CNu=sNu>_AjS]AM=====D]e@|ZuS]@t>O]=\\TrzIbeoSb>CRRQ]EAU=====TnPpBGgg|cNWSbfHOqi=>]^WDcNa=]=======]JPVTNqWSvdE@Q=====FW\r".substring(3);
//        in = "#bQ>RR=mE>>=QC>m]F?]iIW^u[|myL==^===]M>]`cDe>a[=Ja[=U^HRe>A=====>uG=|t[Be=zmQe=Drz\\PFWIbfMNbRRE?>C=====ByQynOgglvaSbfWOqjH==UES^vaV=E=======E>PVTNqWSvdE@Q===>=G[\r".substring(3);

        int[] decoded = Encode.Decode64(in.getBytes(), in.getBytes().length);

//        c_int c = new Waypoint_t("test");
        c_int c = new paramset_t(5);
//        c_int c = new PPMArray();

        System.out.println(decoded[0]);
        c.loadFromInt(decoded, 1);
        c.printOut();



        System.exit(0);
    }
}
