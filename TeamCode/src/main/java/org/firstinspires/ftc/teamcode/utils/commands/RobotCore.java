package org.firstinspires.ftc.teamcode.utils.commands;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;

public abstract class RobotCore {
    private final Type type;
    private final MultipleTelemetry telemetry;

    public RobotCore(Type type, MultipleTelemetry telemetry) {
        this.telemetry = telemetry;
        this.type = type;

        if (this.type == Type.TELEOP) {
            this.configureCommands();
            this.updateTriggers();
        }
    }

    public abstract void configureCommands();

    public abstract void startThreads(CommandOpMode opMode);

    public abstract void updateTriggers();

    public MultipleTelemetry getTelemetry() {
        return telemetry;
    }

    public enum Type {
        AUTON,
        TELEOP,
    }
}
