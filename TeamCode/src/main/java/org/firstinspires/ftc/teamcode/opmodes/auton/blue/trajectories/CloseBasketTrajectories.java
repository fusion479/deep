package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CloseBasketTrajectories {
    private final Pose START, TOP_SPIKEMARK, MID_SPIKEMARK, BOTTOM_SPIKEMARK, SCORE, SUBMERSIBLE;
    private final Point SUBMERSIBLE_CONTROL;

    public Path scorePreload, getBottom, getMid, getTop, scoreBottom, scoreMid, scoreTop, park;

    public CloseBasketTrajectories() throws JSONException, FileNotFoundException {
        String jsonString = "";

        File file = new File(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/blue/close-basket.json"));
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

        this.TOP_SPIKEMARK = new Pose(
                positions.getJSONObject("TOP_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("TOP_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("TOP_SPIKEMARK").getDouble("heading"))
        );

        this.MID_SPIKEMARK = new Pose(
                positions.getJSONObject("MID_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("MID_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("MID_SPIKEMARK").getDouble("heading"))
        );

        this.BOTTOM_SPIKEMARK = new Pose(
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("heading"))
        );

        this.SCORE = new Pose(
                positions.getJSONObject("SCORE").getDouble("x"),
                positions.getJSONObject("SCORE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SCORE").getDouble("heading"))
        );

        this.SUBMERSIBLE = new Pose(
                positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                positions.getJSONObject("SUBMERSIBLE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SUBMERSIBLE").getDouble("heading"))
        );

        this.SUBMERSIBLE_CONTROL = new Point(
                positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                positions.getJSONObject("SUBMERSIBLE").getDouble("y")
        );

    }

    public void buildPaths() {
        this.scorePreload = buildLine(
                this.START,
                this.SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getBottom = buildLine(
                this.SCORE,
                this.BOTTOM_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreBottom = buildLine(
                this.BOTTOM_SPIKEMARK,
                this.SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getMid = buildLine(
                this.SCORE,
                this.MID_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreMid = buildLine(
                this.MID_SPIKEMARK,
                this.SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getTop = buildLine(
                this.SCORE,
                this.TOP_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreTop = buildLine(
                this.TOP_SPIKEMARK,
                this.SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.park = buildCurve(
                this.SCORE,
                this.SUBMERSIBLE_CONTROL,
                this.SUBMERSIBLE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
    }

    public Pose getStart() {
        return this.START;
    }
}
