package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

import java.io.File;
import java.util.ArrayList;

public class CloseBasketTrajectories {
    private final ArrayList<Pose> poses;

    public Path scorePreload, getBottom, getMid, getTop, scoreBottom, scoreMid, scoreTop, park;

    public CloseBasketTrajectories() {
        this.poses = AutonomousHelpers.getPoses(new File("").getAbsolutePath().concat("/sdcard/FIRST/positions/blue/close-basket.pp"));

        this.scorePreload = buildLine(
                poses.get(0),
                poses.get(1),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getTop = buildLine(
                poses.get(1),
                poses.get(2),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreTop = buildLine(
                poses.get(2),
                poses.get(3),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getMid = buildLine(
                poses.get(3),
                poses.get(4),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreMid = buildLine(
                poses.get(4),
                poses.get(5),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.getBottom = buildLine(
                poses.get(5),
                poses.get(6),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.scoreBottom = buildLine(
                poses.get(6),
                poses.get(7),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.park = buildCurve(
                poses.get(7),
                AutonomousHelpers.poseToPoint(poses.get(9)),
                poses.get(8),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return this.poses.get(0);
    }
}
