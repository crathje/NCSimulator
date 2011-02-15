/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;


/**
 * 8bit signed int
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class s8 extends c_int {

    public s8(String name) {
        signed = true;
        length = 8;
        this.name = name;
    }
}
