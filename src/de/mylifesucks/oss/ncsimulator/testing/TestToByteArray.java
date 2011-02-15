/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.datatypes.s32;
import java.io.IOException;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class TestToByteArray {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        s32 s = new s32("test");
        s.value = 12344;
        int[] si = s.getAsInt();
        byte[] sb = c_int.getbytes(s.value);

        for (int i : si) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = sb.length - 1; i > sb.length - 5;  i--) {
            System.out.print((int)sb[i] + " ");
        }
        System.out.println();


        System.exit(0);
    }
}
