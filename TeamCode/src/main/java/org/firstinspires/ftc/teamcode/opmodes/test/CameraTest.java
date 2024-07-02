package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

@TeleOp(name = "Camera Test", group = "TeleOp")
public class CameraTest extends OpModeCore {
    private Camera camera;

    public void initialize() {
        this.camera = new Camera(Trajectories.Color.RED, super.hardwareMap, super.getTelemetry());
    }


    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();

        while (!isStarted()) {
            super.getTelemetry().addData("Detected Region: ", this.camera.getRegion());
            super.getTelemetry().update();
        }
        this.camera.stopStreaming();

        super.waitForStart();
        while (!isStopRequested() && opModeIsActive()) {
            super.resetTimer();
            CommandScheduler.getInstance().run();

            super.log();
        }


        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().disable();
        CommandScheduler.getInstance().reset();
    }
}