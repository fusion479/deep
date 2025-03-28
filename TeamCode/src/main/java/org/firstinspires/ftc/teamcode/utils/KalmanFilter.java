package org.firstinspires.ftc.teamcode.utils;

/*
Explanation:
q is the uncertainty in the sensor. It represents how much we trust the sensor
to be correct.
r is the uncertainty in the measurement. It represents how much we trust the measurement,
ie. odo and telemetry (idk if thats the same thing) to be correct

*/

public class KalmanFilter {
    // numbers 2 tune
    private double q = .1; // process noise

    // its a vector
    private double r = .1; // measurement noise
    private double[] pos = {0,0,0}; // {x-pos, y-pos, angle}
    private double[][] p = {
            {1,0,0}, // initial x uncertainty
            {0,1,0}, // initial y uncertainty
            {0,0,1}  // initial angle uncertainty
    };
    private double[][] kg = new double[3][3]; // kalman gain

    public double[] update(double[] measurements){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                kg[i][j] = p[i][j] / (p[i][j] + r);
            }
        }
        for (int i = 0; i < 3; i++) {
            pos[i] = pos[i] + kg[i][0] * (measurements[i] - pos[i]);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = (1 - kg[i][0]) * p[i][j] + q;
            }
        }

        return pos;
    }

}
