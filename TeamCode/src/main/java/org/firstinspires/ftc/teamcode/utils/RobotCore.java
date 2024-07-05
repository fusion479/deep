package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

public abstract class RobotCore {
    private final Type type;
    private final MultipleTelemetry telemetry;
    private final MecanumDrive drive;

    public RobotCore(Type type, MultipleTelemetry telemetry, MecanumDrive drive) {
        this.type = type;
        this.telemetry = telemetry;
        this.drive = drive;

        if (this.type == Type.TELEOP) {
            this.configureCommands();
            this.updateTriggers();
        }
    }

    public MecanumDrive getDrive() {
        return this.drive;
    }

    public abstract void configureCommands();

    public abstract void updateTriggers();

    public enum Type {
        AUTON,
        TELEOP,
    }
}
