package com.example.meepmeep.trajectories;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BlueFarPush {
    private final Pose2d START, PUSH1, PUSH2, PUSH3, SCOREPUSH, PARK;

    public BlueFarPush(int type) {
        String jsonString = "";

        try {
            File file = new File(new File("").getAbsolutePath().concat(type == 0 ? "/PathVisualizer/src/main/java/com/example/meepmeep/positions/blue/far-basket.json" : "/sdcard/FIRST/positions/blue/far-basket.json"));
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

        this.PUSH1 = new Pose2d(
                positions.getJSONObject("PUSH1").getDouble("x"),
                positions.getJSONObject("PUSH1").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH1").getDouble("heading"))
        );

        this.PUSH2 = new Pose2d(
                positions.getJSONObject("PUSH2").getDouble("x"),
                positions.getJSONObject("PUSH2").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH2").getDouble("heading"))
        );

        this.PUSH3 = new Pose2d(
                positions.getJSONObject("PUSH3").getDouble("x"),
                positions.getJSONObject("PUSH3").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH3").getDouble("heading"))
        );

        this.SCOREPUSH = new Pose2d(
                positions.getJSONObject("SCOREPUSH").getDouble("x"),
                positions.getJSONObject("SCOREPUSH").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SCOREPUSH").getDouble("heading"))
        );

        this.PARK = new Pose2d(
                positions.getJSONObject("PARK").getDouble("x"),
                positions.getJSONObject("PARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PARK").getDouble("heading"))
        );
    }

    public Action start(TrajectoryActionBuilder builder) {
        return builder.splineToLinearHeading(this.SCOREPUSH, Math.toRadians((270)))
                .turnTo(Math.toRadians(270))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(this.PUSH1, Math.toRadians(180))
                .strafeTo(new Vector2d(-46, 55))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(this.PUSH2, Math.toRadians(180))
                .strafeTo(new Vector2d(-56, 55))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(this.PUSH3, Math.toRadians(180))
                .strafeTo(new Vector2d(-62, 55))
                .build();
    }
    public Action park(TrajectoryActionBuilder builder){
        return builder.setTangent(Math.toRadians(180))
                .splineToLinearHeading(this.PARK, Math.toRadians(90))
                .build();
    }

    public Pose2d getStart() {
        return this.START;
    }
}
