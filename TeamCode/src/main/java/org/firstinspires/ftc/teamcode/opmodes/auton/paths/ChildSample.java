package org.firstinspires.ftc.teamcode.opmodes.auton.paths;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.meepmeep.Positions;

import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

public class ChildSample extends ParentSample {
    public ChildSample(Positions.Color color, OpModeCore opMode) {
        super(color, opMode);
    }

    @Override
    public void run() {
        super.run();

        Action pathTwo = super.trajectories.pathThree(super.robot
                .getDrive()
                .actionBuilder(super.robot.getDrive().pose));

        Actions.runBlocking(pathTwo);
    }
}
