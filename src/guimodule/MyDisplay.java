package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet {

    public void setup(){
        size(400,600);
        background(200,200,200);
    }

    public void draw(){
        fill(255,255,0);
        ellipse(200, 200, 390,390);
        fill(0,0,0);
        ellipse(120,130,50,70);
        ellipse(280, 130,50,70);
        arc(200,280,75,75,0,PI);
        noFill();
    }


    public static void main (String[] args) {
        //Add main method for running as application
        PApplet.main(new String(guimodule.MyDisplay.class.getName()));
    }

}

