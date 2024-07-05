package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

public class Trajectories {
    private final Color color;

    public Trajectories(Color color) {
        this.color = color;
    }

    public Pose2d reflectY(Pose2d pose) {
        return this.color == Color.RED ? new Pose2d(pose.position.x, -pose.position.y, Math.toRadians(360) - pose.heading.toDouble()) : pose;
    }

    public Vector2d reflectY(Vector2d vector) {
        return this.color == Color.RED ? new Vector2d(vector.x, -vector.y) : vector;
    }

    public double reflectY(double theta) {
        return this.color == Color.RED ? Math.toRadians(360) - theta : theta;
    }

    public Action pathOne(TrajectoryActionBuilder builder) {
        return builder.lineToX(10).build();
    }

    public Action pathTwo(TrajectoryActionBuilder builder) {
        return builder.lineToX(20).build();
    }

    public Action pathThree(TrajectoryActionBuilder builder) {
        return builder.lineToX(30).build();
    }

    public enum Color {
        BLUE,
        RED
    }
}
