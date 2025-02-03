package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathChainCommand;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "Far Basket", preselectTeleOp = "Main")
public class FarBasket extends OpModeCore {
    public static int HIGH_RUNG_WAIT = 250;
    public static int SLAM_WAIT = 250;
    public static int SPECIMEN_CLOSE_WAIT = 250;
    public static int CYCLE_SPECIMEN_WAIT = 500;
    public static int READY_WAIT = 100;

    public static double SCORE_SPEED = 0.7;
    public static double NORMAL_SPEED = 0.9;

    private CommandRobot robot;
    private FarBasketTrajectories trajectories;

    @Override
    public void initialize() {
        this.trajectories = new FarBasketTrajectories();

        robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        // PRELOAD
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scorePreload, SCORE_SPEED),
                        this.robot.slam,
                        new WaitCommand(SLAM_WAIT),
                        new PathCommand(this.robot, this.trajectories.backFirst, NORMAL_SPEED),
                        this.robot.ready,
                        new WaitCommand(READY_WAIT),

                        // PUSH SAMPLES
                        new ParallelCommandGroup(
                                new PathChainCommand(
                                        this.robot,
                                        NORMAL_SPEED,
                                        this.trajectories.backFirst,
                                        this.trajectories.setupTop,
                                        this.trajectories.pushTop
                                ),
                                new SequentialCommandGroup(
                                        new WaitCommand(READY_WAIT),
                                        this.robot.getReady()
                                )
                        ),

                        new PathChainCommand(
                                this.robot,
                                NORMAL_SPEED,
                                this.trajectories.setupMid,
                                this.trajectories.pushMid
                        ),


                        // 2ND SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeSecond, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.getSpecimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, SCORE_SPEED),
                        this.robot.slam,
                        new WaitCommand(SLAM_WAIT),

                        // 3RD SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeThird, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.getSpecimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreThird, SCORE_SPEED),
                        this.robot.slam,
                        new WaitCommand(SLAM_WAIT),


                        // 4TH SPECIMEN
                        new ParallelCommandGroup(
                                new PathCommand(this.robot, this.trajectories.intakeFourth, NORMAL_SPEED),
                                new SequentialCommandGroup(
                                        new WaitCommand(CYCLE_SPECIMEN_WAIT),
                                        this.robot.getSpecimen()
                                )
                        ),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, SCORE_SPEED),
                        this.robot.slam,
                        new WaitCommand(SLAM_WAIT),

                        // PARK
                        new PathCommand(this.robot, this.trajectories.park, NORMAL_SPEED),
                        this.robot.ready
                )
        );

        this.robot.startAutoThreads(this);
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
