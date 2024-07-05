package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.utils.RobotCore;

public class CommandRobot extends RobotCore {
    public CommandRobot(Type type, HardwareMap hwMap, Pose2d startPose, MultipleTelemetry telemetry) {
        super(type, telemetry, new MecanumDrive(hwMap, startPose));
    }

    @Override
    public void configureCommands() {
    }

    @Override
    public void updateTriggers() {
    }
}
