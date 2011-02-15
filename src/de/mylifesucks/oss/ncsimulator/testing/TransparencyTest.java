package de.mylifesucks.oss.ncsimulator.testing;

import java.awt.*;
import java.lang.reflect.*;
import javax.swing.*;
import javax.swing.event.*;

public class TransparencyTest extends JWindow implements ChangeListener {
 private JSlider slider;

 public TransparencyTest() {
  super();
//  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  slider = new JSlider(0, 255);
  slider.setValue(255);
  slider.addChangeListener(this);

  JPanel panel = new JPanel(new BorderLayout());
  panel.add(slider, BorderLayout.CENTER);
  setContentPane(panel);
  setSize(new Dimension(640, 480));
  setLocationRelativeTo(null);
 }

 public void stateChanged(ChangeEvent e) {
  double value = ((double) slider.getValue())/((double) slider.getMaximum());
  setWindowOpacity(this, value);
 }

 public void setWindowOpacity(JWindow frame, double opacity) {
  long value = (int) (0xff * opacity) << 24;
  try {
   // long windowId = peer.getWindow();
   Field peerField = Component.class.getDeclaredField("peer");
   peerField.setAccessible(true);
   Class<?> xWindowPeerClass = Class.forName("sun.awt.X11.XWindowPeer");
   Method getWindowMethod = xWindowPeerClass.getMethod("getWindow", new Class[0]);
   long windowId = ((Long) getWindowMethod.invoke(peerField.get(frame), new Object[0])).longValue();

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
 }

 public static void main(String[] args) {
  new TransparencyTest().setVisible(true);
 }
}