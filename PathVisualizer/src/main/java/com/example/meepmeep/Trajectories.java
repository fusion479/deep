package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Trajectories {
    public Trajectories() {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat("/PathVisualizer/src/main/java/com/example/meepmeep/positions/blue/close-basket.json"));
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                jsonString += reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND :(");
            e.printStackTrace();
        }

        JSONObject json = new JSONObject(jsonString);

        System.out.println(json.getJSONObject("RUNG").getInt("x"));
    }

    // TODO: Tidy up end tangents and make it less messy
    public static Action redClose(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Math.toRadians(270)),
                        Math.toRadians(210))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.GENERAL.SPIKEMARK1_ANGLE),
                        Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.RED.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.RED.GENERAL.SPIKEMARK3_ANGLE),
                        Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(273))
                // Start of the cycling
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SUBMERSIBLE_SETUP, Math.toRadians(90))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(Positions.RED.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(180))
                .build();
    }

    public static Action redFar(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.vectorToPose(Positions.RED.FAR_BASKET.SPIKEMARK_SETUP, Math.toRadians(270)), Math.toRadians(330))
                .turnTo(Positions.RED.GENERAL.SPIKEMARK1_ANGLE)
                .turnTo(Math.toRadians(270))
                .turnTo(Positions.RED.GENERAL.SPIKEMARK3_ANGLE)
                .turnTo(Math.toRadians(270))
                // Start of the cycling
                .turnTo(Math.toRadians(90))
                .setTangent(Math.toRadians(155))
                .splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.FAR_BASKET.SCORE_SETUP, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.FAR_BASKET.SCORE_SETUP, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.RED.FAR_BASKET.SCORE_SETUP, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(Positions.RED.GENERAL.RUNGS, Math.toRadians(180))
                .build();
    }

    public static Action blueClose(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.BLUE.GENERAL.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.BLUE.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Math.toRadians(90)),
                        Math.toRadians(30))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(93))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.BLUE.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.BLUE.GENERAL.SPIKEMARK1_ANGLE),
                        Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(93))
                .splineToLinearHeading(Positions.vectorToPose(
                                Positions.BLUE.CLOSE_BASKET.SPIKEMARK_SETUP,
                                Positions.BLUE.GENERAL.SPIKEMARK3_ANGLE),
                        Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(93))
                // Start of the cycling
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SUBMERSIBLE_SETUP, Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(0))
                .build();
    }

    public static Action blueFar(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(Positions.BLUE.GENERAL.RUNGS, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.vectorToPose(Positions.BLUE.FAR_BASKET.SPIKEMARK_SETUP, Math.toRadians(90)), Math.toRadians(150))
                .turnTo(Positions.BLUE.GENERAL.SPIKEMARK1_ANGLE)
                .turnTo(Math.toRadians(90))
                .turnTo(Positions.BLUE.GENERAL.SPIKEMARK3_ANGLE)
                .turnTo(Math.toRadians(90))
                // Start of the cycling
                .setTangent(Math.toRadians(335))
                .splineToLinearHeading(Positions.BLUE.FAR_BASKET.SUBMERSIBLE_SETUP, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(Positions.vectorToPose(Positions.BLUE.FAR_BASKET.SPIKEMARK_SETUP, Math.toRadians(90)), Math.toRadians(150))
                .build();
    }
}
