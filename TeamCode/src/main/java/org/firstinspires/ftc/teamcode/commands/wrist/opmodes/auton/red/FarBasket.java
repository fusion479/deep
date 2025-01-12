package org.firstinspires.ftc.teamcode.commands.wrist.opmodes.auton.red;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.commands.wrist.opmodes.auton.blue.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Red Far Basket", preselectTeleOp = "Main")
public class FarBasket extends OpModeCore {
    private CommandRobot robot;
    private FarBasketTrajectories trajectories;

    @Override
    public void initialize() {
        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart(), super.multipleTelemetry, this);

        this.trajectories = new FarBasketTrajectories();
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload),
                        new PathCommand(this.robot, this.trajectories.setupTop),
                        new PathCommand(this.robot, this.trajectories.pushTop),
                        new PathCommand(this.robot, this.trajectories.setupMid),
                        new PathCommand(this.robot, this.trajectories.pushMid),
                        new PathCommand(this.robot, this.trajectories.setupBottom),
                        new PathCommand(this.robot, this.trajectories.pushBottom),
                        new PathCommand(this.robot, this.trajectories.intakeSecond),
                        new PathCommand(this.robot, this.trajectories.scoreSecond),
                        new PathCommand(this.robot, this.trajectories.intakeThird),
                        new PathCommand(this.robot, this.trajectories.scoreThird),
                        new PathCommand(this.robot, this.trajectories.intakeFourth),
                        new PathCommand(this.robot, this.trajectories.scoreFourth),
                        new PathCommand(this.robot, this.trajectories.park)
                )
        );
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }
        super.end();
    }
}