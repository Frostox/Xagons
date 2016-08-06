/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vosaye.xagon.ds;

import android.graphics.Color;


/**
 *
 * @author Roger
 */
public class Node {
    public int iRow, jCol;
    public String name;
    int leastDistanceTillNow;
    public int value;
    public static final int SQUARE = 0, TRIANGLE = 1, CIRCLE = 2;
    public int color = Color.rgb(239,212,186);
    public int colorCode = 0;
    public static final int UNSELECTED = Color.rgb(239,212,186), SELECTED = Color.rgb(200,200,200), HOVER = Color.rgb(250,250,250), GREEN = Color.rgb(161, 255, 208);
    
    public Node(String name){
        this.name = name;
    }
    
}
