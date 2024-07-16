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
import org.firstinspires.ftc.teamcode.utils.commands.RobotCore;

public class ParentSample {
    private final Trajectories trajectories;
    private final CommandRobot robot;
    private final OpModeCore opMode;
    private final Camera camera;
    private final MultipleTelemetry telemetry;
    private int region;

    public ParentSample(Trajectories.Color color, OpModeCore opMode) {
        this.telemetry = new MultipleTelemetry(opMode.telemetry, FtcDashboard.getInstance().getTelemetry());
        this.opMode = opMode;
        this.robot = new CommandRobot(RobotCore.Type.AUTON, opMode.hardwareMap, Positions.START_POS, this.telemetry);
        this.camera = new Camera(color, opMode.hardwareMap, this.telemetry);
        this.trajectories = new Trajectories(color);
    }

    public CommandRobot getRobot() {
        return this.robot;
    }

    public Trajectories getTrajectories() {
        return this.trajectories;
    }

    public void generate() {
        while (this.opMode.opModeInInit()) {
            this.region = this.camera.getRegion();
            this.opMode.getTelemetry().addData("Detected Region: ", this.region);
            this.opMode.getTelemetry().update();
        }
    }

    public void run() {
        Action pathOne = this.region == 1 || this.region == 2 ?
                trajectories.pathOne(this.robot.getDrive().actionBuilder(Positions.START_POS)) :
                trajectories.pathTwo(this.robot.getDrive().actionBuilder(Positions.START_POS));

        Actions.runBlocking(pathOne);
    }
}
