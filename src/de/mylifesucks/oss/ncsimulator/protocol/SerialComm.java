/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.protocol;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.io.*;
import java.util.*;
//import javax.comm.*;
import gnu.io.*;

/**
 *
 * @author fblumenberg
 */
public class SerialComm extends CommunicationBase implements Runnable,SerialPortEventListener {

    static CommPortIdentifier portId;
    static CommPortIdentifier saveportId;
    static Enumeration portList;
    SerialPort serialPort;
    Thread readThread;
//    static boolean outputBufferEmptyFlag = false;
    boolean isUSB;
    static Vector<String> portNames = null;

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


    public static Vector<String> getPorts() {
        if (portNames == null) {
            portNames = new Vector<String>();
            portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    portNames.add(portId.getName());
                }
            }
        }
        return portNames;
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
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(defaultPort)) {
                    System.out.println("Found port: " + defaultPort);
                    portFound = true;
                    break;
                }
            }

        }
        if (!portFound) {
            System.out.println("port " + defaultPort + " not found.");
            //hard!
            System.exit(0);
        }

        portId = CommPortIdentifier.getPortIdentifier(defaultPort);
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
                //System.out.print(".");
                try {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                        int foo = 0;
                        //"0x1B,0x1B,0x55,0xAA,0x00"
                        while (foo < readBuffer.length - 5
                                && (readBuffer[foo] != 0x1B
                                || readBuffer[foo + 1] != 0x1B
                                || readBuffer[foo + 2] != 0x55
                                //|| readBuffer[foo + 3] != 0xAA
                                || readBuffer[foo + 4] != 0x00)) {
                            foo++;
                        }
                        if (readBuffer[foo] == 0x1B
                                && readBuffer[foo + 1] == 0x1B
                                //&& readBuffer[foo + 2] != 0x55
                                && readBuffer[foo + 3] != 0xAA //&& readBuffer[foo + 4] != 0x00
                                ) {
                            DataStorage.setUART(DataStorage.UART_CONNECTION.NC);
                            UartState = 0;
                        } else {
                            for (int i = 0; i < numBytes; i++) {
                                USART0_RX_vect((char) readBuffer[i]);
                            }
                        }
                    }
                } catch (IOException ex) {
                }
                break;
        }
    }


}
