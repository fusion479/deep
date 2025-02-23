package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.SpecFourTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathChainCommand;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "5-Spec Path", preselectTeleOp = "Main")
public class SpecFourPath extends OpModeCore {
    private CommandRobot robot;
    private SpecFourTrajectories trajectories;

    public static double SCORE_SPEED = 1;
    public static double NORMAL_SPEED = 1;
    public static double PUSH = 0.9;

    @Override
    public void initialize() {
        this.trajectories = new SpecFourTrajectories();

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
                        new PathCommand(this.robot, this.trajectories.scorePreload, SCORE_SPEED),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupTop, this.trajectories.pushTop),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupMid, this.trajectories.pushMid),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupBottom, this.trajectories.pushBottom),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, SCORE_SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeThird, NORMAL_SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreThird, SCORE_SPEED),
                        new PathCommand(this.robot, this.trajectories.intakeFourth, NORMAL_SPEED),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, SCORE_SPEED),
                        new PathCommand(this.robot, this.trajectories.park, NORMAL_SPEED)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}