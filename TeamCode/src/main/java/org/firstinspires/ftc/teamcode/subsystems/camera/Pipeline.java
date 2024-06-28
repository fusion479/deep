package org.firstinspires.ftc.teamcode.subsystems.camera;

import com.acmerobotics.dashboard.config.Config;
import com.example.meepmeep.Trajectories;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Config
public class Pipeline extends OpenCvPipeline {
    // BLUE COLOR FILTERS
    public static final int BLUE_LOW_RED = 40;
    public static final int BLUE_LOW_GREEN = 40;
    public static final int BLUE_LOW_BLUE = 0;
    public static final int BLUE_HIGH_RED = 180;
    public static final int BLUE_HIGH_GREEN = 255;
    public static final int BLUE_HIGH_BLUE = 180;

    // RED COLOR FILTERS
    public static final int RED_LOW_RED = 0;
    public static final int RED_LOW_GREEN = 120;
    public static final int RED_LOW_BLUE = 0;
    public static final int RED_HIGH_RED = 255;
    public static final int RED_HIGH_GREEN = 255;
    public static final int RED_HIGH_BLUE = 255;

    // RECT ATTRIBUTES
    public static final int RIGHT_RECT_X = 565;
    public static final int RIGHT_RECT_Y = 125;

    public static final int LEFT_RECT_X = 0;
    public static final int LEFT_RECT_Y = 125;

    public static final int RECT_WIDTH = 75;
    public static final int RECT_HEIGHT = 125;

    // PIPELINE VARIABLES
    public static final double TOLERANCE = 0.3;
    private final Trajectories.Color color;
    private final Scalar LOW_FILTER;
    private final Scalar HIGH_FILTER;
    private final Rect RIGHT_RECT;
    private final Rect LEFT_RECT;
    private final Mat output;
    private int region;

    public Pipeline(Trajectories.Color color) {
        this.color = color;
        this.output = new Mat();

        if (color == Trajectories.Color.RED) {
            LOW_FILTER = new Scalar(RED_LOW_RED, RED_LOW_GREEN, RED_LOW_BLUE);
            HIGH_FILTER = new Scalar(RED_HIGH_RED, RED_HIGH_GREEN, RED_HIGH_BLUE);
        } else {
            LOW_FILTER = new Scalar(BLUE_LOW_RED, BLUE_LOW_GREEN, BLUE_LOW_BLUE);
            HIGH_FILTER = new Scalar(BLUE_HIGH_RED, BLUE_HIGH_GREEN, BLUE_HIGH_BLUE);
        }

        RIGHT_RECT = new Rect(RIGHT_RECT_X, RIGHT_RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        LEFT_RECT = new Rect(LEFT_RECT_X, LEFT_RECT_Y, RECT_WIDTH, RECT_HEIGHT);
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, this.output, Imgproc.COLOR_RGB2HSV);

        Core.inRange(this.output, this.LOW_FILTER, this.HIGH_FILTER, this.output);

        Mat rightBox = output.submat(this.RIGHT_RECT);
        Mat leftBox = output.submat(this.LEFT_RECT);

        Imgproc.rectangle(this.output, this.LEFT_RECT, new Scalar(60, 255, 255), 3);
        Imgproc.rectangle(this.output, this.RIGHT_RECT, new Scalar(60, 255, 255), 3);

        if (Core.sumElems(leftBox).val[0] / this.LEFT_RECT.area() / 255 > TOLERANCE) {
            Imgproc.rectangle(this.output, this.LEFT_RECT, new Scalar(0, 255, 255), 10);
            this.region = 1;
        } else if (Core.sumElems(rightBox).val[0] / this.RIGHT_RECT.area() / 255 > TOLERANCE) {
            Imgproc.rectangle(output, this.RIGHT_RECT, new Scalar(255, 255, 255), 5);
            this.region = 3;
        } else {
            this.region = 2;
        }

        rightBox.release();
        leftBox.release();

        return this.output;
    }

    public int getRegion() {
        return region;
    }
}