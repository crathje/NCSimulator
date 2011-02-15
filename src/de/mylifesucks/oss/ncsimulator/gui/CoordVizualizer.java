/**
 *
 * Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 * admiralcascade@gmail.com
 * Licensed under: Creative Commons / Non Commercial / Share Alike
 * http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.gui;

import de.mylifesucks.oss.ncsimulator.datastorage.DataStorage;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import de.mylifesucks.oss.ncsimulator.datatypes.GPS_Deviation_t;
import de.mylifesucks.oss.ncsimulator.datatypes.GPS_Pos_t;
import de.mylifesucks.oss.ncsimulator.datatypes.c_int;
import de.mylifesucks.oss.ncsimulator.datatypes.s32;
import java.awt.geom.AffineTransform;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapKit.DefaultProviders;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class CoordVizualizer extends JPanel {

    JXMapKit mapKit;
    Waypoint home = new Waypoint();
    Waypoint target = new Waypoint();
    Waypoint current = new Waypoint();

    public CoordVizualizer() {
        super(new BorderLayout());
        mapKit = new JXMapKit();

        mapKit.setName("mapKit");
        mapKit.setPreferredSize(new Dimension(413, 218));
        add(mapKit, BorderLayout.CENTER);
        mapKit.setDefaultProvider(DefaultProviders.OpenStreetMaps);

        mapKit.setCenterPosition(new GeoPosition(54.4248, 10.1721));



        Set<Waypoint> waypoints = new HashSet<Waypoint>();
        waypoints.add(home);
        waypoints.add(current);
        waypoints.add(target);
        WaypointPainter painter = new WaypointPainter();

        painter.setRenderer(new WaypointRenderer() {

            public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
                Image icon = null;
//                String s = "";
                if (wp == home) {
                    icon = DataStorage.iconHome.getImage();
                } else if (wp == target) {
                    icon = DataStorage.iconTarget.getImage();
                } else if (wp == current) {
                    icon = DataStorage.iconCurrent.getImage();
//                    AffineTransform at = AffineTransform.getRotateInstance(30.0);
//                    g.drawImage(icon, at, null);
//                    icon = null;
                }
                if (icon != null) {
                    g.drawImage(icon, AffineTransform.getTranslateInstance(-(icon.getWidth(map) / 2), -icon.getHeight(map)), map);
                }
                return true;
            }
        });
        painter.setWaypoints(waypoints);
        //: warning: [unchecked] unchecked call to setWaypoints(java.util.Set<org.jdesktop.swingx.mapviewer.Waypoint>) as a member of the raw type org.jdesktop.swingx.mapviewer.WaypointPainter
        //painter.setWaypoints(waypoints);
        
        mapKit.getMainMap().setOverlayPainter(painter);


        /*
        WMSService wms = new WMSService();
        wms.setLayer("BMNG");
        wms.setBaseUrl("http://wms.jpl.nasa.gov/wms.cgi?");
        TileFactory fact = new WMSTileFactory(wms);
        mapKit.setTileFactory(fact);
         */

        /*WMSService wms = new WMSService();
        wms.setLayer("0");
        wms.setBaseUrl("http://gdz-dop.bkg.bund.de/ArcGIS/services/DOP/ImageServer/WMSServer?");
        TileFactory fact = new WMSTileFactory(wms);
        mapKit.setTileFactory(fact);
         */


        mapKit.getMainMap().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                GeoPosition geopoint = mapKit.getMainMap().convertPointToGeoPosition(e.getPoint());
                //System.out.println(geopoint);

                if (e.getClickCount() == 2) {
                    if (e.getButton() == 1) { // left
                        setGeoPosition(geopoint, DataStorage.naviData.HomePosition.Latitude, DataStorage.naviData.HomePosition.Longitude);
                    }
                } else {
                    if (e.getButton() == 1) { // left
                        setGeoPosition(geopoint, DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude);
                    } else {
                        setGeoPosition(geopoint, DataStorage.naviData.TargetPosition.Latitude, DataStorage.naviData.TargetPosition.Longitude);
                    }
                }
                update();
            }
        });

    }

    public static double distance(GeoPosition a, GeoPosition b) {
        double ret;
        double b_lat = b.getLatitude();
        double a_lat = a.getLatitude();
        double b_long = b.getLongitude();
        double a_long = a.getLongitude();

        double eradius = 6378.137;
        ret = Math.acos(Math.sin(b_lat / 180 * Math.PI) * Math.sin(a_lat / 180 * Math.PI) + Math.cos(b_lat / 180 * Math.PI) * Math.cos(a_lat / 180 * Math.PI) * Math.cos(b_long / 180 * Math.PI - a_long / 180 * Math.PI)) * eradius;
        return ret;
    }
    public static final double FACTOR = 10000000;

    public static void setGeoPosition(GeoPosition geopoint, c_int Latitude, c_int Longitude) {
        Latitude.setValue((long) (geopoint.getLatitude() * FACTOR), false);
        Longitude.setValue((long) (geopoint.getLongitude() * FACTOR), true);
    }

    public static GeoPosition getGeoPosition(c_int Latitude, c_int Longitude) {
        return new GeoPosition(Latitude.value / FACTOR, Longitude.value / FACTOR);
    }

    /**
     * transform the integer deg into float deg.
     * @see: http://mikrokopter.de/mikrosvn/NaviCtrl/tags/V0.14a/GPS.c
     */
    public static double DegFromGPS(s32 deg) {
        return ((double) deg.value * 1e-7f); // 1E-7 because deg is the value in ° * 1E7
    }
    public static final double M_PI_180 = (Math.PI / 180.0f);

    /**
     * transform the integer deg into float radians
     * @see: http://mikrokopter.de/mikrosvn/NaviCtrl/tags/V0.14a/GPS.c
     */
    public static double RadiansFromGPS(s32 deg) {
        return ((double) deg.value * 1e-7f * M_PI_180); // 1E-7 because deg is the value in ° * 1E7
    }

    /**
     * calculate the bearing to target position from its deviation.
     * @see: http://mikrokopter.de/mikrosvn/NaviCtrl/tags/V0.14a/GPS.c
     */
    public static long DirectionToTarget_N_E(double northdev, double eastdev) {
        long bearing;
        bearing = (long) (Math.atan2(northdev, eastdev) / M_PI_180);
        bearing = (270L - bearing) % 360L;
        return (bearing);
    }

    /**
     * calculate the deviation from the current position to the target position.
     * @see: http://mikrokopter.de/mikrosvn/NaviCtrl/tags/V0.14a/GPS.c
     * @param pCurrentPos
     * @param pTargetPos
     * @return
     */
    public static GPS_Deviation_t GPS_CalculateDeviation(GPS_Pos_t pCurrentPos, GPS_Pos_t pTargetPos) {
        GPS_Deviation_t TargetDeviation = new GPS_Deviation_t();
        double temp1, temp2;
        // if given pointer is NULL
        ////////if((pCurrentPos == null) || (pTargetPos == null)) goto baddata;
        // if positions	are invalid
        ////////if((pCurrentPos.Status == INVALID) || (pTargetPos.Status == INVALID)) goto baddata;

        // The deviation from the current to the target position along north and east direction is
        // simple the lat/lon difference. To convert that angular deviation into an
        // arc length the spherical projection has to be considered.
        // The mean earth radius is 6371km. Therfore the arc length per latitude degree
        // is always 6371km * 2 * Pi / 360deg =  111.2 km/deg.
        // The arc length per longitude degree depends on the correspondig latitude and
        // is 111.2km * cos(latitude).

        // calculate the shortest longitude deviation from target
        temp1 = DegFromGPS(pCurrentPos.Longitude) - DegFromGPS(pTargetPos.Longitude);
        // outside an angular difference of -180 deg ... +180 deg its shorter to go the other way around
        // In our application we wont fly more than 20.000 km but along the date line this is important.
        if (temp1 > 180.0f) {
            temp1 -= 360.0f;
        } else if (temp1 < -180.0f) {
            temp1 += 360.0f;
        }
        temp1 *= Math.cos(RadiansFromGPS(pTargetPos.Latitude));
        // calculate latitude deviation from target
        // this is allways within -180 deg ... 180 deg
        temp2 = DegFromGPS(pCurrentPos.Latitude) - DegFromGPS(pTargetPos.Latitude);
        // deviation from target position in cm
        // i.e. the distance to walk from the target in northern and eastern direction to reach the current position

        ////////TargetDeviation.Status = INVALID;
        TargetDeviation.North.value = (long) (11119492.7f * temp2);
        TargetDeviation.East.value = (long) (11119492.7f * temp1);
        // If the position deviation is small enough to neglect the earth curvature
        // (this is for our application always fulfilled) the distance to target
        // can be calculated by the pythagoras of north and east deviation.
        TargetDeviation.Distance.value = (long) (11119492.7f * Math.hypot(temp1, temp2));
        if (TargetDeviation.Distance.value == 0L) {
            TargetDeviation.Bearing.value = 0L;
        } else {
            TargetDeviation.Bearing.value = DirectionToTarget_N_E(temp2, temp1);
        }
        ////////TargetDeviation.Status = NEWDATA;
        ////////return true;
        return TargetDeviation;

        /*////////baddata:
        TargetDeviation.North.value = 0L;
        TargetDeviation.East.value = 0L;
        TargetDeviation.Distance.value = 0L;
        TargetDeviation.Bearing.value = 0L;
        ////////TargetDeviation.Status = INVALID;
        return false;*/
    }

    public void update() {
        home.setPosition(getGeoPosition(DataStorage.naviData.HomePosition.Latitude, DataStorage.naviData.HomePosition.Longitude));
        current.setPosition(getGeoPosition(DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude));
        target.setPosition(getGeoPosition(DataStorage.naviData.TargetPosition.Latitude, DataStorage.naviData.TargetPosition.Longitude));

        GPS_Deviation_t targetDev = GPS_CalculateDeviation(DataStorage.naviData.CurrentPosition, DataStorage.naviData.HomePosition);
        //DataStorage.naviData.HomePositionDeviation.Distance.value = targetDev.Distance.value;
        DataStorage.naviData.HomePositionDeviation.Bearing.value = targetDev.Bearing.value;

        targetDev = GPS_CalculateDeviation(DataStorage.naviData.CurrentPosition, DataStorage.naviData.TargetPosition);
        //DataStorage.naviData.TargetPositionDeviation.Distance.value = targetDev.Distance.value;
        DataStorage.naviData.TargetPositionDeviation.Bearing.value = targetDev.Bearing.value;

        DataStorage.naviData.HomePositionDeviation.Distance.setValue((long) (10000 * distance(getGeoPosition(DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude), getGeoPosition(DataStorage.naviData.HomePosition.Latitude, DataStorage.naviData.HomePosition.Longitude))), false);
        //DataStorage.naviData.HomePositionDeviation.Bearing.setValue((long) bearing(getGeoPosition(DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude), getGeoPosition(DataStorage.naviData.HomePosition.Latitude, DataStorage.naviData.HomePosition.Longitude)));

        DataStorage.naviData.TargetPositionDeviation.Distance.setValue((long) (10000 * distance(getGeoPosition(DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude), getGeoPosition(DataStorage.naviData.TargetPosition.Latitude, DataStorage.naviData.TargetPosition.Longitude))), true);
        //DataStorage.naviData.TargetPositionDeviation.Bearing.setValue((long) bearing(getGeoPosition(DataStorage.naviData.CurrentPosition.Latitude, DataStorage.naviData.CurrentPosition.Longitude), getGeoPosition(DataStorage.naviData.TargetPosition.Latitude, DataStorage.naviData.TargetPosition.Longitude)));


        mapKit.getMainMap().repaint();
    }
}
