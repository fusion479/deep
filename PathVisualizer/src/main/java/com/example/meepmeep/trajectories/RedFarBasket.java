package com.example.meepmeep.trajectories;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RedFarBasket {
    private final Pose2d START, RUNGS, RUNGS1, RUNGS2, RUNGS3, LEFT_SPIKEMARK, MID_SPIKEMARK, RIGHT_SPIKEMARK, SCORE, PARK;

    public RedFarBasket() {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat("/PathVisualizer/src/main/java/com/example/meepmeep/positions/red/far-basket.json"));
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

        this.RUNGS1 = new Pose2d(
                positions.getJSONObject("RUNG1").getDouble("x"),
                positions.getJSONObject("RUNG1").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG1").getDouble("heading"))
        );

        this.RUNGS2 = new Pose2d(
                positions.getJSONObject("RUNG2").getDouble("x"),
                positions.getJSONObject("RUNG2").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG2").getDouble("heading"))
        );

        this.RUNGS3 = new Pose2d(
                positions.getJSONObject("RUNG3").getDouble("x"),
                positions.getJSONObject("RUNG3").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG3").getDouble("heading"))
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

        this.PARK = new Pose2d(
                positions.getJSONObject("PARK").getDouble("x"),
                positions.getJSONObject("PARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PARK").getDouble("heading"))
        );
    }

    public Action start(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(this.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.MID_SPIKEMARK, Math.toRadians(330))
                .turnTo(this.LEFT_SPIKEMARK.heading)
                .turnTo(Math.toRadians(270))
                .turnTo(this.RIGHT_SPIKEMARK.heading)
                .turnTo(Math.toRadians(270))
                .turnTo(Math.toRadians(90))
                .setTangent(Math.toRadians(155))
                .splineToLinearHeading(this.RUNGS, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.SCORE, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(this.RUNGS1, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.SCORE, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(this.RUNGS2, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.SCORE, Math.toRadians(330))
                .setTangent(Math.toRadians(135))
                .splineToLinearHeading(this.RUNGS3, Math.toRadians(180))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.PARK, Math.toRadians(270))
                .build();
    }

    public Pose2d getStart() {
        return this.START;
    }
}
