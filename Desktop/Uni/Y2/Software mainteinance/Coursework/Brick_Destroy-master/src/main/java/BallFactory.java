package main.java;

import main.Controller.BallControl;

import java.awt.geom.Point2D;

public class BallFactory {
    public BallControl getBallType(String name, Point2D ballPos){
        if (name == null)
            return null;
        if (name.equalsIgnoreCase("RUBBER"))
            return new RubberBall(ballPos);
        return null;
    }
}
