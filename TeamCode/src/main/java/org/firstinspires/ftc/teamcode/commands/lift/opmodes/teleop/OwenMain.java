package org.firstinspires.ftc.teamcode.commands.lift.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Owen's Main")
public class OwenMain extends OpModeCore {
    private CommandRobot robot;
    private MultipleTelemetry multipleTelemetry;

    public void initialize() {
        this.multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        this.robot = new CommandRobot(
                super.hardwareMap,
                this.multipleTelemetry,
                super.gamepad1,
                super.gamepad2,
                this,
                CommandRobot.TeleOpMode.OWEN
        );
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

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