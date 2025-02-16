package org.firstinspires.ftc.teamcode.utils;

public class LimeLightConstants {
    public static double LIME_LIGHT_MOUNT_ANGLE = Math.toRadians(0); // a1
    public static double LIME_LIGHT_LENS_HEIGHT_INCHES = 0; // h1
    public static double SAMPLE_HEIGHT_INCHES = 0; // h2

    public static double calcYDistance(double ty) { // a2
        double angleToSampleRadians = LIME_LIGHT_MOUNT_ANGLE - Math.toRadians(ty);

        return Math.abs((SAMPLE_HEIGHT_INCHES - LIME_LIGHT_LENS_HEIGHT_INCHES) / Math.tan(angleToSampleRadians) + 1.5);
    }

    public static double calcXDistance(double tx, double ty) { // a2
        double angleToSampleRadians = Math.toRadians(tx);

        return calcYDistance(ty) * Math.tan(angleToSampleRadians);
    }
}
