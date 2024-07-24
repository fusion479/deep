package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

public class Trajectories {
    private final Positions.Color color;
    private final Positions positions;

    public Trajectories(Positions.Color color) {
        this.color = color;
        this.positions = new Positions(color);
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
}
