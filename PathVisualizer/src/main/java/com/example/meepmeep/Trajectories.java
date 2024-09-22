package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

public final class Trajectories {
    /*
       ---------------------------------------
       ------------RED CLOSE BASKET-----------
       ---------------------------------------
    */
    public static Action pathOne(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.vectorToPose(Positions.modifyPose(Positions.RED.GENERAL.BASKET, Constants.ROBOT_LENGTH/2, Constants.ROBOT_WIDTH/2), 180),180)
                .setReversed(true)
                .splineToLinearHeading(Positions.vectorToPose(Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP, 225), 90)
                .setReversed(false)
                .splineToLinearHeading(Positions.vectorToPose(Positions.modifyPose(Positions.RED.GENERAL.BASKET, Constants.ROBOT_LENGTH/2, Constants.ROBOT_WIDTH/2), 180),180)
                .setReversed(true)
                .splineToLinearHeading(Positions.vectorToPose(Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP, 160), 90)
                .build();
    }

    public static Action pathTwo(TrajectoryActionBuilder builder) {
        return builder.lineToX(20).build();
    }

    public static Action pathThree(TrajectoryActionBuilder builder) {
        return builder.lineToX(30).build();
    }

    /*
       ---------------------------------------
       ------------RED FAR BASKET-----------
       ---------------------------------------
    */

    /*
       ---------------------------------------
       ------------BLUE CLOSE BASKET-----------
       ---------------------------------------
    */

    /*
       ---------------------------------------
       ------------BLUE FAR BASKET-----------
       ---------------------------------------
    */
}
