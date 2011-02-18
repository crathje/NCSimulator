/*
 *  Copyright (C) 2010-2011 by Claas Anders "CaScAdE" Rathje
 *  admiralcascade@gmail.com
 *  Licensed under: Creative Commons / Non Commercial / Share Alike
 *  http://creativecommons.org/licenses/by-nc-sa/2.0/de/
 *
 */
package de.mylifesucks.oss.graphichelpers;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Claas Anders "CaScAdE" Rathje
 */
public class DrawStringHelpers {

    /**
     * Draw a given String to a graphic object as big as possible but still stay within given dimension.
     *
     * For simple string width you might want so take a look at:
     * @see javax.swing.SwingUtilities#computeStringWidth(null, null);
     *
     * @param g2d the graphics to draw to
     * @param dim the dimension to stay into
     * @param drawString the string to draw
     */
    public static void drawStringBiggest(final Graphics2D g2d, final Dimension dim, final String drawString) {

        Rectangle2D bounds;

//        System.out.println("drawBiggest");

        Object KEY_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Object KEY_TEXT_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        int fontsize = (int) Math.max(6, Math.ceil(dim.getWidth() / drawString.length()) * 3);
        fontsize = Math.min(fontsize, (int) Math.ceil(dim.getHeight() * 1.3));

        //fontsize = 400;


        float posx = 0, posy = 0;
        //System.out.println("Starting fs: " + fontsize + " ws: " + getWidth() + " strlen: " + drawString.length());
        //long starttime = System.currentTimeMillis();
        do {
            fontsize -= 5;

            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, fontsize));
            FontRenderContext frc = g2d.getFontRenderContext();
            TextLayout tl = new TextLayout(drawString, g2d.getFont(), frc);
            bounds = tl.getBounds();
            //bounds = tl.getPixelBounds(frc, 0, 0);
            posx = (float) (dim.getWidth() / 2 - bounds.getCenterX());
            posy = (float) (dim.getHeight() / 2 - bounds.getCenterY());



        } while ((bounds.getMaxX() + posx > dim.getWidth() || bounds.getMaxY() + posy > dim.getHeight()
                || bounds.getMinY() + posy < 0 || bounds.getMinX() + posx < 0)
                && fontsize > 5);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                KEY_ANTIALIASING_before);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                KEY_TEXT_ANTIALIASING_before);

        g2d.drawString(drawString, (int) posx, (int) posy);
    }

    /**
     * Draw a given String to a graphic object centered
     *
     * @param g2d the graphics to draw to
     * @param dim the dimension to stay into
     * @param drawString the string to draw
     * @param fontSize chosen FontSize to draw with
     */
    public static void drawStringCentered(final Graphics2D g2d, final Dimension dim, final String drawString, final int fontSize) {
        Rectangle2D bounds;


        Object KEY_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Object KEY_TEXT_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        float posx = 0, posy = 0;

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, fontSize));
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(drawString, g2d.getFont(), frc);
        bounds = tl.getBounds();
        posx = (float) (dim.getWidth() / 2 - bounds.getCenterX());
        posy = (float) (dim.getHeight() / 2 - bounds.getCenterY());

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                KEY_ANTIALIASING_before);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                KEY_TEXT_ANTIALIASING_before);

        g2d.drawString(drawString, (int) posx, (int) posy);
    }

    /**
     * Draw a given String to a graphic object
     *
     * @param g2d the graphics to draw to
     * @param dim the dimension to stay into
     * @param drawString the string to draw
     * @param fontSize chosen FontSize to draw with
     * @param x
     * @param xpercent
     * @param y
     * @param ypercent
     */
    public static void drawStringAtPos(final Graphics2D g2d,
            final Dimension dim,
            final String drawString,
            final int fontSize,
            final double x,
            final boolean xpercent,
            final double y,
            final boolean ypercent) {
        Rectangle2D bounds;


        Object KEY_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        Object KEY_TEXT_ANTIALIASING_before = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        float posx = 0, posy = 0;

        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, fontSize));
        FontRenderContext frc = g2d.getFontRenderContext();
        TextLayout tl = new TextLayout(drawString, g2d.getFont(), frc);
        bounds = tl.getBounds();

        double xfactor = 1;
        if (xpercent) {
            xfactor = dim.getWidth() / 100.0;
        }
        double yfactor = 1;
        if (ypercent) {
            yfactor = dim.getHeight() / 100.0;
        }

        posx = (float) (x * xfactor - bounds.getCenterX());
        posy = (float) (y * yfactor - bounds.getCenterY());

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                KEY_ANTIALIASING_before);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                KEY_TEXT_ANTIALIASING_before);

        g2d.drawString(drawString, (int) posx, (int) posy);
    }
}
