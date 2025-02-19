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
@Autonomous(name = "Push", preselectTeleOp = "Main")
public class Push extends OpModeCore {
    private CommandRobot robot;
    private SpecFourTrajectories trajectories;

    public static double SPEED = 0.65;

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

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload, SPEED),
                        new PathCommand(this.robot, this.trajectories.backFirst, SPEED),
                        new PathChainCommand(this.robot, SPEED, this.trajectories.setupTop, this.trajectories.pushTop),
                        new PathChainCommand(this.robot, SPEED, this.trajectories.setupMid, this.trajectories.pushMid),
                        new PathChainCommand(this.robot, SPEED, this.trajectories.setupBottom, this.trajectories.pushBottom),
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