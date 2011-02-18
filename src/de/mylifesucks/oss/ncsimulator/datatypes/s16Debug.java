/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.protocol.CommunicationBase;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 * 16bit signed debug-int with label
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class s16Debug extends s16 {

    int index = 0;
    String prefix;
    public int ADDRESS;

    public s16Debug(String name, int index, int address) {
        signed = true;
        length = 16;
        this.name = name;
        this.prefix = name;
        this.index = index;
        this.ADDRESS = address;
    }

    @Override
    public String getSerializeName() {
        return prefix + "" + index;
    }

    public int[] getLabelArray() {
        int[] ret = new int[16];
        String s = ((JTextField) nameLabel).getText();
        if (s.length() > 16) {
            s = s.substring(0, 16);
        }

        int i = 0;
        for (char c : s.toCharArray()) {
            ret[i++] = (int) c;
        }
        for (; i < ret.length; i++) {
            ret[i] = (int) ' ';
        }
        //ret[15] = 0;

        return c_int.concatArray(new int[]{index}, ret);
    }

    @Override
    protected JComponent getNameLabel() {
        if (nameLabel == null) {
            JTextField nameField = new JTextField();
            nameLabel = nameField;
            nameLabel.setPreferredSize(new Dimension(c_int.LABELWIDTH, nameLabel.getPreferredSize().height));
            nameLabel.setSize(nameLabel.getPreferredSize());
            nameLabel.setMaximumSize(nameLabel.getPreferredSize());
            nameLabel.setMinimumSize(nameLabel.getPreferredSize());
            nameField.setText(name + " " + index);

            nameField.addFocusListener(new FocusAdapter() {

                @Override
                public void focusLost(FocusEvent e) {
                    //System.out.println("Focus Losty..." + DataStorage.sendThread.getState());
                    if (DataStorage.sendThread != null && !(DataStorage.sendThread.getState() == Thread.State.TERMINATED || DataStorage.sendThread.getState() == Thread.State.NEW)) {
                        SwingWorker worker = new SwingWorker<Boolean, Void>() {

                            public Boolean doInBackground() {
                                //System.out.println(Serial.NC_ADDRESS + " " + Serial.FC_ADDRESS + " ::: " + ADDRESS);
                                switch (ADDRESS) {
                                    case CommunicationBase.NC_ADDRESS:
                                        if (DataStorage.UART == DataStorage.UART_CONNECTION.NC) {
                                            //System.out.println("FOcus LOST NC " + ADDRESS);
                                            for (s16Debug foo : DataStorage.NCDebugOut.Analog) {
                                                DataStorage.encoder.send_command(CommunicationBase.NC_ADDRESS, 'A', foo.getLabelArray());
                                            }
                                        }
                                        break;
                                    case CommunicationBase.FC_ADDRESS:
                                        if (DataStorage.UART == DataStorage.UART_CONNECTION.FC) {
                                            //System.out.println("FOcus LOST FC " + ADDRESS);
                                            for (s16Debug foo : DataStorage.FCDebugOut.Analog) {
                                                DataStorage.encoder.send_command(CommunicationBase.FC_ADDRESS, 'A', foo.getLabelArray());
                                            }
                                        }
                                        break;
                                }

                                return true;
                            }
                        };

                        worker.execute();
                    }
                }
            });
        }

        return nameLabel;
    }

    public void setName(String get) {
        this.name = get;
        ((JTextField) getNameLabel()).setText(get);
    }
}

