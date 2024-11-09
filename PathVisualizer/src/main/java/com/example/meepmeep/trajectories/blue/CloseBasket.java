package com.example.meepmeep.trajectories.blue;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CloseBasket {
    private final JSONObject positions;
    private final Pose2d RUNGS, LEFT_SPIKEMARK, MID_SPIKEMARK, RIGHT_SPIKEMARK, SCORE, SUBMERSIBLE;

    public CloseBasket() {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat("/PathVisualizer/src/main/java/com/example/meepmeep/positions/blue/close-basket.json"));
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                jsonString += reader.nextLine();
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

        this.positions = new JSONObject(jsonString);

        this.RUNGS = new Pose2d(
                this.positions.getJSONObject("RUNG").getDouble("x"),
                this.positions.getJSONObject("RUNG").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("RUNG").getDouble("heading"))
        );

        this.LEFT_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("LEFT_SPIKEMARK").getDouble("heading"))
        );

        this.MID_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("MID_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("MID_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("MID_SPIKEMARK").getDouble("heading"))
        );

        this.RIGHT_SPIKEMARK = new Pose2d(
                this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("x"),
                this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("heading"))
        );

        this.SCORE = new Pose2d(
                this.positions.getJSONObject("SCORE").getDouble("x"),
                this.positions.getJSONObject("SCORE").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("SCORE").getDouble("heading"))
        );

        this.SUBMERSIBLE = new Pose2d(
                this.positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                this.positions.getJSONObject("SUBMERSIBLE").getDouble("y"),
                Math.toRadians(this.positions.getJSONObject("SUBMERSIBLE").getDouble("heading"))
        );

    }

    public Action blueClose(TrajectoryActionBuilder builder) {
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
                .build();
    }
}
