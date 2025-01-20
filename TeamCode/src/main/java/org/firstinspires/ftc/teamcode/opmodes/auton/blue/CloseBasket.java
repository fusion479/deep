package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.CloseBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private CloseBasketTrajectories trajectories;
    private CommandRobot robot;

    @Override
    public void initialize() {
        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart(), super.multipleTelemetry, this);

        this.trajectories = new CloseBasketTrajectories();
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot.getFollower(), this.trajectories.scorePreload),
                        new PathCommand(this.robot.getFollower(), this.trajectories.getBottom),
                        new PathCommand(this.robot.getFollower(), this.trajectories.scoreBottom),
                        new PathCommand(this.robot.getFollower(), this.trajectories.getMid),
                        new PathCommand(this.robot.getFollower(), this.trajectories.scoreMid),
                        new PathCommand(this.robot.getFollower(), this.trajectories.getTop),
                        new PathCommand(this.robot.getFollower(), this.trajectories.scoreTop),
                        new PathCommand(this.robot.getFollower(), this.trajectories.park)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}
