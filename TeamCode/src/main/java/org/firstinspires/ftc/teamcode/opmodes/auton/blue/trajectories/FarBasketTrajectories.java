package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

public class FarBasketTrajectories {
    private final ArrayList<Pose> poses;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom, backFirst, backSecond, backThird, backFourth;

    public FarBasketTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/blue/far-basket.pp"));

        this.scorePreload = buildLine(
                poses.get(0),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.backFirst = buildLine(
                poses.get(1),
                poses.get(2),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupTop = buildCurve(
                poses.get(2),
                AutonomousHelpers.poseToPoint(poses.get(4)),
                AutonomousHelpers.poseToPoint(poses.get(5)),
                poses.get(3),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushTop = buildLine(
                poses.get(3),
                poses.get(6),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildCurve(
                poses.get(6),
                AutonomousHelpers.poseToPoint(poses.get(8)),
                poses.get(7),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                poses.get(7),
                poses.get(9),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildCurve(
                poses.get(9),
                AutonomousHelpers.poseToPoint(poses.get(11)),
                poses.get(10),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                poses.get(10),
                poses.get(12),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeSecond = buildCurve(
                poses.get(12),
                AutonomousHelpers.poseToPoint(poses.get(14)),
                poses.get(13),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreSecond = buildCurve(
                poses.get(13),
                AutonomousHelpers.poseToPoint(poses.get(16)),
                poses.get(15),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeThird = buildCurve(
                poses.get(15),
                AutonomousHelpers.poseToPoint(poses.get(18)),
                poses.get(17),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreThird = buildCurve(
                poses.get(17),
                AutonomousHelpers.poseToPoint(poses.get(20)),
                poses.get(19),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.intakeFourth = buildCurve(
                poses.get(19),
                AutonomousHelpers.poseToPoint(poses.get(22)),
                poses.get(21),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreFourth = buildCurve(
                poses.get(21),
                AutonomousHelpers.poseToPoint(poses.get(24)),
                poses.get(23),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.park = buildCurve(
                poses.get(23),
                AutonomousHelpers.poseToPoint(poses.get(26)),
                poses.get(25),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}