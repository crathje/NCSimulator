/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.testing;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.AbstractElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.DrawableElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.StringElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.Transformation;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class ElementTest extends JFrame {

    DefaultListModel listModel;

    public ElementTest() {
        super();

        JPanel listPanel = new JPanel();

        listModel = new DefaultListModel();

//        listModel.addElement(new RotateTransformation(45.0));
//        listModel.addElement(new ShiftTransformation(0, 50));
//        listModel.addElement(new ShiftTransformation(0, -50));
//        listModel.addElement(new RotateTransformation(360 - 45.0));
//        listModel.addElement(new BarElement());
//        listModel.addElement(new RotateTransformation(45.0));
//        listModel.addElement(new StringElement(DataStorage.preferences.node("testDummyNode"), "0"));
//        JList list = new JList(listModel);
//        listPanel.add(list);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(listPanel), new DrawPanel());


        //add(splitPane, BorderLayout.CENTER);
        //add(new DrawPanel(), BorderLayout.CENTER);

//        c_intElement c = new c_intElement(DataStorage.preferences.node("testDummyNode"), "0");
//        add(c.getChangeComponent(), BorderLayout.CENTER);

        DrawableElement d = new StringElement(DataStorage.preferences.node("testDummyNode"), "0");
        listModel.addElement(d);
        add(d.getChangeComponent(), BorderLayout.CENTER);


        pack();
        //setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JFrame draw = new JFrame("draw");

        draw.add(new DrawPanel(), BorderLayout.CENTER);

        draw.setSize(300, 400);
        draw.setLocationRelativeTo(this);
        draw.setVisible(true);
        setVisible(true);
        toFront();
    }

    public class DrawPanel extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            Graphics2D g2d = (Graphics2D) g;

            for (int i = 0; i <= 100; i++) {
                g2d.draw(new Line2D.Double(i * 10, 0, i * 10, 10));
            }



            AffineTransform trans = new AffineTransform();

            Object[] arr = listModel.toArray();
            for (int i = 0; i < arr.length; i++) {

                if (arr[i] instanceof AbstractElement) {
                    AbstractElement o = (AbstractElement) arr[i];
//                    o.persist(null, i);
                    if (o instanceof Transformation) {
                        // new set of transformations
                        if (i > 0 && arr[i - 1] instanceof DrawableElement) {
                            trans = new AffineTransform();
                            System.out.println("clearning");
                        }
                        Transformation t = (Transformation) o;
                        trans.concatenate(t.getAffineTransform(DrawPanel.this.getSize()));
                    } else if (o instanceof DrawableElement) {
                        g2d.setTransform(trans);
                        DrawableElement d = (DrawableElement) o;
                        d.drawTo(g2d, DrawPanel.this.getSize());
                    }
//                System.out.println("Drawing: " + d);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ElementTest t = new ElementTest();


    }
}
