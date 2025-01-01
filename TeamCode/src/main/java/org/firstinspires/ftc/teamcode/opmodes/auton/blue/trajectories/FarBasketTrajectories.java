package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FarBasketTrajectories {
    private final Pose START, RUNGS1, RUNGS2, RUNGS3, RUNGS4, BOTTOM_SPIKEMARK, MID_SPIKEMARK, TOP_SPIKEMARK, PUSHBOTTOM, PUSHMID, PUSHTOP, INTAKE;
    private final Point RUNG1_CONTROl, RUNG2_CONTROl, RUNG3_CONTROl, RUNG4_CONTROl, TOP_SPIKEMARK_CONTROL1, TOP_SPIKEMARK_CONTROL2, MID_SPIKEMARK_CONTROL1, BOTTOM_SPIKEMARK_CONTROL1, INTAKEFIRST_CONTROL;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom;

    public FarBasketTrajectories() throws JSONException, FileNotFoundException {
        String jsonString = "";

        File file = new File(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/blue/far-basket.json"));
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

        this.RUNGS1 = new Pose(
                positions.getJSONObject("RUNG1").getDouble("x"),
                positions.getJSONObject("RUNG1").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG1").getDouble("heading"))
        );

        this.RUNG1_CONTROl = new Point(
                positions.getJSONObject("RUNG1_CONTROl").getDouble("x"),
                positions.getJSONObject("RUNG1_CONTROl").getDouble("y")
        );

        this.RUNGS2 = new Pose(
                positions.getJSONObject("RUNG2").getDouble("x"),
                positions.getJSONObject("RUNG2").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG2").getDouble("heading"))
        );

        this.RUNG2_CONTROl = new Point(
                positions.getJSONObject("RUNG2_CONTROl").getDouble("x"),
                positions.getJSONObject("RUNG2_CONTROl").getDouble("y")
        );

        this.RUNGS3 = new Pose(
                positions.getJSONObject("RUNG3").getDouble("x"),
                positions.getJSONObject("RUNG3").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG3").getDouble("heading"))
        );

        this.RUNG3_CONTROl = new Point(
                positions.getJSONObject("RUNG3_CONTROl").getDouble("x"),
                positions.getJSONObject("RUNG3_CONTROl").getDouble("y")
        );

        this.RUNGS4 = new Pose(
                positions.getJSONObject("RUNG4").getDouble("x"),
                positions.getJSONObject("RUNG4").getDouble("y"),
                Math.toRadians(positions.getJSONObject("RUNG4").getDouble("heading"))
        );

        this.RUNG4_CONTROl = new Point(
                positions.getJSONObject("RUNG4_CONTROl").getDouble("x"),
                positions.getJSONObject("RUNG4_CONTROl").getDouble("y")
        );

        this.PUSHBOTTOM = new Pose(
                positions.getJSONObject("PUSHBOTTOM").getDouble("x"),
                positions.getJSONObject("PUSHBOTTOM").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSHBOTTOM").getDouble("heading"))
        );

        this.BOTTOM_SPIKEMARK = new Pose(
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("heading"))
        );

        this.BOTTOM_SPIKEMARK_CONTROL1 = new Point(
                positions.getJSONObject("BOTTOM_SPIKEMARK_CONTROL1").getDouble("x"),
                positions.getJSONObject("BOTTOM_SPIKEMARK_CONTROL1").getDouble("y")
        );

        this.PUSHMID = new Pose(
                positions.getJSONObject("PUSHMID").getDouble("x"),
                positions.getJSONObject("PUSHMID").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSHMID").getDouble("heading"))
        );

        this.MID_SPIKEMARK = new Pose(
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("heading"))
        );

        this.MID_SPIKEMARK_CONTROL1 = new Point(
                positions.getJSONObject("MID_SPIKEMARK_CONTROL1").getDouble("x"),
                positions.getJSONObject("MID_SPIKEMARK_CONTROL1").getDouble("y")
        );

        this.PUSHTOP = new Pose(
                positions.getJSONObject("PUSHTOP").getDouble("x"),
                positions.getJSONObject("PUSHTOP").getDouble("y"),
                Math.toRadians(positions.getJSONObject("PUSHTOP").getDouble("heading"))
        );

        this.TOP_SPIKEMARK = new Pose(
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("x"),
                positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("y"),
                Math.toRadians(positions.getJSONObject("BOTTOM_SPIKEMARK").getDouble("heading"))
        );

        this.TOP_SPIKEMARK_CONTROL1 = new Point(
                positions.getJSONObject("TOP_SPIKEMARK_CONTROL1").getDouble("x"),
                positions.getJSONObject("TOP_SPIKEMARK_CONTROL1").getDouble("y")
        );

        this.TOP_SPIKEMARK_CONTROL2 = new Point(
                positions.getJSONObject("TOP_SPIKEMARK_CONTROL2").getDouble("x"),
                positions.getJSONObject("TOP_SPIKEMARK_CONTROL2").getDouble("y")
        );

        this.INTAKE = new Pose(
                positions.getJSONObject("INTAKE").getDouble("x"),
                positions.getJSONObject("INTAKE").getDouble("y"),
                Math.toRadians(positions.getJSONObject("INTAKE").getDouble("heading"))
        );

        this.INTAKEFIRST_CONTROL = new Point(
                positions.getJSONObject("INTAKE_CONTROL1").getDouble("x"),
                positions.getJSONObject("INTAKE_CONTROL1").getDouble("y")
        );
    }

    public void buildPaths(){
        this.scorePreload = buildCurve(
                this.START,
                this.RUNG1_CONTROl,
                this.RUNGS1,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupTop = buildCurve(
                this.RUNGS1,
                this.TOP_SPIKEMARK_CONTROL1,
                this.TOP_SPIKEMARK_CONTROL2,
                this.TOP_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushTop = buildLine(
                this.TOP_SPIKEMARK,
                this.PUSHTOP,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildCurve(
                this.PUSHTOP,
                this.MID_SPIKEMARK_CONTROL1,
                this.MID_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                this.MID_SPIKEMARK,
                this.PUSHMID,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildCurve(
                this.PUSHMID,
                this.BOTTOM_SPIKEMARK_CONTROL1,
                this.BOTTOM_SPIKEMARK,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                this.BOTTOM_SPIKEMARK,
                this.PUSHBOTTOM,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeSecond = buildCurve(
                this.PUSHBOTTOM,
                this.INTAKEFIRST_CONTROL,
                this.INTAKE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreSecond = buildCurve(
                this.INTAKE,
                this.RUNG2_CONTROl,
                this.RUNGS2,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeThird = buildCurve(
                this.RUNGS2,
                this.RUNG2_CONTROl,
                this.INTAKE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreThird = buildCurve(
                this.INTAKE,
                this.RUNG3_CONTROl,
                this.RUNGS3,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeFourth = buildCurve(
                this.RUNGS3,
                this.RUNG3_CONTROl,
                this.INTAKE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFourth = buildCurve(
                this.INTAKE,
                this.RUNG4_CONTROl,
                this.RUNGS4,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.park = buildCurve(
                this.RUNGS4,
                this.RUNG4_CONTROl,
                this.INTAKE,
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return this.START;
    }
}
