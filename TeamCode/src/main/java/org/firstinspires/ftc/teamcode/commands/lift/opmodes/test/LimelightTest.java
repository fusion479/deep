package org.firstinspires.ftc.teamcode.commands.lift.opmodes.test;


import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

import java.util.List;

@TeleOp(name = "Limelight Test")
public class LimelightTest extends OpModeCore {
    private Limelight3A limelight;
    // private EnhancedIMU imu;

    @Override
    public void initialize() {
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");

        /* this.imu = new EnhancedIMU(hardwareMap.get(IMU.class, "imu"), new RevHubOrientationOnRobot(
                PARAMS.logoFacingDirection, PARAMS.usbFacingDirection)); */
        // IMU: https://docs.limelightvision.io/docs/docs-limelight/getting-started/FTC/programming#advanced-usage

        this.limelight.pipelineSwitch(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        this.limelight.start();
        // this.imu.startThread(this);
        super.waitForStart();
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();


            LLResult result = this.limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose();
                    super.multipleTelemetry.addData("tx", result.getTx());
                    super.multipleTelemetry.addData("ty", result.getTy());

                    super.multipleTelemetry.addData("pose", botpose.toString());
                    super.multipleTelemetry.addData("x", botpose.getPosition().x);
                    super.multipleTelemetry.addData("y", botpose.getPosition().y);

                    List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                    for (LLResultTypes.FiducialResult fr : fiducialResults) {
                        super.multipleTelemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                    }

                    // ...
                }
            }

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}