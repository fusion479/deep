package org.firstinspires.ftc.teamcode.opmodes.auton;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.CloseBasketTrajectoriesReal;
import org.firstinspires.ftc.teamcode.opmodes.auton.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Far Basket PATH", preselectTeleOp = "Main")
public class CloseBasketReal extends OpModeCore {
    private CommandRobot robot;
    private CloseBasketTrajectoriesReal trajectories;

    @Override
    public void initialize() {
        this.trajectories = new CloseBasketTrajectoriesReal();

        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload, 0.75),
                        new PathCommand(this.robot, this.trajectories.getTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.getMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.getBottom, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreBottom, 0.75),
                        new PathCommand(this.robot, this.trajectories.park, 0.75)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}