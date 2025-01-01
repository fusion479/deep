package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.CloseBasketTrajectories;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

import java.io.PrintWriter;
import java.io.StringWriter;

@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private Follower follower;
    private CloseBasketTrajectories trajectories;
    private CommandRobot robot;

    @Override
    public void initialize() {
        this.robot = new CommandRobot(super.hardwareMap, trajectories.getStart(), super.multipleTelemetry, this);

        this.follower = new Follower(hardwareMap, FConstants.class, LConstants.class);

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

        trajectories.buildPaths();

        super.waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new PathCommand(follower, trajectories.scorePreload),
                        new PathCommand(follower, trajectories.grabSpikemark1),
                        new PathCommand(follower, trajectories.scoreSpikemark1),
                        new PathCommand(follower, trajectories.grabSpikemark2),
                        new PathCommand(follower, trajectories.scoreSpikemark2),
                        new PathCommand(follower, trajectories.grabSpikemark3),
                        new PathCommand(follower, trajectories.scoreSpikemark3),
                        new PathCommand(follower, trajectories.park)
                )
        );

        while (opModeIsActive() && !isStopRequested()) {
            CommandScheduler.getInstance().run();
        }
        super.end();
    }
}
