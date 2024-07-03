package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;

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
        super.resetStartUp();

        this.camera.stopStreaming();
        this.camera.initPortal(super.hardwareMap);

        super.logStartUp();
        while (!isStopRequested() && opModeIsActive()) {
            super.resetPeriod();
            CommandScheduler.getInstance().run();

            this.camera.logTagPose();
            super.log();
        }

        super.end();
    }
}