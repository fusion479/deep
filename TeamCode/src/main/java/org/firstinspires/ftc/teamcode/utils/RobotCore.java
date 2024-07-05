package org.firstinspires.ftc.teamcode.utils;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

public abstract class RobotCore {
    private final Type type;
    private final MultipleTelemetry telemetry;

    public RobotCore(Type type, MultipleTelemetry telemetry) {
        this.type = type;
        this.telemetry = telemetry;

        if (this.type == Type.TELEOP) {
            this.configureCommands();
            this.updateTriggers();
        }
    }

    public abstract void configureCommands();

    public abstract void updateTriggers();

    public enum Type {
        AUTON,
        TELEOP,
    }
}
