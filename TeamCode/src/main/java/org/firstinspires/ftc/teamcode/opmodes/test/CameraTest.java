package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Trajectories;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.Drawing;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;

@Photon
@TeleOp(name = "Camera Test", group = "TeleOp")
public class CameraTest extends OpModeCore {
    private Camera camera;
    private MecanumDrive drive;

    public void initialize() {
        this.camera = new Camera(Trajectories.Color.RED, super.hardwareMap, super.getTelemetry());
        this.drive = new MecanumDrive(super.hardwareMap, new Pose2d(0, 0, 0));

        super.enableBulkReads();
    }


    @Override
    public void runOpMode() {
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
            super.bulkReads();
            CommandScheduler.getInstance().run();

            this.camera.relocalize(this.drive);
            this.drive.updatePoseEstimate();

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            this.camera.logTagPose();
            super.logCycles();
            super.getTelemetry().update();
        }

        super.end();
    }
}