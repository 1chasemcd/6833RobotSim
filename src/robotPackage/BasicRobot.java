package robotPackage;

import processing.core.*;
import mainPackage.Main;

// Class for all ghost robots
public class BasicRobot {
    public PVector pos;
    double angle;
    int c;
    private PApplet sketch;

    // Contructor for turnBot and mainBot
    public BasicRobot(PApplet sketch, int c) {
        pos = new PVector(340, 260);
        angle = 4 * Math.PI -Math.PI/4;
        this.c = c;
        this.sketch = sketch;
    }

    // Contructor for ghostBots
    public BasicRobot(PApplet sketch, MainRobot robot) {
        pos = new PVector(robot.pos.x, robot.pos.y);
        angle = robot.angle;
        this.c = sketch.color(0, 100);
        this.sketch = sketch;
    }

    // Method to draw the robot.
    public void show() {
        // Store 4 corners in a list
        PVector[] points = new PVector[4];
        if (Main.TURN_POINT == 0) {
            // Distance from each corner to the center of the robot.
            double radius = Math.sqrt(Math.pow(Main.ROBOT_WIDTH/2, 2) + Math.pow(Main.ROBOT_LENGTH/2, 2));
            // Angle from each corner to the center of the robot.
            double baseAngle = Math.atan((Main.ROBOT_WIDTH/2)/(Main.ROBOT_LENGTH/2));

            // Determine each corner using above variables and polar coordinates.
            points[0] = new PVector(polToCart(radius, baseAngle + angle).x+pos.x,
                    polToCart(radius, baseAngle + angle).y+pos.y);
            points[1] = new PVector(polToCart(radius, Math.PI*2 - baseAngle + angle).x + pos.x,
                    polToCart(radius, Math.PI*2 - baseAngle + angle).y + pos.y);
            points[2] = new PVector(polToCart(radius, Math.PI+baseAngle + angle).x + pos.x,
                    polToCart(radius, Math.PI+baseAngle + angle).y + pos.y);
            points[3] = new PVector(polToCart(radius, Math.PI-baseAngle + angle).x + pos.x,
                    polToCart(radius, Math.PI-baseAngle + angle).y + pos.y);

        } else if (Main.TURN_POINT == 1) {
            // Set rear corners
            points[2] = new PVector(polToCart(Main.ROBOT_WIDTH/2, angle + Math.PI/2).x+pos.x,
                    polToCart(Main.ROBOT_WIDTH/2, angle + Math.PI/2).y+pos.y);
            points[3] = new PVector(polToCart(Main.ROBOT_WIDTH/2, angle - Math.PI/2).x+pos.x,
                    polToCart(Main.ROBOT_WIDTH/2, angle - Math.PI/2).y+pos.y);

            // Set front corners
            double radius = Math.sqrt(Math.pow(Main.ROBOT_WIDTH/2, 2) + Math.pow(Main.ROBOT_LENGTH, 2));
            double addAngle = Math.atan((Main.ROBOT_WIDTH/2)/(Main.ROBOT_LENGTH));
            points[0] = new PVector(polToCart(radius, angle - addAngle).x + pos.x,
                    polToCart(radius, angle - addAngle).y + pos.y);
            points[1] = new PVector(polToCart(radius, angle + addAngle).x + pos.x,
                    polToCart(radius, angle + addAngle).y + pos.y);

        } else if (Main.TURN_POINT == 2) {
            // Set front corners
            points[0] = new PVector(polToCart(Main.ROBOT_WIDTH/2, angle + Math.PI/2).x+pos.x,
                    polToCart(Main.ROBOT_WIDTH/2, angle + Math.PI/2).y+pos.y);
            points[1] = new PVector(polToCart(Main.ROBOT_WIDTH/2, angle - Math.PI/2).x+pos.x,
                    polToCart(Main.ROBOT_WIDTH/2, angle - Math.PI/2).y+pos.y);

            // Set rear corners
            double radius = Math.sqrt(Math.pow(Main.ROBOT_WIDTH/2, 2) + Math.pow(Main.ROBOT_LENGTH, 2));
            double addAngle = Math.atan((Main.ROBOT_WIDTH/2)/(Main.ROBOT_LENGTH));
            points[2] = new PVector(polToCart(-radius, angle + addAngle).x + pos.x,
                    polToCart(-radius, angle + addAngle).y + pos.y);
            points[3] = new PVector(polToCart(-radius, angle - addAngle).x + pos.x,
                    polToCart(-radius, angle - addAngle).y + pos.y);
        }

        // Draw front of robot
        sketch.strokeWeight(7);
        sketch.stroke(0, 255, 0, 150);
        sketch.line(points[0].x, points[0].y, points[1].x, points[1].y);

        // Draw rest of robot
        sketch.stroke(c);
        sketch.line(points[1].x, points[1].y, points[2].x, points[2].y);
        sketch.line(points[2].x, points[2].y, points[3].x, points[3].y);
        sketch.line(points[3].x, points[3].y, points[0].x, points[0].y);
    }

    // Method to convert polar coordinate to cartesian coordinate (used in show method)
    private PVector polToCart(double radius, double theta) {
        PVector cart = new PVector((float) (radius * Math.cos(theta)), (float) (radius * Math.sin(theta)));
        return cart;
    }
}