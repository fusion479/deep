package org.firstinspires.ftc.teamcode.opmodes.auton.red;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.red.trajectories.CloseBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Red Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private CloseBasketTrajectories trajectories;
    private CommandRobot robot;

    @Override
    public void initialize() {
        super.initialize();
        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart());

        this.trajectories = new CloseBasketTrajectories();
    }

    @Override
    public void runOpMode() {
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
