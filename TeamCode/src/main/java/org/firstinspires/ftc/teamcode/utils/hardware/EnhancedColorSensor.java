package org.firstinspires.ftc.teamcode.utils.hardware;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.hardware.rev.RevColorSensorV3;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class EnhancedColorSensor {
    private final RevColorSensorV3 sensor;

    private double distance;
    private int red;
    private int blue;

    public EnhancedColorSensor(RevColorSensorV3 sensor) {
        this.sensor = sensor;
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (opMode.opModeIsActive()) {
                try {
                    synchronized (this.sensor) {
                        this.distance = this.sensor.getDistance(DistanceUnit.MM);
                        this.red = this.sensor.red();
                        this.blue = this.sensor.blue();
                    }
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public double getDistance() {
        return this.distance;
    }

    public double getRed() {
        return this.red;
    }

    public double getBlue() {
        return this.blue;
    }

    public void enableLed(boolean enable) {
        this.sensor.enableLed(enable);
    }
}
