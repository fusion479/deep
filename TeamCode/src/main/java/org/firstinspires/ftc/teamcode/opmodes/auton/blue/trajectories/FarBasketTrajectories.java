package org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;

import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;

public class FarBasketTrajectories {

    public Path scorePreload, intakeSecond, scoreSecond, intakeThird, scoreThird, intakeFourth, scoreFourth, park, setupTop, pushTop, setupMid, pushMid, setupBottom, pushBottom;

    public FarBasketTrajectories() {
        this.scorePreload = buildCurve(
                new Pose(8, 65, 0),
                new Point(20, 75),
                new Pose(38, 72, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupTop = buildCurve(
                new Pose(38, 72, 0),
                new Point(20, 10.5),
                new Point(63, 51.4),
                new Pose(63, 24, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushTop = buildLine(
                new Pose(63, 24, 0),
                new Pose(15, 24, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildCurve(
                new Pose(15, 24, 0),
                new Point(65, 34),
                new Pose(65, 13, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                new Pose(65, 13, 0),
                new Pose(15, 13, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildCurve(
                new Pose(15, 13, 0),
                new Point(65, 21),
                new Pose(65, 10, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                new Pose(65, 8, 0),
                new Pose(15, 10, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return new Pose(8, 65, 0);
    }
}
