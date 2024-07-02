package org.firstinspires.ftc.teamcode.subsystems.camera;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.utils.SubsystemCore;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Config
public class Camera extends SubsystemCore {
    private final Pipeline pipeline;
    private final OpenCvCamera camera;

    public Camera(Trajectories.Color color, HardwareMap hwMap, MultipleTelemetry telemetry) {
        super(telemetry);
        this.pipeline = new Pipeline(color);

        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());

        // Initialize the camera
        this.camera = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
        this.camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 0);
                camera.setPipeline(pipeline);
            }

            @Override
            public void onError(int errorCode) {
                Camera.super.getTelemetry().addLine("Error occured with camera initialization.");
            }
        });
    }

    public void stopStreaming() {
        this.camera.stopStreaming();
        this.camera.closeCameraDevice();
    }

    public int getRegion() {
        return this.pipeline.getRegion();
    }
}
