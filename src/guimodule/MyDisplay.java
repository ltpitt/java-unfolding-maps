package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet {

    public void setup(){
        size(400,600);
        background(200,200,200);
    }

    public void draw(){

    }


    public static void main (String[] args) {
        //Add main method for running as application
        PApplet.main(new String(guimodule.MyDisplay.class.getName()));
    }

}

