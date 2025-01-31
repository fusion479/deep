package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

@Autonomous(name = "Blue Far Basket PATH", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private CommandRobot robot;
    private FarBasketTrajectories trajectories;

    @Override
    public void initialize() {
        this.trajectories = new FarBasketTrajectories();

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
                        new PathCommand(this.robot, this.trajectories.setupTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushTop, 0.75),
                        new PathCommand(this.robot, this.trajectories.setupMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushMid, 0.75),
                        new PathCommand(this.robot, this.trajectories.setupBottom, 0.75),
                        new PathCommand(this.robot, this.trajectories.pushBottom, 0.75),
                        new PathCommand(this.robot, this.trajectories.intakeSecond, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreSecond, 0.75),
                        new PathCommand(this.robot, this.trajectories.intakeThird, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreThird, 0.75),
                        new PathCommand(this.robot, this.trajectories.intakeFourth, 0.75),
                        new PathCommand(this.robot, this.trajectories.scoreFourth, 0.75),
                        new PathCommand(this.robot, this.trajectories.park, 0.75)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }

        super.end();
    }
}