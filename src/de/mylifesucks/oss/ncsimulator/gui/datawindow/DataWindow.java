/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui.datawindow;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.AbstractElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.DrawableElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.RotateTransformation;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.ShiftTransformation;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.StringElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.BarElement;
import de.mylifesucks.oss.ncsimulator.gui.datawindow.elements.Transformation;
import de.mylifesucks.oss.ncsimulator.gui.tray.SimTray;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.RepaintManager;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class DataWindow extends JWindow {

    public static final String WINDOWS_PREFERENCE_NODE_NAME = "datawindow";
    public String myId = createNewWindowID();
    private Preferences myPref;
    private boolean mMoveStart = false;
    boolean mHozResizeEnabled = false, mVerResizeEnabled = false;
    private Point mWindowMouseClickPoint;
    private static int arcw = 5, arch = 5;
    boolean mInvert = false;
//    private String dataString = "DATA";
    private Color bgColor = Color.BLACK;
//    private Color fgColor = Color.RED;
    private boolean opaque = false;
    public static final String SIZEX = "SizeX";
    public static final String SIZEY = "SizeY";
    public static final String POSX = "PositionX";
    public static final String POSY = "PositionY";
    public static final String BGCOLOR = "Background Color";
//    public static final String FGCOLOR = "Foreground Color";
    public static final String OPAQUE = "Opaque";
//    public static final String FOREGROUNDTRANSPARENTVALUE = "Foreground Tansparency";
    public static final String BACKGROUNDTRANSPARENTVALUE = "Background Tansparency";
//    public static final String ADDOFFSET = "An Offset value to add before computing the value";
//    public static final String DATA = "Data";
//    public static final String SPRINTFSTRING = "sprintf String";
//    public static final String DECIMALS = "Number of decimals after coma";
//    public static final String SCALEFACTOR = "Scalefactor to multiply value with";
//    public static final String VISUALIZETEXT = "Visualize value as Text";
//    public static final String VISUALIZEBAR = "Visualize value as bar";
//    public static final String ROTATEBAR = "Rotate the bar by $value degrees";
//    public static final String SHIFTBAR = "Shift the bar by $value/2 percent up/down";
//    private boolean visualizetext = true;
//    private boolean visualizebar = false;
//    private boolean rotatebar = false;
//    private boolean shiftebar = false;
//    private double scalefactor = 1.0d;
//    private long adddoffset = 0;
//    private int decimals = 0;
//    private int foregroundtransparentvalue = 255;
    private int backgroundtransparentvalue = 255;
//    protected c_int data = null;
    private DataPanel dataPanel;
    private Method mSetWindowOpacity = null;
    private Method mSetWindowOpaque = null;
    private Method mSetWindowShape = null;
    private Method mIsTranslucencySupported = null;
//    private String sprintfString = "%s";
//    private DataWindow I = this;
    private boolean firstRun = true;
    private Class<?> awtUtilitiesClass;
    private Class<?> translucencyClass;
    private Object TRANSLUCENT = null;
    private DefaultListModel elementsListModel;
    private JList elementsList;
    private JSplitPane elementsPanel;
    private JPanel elementsDialogPanel;
    JButton addElementButton, removeElementButton, commitButton, upButton, downButton;
    JComboBox possibleElements;

    public DataWindow(String spawnId) {
        super();
//        setUndecorated(true);

        RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);

        if (spawnId != null) {
            myId = spawnId;
        }

        dataPanel = new DataPanel();
        try {
            awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
            mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
            mSetWindowOpaque = awtUtilitiesClass.getMethod("setWindowOpaque", Window.class, boolean.class);
            mSetWindowShape = awtUtilitiesClass.getMethod("setWindowShape", Window.class, Shape.class);
            // TODO: proper reflect
            //  mIsTranslucencySupported = awtUtilitiesClass.getMethod("isTranslucencySupported", AWTUtilities.Translucency.class);

            translucencyClass = Class.forName("com.sun.awt.AWTUtilities$Translucency");
            mIsTranslucencySupported = awtUtilitiesClass.getMethod("isTranslucencySupported", translucencyClass);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        myPref = DataStorage.preferences.node(WINDOWS_PREFERENCE_NODE_NAME).node(myId);

        elementsListModel = new DefaultListModel();
        elementsList = new JList(elementsListModel);
        elementsList.addListSelectionListener(new MyListSelectionHandler());
//        diag = new JFrame("Edit elements");

        elementsDialogPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        addElementButton = new JButton("add");
        addElementButton.addActionListener(dataPanel);
        removeElementButton = new JButton("remove");
        removeElementButton.addActionListener(dataPanel);
        commitButton = new JButton("commit");
        commitButton.addActionListener(dataPanel);
        upButton = new JButton("UP");
        upButton.addActionListener(dataPanel);
        downButton = new JButton("down");
        downButton.addActionListener(dataPanel);


        elementsPanel = new JSplitPane();
        elementsPanel.setDividerLocation(200);
        elementsPanel.setResizeWeight(0.0);
        elementsDialogPanel.add(elementsPanel, BorderLayout.CENTER);

        possibleElements = new JComboBox(new String[]{
                    StringElement.class.getSimpleName(),
                    BarElement.class.getSimpleName(),
                    RotateTransformation.class.getSimpleName(),
                    ShiftTransformation.class.getSimpleName()
                });
        buttonPanel.add(possibleElements);
        buttonPanel.add(addElementButton);
        buttonPanel.add(removeElementButton);
        buttonPanel.add(commitButton);
        buttonPanel.add(upButton);
        buttonPanel.add(downButton);
        elementsDialogPanel.add(buttonPanel, BorderLayout.SOUTH);



        setSize(myPref.getInt(SIZEX, 300), myPref.getInt(SIZEY, 300));
        setLocation(myPref.getInt(POSX, 300), myPref.getInt(POSY, 300));

//        fgColor = new Color(myPref.getInt(FGCOLOR, fgColor.getRGB()), true);
        bgColor = new Color(myPref.getInt(BGCOLOR, bgColor.getRGB()), true);
//        foregroundtransparentvalue = myPref.getInt(FOREGROUNDTRANSPARENTVALUE, foregroundtransparentvalue);
        backgroundtransparentvalue = myPref.getInt(BACKGROUNDTRANSPARENTVALUE, backgroundtransparentvalue);
//        sprintfString = myPref.get(SPRINTFSTRING, sprintfString);
//
//        scalefactor = myPref.getDouble(SCALEFACTOR, scalefactor);
//        decimals = myPref.getInt(DECIMALS, decimals);
//        adddoffset = myPref.getLong(ADDOFFSET, adddoffset);
//
//        visualizebar = myPref.getBoolean(VISUALIZEBAR, visualizebar);
//        visualizetext = myPref.getBoolean(VISUALIZETEXT, visualizetext);
//
//        shiftebar = myPref.getBoolean(SHIFTBAR, shiftebar);
//        rotatebar = myPref.getBoolean(ROTATEBAR, rotatebar);
//
//        String mydata = myPref.get(DATA, data != null ? data.toString() : "");
//        LinkedList<c_int> params = DataStorage.naviData.getList();
//        params.addAll(DataStorage.FCDebugOut.getList());
//        for (c_int c : params) {
//            if (c.toString().equals(mydata)) {
//                data = c;
//                dataString = c.toString();
//                //c.addCNotifyMe(dataPanel);
//                c.addObserver(dataPanel);
//                break;
//            }
//        }





        opaque = myPref.getBoolean(OPAQUE, opaque);
        if (mIsTranslucencySupported != null
                && mSetWindowOpacity != null
                && mSetWindowOpaque != null) {
            try {

                for (Object o : translucencyClass.getEnumConstants()) {
                    if (o.toString().equals("TRANSLUCENT")) {
//                        System.out.println(o);
                        TRANSLUCENT = o;
                        break;
                    }
                }
//                GraphicsConfiguration translucencyCapableGC = null;
//                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//                GraphicsDevice[] devices = env.getScreenDevices();
//                for (int i = 0; i < devices.length && translucencyCapableGC == null; i++) {
//
//                    GraphicsConfiguration[] configs = devices[i].getConfigurations();
//                    for (int j = 0; j < configs.length && translucencyCapableGC == null; j++) {
//                        if (AWTUtilities.isTranslucencyCapable(configs[j])) {
//                            translucencyCapableGC = configs[j];
//                            System.out.println(translucencyCapableGC);
//                        }
//                    }
//                }

                String os = System.getProperty("os.name");
//                System.out.println(os);
                if (os.toLowerCase().contains("linux") && false) {
                    double opacity = 0.5;
                    long value = (int) (0xff * opacity) << 24;
                    try {
                        // long windowId = peer.getWindow();
                        Field peerField = Component.class.getDeclaredField("peer");
                        peerField.setAccessible(true);
                        Class<?> xWindowPeerClass = Class.forName("sun.awt.X11.XWindowPeer");
                        Method getWindowMethod = xWindowPeerClass.getMethod("getWindow", new Class[0]);
                        long windowId = ((Long) getWindowMethod.invoke(peerField.get(this), new Object[0])).longValue();

                        // sun.awt.X11.XAtom.get("_NET_WM_WINDOW_OPACITY").setCard32Property(windowId, value);
                        Class<?> xAtomClass = Class.forName("sun.awt.X11.XAtom");
                        Method getMethod = xAtomClass.getMethod("get", String.class);
                        Method setCard32PropertyMethod = xAtomClass.getMethod("setCard32Property", long.class, long.class);
                        setCard32PropertyMethod.invoke(getMethod.invoke(null, "_NET_WM_WINDOW_OPACITY"), windowId, value);
                    } catch (Exception ex) {
                        // Boo hoo! No transparency for you!
                        ex.printStackTrace();
                        return;
                    }
                } else {
                    if (TRANSLUCENT != null
                            && mSetWindowOpaque != null
                            && (Boolean) mIsTranslucencySupported.invoke(this, TRANSLUCENT)) {
                        mSetWindowOpaque.invoke(null, this, opaque);
                        if (!opaque) {
                            mSetWindowOpacity.invoke(null, this, 1f);
                        } else {
                            mSetWindowOpacity.invoke(null, this, 0.5f);
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(DataWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent evt) {
                Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcw, arch);
                //AWTUtilities.setWindowShape(fd, shape);
                try {
                    mSetWindowShape.invoke(null, this, shape);
                } catch (Exception ex) {
                }
            }
        });




//        System.out.println(AbstractElement.load(myPref));
        for (AbstractElement a : AbstractElement.load(myPref)) {
            elementsListModel.addElement(a);
        }

        setLayout(new BorderLayout());
        add(dataPanel, BorderLayout.CENTER);

        setVisible(true);
        setAlwaysOnTop(true);
    }

    public Preferences getMyPref() {
        return myPref;
    }

    public static String createNewWindowID() {
        return "window" + Calendar.getInstance().getTime().getTime() + "-" + Math.random();
    }

    @Override
    public String toString() {
        return myId + " at: " + getLocation().x + "/" + getLocation().y;
    }

    public static void addDataWindow(DataWindow dw) {
        DataStorage.dataWindows.add(dw);
        DataStorage.dataWindowPanel.addDataWindow(dw);
    }

    public static void removeDataWindow(DataWindow dw) {
        dw.setVisible(false);
        DataStorage.dataWindows.remove(dw);
        DataStorage.dataWindowPanel.removeDataWindow(dw);
        dw.dispose();
        try {
            dw.myPref.removeNode();
        } catch (BackingStoreException ex) {
        }
    }

    public static void init() {
//        System.out.println("INIT?");

        try {
            for (String s : DataStorage.preferences.node(DataWindow.WINDOWS_PREFERENCE_NODE_NAME).childrenNames()) {
                //System.out.println(s);visz
                boolean exists = false;
                for (DataWindow dw : DataStorage.dataWindows) {
                    if (s.equals(dw.myId)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    addDataWindow(new DataWindow(s));
                } else {
                    System.out.println("no init because exists");
                }
            }
        } catch (BackingStoreException ex) {
        }
    }

    public void disposeWindow() {
        DataWindow.removeDataWindow(this);
    }

    public void persistWindow() {
        myPref = DataStorage.preferences.node(WINDOWS_PREFERENCE_NODE_NAME).node(myId);


        myPref.putInt(SIZEX, getSize().width);
        myPref.putInt(SIZEY, getSize().height);
        myPref.putInt(POSX, getLocation().x);
        myPref.putInt(POSY, getLocation().y);
//        myPref.putInt(FGCOLOR, fgColor.getRGB());
        myPref.putInt(BGCOLOR, bgColor.getRGB());
//        myPref.putInt(FOREGROUNDTRANSPARENTVALUE, foregroundtransparentvalue);
        myPref.putInt(BACKGROUNDTRANSPARENTVALUE, backgroundtransparentvalue);
//        myPref.putInt(DECIMALS, decimals);
//        myPref.putLong(ADDOFFSET, adddoffset);

//        myPref.putDouble(SCALEFACTOR, scalefactor);

        myPref.putBoolean(OPAQUE, opaque);
//        myPref.putBoolean(VISUALIZEBAR, visualizebar);
//        myPref.putBoolean(VISUALIZETEXT, visualizetext);
//
//        myPref.put(DATA, data != null ? data.toString() : "");
//        myPref.put(SPRINTFSTRING, sprintfString);

        for (int i = 0; i < elementsListModel.size(); i++) {
            Object elem = elementsListModel.get(i);
            if (elem != null && elem instanceof AbstractElement) {
                ((AbstractElement) elem).persist(i);
            }
        }


        try {
            myPref.flush();
        } catch (BackingStoreException ex) {
        }
    }

    private AbstractElement getSelectedElement() {
        AbstractElement a = (AbstractElement) elementsList.getSelectedValue();
        if (a != null && a instanceof AbstractElement) {
            return a;
        }
        return null;
    }

    class MyListSelectionHandler implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            if (getSelectedElement() != null) {
                elementsPanel.setRightComponent(new JScrollPane(getSelectedElement().getChangeComponent()));
            }
        }
    }

    class DataPanel extends JPanel implements ActionListener, Observer, ChangeListener {

//        JMenuItem bgChoose, fgChoose;
//        JMenuItem opaqueChoose, dataChoose, foregroundtransparentChoose, backgroundtransparentChoose, formatChoose;
        JColorChooser cc;
        JMenuItem disposeItem, elementsItem, bgChoose, backgroundtransparentChoose;
        JMenuItem selected, renameItem;
        JOptionPane customOptionPane;

        public DataPanel() {
            super();
            DataStorage.naviData.addObserver(this);
            DataStorage.FCDebugOut.addObserver(this);



            this.addMouseMotionListener(new MouseAdapter() {

                @Override
                public void mouseDragged(MouseEvent e) {
                    Point point = e.getPoint();
                    if (mMoveStart) {
                        int musDiffX = point.x - mWindowMouseClickPoint.x;
                        int musDiffY = point.y - mWindowMouseClickPoint.y;
                        DataWindow.this.setLocation(DataWindow.this.getLocation().x + musDiffX, DataWindow.this.getLocation().y + musDiffY);
                    } else if (mHozResizeEnabled) {
                        int musDiffX = point.x - mWindowMouseClickPoint.x;
                        DataWindow.this.setSize(DataWindow.this.getWidth() + (mInvert ? -1 : 1) * musDiffX, DataWindow.this.getHeight());
                        mWindowMouseClickPoint.x = point.x;
                    } else if (mVerResizeEnabled) {
                        int musDiffY = point.y - mWindowMouseClickPoint.y;
                        DataWindow.this.setSize(DataWindow.this.getWidth(), DataWindow.this.getHeight() + (mInvert ? -1 : 1) * musDiffY);
                        mWindowMouseClickPoint.y = point.y;
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
//                    System.out.println(new Date() + "mouseMoved");
                    if (e.getX() >= getWidth() - 5) { //|| e.getX() < 5) {
                        setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                    } else if (e.getY() >= getHeight() - 5) { //|| e.getY() < 5) {
                        setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                    } else {
                        setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    }
                }
            });

//            this.addMouseWheelListener(new MouseWheelListener() {
//
//                public void mouseWheelMoved(MouseWheelEvent e) {
//                    System.out.println(e);
//                    int notches = e.getWheelRotation();
//                    if (notches < 0) {
////                        message = "Mouse wheel moved UP "
//                        backgroundtransparentvalue--;
//
//                    } else {
////                        message = "Mouse wheel moved DOWN "
//                        backgroundtransparentvalue++;
//                    }
//                    repaint();
//                    if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
////                        message += "    Scroll type: WHEEL_UNIT_SCROLL" + newline;
////                        message += "    Scroll amount: " + e.getScrollAmount()
////                                + " unit increments per notch" + newline;
////                        message += "    Units to scroll: " + e.getUnitsToScroll()
////                                + " unit increments" + newline;
////                        message += "    Vertical unit increment: "
////                                + scrollPane.getVerticalScrollBar().getUnitIncrement(1)
////                                + " pixels" + newline;
//                    } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
////                        message += "    Scroll type: WHEEL_BLOCK_SCROLL" + newline;
////                        message += "    Vertical block increment: "
////                                + scrollPane.getVerticalScrollBar().getBlockIncrement(1)
////                                + " pixels" + newline;
//                    }
//                }
//            });



            addMouseListener(new MouseAdapter() {

//            @Override
//            public void mouseEntered(MouseEvent e) {
//                setCursor(new Cursor(Cursor.MOVE_CURSOR));
//            }
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == 1) {
                        if (e.getX() >= getWidth() - 5) { //|| e.getX() < 5) {
                            mHozResizeEnabled = true;

                        } else if (e.getY() >= getHeight() - 5) { //|| e.getY() < 5) {
                            mVerResizeEnabled = true;
                        } else {
                            mMoveStart = true;
                        }

                        if (e.getX() < 5 || e.getY() < 5) {
                            mInvert = true;
                        } else {
                            mInvert = false;
                        }

                        mWindowMouseClickPoint = e.getPoint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {


                    if (mMoveStart || mVerResizeEnabled || mHozResizeEnabled) {
                        persistWindow();
                    }
                    mMoveStart = false;
                    mVerResizeEnabled = false;
                    mHozResizeEnabled = false;
                }
            });


            //setBackground(bgColor);
            //setBackground(new Color(0, 0, 0, 50));
            setOpaque(false);
            JPopupMenu popup = new JPopupMenu();

            renameItem = new JMenuItem("Rename");
            renameItem.addActionListener(this);
            popup.add(renameItem);

//
//            dataChoose = new JMenuItem(DATA);
//            dataChoose.addActionListener(this);
//            popup.add(dataChoose);
//
//            formatChoose = new JMenuItem("Formatting");
//            formatChoose.addActionListener(this);
//            popup.add(formatChoose);
//
//            popup.addSeparator();
//
//            fgChoose = new JMenuItem(FGCOLOR);
//            fgChoose.addActionListener(this);
//            popup.add(fgChoose);
//
            bgChoose = new JMenuItem(BGCOLOR);
            bgChoose.addActionListener(this);
            popup.add(bgChoose);
//
//            opaqueChoose = new JMenuItem(OPAQUE + (opaque ? " OFF" : " ON"));
            //popup.add(opaqueChoose);

//            opaqueChoose.addActionListener(this);
//            foregroundtransparentChoose = new JMenuItem(FOREGROUNDTRANSPARENTVALUE);
//
//            popup.add(foregroundtransparentChoose);
//
//            foregroundtransparentChoose.addActionListener(this);
            backgroundtransparentChoose = new JMenuItem(BACKGROUNDTRANSPARENTVALUE);
            popup.add(backgroundtransparentChoose);
            backgroundtransparentChoose.addActionListener(this);

            elementsItem = new JMenuItem("Edit Items");
            elementsItem.addActionListener(this);
            popup.add(elementsItem);

            popup.addSeparator();
            disposeItem = new JMenuItem("Dispose this window");

            disposeItem.addActionListener(this);

            popup.add(disposeItem);
            this.setComponentPopupMenu(popup);
        }

        public void update(final Observable o, Object arg) {
//            System.out.println(this.getClass().getSimpleName() + " update by " + o);
            if (o instanceof c_int) {
                Runnable r = new Runnable() {

                    public void run() {
                        firstRun = false;
                        repaint();
                    }
                };
                DataStorage.executors.submit(r);
            }
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (e.getSource() == disposeItem) {
                disposeWindow();
            } else if (e.getSource() == bgChoose) {
                cc = new JColorChooser(bgColor);
                JDialog dialog = JColorChooser.createDialog(this,
                        "Change " + BGCOLOR, true, cc,
                        this, null);
                selected = bgChoose;
                cc.getSelectionModel().addChangeListener(this);
//                DataWindow.this.toBack();
                DataWindow.this.setAlwaysOnTop(false);
                dialog.setVisible(true);
                dialog.toFront();
            } else if (e.getSource() == addElementButton) {
                String type = possibleElements.getSelectedItem().toString();
                AbstractElement elem = AbstractElement.elementFromString(myPref, type, elementsListModel.size());
                if (elem != null) {
                    elementsListModel.addElement(elem);
                }
            } else if (e.getSource() == commitButton) {
                persistWindow();
                repaint();
            } else if (e.getSource() == removeElementButton) {

                if (getSelectedElement() != null) {
                    int index = elementsList.getSelectedIndex();
                    AbstractElement a = getSelectedElement();
                    elementsListModel.remove(index);
                    a.dispose();
                    for (int i = index; i < elementsListModel.getSize(); i++) {
                        AbstractElement curr = (AbstractElement) elementsListModel.get(i);
                        curr.persist(i);
                        elementsListModel.remove(i);
                        elementsListModel.add(i, curr);
                    }
                }
            } else if (e.getSource() == upButton) {
                int index = elementsList.getSelectedIndex();
                if (index > 0) {
                    AbstractElement curr = (AbstractElement) elementsListModel.get(index);
                    AbstractElement prev = (AbstractElement) elementsListModel.get(index - 1);
                    elementsListModel.remove(index);
                    elementsListModel.remove(index - 1);
                    elementsListModel.add(index - 1, curr);
                    elementsListModel.add(index, prev);

                    curr.persist(index - 1);
                    prev.persist(index);

                    elementsList.setSelectedIndex(index - 1);
                }
            } else if (e.getSource() == downButton) {
                int index = elementsList.getSelectedIndex();
                if (index < elementsListModel.getSize() - 1) {
                    AbstractElement curr = (AbstractElement) elementsListModel.get(index);
                    AbstractElement next = (AbstractElement) elementsListModel.get(index + 1);
                    elementsListModel.remove(index + 1);
                    elementsListModel.remove(index);
                    elementsListModel.add(index, next);
                    elementsListModel.add(index + 1, curr);

                    curr.persist(index + 1);
                    next.persist(index);

                    elementsList.setSelectedIndex(index + 1);
                }
            } else if (e.getSource() == elementsItem) {
                elementsPanel.setLeftComponent(new JScrollPane(elementsList));
                if (elementsListModel.size() > 0) {
                    AbstractElement a;
                    if (elementsList.getSelectedIndex() != -1) {
                        a = (AbstractElement) elementsListModel.get(elementsList.getSelectedIndex());
                    } else {
                        a = (AbstractElement) elementsListModel.get(0);
                        elementsList.setSelectedIndex(0);
                    }
                    elementsPanel.setRightComponent(new JScrollPane(a.getChangeComponent()));
                } else {
                    elementsPanel.setRightComponent(new JScrollPane(new JPanel()));
                }

                Object[] options = {"OK"};
                Object[] array = {elementsDialogPanel};
                final JOptionPane optionPane = new JOptionPane(array,
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.YES_OPTION,
                        null,
                        options,
                        options[0]);
                JDialog jd = new JDialog(SimTray.mainFrame, "Edit Elements");
                jd.setContentPane(optionPane);
                jd.setModal(true);
                jd.pack();
                jd.setLocationRelativeTo(DataWindow.this);
                jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                DataWindow.this.setAlwaysOnTop(false);
                jd.setVisible(true);
                DataWindow.this.setAlwaysOnTop(true);
            } else if (e.getSource() == renameItem) {
                JTextField name = new JTextField(myId);
                Object[] options = {"OK"};
                Object[] array = {name};
                final JOptionPane optionPane = new JOptionPane(array,
                        JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.YES_OPTION,
                        null,
                        options,
                        options[0]);
                JDialog jd = new JDialog(SimTray.mainFrame, "Rename Windows");
                jd.setContentPane(optionPane);
                jd.setModal(true);
                jd.pack();
                jd.setLocationRelativeTo(DataWindow.this);
                jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                DataWindow.this.setAlwaysOnTop(false);
                jd.setVisible(true);
                DataWindow.this.setAlwaysOnTop(true);
                myId = name.getText();
            } else if (e.getSource() == backgroundtransparentChoose) {
                customOptionPane = new JOptionPane();

                JSlider slider = new JSlider();
                slider.setMajorTickSpacing(50);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);


                slider.setMinimum(0);
                slider.setMaximum(255);
                slider.setValue(backgroundtransparentvalue);

                ChangeListener changeListener = new ChangeListener() {

                    public void stateChanged(ChangeEvent changeEvent) {
                        JSlider theSlider = (JSlider) changeEvent.getSource();
//                        if (!theSlider.getValueIsAdjusting()) {
                        customOptionPane.setInputValue(new Integer(theSlider.getValue()));

                        backgroundtransparentvalue = Integer.valueOf(customOptionPane.getInputValue().toString());

                        repaint();
//                        }
                    }
                };
                slider.addChangeListener(changeListener);

                customOptionPane.setInputValue(new Integer(slider.getValue()));
                customOptionPane.setMessage(new Object[]{BACKGROUNDTRANSPARENTVALUE + ": ", slider});
                customOptionPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
                customOptionPane.setOptionType(JOptionPane.YES_OPTION);
                JDialog dialog = customOptionPane.createDialog(DataWindow.this, BACKGROUNDTRANSPARENTVALUE);

                //slider.getSelectionModel().addChangeListener(this);
                //slider.addChangeListener(this);
//                DataWindow.this.toBack();
                DataWindow.this.setAlwaysOnTop(false);
                dialog.setVisible(true);
                dialog.toFront();
                DataWindow.this.persistWindow();
//                persistWindow();
            }
            //                DataWindow.this.toBack();
            DataWindow.this.setAlwaysOnTop(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;


//            ScriptEngineManager manager = new ScriptEngineManager();

//            for (ScriptEngineFactory foo : manager.getEngineFactories()) {
//                System.out.println(foo.getEngineName());
//            }

//            ScriptEngine engine = manager.getEngineByName("JavaScript");
//            String script = "function hello(name) { print('Hello, ' + name); }";
//            engine.put("i", dataString);
//            try {
//                // evaluate script
//                engine.eval("i := 4 *");
//                System.out.println(engine.get("i"));
//            } catch (Exception ex) {
//                Logger.getLogger(DataWindow.class.getName()).log(Level.SEVERE, null, ex);
//            }




            //System.out.println("\t" + (System.currentTimeMillis() - starttime) + "ms Ended at: " + fontsize);

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            //g.create(0, 0, getWidth(), getHeight());
            g2d.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), backgroundtransparentvalue));
            //System.out.println(f.getAlpha());
            g2d.fillRect(0, 0, getWidth(), getHeight());

            AffineTransform trans = new AffineTransform();

            Object[] arr = elementsListModel.toArray();
            for (int i = 0; i < arr.length; i++) {

                if (arr[i] instanceof AbstractElement) {
                    AbstractElement o = (AbstractElement) arr[i];
//                    o.persist(null, i);
                    if (o instanceof Transformation) {
                        // new set of transformations
                        if (i > 0 && arr[i - 1] instanceof DrawableElement) {
                            trans = new AffineTransform();
//                            System.out.println("clearning");
                        }
                        Transformation t = (Transformation) o;
                        trans.concatenate(t.getAffineTransform(this.getSize()));
                    } else if (o instanceof DrawableElement) {
                        g2d.setTransform(trans);
                        DrawableElement d = (DrawableElement) o;
                        d.drawTo(g2d, this.getSize());
                    }
//                System.out.println("Drawing: " + d);
                }
            }
            g2d.setTransform(new AffineTransform());

            g2d.setColor(Color.BLACK);
            g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arcw, arch));

        }

        public void stateChanged(ChangeEvent e) {
            if (e.getSource() instanceof DefaultColorSelectionModel) {
                if (selected == bgChoose) {
                    bgColor = cc.getColor();
                    repaint();
                } else {
                    System.out.println(e.getSource());
                }
            }
        }
    }
}

