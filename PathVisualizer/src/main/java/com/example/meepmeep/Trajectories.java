package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

public final class Trajectories {
    /*
       ---------------------------------------
       ------------RED CLOSE BASKET-----------
       ---------------------------------------
    */
    //todo: tidy up end tangents and make it less messy
    public static Action redClose(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(0))
                .splineToLinearHeading(Positions.vectorToPose(
                        Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                        Math.toRadians(270)),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(285))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.CLOSE_BASKET.SPIKEMARK1_ANGLE),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(285))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.CLOSE_BASKET.SPIKEMARK3_ANGLE),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(285))
                .build();
    }

    public static Action redFar(TrajectoryActionBuilder builder) {
        return builder.lineToX(Positions.modifyPose(Positions.RED.GENERAL.BASKET,
                        Constants.ROBOT_LENGTH,
                        0).x)
                .build();
    }

    public static Action blueClose(TrajectoryActionBuilder builder) {
        return builder.lineToX(30).build();
    }

    public static Action blueFar(TrajectoryActionBuilder builder) {
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
