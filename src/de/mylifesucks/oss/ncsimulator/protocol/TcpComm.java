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
import java.net.*;

/**
 *
 * @author fblumenberg
 */
public class TcpComm extends CommunicationBase implements Runnable {

    ServerSocket serverSocket;
    Thread readThread;

    public TcpComm(int listenPort) throws Exception {
        System.out.println("Create socket for listening on port:" + listenPort);
        serverSocket = new ServerSocket(listenPort);
        System.out.println("Listening for connections on port " + serverSocket.getLocalPort());

        // start the read thread
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        byte[] readBuffer = new byte[220];
        Socket clientSocket = null;
        try {
            while (true) {
                System.out.println("Wait for connection");
                clientSocket = serverSocket.accept();
                try {
                    System.out.println("Connection established with " + clientSocket);
                    Thread input = new InputThread(clientSocket);
                    input.start();
                } catch (Exception ex) {
                    System.out.println("Socket error:" + ex);
                }
            }
        } catch (IOException e) {
            System.out.println("Accept failed");
            System.exit(-1);
        }
        System.out.println("Thread ended");
    }

    class InputThread extends Thread {

        Socket clientSocket;

        public InputThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            byte[] readBuffer = new byte[220];
            try {
                CommunicationBase.outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream();
                while (!clientSocket.isClosed()) {
                    int numBytes = inputStream.read(readBuffer);
//                    System.out.println("Read bytes from socket:" + numBytes);
//                    System.out.println("socket:" + clientSocket.isClosed());
//                    System.out.println("socket:" + clientSocket.isConnected());

                    if(numBytes<0)
                        clientSocket.close();

                    String out = "";
                    for (int i = 0; i < numBytes; i++) {
                        out += (char) readBuffer[i];
                    }

                    if(!out.startsWith("#a") && !out.startsWith("#co") )
                      System.out.println("Read bytes from socket:" + out);

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
                System.out.println("Socket error:" + ex);
            }
        }
    }
}
