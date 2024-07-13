package org.firstinspires.ftc.teamcode.opmodes.test.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

@TeleOp(name = "Main", group = "TeleOp")
public class Main extends OpModeCore {
    private CommandRobot robot;

    public void initialize() {
        this.robot = new CommandRobot(
                RobotCore.Type.TELEOP,
                hardwareMap,
                new Pose2d(0, 0, 0),
                new MultipleTelemetry(super.telemetry, FtcDashboard.getInstance().getTelemetry())
        );
    }


    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

        super.waitForStart();
        while (super.opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            this.robot.updateTriggers();

            super.logCycles();
            super.getTelemetry().update();
        }

        super.end();
    }
}