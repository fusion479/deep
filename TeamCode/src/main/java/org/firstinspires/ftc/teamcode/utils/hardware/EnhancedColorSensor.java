package org.firstinspires.ftc.teamcode.utils.hardware;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class EnhancedColorSensor {
    private final ColorRangeSensor sensor;
    private double distance;

    public EnhancedColorSensor(ColorRangeSensor sensor) {
        this.sensor = sensor;
    }

    public void startThread(CommandOpMode opMode) {
        new Thread(() -> {
            while (!opMode.isStopRequested()) {
                try {
                    synchronized (this.sensor) {
                        this.distance = this.sensor.getDistance(DistanceUnit.MM);
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
}