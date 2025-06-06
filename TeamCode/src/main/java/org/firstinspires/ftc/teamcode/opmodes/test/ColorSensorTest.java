package org.firstinspires.ftc.teamcode.opmodes.test;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.utils.commands.OpModeCore;
import org.firstinspires.ftc.teamcode.utils.hardware.EnhancedColorSensor;

@TeleOp(name = "Color Sensor Test")
public class ColorSensorTest extends OpModeCore {
    private EnhancedColorSensor sensor;

    public void initialize() {
        this.sensor = new EnhancedColorSensor(hardwareMap.get(RevColorSensorV3.class, "sensor"));
    }

    public void runOpMode() throws InterruptedException {
        this.initialize();

        CommandScheduler.getInstance().enable();

        super.waitForStart();

        this.sensor.startThread(this);
        while (opModeIsActive()) {
            super.multipleTelemetry.addData("Distance", this.sensor.getDistance());
            super.multipleTelemetry.addData("Red", this.sensor.getRed());
            super.multipleTelemetry.addData("Blue", this.sensor.getBlue());

            super.multipleTelemetry.update();
        }
        super.end();
    }
}
