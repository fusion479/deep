package com.example.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;

public class Positions {
    private final Color color;

    public Pose2d START_POS = new Pose2d(0, 0, 0);
    public Pose2d EXAMPLE_ONE_POS = new Pose2d(10, 10, 90);
    public Pose2d EXAMPLE_TWO_POS = new Pose2d(20, 20, 180);
    public Pose2d EXAMPLE_THREE_POS = new Pose2d(30, 30, 270);


    public Positions(Color color) {
        this.color = color;

        if (this.color == Color.RED) {
            this.EXAMPLE_ONE_POS = reflectY(this.EXAMPLE_ONE_POS);
            this.EXAMPLE_ONE_POS = reflectY(this.EXAMPLE_ONE_POS);
            this.EXAMPLE_TWO_POS = reflectY(this.EXAMPLE_TWO_POS);
            this.EXAMPLE_THREE_POS = reflectY(this.EXAMPLE_THREE_POS);
        }
    }

    public Pose2d reflectY(Pose2d pose) {
        return new Pose2d(pose.position.x, -pose.position.y, Math.toRadians(360) - pose.heading.toDouble());
    }

    public enum Color {
        RED,
        BLUE
    }
}
