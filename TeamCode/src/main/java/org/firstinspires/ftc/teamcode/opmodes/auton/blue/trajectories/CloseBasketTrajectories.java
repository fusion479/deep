package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CloseBasketTrajectories {
    public Follower follower;

    public final Pose START, TOP_SPIKEMARK, MID_SPIKEMARK, BOTTOM_SPIKEMARK, SCORE, SUBMERSIBLE, SUBMERSIBLE_CONTROL;

    public Path scorePreload, grabSpikemark1, grabSpikemark2, grabSpikemark3, scoreSpikemark1, scoreSpikemark2, scoreSpikemark3, park;

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

        this.SUBMERSIBLE_CONTROL = new Pose(
                positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                positions.getJSONObject("SUBMERSIBLE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("SUBMERSIBLE").getDouble("heading"))
        );

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(START);
        buildPaths();
    }

    public void buildPaths(){
        scorePreload = new Path(new BezierLine(new Point(START), new Point(SCORE)));
        scorePreload.setLinearHeadingInterpolation(START.getHeading(), SCORE.getHeading());

        grabSpikemark1 = new Path(new BezierLine(new Point(SCORE), new Point(BOTTOM_SPIKEMARK)));
        grabSpikemark1.setLinearHeadingInterpolation(SCORE.getHeading(), BOTTOM_SPIKEMARK.getHeading());

        scoreSpikemark1 = new Path(new BezierLine(new Point(BOTTOM_SPIKEMARK), new Point(SCORE)));
        scoreSpikemark1.setLinearHeadingInterpolation(BOTTOM_SPIKEMARK.getHeading(), SCORE.getHeading());

        grabSpikemark2 = new Path(new BezierLine(new Point(SCORE), new Point(MID_SPIKEMARK)));
        grabSpikemark2.setLinearHeadingInterpolation(SCORE.getHeading(), MID_SPIKEMARK.getHeading());

        scoreSpikemark2 = new Path(new BezierLine(new Point(MID_SPIKEMARK), new Point(SCORE)));
        scoreSpikemark2.setLinearHeadingInterpolation(MID_SPIKEMARK.getHeading(), SCORE.getHeading());

        grabSpikemark3 = new Path(new BezierLine(new Point(SCORE), new Point(TOP_SPIKEMARK)));
        grabSpikemark3.setLinearHeadingInterpolation(SCORE.getHeading(), TOP_SPIKEMARK.getHeading());

        scoreSpikemark3 = new Path(new BezierLine(new Point(TOP_SPIKEMARK), new Point(SCORE)));
        scoreSpikemark3.setLinearHeadingInterpolation(TOP_SPIKEMARK.getHeading(), SCORE.getHeading());

        park = new Path(new BezierCurve(new Point(SCORE), /* Control Point */ new Point(SUBMERSIBLE_CONTROL), new Point(SUBMERSIBLE)));
        park.setLinearHeadingInterpolation(SCORE.getHeading(), SUBMERSIBLE.getHeading());
    }

    public Pose getStart() {
        return this.START;
    }
}
