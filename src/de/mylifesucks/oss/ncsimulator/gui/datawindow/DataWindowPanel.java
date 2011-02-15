/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class DataWindowPanel extends JPanel implements ActionListener {

    JList dataWindowJList;
    public DefaultListModel listModel;
    JButton show, hide, tofront, toback, expo, impo, dispo;

    public DataWindowPanel() {
        super(new BorderLayout());
        DataStorage.dataWindowPanel = this;
        Box verticalBox = Box.createVerticalBox();


        final JButton newWindowb = new JButton("New Datawindow");
        newWindowb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                DataWindow newWindow = new DataWindow(null);
//                System.out.println(e.getSource());
                newWindow.setLocation(newWindowb.getLocationOnScreen());
                DataWindow.addDataWindow(newWindow);
            }
        });
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalGlue());
        hBox.add(newWindowb);
        hBox.add(Box.createHorizontalGlue());
        verticalBox.add(hBox);

        listModel = new DefaultListModel();

        dataWindowJList = new JList(listModel);
        dataWindowJList.setMinimumSize(new Dimension(100, 100));


        hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalGlue());
        hBox.add(new JScrollPane(dataWindowJList));
        hBox.add(Box.createHorizontalGlue());
        verticalBox.add(hBox);


        show = new JButton("Show");
        hide = new JButton("Hide");
        tofront = new JButton("To Front");
        toback = new JButton("To Back");
        dispo = new JButton("Dispose");
        expo = new JButton("Export");
        impo = new JButton("Import");

        show.addActionListener(this);
        hide.addActionListener(this);
        tofront.addActionListener(this);
        toback.addActionListener(this);
        dispo.addActionListener(this);
        expo.addActionListener(this);
        impo.addActionListener(this);

        hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalGlue());
        hBox.add(show);
        hBox.add(hide);
        hBox.add(tofront);
        hBox.add(toback);
        hBox.add(dispo);
        hBox.add(expo);
        hBox.add(impo);
        hBox.add(Box.createHorizontalGlue());
        verticalBox.add(hBox);


        add(verticalBox);
    }

    public void addDataWindow(DataWindow dw) {
        listModel.addElement(dw);
    }

    public void removeDataWindow(DataWindow dw) {
        listModel.removeElement(dw);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == impo) {
            try {
                JFileChooser fc = new JFileChooser();

                int returnVal = fc.showOpenDialog(this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();


//                        if (!file.canRead()) {
//                            System.out.println(file + " not readable" + "\n");
//                            return;
//                        }
//                        String content = new Scanner(new FileInputStream(file), "UTF-8").useDelimiter("\\Z").next();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

                    DocumentBuilder db;
                    try {
                        db = dbf.newDocumentBuilder();


                        Document doc = db.parse(file);
                        doc.getDocumentElement().normalize();
//                        System.out.println("Root element " + doc.getDocumentElement().getNodeName());
                        NodeList nodeLst = doc.getElementsByTagName("node");

                        String loadId = "";

                        for (int s = 0; s < nodeLst.getLength(); s++) {
                            Node fstNode = nodeLst.item(s);
                            if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element fstElmnt = (Element) fstNode;
//                                System.out.println(s + " " + fstElmnt.getAttribute("name"));
                                loadId = fstElmnt.getAttribute("name");
                            }
                        }

                        DataWindow changed = null;
                        for (DataWindow dw : DataStorage.dataWindows) {
                            if (loadId.equals(dw.myId)) {
                                System.err.println("Damn, window exists... Renaming existing one");
                                changed = dw;
                                break;
                            }
                        }
                        // prevent concurrentmod...
                        if (changed != null) {
                            DataWindow.removeDataWindow(changed);
                            changed.myId = DataWindow.createNewWindowID();
                            changed.persistWindow();
                            DataWindow.addDataWindow(changed);
                        }
//

                        System.out.println("ready to load " + loadId);
                        try {
                            Preferences.importPreferences(new BufferedInputStream(new FileInputStream(file)));

                        } catch (InvalidPreferencesFormatException ex) {
                            System.err.println("failed to load " + loadId + " because: " + ex);
                            return;
                        }


                    } catch (SAXException ex) {
                        Logger.getLogger(DataWindowPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                    }


                    DataWindow.init();
                    
                    //current.getMyPref().exportSubtree(new BufferedOutputStream(new FileOutputStream(file)));
//                        Preferences.importPreferences(new BufferedInputStream(new FileInputStream(file)));



                    System.out.println("Loaded from : " + file.getName() + "." + "\n");
                } else {
                    System.out.println("Open command cancelled by user." + "\n");
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//            } catch (InvalidPreferencesFormatException ex) {
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }


        int index = dataWindowJList.getSelectedIndex();
        if (index < 0 || index > listModel.getSize() - 1) {
            return;
        }
        Object select = listModel.getElementAt(index);
        if (select instanceof DataWindow) {
            DataWindow current = (DataWindow) select;
            if (src == show) {
                current.setVisible(true);
            } else if (src == hide) {
                current.setVisible(false);
            } else if (src == tofront) {
                current.toFront();
            } else if (src == toback) {
                current.toBack();
            } else if (src == dispo) {
                DataWindow.removeDataWindow(current);
            } else if (src == expo) {
                try {
                    JFileChooser fc = new JFileChooser();

                    int returnVal = fc.showSaveDialog(this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();

//                        if (!file.getParentFile().canWrite()) {
//                            System.out.println(file.getParentFile() + " write Protected" + "\n");
//                            return;
//                        }

                        current.getMyPref().exportSubtree(new BufferedOutputStream(new FileOutputStream(file)));

                        System.out.println("Saved to : " + file.getName() + "." + "\n");
                    } else {
                        System.out.println("Save command cancelled by user." + "\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                } catch (BackingStoreException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//                } catch (InvalidPreferencesFormatException ex) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
