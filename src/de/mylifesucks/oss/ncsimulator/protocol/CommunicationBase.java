/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com Licensed under: Creative Commons / Non Commercial /
 * Share Alike http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.protocol;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.datatypes.BLData_t;
import de.mylifesucks.oss.ncsimulator.datatypes.Waypoint_t;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.datatypes.s8;
import de.mylifesucks.oss.ncsimulator.gui.LogPanel;
import java.io.*;
import java.util.*;
//import javax.comm.*;
import gnu.io.*;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class CommunicationBase {

    // slave addresses http://svn.mikrokopter.de/filedetails.php?repname=NaviCtrl&path=%2Ftags%2FV0.20c%2Fmkprotocol.h
    public static final int ANY_ADDRESS = 0;
    public static final int FC_ADDRESS = 1;
    public static final int NC_ADDRESS = 2;
    public static final int MK3MAG_ADDRESS = 3;
    public static final int MKOSD_ADDRESS = 4;
    public static final int BL_ADDRESS = 5;
    InputStream inputStream;
    static OutputStream outputStream;

    public OutputStream getOutputStream() {
        return outputStream;
    }
    public static final int MAX_EMPFANGS_BUFF = 190;
    volatile int RxdBuffer[] = new int[MAX_EMPFANGS_BUFF];
    int crc;
    int crc1, crc2, buf_ptr;
    char UartState = 0;
    boolean CrcOkay = false;
    volatile boolean NeuerDatensatzEmpfangen = false;
    int AnzahlEmpfangsBytes = 0;

    protected void USART0_RX_vect(int SioTmp) {
        if (buf_ptr >= MAX_EMPFANGS_BUFF) {
            UartState = 0;
//            System.out.println("overflow");
        }
        if (SioTmp == '\r' && UartState == 2) {
            UartState = 0;
            crc -= RxdBuffer[buf_ptr - 2];
            crc -= RxdBuffer[buf_ptr - 1];
            crc %= 4096;
            crc1 = '=' + crc / 64;
            crc2 = '=' + crc % 64;
            CrcOkay = false;
            if ((crc1 == RxdBuffer[buf_ptr - 2]) && (crc2 == RxdBuffer[buf_ptr - 1])) {
                CrcOkay = true;
            } else {
                CrcOkay = false;

            }
            if (!NeuerDatensatzEmpfangen && CrcOkay) { // Datensatz schon verarbeitet

                NeuerDatensatzEmpfangen = true;
                AnzahlEmpfangsBytes = buf_ptr + 1;
                RxdBuffer[buf_ptr] = '\r';

                /*if (RxdBuffer[2] == 'R') {
                LcdClear();
                wdt_enable(WDTO_250MS); // Reset-Commando
                ServoActive = 0;
                
                }*/

                final int RxdBuffer_work[] = new int[MAX_EMPFANGS_BUFF];
                System.arraycopy(RxdBuffer, 0, RxdBuffer_work, 0, RxdBuffer.length);
                // thread away the data handling 
                DataStorage.executors.submit(new Runnable() {

                    public void run() {

                        BearbeiteRxDaten(RxdBuffer_work);
                    }
                });
            } else {
//                System.out.println("NeuerDatensatzEmpfangen: " + NeuerDatensatzEmpfangen + " CrcOkay: " + CrcOkay);
            }
        } else {
            switch (UartState) {
                case 0:
                    if (SioTmp == '#' && !NeuerDatensatzEmpfangen) {
                        UartState = 1;  // Startzeichen und Daten schon verarbeitet
                    }
                    buf_ptr = 0;
                    RxdBuffer[buf_ptr++] = SioTmp;
                    crc = SioTmp;
                    break;
                case 1: // Adresse auswerten
                    UartState++;
                    RxdBuffer[buf_ptr++] = SioTmp;
                    crc += SioTmp;
                    break;
                case 2: //  Eingangsdaten sammeln
                    RxdBuffer[buf_ptr] = SioTmp;
                    if (buf_ptr < MAX_EMPFANGS_BUFF) {
                        buf_ptr++;
                    } else {
                        UartState = 0;
                    }
                    crc += SioTmp;
                    break;
                default:
                    UartState = 0;
                    break;
            }
        }
    }
    int RxDataLen = 0;
    int pRxData = 0;

    public void Decode64(int[] RxdBuffer) {// die daten werden im rx buffer dekodiert, das geht nur, weil aus 4 byte immer 3 gemacht werden.
        int a, b, c, d;
        int x, y, z;
        int ptrIn = 3; // start at begin of data block
        int ptrOut = 3;
        int len = AnzahlEmpfangsBytes - 6; // von der Gesamtbytezahl eines Frames gehen 3 Bytes des Headers  ('#',Addr, Cmd) und 3 Bytes des Footers (CRC1, CRC2, '\r') ab.


        while (len != 0) {
            a = 0;
            b = 0;
            c = 0;
            d = 0;
            try {
                a = RxdBuffer[ptrIn++] - '=';
                b = RxdBuffer[ptrIn++] - '=';
                c = RxdBuffer[ptrIn++] - '=';
                d = RxdBuffer[ptrIn++] - '=';
            } catch (Exception e) {
            }

            x = (a << 2) | (b >> 4);
            y = ((b & 0x0f) << 4) | (c >> 2);
            z = ((c & 0x03) << 6) | d;

            if ((len--) != 0) {
                RxdBuffer[ptrOut++] = x;
            } else {
                break;
            }
            if ((len--) != 0) {
                RxdBuffer[ptrOut++] = y;
            } else {
                break;
            }
            if ((len--) != 0) {
                RxdBuffer[ptrOut++] = z;
            } else {
                break;
            }
        }
        pRxData = 3; // decodierte Daten beginnen beim 4. Byte
        RxDataLen = ptrOut - 3;  // wie viele Bytes wurden dekodiert?

    }

    public void BearbeiteRxDaten(int[] RxdBuffer) {
        if (!NeuerDatensatzEmpfangen) {
//             System.out.println("NeuerDatensatzEmpfangen: " + NeuerDatensatzEmpfangen );
            return;
        }

        DataStorage.statusBar.uartRX.toggle();

        String out = "";
        for (int i = pRxData; i < AnzahlEmpfangsBytes; i++) {
            out += (char) RxdBuffer[i];
        }
//        System.out.println(out);

        if (LogPanel.showInput.isSelected()) {
            LogPanel.giveMessage(out, LogPanel.green);
        }

//        int i = 0;
//        while (i < AnzahlEmpfangsBytes) {
//            System.out.print((char) RxdBuffer[i]);
//            i++;
//        }
//        System.out.println();
//        System.out.print(" decoded: ");
        Decode64(RxdBuffer);

        try {

//        i = pRxData;
//        while (i < RxDataLen) {
//            System.out.print((char) RxdBuffer[i]);
//            i++;
//        }
//        System.out.println();
//        System.out.print("\traw int: ");
//        i = pRxData;
//        while (i < RxDataLen) {
//            System.out.print(RxdBuffer[i]);
//            i++;
//        }
//        System.out.println();

            if (outputStream != null) {
                int tempchar = 0;

//            System.out.println("command for: " + (RxdBuffer[1] - 'a') + " -> " + (char)RxdBuffer[2]);


                switch (DataStorage.UART) {
                    case NC:
                        switch (RxdBuffer[1] - 'a') {
                            case CommunicationBase.NC_ADDRESS:
                                switch (RxdBuffer[2]) {
                                    case 'c': // request for 3d info
                                        //System.out.println("3D from NC");
                                        if ((DataStorage.data3d_t.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'C', DataStorage.data3d_t.getAsInt());
                                        }
                                        break;
                                    case 'z': // connection checker
//                                    System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " connection checker ");
                                        break;
                                    case 'e': // request for the text of the error status
//                                    System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " request for the text of the error status ");
                                        break;
                                    case 's'://  new target position
                                        System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " new target position");
                                        break;
                                    case 't'://  SystemTime
                                        // force current date
                                        DataStorage.SystemTime.set(Calendar.getInstance());
                                        DataStorage.encoder.send_command(NC_ADDRESS, 'T', DataStorage.SystemTime.getAsInt());
                                        break;
                                    case 'u': // redirect debug uart
                                        switch (RxdBuffer[pRxData]) {
                                            case 0:
                                                DataStorage.setUART(DataStorage.UART_CONNECTION.FC);
                                                break;
                                            case 1:
                                                DataStorage.setUART(DataStorage.UART_CONNECTION.MK3MAG);
                                                break;
                                            case 2:
                                                DataStorage.setUART(DataStorage.UART_CONNECTION.MKGPS);
                                                break;
                                        }
                                        //System.out.println(" switched uart to " + DataStorage.UART.name());
                                        break;
                                    case 'w'://  Append Waypoint to List
//                                    System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " Append Waypoint to List");
//                                    System.out.println(out);

                                        Waypoint_t rec = new Waypoint_t("recieved WP");
                                        rec.loadFromInt(RxdBuffer, pRxData);


                                        System.out.println("recieved WP " + rec.Index.value);
//
                                        if ((rec.Position.Status.value == Waypoint_t.INVALID) && (rec.Index.value == 0)) {
                                            System.out.println("Clear WP");
                                            DataStorage.clearWP();
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'W', new int[]{(int) rec.Index.value});
                                        } else {
                                            System.out.println("Set WP " + rec.Index.value);

                                            if (rec.Index.value > 32) {
                                                System.out.println("Invalid index " + rec.Index.value);
                                                rec.Index.value = 254;
                                            } else {
                                                DataStorage.waypointList[(int) (rec.Index.value - 1)].loadFromInt(RxdBuffer, pRxData);
                                            }
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'W', new int[]{(int) rec.Index.value});
                                        }
//                                    rec.printOut();
                                        break;
                                    case 'x'://  Read Waypoint from List
                                        System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " Read Waypoint from List");
                                        int index = RxdBuffer[pRxData];
                                        System.out.println("Read index " + index);
                                        if (index <= DataStorage.waypointList.length) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'X', c_int.concatArray(new int[]{DataStorage.waypointList.length, index}, DataStorage.waypointList[index - 1].getAsInt()));
                                        } else {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'X', new int[]{DataStorage.waypointList.length});
                                        }
                                        break;
                                    case 'j':// Set/Get NC-Parameter
//                                    System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " Set/Get NC-Parameter");
                                        break;
                                    case 'O':// OSD Dataset from someone else
                                        //System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " OSD Data from elsewhere");
                                        DataStorage.naviData.loadFromInt(RxdBuffer, pRxData);
                                        DataStorage.coordVizualizer.update();
                                        break;
                                    default:
                                        //System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " unsupported command recieved");
                                        // unsupported command recieved
                                        break;
                                }
                            // "break;" is missing here to fall thru to the common commands

                            default:
                                switch (RxdBuffer[2]) {
                                    case 'a':// request for the labels of the analog debug outputs
                                        //System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " request for the labels of the analog debug outputs");
                                        int index = RxdBuffer[pRxData];
                                        if (index > 31) {
                                            index = 31;
                                        }
                                        DataStorage.encoder.send_command(NC_ADDRESS, 'A', DataStorage.NCDebugOut.Analog[index].getLabelArray());
                                        break;
                                    case 'd': // request for debug data
                                        //System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " request for debug data");
                                        if ((DataStorage.NCDebugOut.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'D', DataStorage.NCDebugOut.getAsInt());
                                        }
                                        break;
                                    case 'c': // request for 3D data;
                                        if ((DataStorage.str_Data3D.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'C', DataStorage.str_Data3D.getAsInt());
                                        }
                                        break;
                                    case 'h':// reqest for display line
                                        if ((DataStorage.lcddata.requestTime = RxdBuffer[pRxData + 1] * 10) > 0) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'H', DataStorage.lcddata.getAsInt());
                                        }
                                        break;
                                    case 'k':// BL Ctrl Status
                                        int blNum = RxdBuffer[pRxData + 1];
                                        blNum = blNum % DataStorage.bldata_t.length;

                                        if ((DataStorage.bldata_t[0].requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            //System.out.println("BLDATA_TIME: " + RxdBuffer[pRxData] * 10 + " sending " + blNum);
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'K', DataStorage.bldata_t[blNum].getAsInt());
                                            // System.out.println("sending " + blNum + " with " + DataStorage.bldata_t[blNum].Temperature);
//                                         for (BLData_t bld : DataStorage.bldata_t) {                                            
//                                            DataStorage.encoder.send_command(NC_ADDRESS, 'K', bld.getAsInt());
//                                       }
                                        }
                                        //  System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " BL Ctrl Status from #" + blNum);
                                        break;
                                    case 'l':// reqest for display columns
//                                    System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " reqest for display columns");
                                        DataStorage.encoder.send_command(NC_ADDRESS, 'L', DataStorage.lcddata.getAsInt());

                                        break;
                                    case 'o': // request for navigation information
                                        if ((DataStorage.naviData.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(NC_ADDRESS, 'O', DataStorage.naviData.getAsInt());
                                        }
                                        break;
                                    case 'v': // request for version info
                                        DataStorage.encoder.send_command(NC_ADDRESS, 'V', DataStorage.NCversion.getAsInt());
                                        break;
                                    case 'D':// Debug Dataset from someone else
                                        //System.out.println(DataStorage.UART.name() + ":" + (RxdBuffer[1] - 'a') + (char) RxdBuffer[2] + " DEBUG Data from elsewhere");
                                        DataStorage.FCDebugOut.loadFromInt(RxdBuffer, pRxData);
                                        break;
                                }
                                break;
                        }
                        break;
                    case FC:
                        switch (RxdBuffer[1] - 'a') {
                            case CommunicationBase.FC_ADDRESS:
                                switch (RxdBuffer[2]) {
                                    case 'K':// Kompasswert
                                        break;
                                    case 't':// Motortest
                                        break;
                                    case 'n':// "Get Mixer
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'N', DataStorage.mixerset.getAsInt());
                                        break;
                                    case 'm':// "Write Mixer
                                        break;
                                    case 'p': // get PPM Channels
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'P', DataStorage.ppmarray.getAsInt());
                                        break;
                                    case 'q':// "Get"-Anforderung für Settings
                                        int para = RxdBuffer[pRxData];
                                        if (para == 0xFF) {
                                            para = DataStorage.activeParamset;
                                        }

                                        if (para > 5) {
                                            para = 5;
                                        }
                                        //System.out.println(" setting from FC " + para);
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'Q', DataStorage.paramset[para - 1].getAsInt());
                                        break;
                                    case 's': // Parametersatz speichern
//                                    System.out.println(" setting to FC ");
                                        if (1 <= RxdBuffer[pRxData] && RxdBuffer[pRxData] <= 5) {
                                            tempchar = RxdBuffer[pRxData];

                                            DataStorage.activeParamset = tempchar;
                                            System.out.println("Set active setting to " + tempchar);
                                            //tempchar1 = GetActiveParamSet();
//                                        System.out.println(RxDataLen + " RxDataLen");
//                                        for (int i : RxdBuffer) {
//                                            System.out.print(i + ": " + ((char)i) + "  ");
//                                        }
//                                        System.out.println();
                                            DataStorage.paramset[tempchar - 1].loadFromInt(RxdBuffer, pRxData + 1);
                                        } else {
                                            tempchar = 0;
                                        }
                                        //SendOutData('S', FC_ADDRESS, 1, &tempchar1, sizeof(tempchar1));
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'S', new int[]{tempchar});
                                        break;
                                    case 'f': // auf anderen Parametersatz umschalten
                                    {
                                        tempchar = RxdBuffer[pRxData];
                                        System.out.println("Set active setting to " + tempchar);
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'F', new int[]{tempchar});
                                    }
                                    break;
                                    case 'y':// serial Potis
//                                    PPM_in[13] = (signed char) pRxData[0]; PPM_in[14] = (signed char) pRxData[1]; PPM_in[15] = (signed char) pRxData[2]; PPM_in[16] = (signed char) pRxData[3];
//                                    PPM_in[17] = (signed char) pRxData[4]; PPM_in[18] = (signed char) pRxData[5]; PPM_in[19] = (signed char) pRxData[6]; PPM_in[20] = (signed char) pRxData[7];
//                                    PPM_in[21] = (signed char) pRxData[8]; PPM_in[22] = (signed char) pRxData[9]; PPM_in[23] = (signed char) pRxData[10]; PPM_in[24] = (signed char) pRxData[11];
                                        s8 tempVal = new s8("temp");
                                        for (int i = 0; i < 12; i++) {
                                            tempVal.loadFromInt(RxdBuffer, pRxData + i);
//                                        System.out.println("serial Poti value: " + tempVal.value);
                                            DataStorage.ppmarray.PPMArray[13 + i].setValue(tempVal.value, false);
                                        }
                                        break;
                                    case 'u': // request BL parameter
                                        break;
                                    case 'w': // write BL parameter
                                        break;
                                }
                            default:
                                switch (RxdBuffer[2]) {
                                    // 't' comand placed here only for compatibility to BL
                                    case 't':// Motortest
                                        break;
                                    // 'K' comand placed here only for compatibility to old MK3MAG software, that does not send the right Slave Address
                                    case 'K':// Kompasswert
                                        break;
                                    case 'a':// Texte der Analogwerte
                                        int index = RxdBuffer[pRxData];
                                        if (index > 31) {
                                            index = 31;
                                        }
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'A', DataStorage.FCDebugOut.Analog[index].getLabelArray());
                                        break;
                                    case 'b': // ExternControl
                                        break;
                                    case 'c': // request for 3D data;
                                        if ((DataStorage.str_Data3D.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(FC_ADDRESS, 'C', DataStorage.str_Data3D.getAsInt());
                                        }
                                        break;
                                    case 'd': // Poll the debug data
                                        if ((DataStorage.FCDebugOut.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(FC_ADDRESS, 'D', DataStorage.FCDebugOut.getAsInt());
                                        }
                                        break;
                                    case 'h':// x-1 Displayzeilen
                                        if ((DataStorage.lcddata.requestTime = RxdBuffer[pRxData + 1] * 10) > 0) {
                                            DataStorage.encoder.send_command(FC_ADDRESS, 'H', DataStorage.lcddata.getAsInt());
                                        }
                                        break;
                                    case 'l':// x-1 Displayzeilen
                                        break;
                                    case 'v': // Version-Anforderung und Ausbaustufe
                                        DataStorage.encoder.send_command(FC_ADDRESS, 'V', DataStorage.FCversion.getAsInt());
                                        break;
                                    case 'g'://
                                        //GetExternalControl = 1;
                                        break;
                                    default:
                                        //unsupported command received
                                        break;
                                }
                        }
                        break;
                    case MK3MAG:
                        switch (RxdBuffer[1] - 'a') {
                            //case Serial.MK3MAG_ADDRESS:
                            default:
                                switch (RxdBuffer[2]) {
                                    case 'v': // request for version info
                                        DataStorage.encoder.send_command(MK3MAG_ADDRESS, 'V', DataStorage.MK3version.getAsInt());
                                        break;
                                    case 'a':// Texte der Analogwerte
                                        int index = RxdBuffer[pRxData];
                                        if (index > 31) {
                                            index = 31;
                                        }
                                        DataStorage.encoder.send_command(MK3MAG_ADDRESS, 'A', DataStorage.MK3MAGDebugOut.Analog[index].getLabelArray());
                                        break;
                                    case 'd': // Poll the debug data
                                        if ((DataStorage.MK3MAGDebugOut.requestTime = RxdBuffer[pRxData] * 10) > 0) {
                                            DataStorage.encoder.send_command(MK3MAG_ADDRESS, 'D', DataStorage.MK3MAGDebugOut.getAsInt());
                                        }
                                        break;
                                }
                        }
                        break;
                    case MKGPS:
                        break;

                    default:
                        System.out.println("Unknown Address : " + (char) RxdBuffer[2]);
                        break;
                }

            }
        } catch (Exception ex) {
            System.out.println("Exception while getting the MK data");
            System.out.println(ex);
        }
        NeuerDatensatzEmpfangen = false;
        pRxData = 0;
        RxDataLen = 0;
    }
}
