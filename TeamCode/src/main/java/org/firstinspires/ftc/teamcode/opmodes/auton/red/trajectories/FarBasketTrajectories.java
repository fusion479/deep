package org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

public class FarBasketTrajectories {
    private final ArrayList<Pose> poses;

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom;

    // [(136.0, 79.0, 180.0), (106.0, 72.0, 180.0), (124.0, 69.0, -1.0), (81.0, 120.0, 180.0), (124.0, 133.5, -1.0), (81.0, 92.6, -1.0), (129.0, 120.0, 180.0), (81.0, 131.0, 180.0), (79.0, 110.0, -1.0), (129.0, 131.0, 180.0), (81.0, 136.5, 180.0), (79.0, 123.0, -1.0), (129.0, 136.5, 180.0), (135.0, 114.0, 180.0), (127.0, 114.0, -1.0), (106.0, 75.0, 180.0), (124.0, 77.0, -1.0), (135.0, 114.0, 180.0), (124.0, 77.0, -1.0), (106.0, 78.0, 180.0), (124.0, 79.0, -1.0), (135.0, 114.0, 180.0), (124.0, 79.0, -1.0), (106.0, 81.0, 180.0), (124.0, 81.0, -1.0), (135.0, 114.0, 180.0), (124.0, 81.0, -1.0)]

    public FarBasketTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/red/far-basket.pp"));

        this.scorePreload = buildCurve(
                poses.get(0),
                AutonomousHelpers.poseToPoint(poses.get(2)),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupTop = buildCurve(
                poses.get(1),
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