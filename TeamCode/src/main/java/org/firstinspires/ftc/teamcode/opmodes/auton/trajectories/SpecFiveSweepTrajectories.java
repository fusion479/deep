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
public class SpecFiveSweepTrajectories {
    private final ArrayList<Pose> poses;
    public static double SCORE_PRELOAD = 1.4;
    public static double SCORE_SECOND = 1.5;
    public static double SCORE_THIRD = 1.5;
    public static double SCORE_FOURTH = 1.5;
    public static double BACK_FIRST = 1.1;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, intakeFifth, scoreFifth, park, setupTop, sweepTop, setupMid, sweepMid, setupBottom, sweepBottom;

    public SpecFiveSweepTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/far-5-basket.pp"));

        this.scorePreload = buildCurve(
                poses.get(0),
                AutonomousHelpers.poseToPoint(poses.get(2)),
                AutonomousHelpers.poseToPoint(poses.get(3)),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );

        this.setupTop = buildCurve(
                poses.get(1),
                AutonomousHelpers.poseToPoint(poses.get(5)),
                poses.get(4),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.sweepTop = buildLine(
                poses.get(4),
                poses.get(6),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildLine(
                poses.get(6),
                poses.get(7),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.sweepMid = buildLine(
                poses.get(7),
                poses.get(8),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildLine(
                poses.get(8),
                poses.get(9),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.sweepBottom = buildLine(
                poses.get(9),
                poses.get(10),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeSecond = buildCurve(
                poses.get(10),
                AutonomousHelpers.poseToPoint(poses.get(12)),
                poses.get(11),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreSecond = buildCurve(
                poses.get(11),
                AutonomousHelpers.poseToPoint(poses.get(14)),
                poses.get(13),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeThird = buildCurve(
                poses.get(13),
                AutonomousHelpers.poseToPoint(poses.get(16)),
                AutonomousHelpers.poseToPoint(poses.get(17)),
                poses.get(15),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreThird = buildCurve(
                poses.get(15),
                AutonomousHelpers.poseToPoint(poses.get(19)),
                poses.get(18),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeFourth = buildCurve(
                poses.get(18),
                AutonomousHelpers.poseToPoint(poses.get(21)),
                AutonomousHelpers.poseToPoint(poses.get(22)),
                poses.get(20),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFourth = buildCurve(
                poses.get(20),
                AutonomousHelpers.poseToPoint(poses.get(24)),
                poses.get(23),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeFifth = buildCurve(
                poses.get(23),
                AutonomousHelpers.poseToPoint(poses.get(26)),
                AutonomousHelpers.poseToPoint(poses.get(27)),
                poses.get(25),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );


        this.scoreFifth = buildCurve(
                poses.get(25),
                AutonomousHelpers.poseToPoint(poses.get(29)),
                poses.get(28),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.park = buildCurve(
                poses.get(28),
                AutonomousHelpers.poseToPoint(poses.get(31)),
                AutonomousHelpers.poseToPoint(poses.get(32)),
                poses.get(30),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}