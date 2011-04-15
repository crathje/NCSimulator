/*
 *  Copyright (C) 2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.s16;
import de.mylifesucks.oss.ncsimulator.datatypes.s32;
import java.io.IOException;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class TestLoadFromInt {

    public static void main(String[] args) throws IOException {
        s32 s = new s32("test");
        s.value = 31195136;
        int[] data = s.getAsInt();
        for (int i : data) {
            System.out.println("d: " + i);
        }
        int f = ((int) (((data[3] << 24) & 0xFF000000) | ((data[2] << 16) & 0xFF0000) | ((data[1] << 8) & 0xFF00) | data[0]));
        System.out.println(f);
    }
}
