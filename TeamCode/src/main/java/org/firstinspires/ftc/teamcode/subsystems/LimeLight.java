package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimeLight extends SubsystemBase {
    private Limelight3A limelight;
    private double ta, tx, ty, tangle;

    public static int PIPELINE = 7; //placeholder

    public LimeLight(final HardwareMap hwMap){
        this.limelight = hwMap.get(Limelight3A.class, "limeight");

        limelight.setPollRateHz(100);

        limelight.pipelineSwitch(PIPELINE);
        limelight.start();
    }

    public void update() {
        LLResult result = limelight.getLatestResult();
        if (result != null) {
            ta = result.getTa();
            tx = result.getTx();
            ty = result.getTy();
            tangle = result.getPythonOutput()[3];
        }
    }

    public void stop() {
        limelight.stop();
    }

    public void setPipeline(int pipeline) {
        limelight.pipelineSwitch(pipeline);
    }

    public double getTa() {
        return ta;
    }
    public double getTx() {
        return tx;
    }

    public double getTy() {
        return ty;
    }

    public double getTangle() {
        return tangle;
    }
}
