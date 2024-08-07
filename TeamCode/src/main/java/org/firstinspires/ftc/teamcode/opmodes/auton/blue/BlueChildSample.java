package org.firstinspires.ftc.teamcode.opmodes.auton.blue;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.example.meepmeep.Positions;

import org.firstinspires.ftc.teamcode.opmodes.auton.paths.ChildSample;
import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;

public class BlueChildSample extends OpModeCore {
    private ChildSample auto;

    @Override
    public void initialize() {
        this.auto = new ChildSample(Positions.Color.BLUE, this);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().enable();
        this.initialize();
        this.auto.generate();

        waitForStart();
        this.auto.startThreads();
        this.auto.run();

        super.end();
    }
}
