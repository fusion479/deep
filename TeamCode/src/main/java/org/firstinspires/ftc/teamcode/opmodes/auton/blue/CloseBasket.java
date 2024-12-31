package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.opmodes.auton.blue.trajectories.CloseBasketTrajectories;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

import java.io.PrintWriter;
import java.io.StringWriter;

@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private Follower follower;
    private CloseBasketTrajectories trajectories;
    private CommandRobot robot;

    @Override
    public void initialize() {
        // TODO: Input correct starting position
        this.robot = new CommandRobot(super.hardwareMap, new Pose(0, 0, 0), super.multipleTelemetry, this);

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

        super.waitForStart();

        // TODO: Add auto pathing and functionality
        // TODO: Make an end command for roadrunner (see kookybotz)?
        // TODO: Research how to stop Action.runBlocking() without error

        super.end();
    }
}
