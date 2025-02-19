package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.SpecFiveSweepTrajectories;
import org.firstinspires.ftc.teamcode.utils.TelemetryCore;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "5-Spec Far Basket", preselectTeleOp = "Main")
public class SpecFive extends OpModeCore {
    private CommandRobot robot;
    private SpecFiveSweepTrajectories trajectories;

    public static double SPEED = 0.6;

    @Override
    public void initialize() {
        super.initialize();
        this.trajectories = new SpecFiveSweepTrajectories();

        robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload, SPEED),
                        new PathCommand(this.robot, this.trajectories.setupTop, SPEED),
                        new PathCommand(this.robot, this.trajectories.sweepTop, SPEED),
                        new PathCommand(this.robot, this.trajectories.setupMid, SPEED),
                        new PathCommand(this.robot, this.trajectories.sweepMid, SPEED),
                        new PathCommand(this.robot, this.trajectories.setupBottom, SPEED),
                        new PathCommand(this.robot, this.trajectories.sweepBottom, SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeSecond, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeThird, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreThird, SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeFourth, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, SPEED),
                        new PathCommand(this.robot, this.trajectories.park, SPEED)
                )
        );

        this.robot.startAutoThreads(this);
        while (opModeIsActive()) {
            TelemetryCore.getInstance().addLine(this.trajectories.poses.toString());
            TelemetryCore.getInstance().update();
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
