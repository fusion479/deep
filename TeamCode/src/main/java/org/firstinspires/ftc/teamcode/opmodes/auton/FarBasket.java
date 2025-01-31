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
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Config
@Autonomous(name = "Far Basket", preselectTeleOp = "Main")
public class FarBasket extends OpModeCore {
    public static int SCORE_ONE = 50;
    public static int DRIVE_ONE = 250;
    public static int SCORE_ONE_OPEN = 450;
    public static int SCORE_ONE_RETRACT = 500;

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
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(DRIVE_ONE),
                                        new PathCommand(robot, this.trajectories.scorePreload, 1)
                                ),
                                new SequentialCommandGroup(
                                        new WaitCommand(SCORE_ONE),
                                        robot.highRung
                                )
                        ),
                        new SequentialCommandGroup(
                                robot.open,
                                new WaitCommand(SCORE_ONE_OPEN),
                                new PathCommand(robot, this.trajectories.backFirst, 1),
                                new ParallelCommandGroup(
                                        new PathCommand(robot, this.trajectories.setupTop, 1),
                                        new SequentialCommandGroup(
                                                new WaitCommand(SCORE_ONE_RETRACT),
                                                robot.autoReady
                                        )
                                )
                        ),
                        new PathCommand(robot, this.trajectories.pushTop, 0.75),
                        new PathCommand(robot, this.trajectories.setupMid, 0.75),
                        new PathCommand(robot, this.trajectories.pushMid, 0.75),
                        new PathCommand(robot, this.trajectories.setupBottom, 0.75),
                        new PathCommand(robot, this.trajectories.pushBottom, 0.75),
                        new PathCommand(robot, this.trajectories.intakeSecond, 0.75),
                        new PathCommand(robot, this.trajectories.scoreSecond, 0.75),
                        new PathCommand(robot, this.trajectories.intakeThird, 0.75),
                        new PathCommand(robot, this.trajectories.scoreThird, 0.75),
                        new PathCommand(robot, this.trajectories.intakeFourth, 0.75),
                        new PathCommand(robot, this.trajectories.scoreFourth, 0.75),
                        new PathCommand(robot, this.trajectories.park, 0.75)
                )
        );

        this.robot.startAutoThreads(this);
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
