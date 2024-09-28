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
        return builder.lineToX(Positions.modifyPose(Positions.RED.GENERAL.BASKET,
                        Constants.ROBOT_LENGTH,
                        0).x)
                .splineToLinearHeading(Positions.vectorToPose(
                        Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                        Math.toRadians(270)),
                        Math.toRadians(180))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, 275)
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
