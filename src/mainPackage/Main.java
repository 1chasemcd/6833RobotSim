package mainPackage;

import processing.core.*;
import java.util.ArrayList;
import robotPackage.*;

public class Main extends PApplet{

    // Ghost bots showing previous positions
    ArrayList<BasicRobot> ghostBots = new ArrayList<>();

    // Lines showing previous movements
    ArrayList<PathLine> lines = new ArrayList<>();

    // Robot and field image variables
    MainRobot turnBot;
    MainRobot mainBot;
    PImage fieldImg;

    // Robot variables. Change based on robot specifications
    public static final double SCALE = (600 / (11.75 * 12));

    // This should be the inches between the wheels on each side of the robot
    public static final double ROBOT_WIDTH = 13 * SCALE;
    public static final double ROBOT_LENGTH = 18 * SCALE;

    // 0 = Center, 1 = Back, 2 = Front
    public static final int TURN_POINT = 1;

    public void settings() { size(600, 600); }

    // Prepare variables in setup method
    public void setup() {
        turnBot = new MainRobot(this, color(0, 100));
        mainBot = new MainRobot(this, color(255, 0, 0));

        ghostBots.add(new BasicRobot(this, mainBot));

        fieldImg = loadImage("/Users/chaseandclaire/Documents/IdeaProjects/RobotSim/src/mainPackage/FTCField.png");
        noFill();
    }

    public void draw() {
        // Draw in background to clear
        background(fieldImg);

        // Draw in pathlines
        for (PathLine l : lines) {
            l.show();
        }
        PathLine tempLine = new PathLine(this, turnBot.pos.x, turnBot.pos.y,
                mainBot.pos.x, mainBot.pos.y);
        tempLine.show();

        // Draw in ghost robots
        for (BasicRobot robot : ghostBots) {
            robot.show();
        }

        // Change main and turn robot positions and angles based on mouse
        turnBot.setAngle();
        mainBot.setAngle();
        mainBot.pos.x = mouseX;
        mainBot.pos.y = mouseY;

        // Draw two robots. These will be placed on top
        turnBot.show();
        mainBot.show();
        //println(mainBot.angle);
    }

    // Check if mouse is clicked
    public void mouseClicked() {
        // Print out encoder tick values
        println("path.add(new MoveByEncoder(" +
                mainBot.getTurnValue() + ", 0.7, MoveByEncoder.TURN, 1));");

        println("path.add(new MoveByEncoder(" +
                mainBot.getMoveValue() + ", 0.7, MoveByEncoder.FORWARD, 1));");

        // Append ghost robots, lines, and set up for next click
        lines.add(new PathLine(this, turnBot.pos.x, turnBot.pos.y,
                mainBot.pos.x, mainBot.pos.y));
        ghostBots.add(new BasicRobot(this, turnBot));
        ghostBots.add(new BasicRobot(this, mainBot));
        mainBot.reset();
        turnBot.reset();
    }

    public void keyPressed() {
        if (keyCode == SHIFT) {
            mainBot.forwards = false;
        } else if (key == 'l') {
            println("path.add(new Lift(1.3, 0.7, 1));");
        }
    }

    public void keyReleased() {
        if (keyCode == SHIFT) {
            mainBot.forwards = true;
        }
    }

    public static void main(String[] args) {
        PApplet.main("mainPackage.Main");
    }
}
