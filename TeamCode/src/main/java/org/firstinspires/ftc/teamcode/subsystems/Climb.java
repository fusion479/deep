package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Climb extends SubsystemBase {
    private final MultipleTelemetry telemetry;
    public Climb(final HardwareMap hwMap, final MultipleTelemetry telemetry){
        this.telemetry = telemetry;
        //todo: figure out how climb will work
    }
}
