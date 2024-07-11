package org.firstinspires.ftc.teamcode.opmodes.auton.paths;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.meepmeep.Trajectories;

import org.firstinspires.ftc.teamcode.utils.OpModeCore;

public class ChildSample extends ParentSample {
    public ChildSample(Trajectories.Color color, OpModeCore opMode) {
        super(color, opMode);
    }

    @Override
    public void run() {
        super.run();

        Action pathTwo = super.getTrajectories().pathThree(super.getRobot()
                .getDrive()
                .actionBuilder(super.getRobot().getDrive().pose));

        Actions.runBlocking(pathTwo);
    }
}
