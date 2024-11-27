package org.firstinspires.ftc.teamcode.opmodes.test;

import static org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive.PARAMS;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.hardware.EnhancedIMU;

@TeleOp(name = "Limelight Test")
public class LimelightTest extends OpModeCore {
    private GamepadEx gamepad;
    private Limelight3A limelight;
    private EnhancedIMU imu;

    @Override
    public void initialize() {
        this.gamepad = new GamepadEx(super.gamepad1);
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        this.imu = new EnhancedIMU(hardwareMap.get(IMU.class, "imu"), new RevHubOrientationOnRobot(
                PARAMS.logoFacingDirection, PARAMS.usbFacingDirection));

        this.limelight.pipelineSwitch(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        this.initialize();
        CommandScheduler.getInstance().enable();

        this.limelight.start();
        this.imu.startThread(this);
        super.waitForStart();
        while (opModeIsActive()) {
            super.resetCycle();
            CommandScheduler.getInstance().run();

            LLResult result = this.limelight.getLatestResult();
            if (result != null) {
                if (result.isValid()) {
                    Pose3D botpose = result.getBotpose_MT2();
                    super.multipleTelemetry.addData("tx", result.getTx());
                    super.multipleTelemetry.addData("ty", result.getTy());
                    super.multipleTelemetry.addData("Botpose", botpose.toString());
                }
            }

            super.logCycles();
            super.multipleTelemetry.update();
        }

        super.end();
    }
}