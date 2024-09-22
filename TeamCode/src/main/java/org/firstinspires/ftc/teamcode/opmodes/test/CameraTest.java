package org.firstinspires.ftc.teamcode.opmodes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.Drawing;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

@TeleOp(name = "Camera Test")
public class CameraTest extends OpModeCore {
    public static int COLOR = 0; // 0 for red, 1 for blue

    private Camera camera;
    private MecanumDrive drive;

    public void initialize() {
        this.camera = new Camera(CameraTest.COLOR == 0 ? Camera.Color.RED : Camera.Color.BLUE, super.hardwareMap, super.multipleTelemetry);
        this.drive = new MecanumDrive(super.hardwareMap, new Pose2d(0, 0, 0));
    }

    @Override
    public void runOpMode() {
        this.initialize();

        while (super.opModeInInit()) {
            super.multipleTelemetry.addData("Detected Region: ", this.camera.getRegion());
            super.multipleTelemetry.update();
        }
        this.camera.startTagProcessor();

        while (super.opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            this.camera.relocalize(this.drive);

            TelemetryPacket packet = new TelemetryPacket();
            packet.fieldOverlay().setStroke("#3F51B5");
            Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
            FtcDashboard.getInstance().sendTelemetryPacket(packet);

            this.camera.logTagPose();
            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}