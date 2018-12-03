package mainPackage;

import processing.core.*;

public class PathLine {
    PVector point1;
    PVector point2;
    PApplet sketch;

    public PathLine(PApplet sketch, float x0,float y0,float x1,float y1) {
        point1 = new PVector(x0, y0);
        point2 = new PVector(x1, y1);
        this.sketch = sketch;
    }

    public void show() {
        sketch.strokeWeight(2);
        sketch.stroke(0);
        sketch.line(point1.x, point1.y, point2.x, point2.y);
    }
}