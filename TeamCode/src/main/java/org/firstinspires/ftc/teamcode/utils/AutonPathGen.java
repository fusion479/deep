package org.firstinspires.ftc.teamcode.utils;

import com.example.meepmeep.Trajectories;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;

public abstract class AutonPathGen {
    private int region;
    public final Trajectories.Color color;
    public final OpModeCore opMode;
    public final CommandRobot robot;
    public final Camera camera;

    public AutonPathGen(Trajectories.Color color, OpModeCore opMode) {
        this.opMode = opMode;
        this.color = color;

        this.camera = new Camera(this.color, this.opMode.hardwareMap, this.opMode.getTelemetry());
        this.robot = new CommandRobot(RobotCore.Type.AUTON);
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getRegion() {
        return this.region;
    }

    public abstract void generatePaths();

    public abstract void runPaths();
}
