/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.ncsimulator.datatypes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 8bit signed int Test Class
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class s8Test {

    public s8Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSigned() {
        s8 t = new s8("test");
        t.loadFromInt(new int[]{0}, 0);
        assertEquals(0, t.value);
        t.loadFromInt(new int[]{1}, 0);
        assertEquals(1, t.value);
        t.loadFromInt(new int[]{129}, 0);
        assertEquals(-127, t.value);
        t.loadFromInt(new int[]{255}, 0);
        assertEquals(-1, t.value);
    }
}
