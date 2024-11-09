package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Trajectories {
    private static final String BASE_PATH = "/PathVisualizer/src/main/java/com/example/meepmeep/positions";

    public static String BLUE_CLOSE_PATH = BASE_PATH.concat("/blue/close-basket.json");
    public static String BLUE_FAR_PATH = BASE_PATH.concat("/blue/far-basket.json");
    public static String RED_CLOSE_PATH = BASE_PATH.concat("/red/close-basket.json");
    public static String RED_FAR_PATH = BASE_PATH.concat("/red/far-basket.json");

    private final JSONObject positions;

    public Trajectories(String path) {
        // Load the position from specified JSON file
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat("/PathVisualizer/src/main/java/com/example/meepmeep/positions/blue/close-basket.json"));
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                jsonString += reader.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.positions = new JSONObject(jsonString);
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

    public Action blueClose(TrajectoryActionBuilder builder) {
        Pose2d RUNGS = new Pose2d(
                this.positions.getJSONObject("RUNG").getDouble("x"),
                this.positions.getJSONObject("RUNG").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("RUNG").getDouble("heading"))
        );

        Pose2d LEFT_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("heading"))
        );

        Pose2d MID_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("MID_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("MID_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("MID_SPIKEMARK").getDouble("heading"))
        );

        Pose2d RIGHT_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("heading"))
        );

        Pose2d SCORE = new Pose2d(
                this.positions.getJSONObject("SCORE").getDouble("x"),
                this.positions.getJSONObject("SCORE").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("SCORE").getDouble("heading"))
        );

        Pose2d SUBMERSIBLE = new Pose2d(
                this.positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                this.positions.getJSONObject("SUBMERSIBLE").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("SUBMERSIBLE").getDouble("heading"))
        );

        return builder.splineToLinearHeading(RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(MID_SPIKEMARK, Math.toRadians(30))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(SCORE, Math.toRadians(93))
                .splineToLinearHeading(LEFT_SPIKEMARK, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(SCORE, Math.toRadians(93))
                .splineToLinearHeading(RIGHT_SPIKEMARK, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(SCORE, Math.toRadians(93))
                // Start of the cycling
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(SUBMERSIBLE, Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(Positions.BLUE.CLOSE_BASKET.SCORE_SETUP, Math.toRadians(0))
                .build();
    }
}
