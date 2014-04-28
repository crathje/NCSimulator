/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import java.util.LinkedList;

/**
 *
 * @author frankblumenberg
 */
public class WPL_Answer_t extends c_int {

    public static final int WPL_ERROR = 0;
    public static final int WPL_OK = 1;
    public static final int WPL_FILEEXIST = 2;
    public static final int WPL_NO_SDCARD_FOUND = 3;
    public static final int WPL_NO_WAYPOINTS = 4;

    public u8 Index;
    public u8 Status;

    public WPL_Answer_t() {
        super();
        allAttribs = new LinkedList<c_int>();
        Index = new u8("Index");
        Status = new u8("Status");
        allAttribs.add(Index);
        allAttribs.add(Status);
    }
}
