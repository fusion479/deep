package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.SpecFiveTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathChainCommand;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "Push", preselectTeleOp = "Main")
public class Push extends OpModeCore {
    private CommandRobot robot;
    private SpecFiveTrajectories trajectories;

    public static double SPEED = 1;
    public static double PUSH = 0.9;

    @Override
    public void initialize() {
        this.trajectories = new SpecFiveTrajectories();

        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        this.robot.startAutoThreads(this);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload, SPEED),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupTop, this.trajectories.pushTop),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupMid, this.trajectories.pushMid),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupBottom, this.trajectories.pushBottom),
                        new PathCommand(this.robot, this.trajectories.intakeSecond, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeThird, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreThird, SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeFourth, SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, SPEED),
                        new PathCommand(this.robot, this.trajectories.park, SPEED)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}