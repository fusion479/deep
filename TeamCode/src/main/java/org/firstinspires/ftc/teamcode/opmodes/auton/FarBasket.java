package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
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
                        this.robot.highRung,
                        new WaitCommand(750),
                        new PathCommand(robot, this.trajectories.scorePreload, 0.6),
                        this.robot.ensure,
                        new WaitCommand(450),
                        new PathCommand(robot, this.trajectories.backFirst, 0.75),
                        this.robot.ready,
                        new WaitCommand(10000),
                        new PathCommand(robot, this.trajectories.setupTop, 0.75),
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
