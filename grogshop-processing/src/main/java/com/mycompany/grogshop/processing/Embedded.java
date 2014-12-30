/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.grogshop.processing;

import java.awt.Color;
import peasy.PeasyCam;
import processing.core.PApplet;

/**
 *
 * @author grogdj
 */
public class Embedded extends PApplet {

    private PeasyCam cam;
    private int screenWidth;
    private int screenHeight;
    private int boxSize = 30;
    private int[] bids = new int[100]; 
    private int nroOfBids = 0;

    public Embedded(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
    }
    
    public void addNewBid(int amount){
        bids[nroOfBids] = amount;
        nroOfBids++;
    }
    

    public void setup() {
        // original setup code here ...
        size(screenWidth, screenHeight, P3D);
        setBackground(Color.BLACK);
        if (frame != null) {
            frame.setResizable(true);
        }
        cam = new PeasyCam(this, 100);
        cam.setMinimumDistance(50);
        cam.setMaximumDistance(500);
        setVisible(true);
//        cam.setMinimumDistance(50);
//        cam.setMaximumDistance(1000);
//        cam.setWheelScale(1.5);

        // exitFullscreen();
//        smooth();
//        noStroke();
        // prevent thread from starving everything else
        // noLoop();
    }

    public void draw() {
        // drawing code goes here
        background(0);
        lights();
        sphere(boxSize);
        translate((width / 5), height / 5, -100);
        for(int bid : bids){
            rotate(PI/4);
            translate((width / 5), height / 5, -100);
            box(bid);
        }
        
        

    }

    public void mousePressed() {
         // do something based on mouse movement

        // update the screen (run draw once)
        //redraw();
    }
}
