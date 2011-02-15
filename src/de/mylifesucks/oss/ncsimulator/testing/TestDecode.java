/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.Waypoint_t;
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
        String in = "#w=OqjHMzsZdU======MA=>ME==M==============m\r".substring(2);
        
        int[] decoded = Encode.Decode64(in.getBytes(), in.getBytes().length);
      
        Waypoint_t wp = new Waypoint_t("test");
        wp.loadFromInt(decoded, 0);
        wp.printOut();





        System.exit(0);
    }
}
