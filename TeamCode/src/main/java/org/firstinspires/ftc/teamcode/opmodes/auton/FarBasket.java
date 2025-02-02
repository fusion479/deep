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
    public static int HIGH_RUNG_WAIT = 750;
    public static int ENSURE_WAIT = 450;
    public static int SPECIMEN_CLOSE_WAIT = 250;
    public static int READY_WAIT = 250;

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
                        new PathCommand(this.robot, this.trajectories.scorePreload, 0.6),
                        this.robot.ensure,
                        new WaitCommand(ENSURE_WAIT),
                        new PathCommand(this.robot, this.trajectories.backFirst, 0.75),
                        this.robot.ready,
                        new WaitCommand(READY_WAIT),

                        // PUSH SAMPLES
                        new PathChainCommand(
                                this.robot,
                                0.75,
                                this.trajectories.setupTop,
                                this.trajectories.pushTop
                        ),
                        new PathChainCommand(
                                this.robot,
                                0.75,
                                this.trajectories.setupMid,
                                this.trajectories.pushMid
                        ),


                        // 2ND SPECIMEN
                        this.robot.specimen,
                        new PathCommand(this.robot, this.trajectories.intakeSecond, 0.75),
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, 0.6),
                        this.robot.ensure,
                        new WaitCommand(ENSURE_WAIT),

                        // 3RD SPECIMEN
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(READY_WAIT),
                                        this.robot.specimen
                                ),
                                new PathCommand(this.robot, this.trajectories.intakeThird, 0.75)
                        ),

                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreThird, 0.6),
                        this.robot.ensure,
                        new WaitCommand(ENSURE_WAIT),


                        // 4TH SPECIMEN
                        new ParallelCommandGroup(
                                new SequentialCommandGroup(
                                        new WaitCommand(READY_WAIT),
                                        this.robot.specimen
                                ),
                                new PathCommand(this.robot, this.trajectories.intakeFourth, 0.75)
                        ),

                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.close,
                        new WaitCommand(SPECIMEN_CLOSE_WAIT),
                        this.robot.highRung,
                        new WaitCommand(HIGH_RUNG_WAIT),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, 0.6),
                        this.robot.ensure,
                        new WaitCommand(ENSURE_WAIT),

                        // PARK
                        this.robot.ready,
                        new PathCommand(this.robot, this.trajectories.park, 0.75)
                )
        );

        this.robot.startAutoThreads(this);
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
