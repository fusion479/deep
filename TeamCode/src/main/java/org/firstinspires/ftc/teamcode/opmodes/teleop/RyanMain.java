package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Ryan's Main")
public class RyanMain extends OpModeCore {
    private CommandRobot robot;
    private MultipleTelemetry multipleTelemetry;

    public void initialize() {
        super.initialize();

        this.multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        this.robot = new CommandRobot(
                super.hardwareMap,
                this.multipleTelemetry,
                super.gamepad1,
                super.gamepad2,
                this,
                CommandRobot.TeleOpMode.RYAN
        );
    }

    @Override
    public void runOpMode() {
        this.initialize();
        super.waitForStart();

        this.robot.startThreads();
        while (!isStopRequested() && opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            super.logCycles();
            super.telemetry.update();
        }

        super.end();
    }
}