/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

/**
 * 32bit unsigned int
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class u32 extends c_int {

    public u32(String name) {
        signed = false;
        length = 32;
        this.name = name;
    }
}
