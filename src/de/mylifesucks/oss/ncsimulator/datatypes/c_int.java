/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * base class to map all those datatype to char-arrays
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public abstract class c_int extends Observable {

    final static int LABELWIDTH = 120;
    public boolean signed;
    public int length = 0;
    public long value = 0;
    public volatile long requestTime = -1;
    public String name;
    JSlider slider;
    JTextField valueField;
    public JComponent nameLabel = null;
    public LinkedList<c_int> allAttribs = null;
    protected Integer minValue;
    protected Integer maxValue;

    public LinkedList<c_int> getList() {
        LinkedList<c_int> poss = new LinkedList<c_int>();
        if (allAttribs == null) {
            poss.add(this);
        } else {
            for (c_int c : allAttribs) {
                poss.addAll(c.getList());
            }
        }
        return poss;
    }

    public int getMin() {
        if (!signed) {
            return 0;
        } else {
            if (minValue != null) {
                return minValue.intValue();
            }
            return (int) -(Math.pow(2, length - 1));
        }
    }

    public int getLength() {
        if (allAttribs == null) {
            return length;
        } else {
            int len = 0;
            for (c_int c : allAttribs) {
                len += c.getLength();
            }
            return len;
        }
    }

    @Override
    public String toString() {
        return getSerializeName();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        if (allAttribs != null && allAttribs.size() > 0) {
            for (c_int c : allAttribs) {
                c.addObserver(o);
            }
        }
    }

    public void loadFromInt(int RxdBuffer[], int pRxData) {
        if (allAttribs != null && allAttribs.size() > 0) {
            int offset = 0;
            for (c_int c : allAttribs) {
                c.loadFromInt(RxdBuffer, pRxData + offset);
                offset += c.getLength() / 8;
            }
            Runnable r = new Runnable() {

                public void run() {
                    setChanged();
                    notifyObservers();
                }
            };
            DataStorage.executors.submit(r);
        } else {
            long v = 0;
            if (pRxData + (getLength() / 8) <= RxdBuffer.length) {
                for (int i = (length / 8) - 1; i >= 0; i--) {
                    v <<= 8;
                    v |= RxdBuffer[i + pRxData];
                }
                //TODO: signed!!!!
                if (signed) {
                    long signmask = 1 << (getLength() - 1);
                    long signbit = ((v & signmask) != 0) ? 1 : 0;
                    v &= ~signmask;


//                    long newsignmask = signbit << 63;
//                    v = v | newsignmask;
                    if (signbit == 1) {
                        v = v + getMin();
                    }

//                    System.out.println(v);
                }
                setValue(v, false);
            } else {
                //System.out.println("\tBuffer to short?");
            }
        }

    }

    public String getSerializeName() {
        return name;
    }

    public int getMax() {

        if (maxValue != null) {
            return maxValue.intValue();
        }

        if (!signed) {
            return (int) (Math.pow(2, length) - 1);
        } else {
            return (int) (Math.pow(2, length - 1) - 1);
        }
    }

    public void setValue(long value, boolean notify) {
        this.value = value;
        if (valueField != null) {
            valueField.setText("" + value);
        }

        if (notify) {
            Runnable r = new Runnable() {

                public void run() {
                    setChanged();
                    notifyObservers();
                }
            };
            DataStorage.executors.submit(r);
        }

    }

    public static byte[] getbytes(Object obj) throws java.io.IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();
        byte[] data = bos.toByteArray();
        return data;
    }

    public int[] getAsInt() {
        if (allAttribs == null) {
            int[] ret = new int[length / 8];
            for (int i = 0; i < ret.length; i++) {
                ret[ret.length - 1 - i] = (char) (0xff & (value >> ((ret.length - i - 1) * 8)));
            }
            return ret;
        } else {
            int[] ret = new int[0];
            for (c_int o : allAttribs) {
                ret = concatArray(ret, o.getAsInt());
            }
            return ret;
        }
    }

    protected void initComponent() {
        DataStorage.addToSerializePool(this);
        slider = new JSlider(JSlider.HORIZONTAL,
                getMin(), getMax(), Math.min(getMax(), (int) value));
        //slider.setPreferredSize(new Dimension(LABELWIDTH * 4, slider.getPreferredSize().height));
        slider.setMinimumSize(new Dimension(LABELWIDTH, slider.getPreferredSize().height));
        valueField = new JTextField();
        //valueField.setEditable(false);
        valueField.setPreferredSize(new Dimension(LABELWIDTH, valueField.getPreferredSize().height));
        valueField.setSize(valueField.getPreferredSize());
        valueField.setMaximumSize(valueField.getPreferredSize());
        valueField.setMinimumSize(valueField.getPreferredSize());
        valueField.setDocument(new ValueDocument());
        valueField.setText("" + value);




        ChangeListener myCL = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == slider) {
                    value = slider.getValue();
                    //valueField.setText("" + value);
                    setValue(value, true);
                } else if (e.getSource() == valueField) {
                    System.out.println(e);
                }
            }
        };

        slider.addChangeListener(myCL);
    }

    private void doUpSrcText() {
        try {
            long val = Long.valueOf(valueField.getText());
            if (val >= getMin() && val <= getMax()) {
                slider.setValue((int) val);
                value = val;
            }
        } catch (NumberFormatException ex) {
        }
    }

    protected JComponent getNameLabel() {
        if (nameLabel == null) {
            nameLabel = new JLabel(name);
        }
        return nameLabel;
    }

    public synchronized void addToPanel(JComponent panel, GridBagConstraints gbc) {
        if (nameLabel == null) {
            initComponent();
        }
        if (allAttribs == null) {

            gbc.gridx = 0;
            panel.add(getNameLabel(), gbc);

//                System.out.println(getNameLabel());

            gbc.gridx++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(valueField, gbc);
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridx++;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(slider, gbc);
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.gridy++;
        } else {
            for (c_int foo : allAttribs) {
                foo.addToPanel(panel, gbc);
            }
        }
    }

    public void printOut() {
        if (allAttribs != null) {
            for (c_int c : allAttribs) {
                c.printOut();
            }
        } else {
            System.out.println(name + "\t" + value);
        }
    }

    public class ValueDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str.length() == 0 || str.equals("")) {
                super.insertString(offs, str, a);
                doUpSrcText();
            } else if (str != null) {
                try {
                    Long.valueOf(str);
                    super.insertString(offs, str, a);
                    doUpSrcText();
                } catch (NumberFormatException ex) {
                    return;
                }
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
            doUpSrcText();
        }
    }

    /**
     * Concat two integer arrays
     * @param a one array
     * @param b another array
     * @return ab
     */
    public static int[] concatArray(int[] a, int[] b) {
        if (a.length == 0) {
            return b;
        }
        if (b.length == 0) {
            return a;
        }
        int[] ret = new int[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            ret[i] = a[i];
        }
        for (int i = a.length; i < a.length + b.length; i++) {
            ret[i] = b[i - a.length];
        }
        return ret;
    }

    public static String paddingString(String s, int n, char c, boolean paddingLeft) {
        if (s == null) {
            return s;
        }
        int add = n - s.length(); // may overflow int size... should not be a problem in real life

        if (add <= 0) {
            return s;
        }
        StringBuffer str = new StringBuffer(s);

        char[] ch = new char[add];
        Arrays.fill(ch, c);

        if (paddingLeft) {
            str.insert(0, ch);
        } else {
            str.append(ch);
        }
        return str.toString();
    }
}
