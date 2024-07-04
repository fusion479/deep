package org.firstinspires.ftc.teamcode.opmodes.auton.paths;

import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.utils.AutonPathGen;
import org.firstinspires.ftc.teamcode.utils.OpModeCore;

public class ParentSample extends AutonPathGen {
    public ParentSample(Trajectories.Color color, OpModeCore opMode) {
        super(color, opMode);
    }

    public void generatePaths() {
        // Declare paths/actions
        // ...

        while (!super.opMode.isStarted()) {
            super.opMode.getTelemetry().addData("Detected Region: ", super.camera.getRegion());
            super.opMode.getTelemetry().update();
        }
        super.camera.stopStreaming();

        // ...
    }

    public void runPaths() {
        // ...
    }
}
