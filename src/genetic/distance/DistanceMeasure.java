package genetic.distance;

import genetic.Chromosome;
import org.opencv.core.Mat;

/**
 * Created by mgunes on 25.12.2016.
 */
public interface DistanceMeasure {
    int findDistance(Chromosome c1, Chromosome c2);
}
