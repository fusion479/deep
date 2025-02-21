package org.firstinspires.ftc.teamcode.opmodes.auton.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

@Config
public class SpecFiveTrajectories {
    private final ArrayList<Pose> poses;
    public static double SCORE_PRELOAD = 1.4;
    public static double SCORE_SECOND = 3;
    public static double SCORE_THIRD = 2;
    public static double SCORE_FOURTH = 2;
    public static double SCORE_FIFTH = 2;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, intakeFifth, scoreFifth, park, setupTop, strafeTop, pushTop, setupMid, strafeMid, pushMid, setupBottom, strafeBottom, pushBottom;

    public SpecFiveTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/spec5.pp"));

        this.scorePreload = buildCurve(
                poses.get(0),
                AutonomousHelpers.poseToPoint(poses.get(2)),
                AutonomousHelpers.poseToPoint(poses.get(3)),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scorePreload.setPathEndTimeoutConstraint(SCORE_PRELOAD);

        this.setupTop = buildCurve(
                poses.get(1),
                AutonomousHelpers.poseToPoint(poses.get(5)),
                poses.get(4),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );


        this.strafeTop = buildLine(
                poses.get(4),
                poses.get(6),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );


        this.pushTop = buildLine(
                poses.get(6),
                poses.get(7),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildLine(
                poses.get(7),
                poses.get(8),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.strafeMid = buildLine(
                poses.get(8),
                poses.get(9),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                poses.get(9),
                poses.get(10),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildLine(
                poses.get(10),
                poses.get(11),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.strafeBottom = buildLine(
                poses.get(11),
                poses.get(12),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                poses.get(12),
                poses.get(13),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );


        this.scoreSecond = buildLine(
                poses.get(13),
                poses.get(14),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreSecond.setPathEndTimeoutConstraint(SCORE_SECOND);

        this.intakeThird = buildLine(
                poses.get(14),
                poses.get(15),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreThird = buildLine(
                poses.get(15),
                poses.get(16),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreThird.setPathEndTimeoutConstraint(SCORE_THIRD);

        this.intakeFourth = buildLine(
                poses.get(16),
                poses.get(17),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFourth = buildLine(
                poses.get(17),
                poses.get(18),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreFourth.setPathEndTimeoutConstraint(SCORE_FOURTH);

        this.intakeFifth = buildLine(
                poses.get(18),
                poses.get(19),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFifth = buildLine(
                poses.get(19),
                poses.get(20),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreFifth.setPathEndTimeoutConstraint(SCORE_FIFTH);

        this.park = buildLine(
                poses.get(20),
                poses.get(21),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}