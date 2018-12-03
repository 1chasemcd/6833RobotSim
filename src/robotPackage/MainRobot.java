package robotPackage;

import processing.core.*;
import mainPackage.Main;

// Class for turnBot and mainBot
public class MainRobot extends BasicRobot {
    PVector initialPos;
    double initialAngle;
    public boolean forwards = true;
    PApplet sketch;

    // Constructor
    public MainRobot(PApplet sketch, int c) {
        super(sketch, c);
        initialPos = new PVector(pos.x, pos.y);
        initialAngle = angle;
        this.sketch = sketch;
    }

    // Get encoder ticks for a turn of any angle
    public double getTurnValue() {
        double theta = angle - initialAngle;

        while (theta < -Math.PI || theta > Math.PI) {
            if (theta > Math.PI) {
                theta -= Math.PI * 2;
            } else if (theta < -Math.PI) {
                theta += Math.PI * 2;
            }
        }

        return theta;
    }

    // Get encoder ticks for moving forward.
    public double getMoveValue() {
        double inches = Math.sqrt(Math.pow((sketch.mouseX-initialPos.x)/Main.SCALE, 2)
                +Math.pow((sketch.mouseY-initialPos.y)/Main.SCALE, 2));

        if (forwards) {
            return inches;
        } else {
            return -inches;
        }
    }

    // Method to set the angle of the robot.
    public void setAngle() {
        if (sketch.mouseY != initialPos.y && sketch.mouseX != initialPos.x) {
            angle = Math.atan((sketch.mouseY-initialPos.y)/(sketch.mouseX-initialPos.x));
            if (sketch.mouseX < initialPos.x) {
                angle += Math.PI;
            } else if (sketch.mouseY < initialPos.y && sketch.mouseX > initialPos.x) {
                angle += Math.PI * 2;
            }
        }

        if (!forwards) {
            angle = angle - Math.PI;
        }
    }

    // Method to set the robot up for the next turn
    public void reset() {
        setAngle();
        initialAngle = angle;
        pos.x = sketch.mouseX;
        pos.y = sketch.mouseY;
        initialPos.x = sketch.mouseX;
        initialPos.y = sketch.mouseY;

        show();
    }
}