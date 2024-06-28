package org.firstinspires.ftc.teamcode.utils;

public abstract class RobotCore {
    private final Type type;

    public RobotCore(Type type) {
        this.type = type;

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
