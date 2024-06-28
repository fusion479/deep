package org.firstinspires.ftc.teamcode.utils;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;

public class CommandAction implements Action {
    private final Command command;
    private boolean initialized = false;
    private boolean finished = false;

    public CommandAction(Command command) {
        this.command = command;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        if (!initialized) {
            command.initialize();
            initialized = true;
        } else if (command.isFinished()) {
            command.end(false);
            CommandScheduler.getInstance().run();
            finished = true;
        } else {
            CommandScheduler.getInstance().run();
            command.execute();
        }
        return !finished;
    }
}