package org.firstinspires.ftc.teamcode.opmodes.auton.paths;

import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.utils.OpModeCore;

public class ChildSample extends ParentSample {
    public ChildSample(Trajectories.Color color, OpModeCore opMode) {
        super(color, opMode);
    }

    @Override
    public void generatePaths() {
        super.generatePaths();

        // ...
    }

    @Override
    public void runPaths() {
        super.runPaths();

        // ...
    }
}
