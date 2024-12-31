package org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories;

import com.pedropathing.localization.Pose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RedFarPush {
    private final Pose START, PUSH1, PUSH2, PUSH3, SCOREPUSH, PARK;

    public RedFarPush() throws JSONException, FileNotFoundException {
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

        this.PUSH1 = new Pose(
                positions.getJSONObject("PUSH1").getDouble("x"),
                positions.getJSONObject("PUSH1").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH1").getDouble("heading"))
        );

        this.PUSH2 = new Pose(
                positions.getJSONObject("PUSH2").getDouble("x"),
                positions.getJSONObject("PUSH2").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH2").getDouble("heading"))
        );

        this.PUSH3 = new Pose(
                positions.getJSONObject("PUSH3").getDouble("x"),
                positions.getJSONObject("PUSH3").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSH3").getDouble("heading"))
        );

        this.SCOREPUSH = new Pose(
                positions.getJSONObject("SCOREPUSH").getDouble("x"),
                positions.getJSONObject("SCOREPUSH").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SCOREPUSH").getDouble("heading"))
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
