/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;


/**
 * 16bit unsigned int
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class u16 extends c_int {

    public u16(String name) {
        signed = false;
        length = 16;
        this.name = name;
    }
    public u16(String name,int maxValue) {
        signed = false;
        length = 16;
        this.name = name;
        this.maxValue=new Integer(maxValue);
    }
}
