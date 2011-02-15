/*
 *  Copyright (C) 2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.s16;
import java.io.IOException;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class TestLoadFromInt {

    public static void main(String[] args) throws IOException {
        s16 s = new s16("test");
        s.value = 123;
        int[] data = s.getAsInt();
        short f = ((short) (((data[1] << 8) & 0xFF00) | data[0]));
        System.out.println(f);
    }
}
