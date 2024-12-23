package org.firstinspires.ftc.teamcode.opmodes.auton;

import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildCurve;
import static org.firstinspires.ftc.teamcode.utils.AutonomousHelpers.buildLine;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.utils.AutonomousHelpers;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "0+5", preselectTeleOp = "Solo")
public class ZeroPlusFive extends OpModeCore {
    public static Path[] paths = new Path[16];

    public void buildPaths() {
        paths[0] = buildLine(
                Constants.specimenStartPose,
                new Pose(41.063, 65.625, 180),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[1] = buildCurve(
                new Pose(41.063, 65.625, 180),
                new Point(28.125, 50.063),
                new Pose(46.500, 36.750, 270),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[2] = buildLine(
                new Pose(46.500, 36.750, 270),
                new Pose(46.500, 36.750, 200),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[3] = buildLine(
                new Pose(46.500, 36.750, 200),
                new Pose(46.500, 36.750, 270),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[4] = buildLine(
                new Pose(46.500, 36.750, 270),
                new Pose(46.500, 36.750, 200),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[5] = buildLine(
                new Pose(46.500, 36.750, 200),
                new Pose(46.500, 28.000, 270),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[6] = buildLine(
                new Pose(46.500, 28.000, 270),
                new Pose(46.500, 28.000, 200),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[7] = buildCurve(
                new Pose(46.500, 28.000, 200),
                new Point(26.625, 34.688),
                new Pose(7.125, 36.375, 0),
                AutonomousHelpers.HeadingInterpolation.LINEAR
        );
        paths[8] = buildCurve(
                new Pose(7.125, 36.375, 0),
                new Point(28.313, 64.125),
                new Pose(41.063, 64.500, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[9] = buildCurve(
                new Pose(41.063, 64.500, 0),
                new Point(28.313, 64.125),
                new Pose(7.125, 36.375, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[10] = buildCurve(
                new Pose(7.125, 36.375, 0),
                new Point(28.313, 64.125),
                new Pose(41.063, 67.500, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[11] = buildCurve(
                new Pose(41.063, 67.500, 0),
                new Point(28.313, 64.125),
                new Pose(7.125, 36.375, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[12] = buildCurve(
                new Pose(7.125, 36.375, 0),
                new Point(28.313, 64.125),
                new Pose(41.063, 69.500, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[13] = buildCurve(
                new Pose(41.063, 69.500, 0),
                new Point(28.313, 64.125),
                new Pose(7.125, 36.375, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[14] = buildCurve(
                new Pose(7.125, 36.375, 0),
                new Point(28.313, 64.125),
                new Pose(41.063, 71.500, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
        paths[15] = buildCurve(
                new Pose(41.063, 71.500, 0),
                new Point(13.875, 47.625),
                new Pose(6.750, 37.500, 0),
                AutonomousHelpers.HeadingInterpolation.CONSTANT
        );
    }

    public void initialize() {
    }

    @Override
    public void runOpMode() {
        Drivetrain drivetrain = new Drivetrain(hardwareMap, super.multipleTelemetry, Constants.specimenStartPose);
        CommandScheduler.getInstance().reset();

        buildPaths();

        while (!isStarted()) {
            CommandScheduler.getInstance().run();
        }

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(drivetrain.getFollower(), paths[0]),
                        new PathCommand(drivetrain.getFollower(), paths[1]),
                        new PathCommand(drivetrain.getFollower(), paths[2]),
                        new PathCommand(drivetrain.getFollower(), paths[3]),
                        new PathCommand(drivetrain.getFollower(), paths[4]),
                        new PathCommand(drivetrain.getFollower(), paths[5]),
                        new PathCommand(drivetrain.getFollower(), paths[6]),
                        new PathCommand(drivetrain.getFollower(), paths[7]),
                        new PathCommand(drivetrain.getFollower(), paths[8]),
                        new PathCommand(drivetrain.getFollower(), paths[9]),
                        new PathCommand(drivetrain.getFollower(), paths[10]),
                        new PathCommand(drivetrain.getFollower(), paths[11]),
                        new PathCommand(drivetrain.getFollower(), paths[12]),
                        new PathCommand(drivetrain.getFollower(), paths[13]),
                        new PathCommand(drivetrain.getFollower(), paths[14]),
                        new PathCommand(drivetrain.getFollower(), paths[15])
                )
        );

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
        }
    }
}