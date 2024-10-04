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
                        Math.toRadians(210))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.GENERAL.SPIKEMARK1_ANGLE),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.GENERAL.SPIKEMARK3_ANGLE),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                .build();
    }

    public static Action redFar(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .splineToLinearHeading(Positions.vectorToPose(Positions.RED.FAR_BASKET.SPIKEMARK_SETUP, Math.toRadians(270)), Math.toRadians(330))
                .turnTo(Positions.RED.GENERAL.SPIKEMARK1_ANGLE)
                .turnTo(Math.toRadians(270))
                .turnTo(Positions.RED.GENERAL.SPIKEMARK3_ANGLE)
                .turnTo(Math.toRadians(270))
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
