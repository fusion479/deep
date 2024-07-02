package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

@TeleOp(name = "Camera Test", group = "TeleOp")
public class CameraTest extends CommandOpMode {
    private Camera camera;
    private MultipleTelemetry multipleTelemetry;

    public void initialize() {
        this.multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        this.camera = new Camera(Trajectories.Color.RED, super.hardwareMap, this.multipleTelemetry);
    }


    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();

        this.initialize();

        while (!isStarted()) {
            this.multipleTelemetry.addData("Detected Region: ", this.camera.getRegion());
            this.multipleTelemetry.update();
        }

        super.waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            CommandScheduler.getInstance().run();

            this.multipleTelemetry.update();
        }


        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }
}