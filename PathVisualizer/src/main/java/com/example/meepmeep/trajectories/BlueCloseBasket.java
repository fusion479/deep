package com.example.meepmeep.trajectories;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BlueCloseBasket {
    private final Pose2d START, RUNGS, LEFT_SPIKEMARK, MID_SPIKEMARK, RIGHT_SPIKEMARK, SCORE, SUBMERSIBLE;

    public BlueCloseBasket(int type) {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat(type == 0 ? "/PathVisualizer/src/main/java/com/example/meepmeep/positions/blue/close-basket.json" : "/sdcard/FIRST/positions/blue/close-basket.json"));
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                jsonString += reader.nextLine();
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject positions = new JSONObject(jsonString);

        this.START = new Pose2d(
                positions.getJSONObject("START").getDouble("x"),
                positions.getJSONObject("START").getDouble("y"),
                Math.toRadians(positions.getJSONObject("START").getDouble("heading"))
        );

        this.RUNGS = new Pose2d(
                positions.getJSONObject("RUNG").getDouble("x"),
                positions.getJSONObject("RUNG").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG").getDouble("heading"))
        );

        this.LEFT_SPIKEMARK = new Pose2d(
                positions.getJSONObject("LEFT_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("LEFT_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("LEFT_SPIKEMARK").getDouble("heading"))
        );

        this.MID_SPIKEMARK = new Pose2d(
                positions.getJSONObject("MID_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("MID_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("MID_SPIKEMARK").getDouble("heading"))
        );

        this.RIGHT_SPIKEMARK = new Pose2d(
                positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("heading"))
        );

        this.SCORE = new Pose2d(
                positions.getJSONObject("SCORE").getDouble("x"),
                positions.getJSONObject("SCORE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SCORE").getDouble("heading"))
        );

        this.SUBMERSIBLE = new Pose2d(
                positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                positions.getJSONObject("SUBMERSIBLE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SUBMERSIBLE").getDouble("heading"))
        );

    }

    public Action start(TrajectoryActionBuilder builder) {
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
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(SUBMERSIBLE, Math.toRadians(270))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(SCORE, Math.toRadians(0))
                .build();
    }

    public Pose2d getStart() {
        return this.START;
    }

    public String getScore() {
        return "(" + this.SCORE.position.x + ", " + this.SCORE.position.y + ")";
    }
}
