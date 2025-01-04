package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.CloseBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

import java.io.PrintWriter;
import java.io.StringWriter;

@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private CloseBasketTrajectories trajectories;
    private CommandRobot robot;

    @Override
    public void initialize() {
        this.robot = new CommandRobot(super.hardwareMap, this.trajectories.getStart(), super.multipleTelemetry, this);

        try {
            this.trajectories = new CloseBasketTrajectories();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            super.multipleTelemetry.addLine(errors.toString());
        }
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        this.trajectories.buildPaths();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(this.robot, this.trajectories.scorePreload),
                        new PathCommand(this.robot, this.trajectories.getBottom),
                        new PathCommand(this.robot, this.trajectories.scoreBottom),
                        new PathCommand(this.robot, this.trajectories.getMid),
                        new PathCommand(this.robot, this.trajectories.scoreMid),
                        new PathCommand(this.robot, this.trajectories.getTop),
                        new PathCommand(this.robot, this.trajectories.scoreTop),
                        new PathCommand(this.robot, this.trajectories.park)
                )
        );

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
        }
        super.end();
    }
}
