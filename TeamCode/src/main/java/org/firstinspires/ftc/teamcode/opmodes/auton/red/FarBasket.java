package org.firstinspires.ftc.teamcode.opmodes.auton.red;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.FarBasketTrajectories;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.commands.PathCommand;

import java.io.PrintWriter;
import java.io.StringWriter;

@Autonomous(name = "Red Far Basket", preselectTeleOp = "Main")
public class FarBasket extends OpModeCore {
    private Follower follower;
    private CommandRobot robot;
    private FarBasketTrajectories trajectories;

    @Override
    public void initialize() {
        // TODO: Input correct starting position
        this.robot = new CommandRobot(super.hardwareMap, trajectories.getStart(), super.multipleTelemetry, this);

        this.follower = new Follower(hardwareMap, FConstants.class, LConstants.class);

        try {
            this.trajectories = new FarBasketTrajectories();
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
                        new PathCommand(follower, trajectories.setupTop),
                        new PathCommand(follower, trajectories.pushTop),
                        new PathCommand(follower, trajectories.setupMid),
                        new PathCommand(follower, trajectories.pushMid),
                        new PathCommand(follower, trajectories.setupBottom),
                        new PathCommand(follower, trajectories.pushBottom),
                        new PathCommand(follower, trajectories.intakeSecond),
                        new PathCommand(follower, trajectories.scoreSecond),
                        new PathCommand(follower, trajectories.intakeThird),
                        new PathCommand(follower, trajectories.scoreThird),
                        new PathCommand(follower, trajectories.intakeFourth),
                        new PathCommand(follower, trajectories.scoreFourth),
                        new PathCommand(follower, trajectories.park)
                )
        );
        super.end();
    }
}