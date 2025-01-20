package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Blue Far Basket", preselectTeleOp = "Main")
public class FarBasket extends OpModeCore {
    private CommandRobot robot;
    private FarBasketTrajectories trajectories;

    @Override
    public void initialize() {
        super.initialize();
        this.trajectories = new FarBasketTrajectories();

        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart(), super.multipleTelemetry, this);
    }

    @Override
    public void runOpMode() {
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload, 0.75),
                        new PathCommand(this.robot, this.trajectories.setupTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.setupMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.setupBottom, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushBottom, 0.75)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}