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
import org.firstinspires.ftc.teamcode.utils.SubsystemCore;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class Camera extends SubsystemCore {
    // COLOR FILTER
    private final Pipeline pipeline;
    private final OpenCvCamera camera;

    // APRILTAG
    private final AprilTagProcessor processor;
    private VisionPortal portal;

    public Camera(Trajectories.Color color, HardwareMap hwMap, MultipleTelemetry telemetry) {
        super(telemetry);
        this.pipeline = new Pipeline(color);

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        // Initialize the color camera
        this.camera = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
        this.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 60);
                camera.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                Camera.super.getTelemetry().addLine("Error occured with camera initialization.");
            }
        });

        // Initialize the apriltag processor and vision portal
        this.processor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();
    }

    public void stopStreaming() {
        this.camera.stopStreaming();
        this.camera.closeCameraDevice();
    }

    public void initPortal(HardwareMap hwMap) {
        this.portal = new VisionPortal.Builder()
                .addProcessor(this.processor)
                .setCamera(hwMap.get(WebcamName.class, "camera"))
                .setCameraResolution(new Size(640, 480))
                .build();

        FtcDashboard.getInstance().startCameraStream(this.portal, 60);
    }

    public void logTagPose() {
        if (this.processor.getDetections().size() > 0) {
            AprilTagDetection detection = this.processor.getDetections().get(0);

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

    public void relocalize(MecanumDrive drive, int id) {
        if (this.processor.getDetections().size() > 0) {
            AprilTagDetection detection = this.processor.getDetections().get(0);

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

        for (AprilTagDetection tag : this.processor.getDetections()) {
            if (tag.id == id) {
                desiredTag = tag;
                break;
            }
        }

        if (desiredTag != null) {
            Actions.runBlocking(
                    drive.actionBuilder(drive.pose)
                            .splineToLinearHeading(
                                    new Pose2d(desiredTag.metadata.fieldPosition.getData()[0],
                                            desiredTag.metadata.fieldPosition.getData()[1],
                                            Math.toRadians(0)), Math.toRadians(0))
            );
        }
    }

    public int getRegion() {
        return this.pipeline.getRegion();
    }
}
