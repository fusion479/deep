package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@Autonomous(name = "Blue Close Basket", preselectTeleOp = "Main")
public class CloseBasket extends OpModeCore {
    private CommandRobot robot;

    @Override
    public void initialize() {
        // TODO: Input correct starting position
        this.robot = new CommandRobot(super.hardwareMap, new Pose2d(0, 0, 0), super.multipleTelemetry, this);
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
