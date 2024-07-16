package org.firstinspires.ftc.teamcode.subsystems.camera;

import android.util.Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.meepmeep.Constants;
import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.commands.SubsystemCore;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Config
public class Camera extends SubsystemCore {
    private final AprilTagProcessor tagProcessor;
    private final PropProcessor propProcessor;
    private final VisionPortal portal;

    public Camera(Trajectories.Color color, HardwareMap hwMap, MultipleTelemetry telemetry) {
        super(telemetry);

        this.propProcessor = new PropProcessor(color, telemetry);

        this.tagProcessor = new AprilTagProcessor.Builder().build();

        this.portal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "camera"))
                .setCameraResolution(new Size(640, 480))
                .addProcessors(this.propProcessor, tagProcessor)
                .build();

        this.portal.setProcessorEnabled(this.tagProcessor, false);

        FtcDashboard.getInstance().startCameraStream(this.portal, 0);
    }

    public void logTagPose() {
        if (this.tagProcessor.getDetections().size() > 0) {
            AprilTagDetection detection = this.tagProcessor.getDetections().get(0);

            super.getTelemetry().addData("X: ", detection.ftcPose.x);
            super.getTelemetry().addData("Y: ", detection.ftcPose.y);
            super.getTelemetry().addData("Z: ", detection.ftcPose.z);
            super.getTelemetry().addData("Range: ", detection.ftcPose.range);
            super.getTelemetry().addData("Elevation: ", detection.ftcPose.elevation);
            super.getTelemetry().addData("Bearing: ", detection.ftcPose.bearing);
            super.getTelemetry().addData("Pitch: ", detection.ftcPose.pitch);
            super.getTelemetry().addData("Yaw: ", detection.ftcPose.yaw);
            super.getTelemetry().addData("Roll: ", detection.ftcPose.roll);
            super.getTelemetry().addData("Tag Pose X on Field: ", detection.metadata.fieldPosition.getData()[0]);
            super.getTelemetry().addData("Tag Pose Y on Field: ", detection.metadata.fieldPosition.getData()[1]);
            super.getTelemetry().addData("Tag ID: ", detection.id);

            double x = detection.metadata.fieldPosition.getData()[0] - detection.ftcPose.y - Constants.ROBOT_LENGTH;
            double y = detection.metadata.fieldPosition.getData()[1] + detection.ftcPose.x;
            double bearing = Math.toRadians(detection.ftcPose.bearing);

            super.getTelemetry().addData("Robot Pose: ", "(" + x + ", " + y + ", " + bearing + ")");
        }
    }

    public void relocalize(MecanumDrive drive) {
        if (this.tagProcessor.getDetections().size() > 0) {
            AprilTagDetection detection = this.tagProcessor.getDetections().get(0);

            drive.pose = new Pose2d(
                    detection.metadata.fieldPosition.getData()[0] - detection.ftcPose.y - Constants.CENTER_TO_CAMERA,
                    detection.metadata.fieldPosition.getData()[1] + detection.ftcPose.x,
                    Math.toRadians(detection.ftcPose.bearing)
            );

            drive.updatePoseEstimate();
        }
    }

    public void driveToTag(MecanumDrive drive, int id) {
        AprilTagDetection desiredTag = null;

        for (AprilTagDetection tag : this.tagProcessor.getDetections()) {
            if (tag.id == id) {
                desiredTag = tag;
                break;
            }
        }

        if (desiredTag != null) {
            Actions.runBlocking(
                    drive.actionBuilder(drive.pose)
                            .splineToLinearHeading(
                                    new Pose2d(
                                            desiredTag.metadata.fieldPosition.getData()[0],
                                            desiredTag.metadata.fieldPosition.getData()[1],
                                            Math.toRadians(0)
                                    ),
                                    Math.toRadians(0)
                            )
                            .build()
            );
        }
    }

    public void startTagProcessor() {
        this.portal.setProcessorEnabled(this.propProcessor, false);
        this.portal.setProcessorEnabled(this.tagProcessor, true);
    }

    public int getRegion() {
        return this.propProcessor.getRegion();
    }
}
