package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.SpecFiveTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathChainCommand;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "5-Spec Far Basket", preselectTeleOp = "Main")
public class SpecFive extends OpModeCore {
    private CommandRobot robot;
    private SpecFiveTrajectories trajectories;

    public static int HIGH_RUNG_WAIT = 300;
    public static int SLAM_WAIT = 300;
    public static int SPECIMEN_CLOSE_WAIT = 300;
    public static int CYCLE_SPECIMEN_WAIT = 750;
    public static int INTAKE_SECOND_WAIT = 1500;
    public static int SCORE_WAIT = 300;
    public static int PARK_WAIT = 500;
    public static int READY_WAIT = 500;

    public static double SCORE_SPEED = 0.9;
    public static double NORMAL_SPEED = 1;
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
                        // PRELOAD
                        this.robot.highRung(),
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scorePreload, SCORE_SPEED),
                        new WaitCommand(SCORE_WAIT),
                        this.robot.slam(),
                        new WaitCommand(SLAM_WAIT),

                        // PUSH SAMPLES
                        new ParallelCommandGroup(
                                new PathChainCommand(this.robot, PUSH, this.trajectories.setupTop, this.trajectories.pushTop),
                                new SequentialCommandGroup(
                                        new WaitCommand(READY_WAIT),
                                        this.robot.ready()
                                )
                        ),
                        new PathChainCommand(this.robot, PUSH, this.trajectories.setupMid, this.trajectories.pushMid),

                        // 2ND SPECIMEN
                        new ParallelCommandGroup(
                                new PathChainCommand(this.robot, PUSH, this.trajectories.setupBottom, this.trajectories.pushBottom),
                                new SequentialCommandGroup(
                                        new WaitCommand(INTAKE_SECOND_WAIT),
                                        this.robot.specimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close(),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung(),
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, SCORE_SPEED),
                        new WaitCommand(SCORE_WAIT),
                        this.robot.slam(),
                        new WaitCommand(SLAM_WAIT),

                        // 3RD SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeThird, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.specimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close(),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung(),
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreThird, SCORE_SPEED),
                        new WaitCommand(SCORE_WAIT),
                        this.robot.slam(),
                        new WaitCommand(SLAM_WAIT),

                        // 4TH SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeFourth, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.specimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close(),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung(),
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, SCORE_SPEED),
                        new WaitCommand(SCORE_WAIT),
                        this.robot.slam(),
                        new WaitCommand(SLAM_WAIT),

                        // 5TH SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeFifth, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.specimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close(),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung(),
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreFifth, SCORE_SPEED),
                        new WaitCommand(SCORE_WAIT),
                        this.robot.slam(),
                        new WaitCommand(SLAM_WAIT),

                        //
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.park, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(PARK_WAIT),
                                        this.robot.ready()
                                )
                        )
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}