package genetic.distance;

import genetic.Chromosome;
import org.opencv.core.Mat;

/**
 * Created by mgunes on 25.12.2016.
 */
public class MeasureBetweenGrayPixels implements DistanceMeasure {
    @Override
    public int findDistance(Chromosome c1, Chromosome c2) {
        int diff = 0;

        for(int i = 0; i < c1.getImg().height(); i++) {
            for(int j = 0; j < c1.getImg().width(); j++) {
                diff += Math.abs(c1.getImg().get(i,j)[0] - c2.getImg().get(i,j)[0]);
            }
        }

        return diff;
    }
}
