package org.firstinspires.ftc.teamcode.opmodes.auton.paths;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.meepmeep.Positions;
import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.CommandRobot;
import org.firstinspires.ftc.teamcode.subsystems.camera.Camera;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

public class ParentSample {
    public final Trajectories trajectories;
    public final CommandRobot robot;

    private final Positions positions;
    private final Camera camera;
    private final OpModeCore opMode;
    private final MultipleTelemetry telemetry;

    public int region;

    public ParentSample(Positions.Color color, OpModeCore opMode) {
        this.telemetry = new MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().getTelemetry());
        this.opMode = opMode;
        this.camera = new Camera(color, opMode.hardwareMap, this.telemetry);
        this.trajectories = new Trajectories(color);
        this.positions = new Positions(color);
        this.robot = new CommandRobot(opMode.hardwareMap, this.positions.START_POS, this.telemetry);
    }

    public void startThreads() {
        this.robot.startAutonThreads(this.opMode);
    }

    public void generate() {
        while (this.opMode.opModeInInit()) {
            this.region = this.camera.getRegion();
            this.opMode.multipleTelemetry.addData("Detected Region: ", this.region);
            this.opMode.multipleTelemetry.update();
        }
    }

    public void run() {
        Action pathOne = this.region == 1 || this.region == 2 ?
                trajectories.pathOne(this.robot.getDrive().actionBuilder(this.positions.START_POS)) :
                trajectories.pathTwo(this.robot.getDrive().actionBuilder(this.positions.START_POS));

        Actions.runBlocking(pathOne);
    }
}
