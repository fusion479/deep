package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CloseBasketTrajectories {
    public Follower follower;

    public final Pose START, TOP_SPIKEMARK, MID_SPIKEMARK, BOTTOM_SPIKEMARK, SCORE, SUBMERSIBLE;
    public final Point SUBMERSIBLE_CONTROL;

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

        this.SUBMERSIBLE_CONTROL = new Point(
                positions.getJSONObject("SUBMERSIBLE").getDouble("x"),
                positions.getJSONObject("SUBMERSIBLE").getDouble("y")
        );

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(START);
        buildPaths();
    }

    public void buildPaths(){
        scorePreload = buildLine(
                START,
                SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        grabSpikemark1 = buildLine(
                SCORE,
                BOTTOM_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        scoreSpikemark1 = buildLine(
                BOTTOM_SPIKEMARK,
                SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        grabSpikemark2 = buildLine(
                SCORE,
                MID_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        scoreSpikemark2 = buildLine(
                MID_SPIKEMARK,
                SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        grabSpikemark3 = buildLine(
                SCORE,
                TOP_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        scoreSpikemark3 = buildLine(
                TOP_SPIKEMARK,
                SCORE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        park = buildCurve(
                SCORE,
                SUBMERSIBLE_CONTROL,
                SUBMERSIBLE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
    }

    public Pose getStart() {
        return this.START;
    }
}
