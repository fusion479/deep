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
public class SpecFourTrajectories {
    private final ArrayList<Pose> poses;

    public static double SCORE_PRELOAD = 1.4;
    public static double SCORE_SECOND = 1.5;
    public static double SCORE_THIRD = 1.5;
    public static double SCORE_FOURTH = 1.5;
    public static double BACK_FIRST = 1.1;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom, backFirst;

    public SpecFourTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/spec4.pp"));

        this.scorePreload = buildCurve(
                poses.get(0),
                AutonomousHelpers.poseToPoint(poses.get(2)),
                AutonomousHelpers.poseToPoint(poses.get(3)),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scorePreload.setPathEndTimeoutConstraint(SCORE_PRELOAD);

        this.backFirst = buildLine(
                poses.get(1),
                poses.get(4),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.backFirst.setPathEndTimeoutConstraint(BACK_FIRST);

        this.setupTop = buildCurve(
                poses.get(4),
                AutonomousHelpers.poseToPoint(poses.get(6)),
                AutonomousHelpers.poseToPoint(poses.get(7)),
                poses.get(5),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushTop = buildLine(
                poses.get(5),
                poses.get(8),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildCurve(
                poses.get(8),
                AutonomousHelpers.poseToPoint(poses.get(10)),
                poses.get(9),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                poses.get(9),
                poses.get(11),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildCurve(
                poses.get(11),
                AutonomousHelpers.poseToPoint(poses.get(13)),
                poses.get(12),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                poses.get(12),
                poses.get(14),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeSecond = buildCurve(
                poses.get(11),
                AutonomousHelpers.poseToPoint(poses.get(16)),
                poses.get(15),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreSecond = buildCurve(
                poses.get(15),
                AutonomousHelpers.poseToPoint(poses.get(18)),
                poses.get(17),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreSecond.setPathEndTimeoutConstraint(SCORE_SECOND);

        this.intakeThird = buildCurve(
                poses.get(17),
                AutonomousHelpers.poseToPoint(poses.get(20)),
                AutonomousHelpers.poseToPoint(poses.get(21)),
                poses.get(19),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreThird = buildCurve(
                poses.get(19),
                AutonomousHelpers.poseToPoint(poses.get(23)),
                poses.get(22),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreThird.setPathEndTimeoutConstraint(SCORE_THIRD);

        this.intakeFourth = buildCurve(
                poses.get(22),
                AutonomousHelpers.poseToPoint(poses.get(25)),
                AutonomousHelpers.poseToPoint(poses.get(26)),
                poses.get(24),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFourth = buildCurve(
                poses.get(24),
                AutonomousHelpers.poseToPoint(poses.get(28)),
                poses.get(27),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        this.scoreFourth.setPathEndTimeoutConstraint(SCORE_FOURTH);

        this.park = buildCurve(
                poses.get(27),
                AutonomousHelpers.poseToPoint(poses.get(30)),
                poses.get(29),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}