package org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories;

import com.pedropathing.localization.Pose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FarBasketTrajectories {
    private final Pose START, RUNGS, RUNGS1, RUNGS2, RUNGS3, LEFT_SPIKEMARK, MID_SPIKEMARK, RIGHT_SPIKEMARK, SCORE, PARK;

    public FarBasketTrajectories() throws JSONException, FileNotFoundException {
        String jsonString = "";

        File file = new File(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/red/far-basket.json"));
        Scanner reader = new Scanner(file);

        while (reader.hasNextLine()) {
            jsonString += reader.nextLine();
        }

        JSONObject positions = new JSONObject(jsonString);

        this.START = new Pose(
                positions.getJSONObject("START").getDouble("x"),
                positions.getJSONObject("START").getDouble("y"),
                Math.toRadians(positions.getJSONObject("START").getDouble("heading"))
        );

        this.RUNGS = new Pose(
                positions.getJSONObject("RUNG").getDouble("x"),
                positions.getJSONObject("RUNG").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG").getDouble("heading"))
        );

        this.RUNGS1 = new Pose(
                positions.getJSONObject("RUNG1").getDouble("x"),
                positions.getJSONObject("RUNG1").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG1").getDouble("heading"))
        );

        this.RUNGS2 = new Pose(
                positions.getJSONObject("RUNG2").getDouble("x"),
                positions.getJSONObject("RUNG2").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG2").getDouble("heading"))
        );

        this.RUNGS3 = new Pose(
                positions.getJSONObject("RUNG3").getDouble("x"),
                positions.getJSONObject("RUNG3").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG3").getDouble("heading"))
        );

        this.LEFT_SPIKEMARK = new Pose(
                positions.getJSONObject("LEFT_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("LEFT_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("LEFT_SPIKEMARK").getDouble("heading"))
        );

        this.MID_SPIKEMARK = new Pose(
                positions.getJSONObject("MID_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("MID_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("MID_SPIKEMARK").getDouble("heading"))
        );

        this.RIGHT_SPIKEMARK = new Pose(
                positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RIGHT_SPIKEMARK").getDouble("heading"))
        );

        this.SCORE = new Pose(
                positions.getJSONObject("SCORE").getDouble("x"),
                positions.getJSONObject("SCORE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SCORE").getDouble("heading"))
        );

        this.PARK = new Pose(
                positions.getJSONObject("PARK").getDouble("x"),
                positions.getJSONObject("PARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PARK").getDouble("heading"))
        );
    }

    public Pose getStart() {
        return this.START;
    }
}
