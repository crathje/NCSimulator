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
import de.mylifesucks.oss.ncsimulator.datatypes.s8;
import de.mylifesucks.oss.ncsimulator.datatypes.str_ExternControl;
import de.mylifesucks.oss.ncsimulator.datatypes.u16;
import de.mylifesucks.oss.ncsimulator.protocol.CommunicationBase;
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

        Encode e = new Encode(System.out);

        s8[] serialPotis = new s8[12];
        int[] ret = new int[0];
        for (int i = 0; i < serialPotis.length; i++) {
            serialPotis[i] = new s8("Serial Poti " + i);
            serialPotis[i].setValue(-2 * i + 5, false);
            ret = c_int.concatArray(ret, serialPotis[i].getAsInt());
        }
        
        e.send_command(CommunicationBase.FC_ADDRESS, 'y', ret);
        
        System.out.println("+++++++");
        
        u16 echo = new u16("echo");
        echo.value = 123;
        e.send_command(CommunicationBase.NC_ADDRESS, 'Z', echo.getAsInt());


        //e.send_command(CommunicationBase.FC_ADDRESS, 'o', new int[] {10});


        String u16in_raw = "#czBm==Ef\r";
        String u16in = u16in_raw.substring(3); // cut off start sign, address and command
        int[] u16decoded = Encode.Decode64(u16in.getBytes(), u16in.getBytes().length);
        u16 u16indec = new u16("loaded");
        u16indec.loadFromInt(u16decoded, 0);
        System.out.println("u16: " + u16indec.value);

        System.exit(0);



        str_ExternControl ex = new str_ExternControl();
        ex.Nick.value = -106;
        ex.free.value = 139;
        ex.Frame.value = 1;
        e.send_command(CommunicationBase.ANY_ADDRESS, 'b', ex.getAsInt());

        String in = "#ab====b]====?H=M==QU\r";
//        in = "#bQ>RR=mE>>=QC>m]F?]iIW^u[|myL==^===]M>]`cDe>a[=Ja[=U^HRe>A=====>uG=|t[Be=zmQe=Drz\\PFWIbfMNbRRE?>C=====ByQynOgglvaSbfWOqjH==UES^vaV=E=======E>PVTNqWSvdE@Q===>=G[\r".substring(3);

        System.out.println("\ncrc: " + Encode.mkCRC(in.getBytes()));
        in = in.substring(3);
        int[] decoded = Encode.Decode64(in.getBytes(), in.getBytes().length);

//        c_int c = new Waypoint_t("test");
//        c_int c = new paramset_t(5);
//        c_int c = new PPMArray();
        c_int c = new str_ExternControl();



        //System.out.println(decoded[0]);
        c.loadFromInt(decoded, 0);
        c.printOut();



        System.exit(0);
    }
}
