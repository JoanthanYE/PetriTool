//****************************************************************************// CLASS NAME:	MultiLineLabel.java//// AUTHOR:	Rick Brink//		    rick@mail.csh.rit.edu//		    http://www.csh.rit.edu/~rick////          Adapted from an example in "Java in a Nutshell"//		    by David Flanagam, O'Reilly & Associates, Inc.////// VERSION:	1.0//// HISTORY:	4/16/96		Initial Version//// COPYRIGHT INFORMATION://// This program and the Java source is in the public domain.// Permission to use, copy, modify, and distribute this software// and its documentation for NON-COMMERCIAL purposes and// without fee is hereby granted.////    Copyright 1996////    Rick Brink//    1266 Brighton-Henrietta Townline Rd.//    Rochester, NY 14623//// DISCLAIMER://// The author claims no responsibility for any damage, direct or indirect,// to any harware or software as a result of using this program.//****************************************************************************package PetriTool;import java.awt.Canvas;import java.awt.FontMetrics;import java.awt.Color;import java.awt.Dimension;import java.awt.Graphics;import java.awt.Font;import java.util.StringTokenizer;/**  * A class representing a label capable of handling  * more than one line of text.  *  * @see YesNoDialog  * @see InfoDialog  * @see java.awt.Canvas  * @see java.awt.FontMetrics  * @see java.awt.Color  * @see java.awt.Dimension  * @see java.awt.Event  * @see java.awt.Graphics  * @see java.awt.Font  * @see java.util.StringTokenizer  *  * @version 1.0 July 3, 1996  *  * @author  Rick Brink**/public class MultiLineLabel extends Canvas {    /** Alignment constant for Left justification **/    public static final int LEFT = 0;     /** Alignment constant for Center justification **/    public static final int CENTER = 1;    /** Alignment constant for Right justification **/    public static final int RIGHT = 2;    /** The lines of text to display **/    protected String[] lines;    /** The number of lines **/    protected int num_lines;    /** Left and right margins **/    protected int margin_width;    /** Top and bottom margins **/    protected int margin_height;    /** Total height of the font **/    protected int line_height;    /** Font height above baseline **/    protected int line_ascent;    /** How wide each line is **/    protected int[] line_widths;    /** The width of the widest line **/    protected int max_width;    /** The alignment of the text **/    protected int alignment = LEFT;    /**      * This method breaks a specified label up into an array of lines.      * It uses the StringTokenizer utility class.    **/    protected void newLabel(String label) {        StringTokenizer t = new StringTokenizer(label, "\n");        num_lines = t.countTokens();        lines = new String[num_lines];        line_widths = new int[num_lines];        for(int i = 0; i < num_lines; i++) lines[i] = t.nextToken();    }    /**      * This method figures out how the font is, and how wide each      * line of the label is, and how wide the widest line is.    **/    protected void measure() {        FontMetrics fm = this.getFontMetrics(this.getFont());        // If we don't have font metrics yet, just return.        if (fm == null) return;        line_height = fm.getHeight();        line_ascent = fm.getAscent();        max_width = 0;        for(int i = 0; i < num_lines; i++) {            line_widths[i] = fm.stringWidth(lines[i]);            if (line_widths[i] > max_width) max_width = line_widths[i];        }    }    /**      * Construct a new MultiLineLabel.      * Break the label up into separate lines, and save the other      * parameters.      *      * @param label One or more lines of text to display      * @param margin_width Integer size of the margin width      * @param margin_height Integer size of the margin height      * @param alignment Whether label should be left, right, or      *                  center justified    **/    public MultiLineLabel(String label, int margin_width, int margin_height,                  int alignment) {        newLabel(label);        this.margin_width = margin_width;        this.margin_height = margin_height;        this.alignment = alignment;    }    /**      * Construct a new MultiLineLabel.      * Break the label up into separate lines, and save the other      * parameters. Alignment is assumed to be LEFT.      *      * @param label One or more lines of text to display      * @param margin_width Integer size of the margin width      * @param margin_height Integer size of the margin height      *    **/    public MultiLineLabel(String label, int margin_width, int margin_height) {        this(label, margin_width, margin_height, LEFT);    }    /**      * Construct a new MultiLineLabel.      * Break the label up into separate lines, and save the other      * parameters. Margins are assumed to be 10.      *      * @param label One or more lines of text to display      * @param alignment Whether label should be left, right, or      *                  center justified    **/    public MultiLineLabel(String label, int alignment) {        this(label, 10, 10, alignment);    }    /**      * Construct a new MultiLineLabel.      * Break the label up into separate lines. Margins are      * assumed to be 10, and alignment LEFT.      *      * @param label One or more lines of text to display    **/    public MultiLineLabel(String label) {        this(label, 10, 10, LEFT);    }    /**      * Set the label of this component    **/    public void setLabel(String label) {        newLabel(label);        measure();        repaint();    }    /**      * Set the Font of this component    **/    public void setFont(Font f) {        super.setFont(f);        measure();        repaint();    }    /**      * Set the foreground color of this component    **/    public void setForeground(Color c) {        super.setForeground(c);        repaint();    }    /**      * Set the alignment of this component    **/    public void setAlignment(int a) {        alignment = a;        repaint();    }    /**      * Set the margin width of this component    **/    public void setMarginWidth(int mw) {        margin_width = mw;        repaint();    }    /**      * Set the margin height of this component    **/    public void setMarginHeight(int mh) {        margin_height = mh;        repaint();    }    /**      * Return the alignment of this component    **/    public int getAlignment() {        return alignment;    }    /**      * Return the margin width of this component    **/    public int getMarginWidth() {        return margin_width;    }    /**      * Return the margin height of this component    **/    public int getMarginHeight() {        return margin_height;    }    /** This method is invoked after our Canvas is first created      * but before it can actually be displayed.  After we've      * invoked our superclass's addNotify() method, we have font      * metrics and can successfully call measure() to figure out      * how big the label is.    **/    public void addNotify() {        super.addNotify();        measure();    }    /**      * This method is called by a layout manager when it wants to      * know how big we'd like to be.    **/    public Dimension getPreferredSize() {        return new Dimension(max_width + 2*margin_width,                     num_lines * line_height + 2*margin_height);    }    /**      * This method is called when the layout manager wants to know      * the bare minimum amount of space we need to get by.    **/    public Dimension getMinimumSize() {        return new Dimension(max_width, num_lines * line_height);    }    /**      * This method draws the label.      * Note that it handles the margins and the alignment, but that      * it doesn't have to worry about the color or font--the superclass      * takes care of setting those in the Graphics object we're passed.    **/    public void paint(Graphics g) {        int x, y;        Dimension d = this.size();        y = line_ascent + (d.height - num_lines * line_height)/2;        for(int i = 0; i < num_lines; i++, y += line_height) {            switch(alignment) {            case LEFT:                x = margin_width; break;            case CENTER:            default:                x = (d.width - line_widths[i])/2; break;            case RIGHT:                x = d.width - margin_width - line_widths[i]; break;            }            g.drawString(lines[i], x, y);        }    }}