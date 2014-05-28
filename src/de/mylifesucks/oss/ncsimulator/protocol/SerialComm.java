/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com Licensed under: Creative Commons / Non Commercial /
 * Share Alike http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.protocol;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.gui.LogPanel;
import java.io.*;
import java.util.*;
//import javax.comm.*;
import gnu.io.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author fblumenberg
 */
public class SerialComm extends CommunicationBase implements Runnable, SerialPortEventListener {

    static CommPortIdentifier portId;
    static CommPortIdentifier saveportId;
    SerialPort serialPort;
    Thread readThread;
//    static boolean outputBufferEmptyFlag = false;
    boolean isUSB;
    static HashMap<String, CommPortIdentifier> portMap;

    public static HashMap<String, CommPortIdentifier> getPorts() {
        if (portMap == null) {
            portMap = new HashMap<String, CommPortIdentifier>();
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    portMap.put(portId.getName(), portId);
                }
            }
        }
        return portMap;
    }

    public void initwritetoport() {
        // initwritetoport() assumes that the port has already been opened and
        //    initialized by "public nulltest()"

        try {
            // get the outputstream
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
        }

        try {
            // activate the OUTPUT_BUFFER_EMPTY notifier
            // DISABLE for USB
            //System.out.println(serialPort.getName());
            if (!isUSB) {
                serialPort.notifyOnOutputEmpty(true);
            }
        } catch (Exception e) {
            System.out.println("Error setting event notification");
            System.out.println(e.toString());
            System.exit(-1);
        }

    }

    public SerialComm(String port, boolean isUSB) throws Exception {

        this.isUSB = isUSB;
        boolean portFound = false;
        String defaultPort;

        // determine the name of the serial port on several operating systems
        String osname = System.getProperty("os.name", "").toLowerCase();
        if (osname.startsWith("windows")) {
            // windows
            defaultPort = "COM1";
        } else if (osname.startsWith("linux")) {
            // linux
            defaultPort = "/dev/ttyS0";
        } else if (osname.startsWith("mac")) {
            // mac
            defaultPort = "????";
        } else {
            System.out.println("Sorry, your operating system is not supported");
            return;
        }

        if (port != null) {
            defaultPort = port;
        }

        System.out.println("Set default port to " + defaultPort);

        // parse ports and if the default port is found, initialized the reader
        if (!portMap.keySet().contains(defaultPort)) {
            System.out.println("port " + defaultPort + " not found.");
            System.exit(0);
        }

        portId = portMap.get(defaultPort);
        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        inputStream = serialPort.getInputStream();

        serialPort.addEventListener(this);

        // activate the DATA_AVAILABLE notifier
        serialPort.notifyOnDataAvailable(true);

        // set port parameters
        serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // first thing in the thread, we initialize the write operation
        initwritetoport();

        // start the read thread
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
            }
        }
    }

    byte[] readBuffer = new byte[220];

    /**
     *
     * @param event
     * @see http://www.mikrokopter.de/ucwiki/en/SerialProtocol
     */
    public void serialEvent(SerialPortEvent event) {

        //"0x1B,0x1B,0x55,0xAA,0x00"
        byte[] pattern = new byte[]{27, 27, 85, (byte) 170, 0};

        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    while (inputStream.available() > 0) {

                        Arrays.fill(readBuffer, (byte) 0);
                        int numBytes = inputStream.read(readBuffer);
                        byte[] data = Arrays.copyOfRange(readBuffer, 0, numBytes);
//        System.out.println(Hex.encodeHexString(readBuffer));
//        System.out.println(Hex.encodeHexString(data));

                        HandleInputData(data);
                    }
                } catch (IOException ex) {
                }
                break;
        }
    }

}
