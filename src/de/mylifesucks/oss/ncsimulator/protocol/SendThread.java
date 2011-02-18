/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.protocol;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class SendThread extends Thread {

    int calls = 0;
    long lasttime = System.currentTimeMillis();
    long osdLasttime = lasttime, FCdebugLasttime = lasttime, NCdebugLasttime = lasttime, data3DLasttime = lasttime, lcdLasttime = lasttime;
    long sleeptime = 50;
    long reqestosdlasttime = lasttime;
    long reqestdebuglasttime = lasttime;

    public SendThread(String name) {
        super(name);
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(Math.max(0, sleeptime));
                lasttime = System.currentTimeMillis();
                if (DataStorage.requestOSDtime > 0 && System.currentTimeMillis() + sleeptime > reqestosdlasttime + DataStorage.requestOSDtime) {
                    reqestosdlasttime = System.currentTimeMillis();
                    DataStorage.encoder.send_command(Serial.FC_ADDRESS, 'o', new int[]{10});
                    //System.out.println("OSD request");
                }
                if (DataStorage.requestDEBUGtime > 0 && System.currentTimeMillis() + sleeptime > reqestdebuglasttime + DataStorage.requestDEBUGtime) {
                    reqestdebuglasttime = System.currentTimeMillis();
                    DataStorage.encoder.send_command(Serial.FC_ADDRESS - 1, 'd', new int[]{10});
                    //System.out.println("Debug request");
                }

                switch (DataStorage.UART) {
                    case NC:
                        if (DataStorage.naviData.requestTime > 0 && System.currentTimeMillis() + sleeptime > osdLasttime + DataStorage.naviData.requestTime) {
                            osdLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.NC_ADDRESS, 'O', DataStorage.naviData.getAsInt());
                            //System.out.println("OSD autosend");
                        }
                        if (DataStorage.NCDebugOut.requestTime > 0 && System.currentTimeMillis() + sleeptime > NCdebugLasttime + DataStorage.NCDebugOut.requestTime) {
                            NCdebugLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.NC_ADDRESS, 'D', DataStorage.NCDebugOut.getAsInt());
                            //System.out.println("Debug autosend");
                        }
                        if (DataStorage.data3d_t.requestTime > 0 && System.currentTimeMillis() + sleeptime > data3DLasttime + DataStorage.data3d_t.requestTime) {
                            data3DLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.NC_ADDRESS + 1, 'C', DataStorage.data3d_t.getAsInt());
                            //System.out.println("3D autosend");
                        }
                        if (DataStorage.lcddata.requestTime > 0 && System.currentTimeMillis() + sleeptime > lcdLasttime + DataStorage.lcddata.requestTime) {
                            lcdLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.NC_ADDRESS, 'H', DataStorage.lcddata.getAsInt());
                            //System.out.println("LCD autosend");
                        }
                        break;
                    case FC:
                        if (DataStorage.FCDebugOut.requestTime > 0 && System.currentTimeMillis() + sleeptime > FCdebugLasttime + DataStorage.FCDebugOut.requestTime) {
                            FCdebugLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.FC_ADDRESS, 'D', DataStorage.FCDebugOut.getAsInt());
                            //System.out.println("Debug autosend");
                        }
                        if (DataStorage.data3d_t.requestTime > 0 && System.currentTimeMillis() + sleeptime > data3DLasttime + DataStorage.data3d_t.requestTime) {
                            data3DLasttime = System.currentTimeMillis();
                            DataStorage.encoder.send_command(Serial.FC_ADDRESS, 'C', DataStorage.data3d_t.getAsInt());
                            //System.out.println("3D autosend");
                        }
                        break;
                }

                //System.out.println("round: " + (System.currentTimeMillis() - lasttime));
            } catch (InterruptedException ex) {
            }
        }

    }
}

