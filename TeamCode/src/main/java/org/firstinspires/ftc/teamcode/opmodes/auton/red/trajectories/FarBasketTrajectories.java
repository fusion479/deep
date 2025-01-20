package org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories;

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
                new Pose(136, 79, 180),
                new Point(124, 69),
                new Pose(106, 72, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupTop = buildCurve(
                new Pose(106, 72, 180),
                new Point(124, 133.5),
                new Point(81, 92.6),
                new Pose(81, 120, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushTop = buildLine(
                new Pose(81, 120, 180),
                new Pose(129, 120, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupMid = buildCurve(
                new Pose(129, 120, 180),
                new Point(79, 110),
                new Pose(81, 131, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushMid = buildLine(
                new Pose(81, 131, 180),
                new Pose(129, 131, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.setupBottom = buildCurve(
                new Pose(129, 131, 180),
                new Point(79, 123),
                new Pose(81, 136.5, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

        this.pushBottom = buildLine(
                new Pose(81, 136.5, 180),
                new Pose(129, 136.5, 180),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );

    }

    public Pose getStart() {
        return new Pose(136, 79, 180);
    }
}
