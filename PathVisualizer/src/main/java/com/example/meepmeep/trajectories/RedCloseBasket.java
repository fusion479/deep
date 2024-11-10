package com.example.meepmeep.trajectories;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RedCloseBasket {
    private final Pose2d START, RUNGS, LEFT_SPIKEMARK, MID_SPIKEMARK, RIGHT_SPIKEMARK, SCORE, SUBMERSIBLE;

    public RedCloseBasket() {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat("/PathVisualizer/src/main/java/com/example/meepmeep/positions/red/close-basket.json"));
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
        return builder.splineToLinearHeading(this.RUNGS, Math.toRadians(0))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(this.MID_SPIKEMARK, Math.toRadians(210))
                .setTangent(0)
                .splineToLinearHeading(this.SCORE, Math.toRadians(273))
                .splineToLinearHeading(this.LEFT_SPIKEMARK, Math.toRadians(180))
                .setTangent(0)
                .splineToLinearHeading(this.SCORE, Math.toRadians(273))
                .splineToLinearHeading(this.RIGHT_SPIKEMARK, Math.toRadians(180))
                .setTangent(0)
                .splineToLinearHeading(this.SCORE, Math.toRadians(273))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(this.SUBMERSIBLE, Math.toRadians(90))
                .build();
    }

    public Pose2d getStart() {
        return this.START;
    }
}
