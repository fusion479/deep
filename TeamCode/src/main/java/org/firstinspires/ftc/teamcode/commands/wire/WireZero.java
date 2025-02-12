package org.firstinspires.ftc.teamcode.commands.wire;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TestWire;

public class WireZero extends CommandBase {
    private final TestWire testWire;

    public WireZero(final TestWire testWire) {
        this.testWire = testWire;
        super.addRequirements(testWire);
    }

    @Override
    public void initialize() {
        this.testWire.setPosition0(testWire.ZERO);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
