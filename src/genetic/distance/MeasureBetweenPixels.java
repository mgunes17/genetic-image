package genetic.distance;

import genetic.Chromosome;
import org.opencv.core.Mat;

/**
 * Created by mgunes on 25.12.2016.
 */
public class MeasureBetweenPixels implements DistanceMeasure {
    @Override
    public int findDistance(Chromosome c1, Chromosome c2) {
        int diff = 0;
        double[] values1 = new double[3];
        double[] values2 = new double[3];

        for (int i = 0; i < c1.getImg().height(); i++) {
            for (int j = 0; j < c1.getImg().width(); j++) {
                values1 = c1.getImg().get(i, j);
                values2 = c2.getImg().get(i, j);
                diff += Math.abs((values1[0] - values2[0])) + Math.abs((values1[1] - values2[1])) + Math.abs((values1[2] - values2[2]));
            }
        }
        return diff;
    }
}
