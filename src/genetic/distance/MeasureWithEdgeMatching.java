package genetic.distance;

import genetic.Chromosome;
import genetic.Coordinate;
import org.opencv.core.Mat;

import java.util.List;

/**
 * Created by mgunes on 25.12.2016.
 */
public class MeasureWithEdgeMatching implements DistanceMeasure {
    @Override
    public int findDistance(Chromosome c1, Chromosome c2) {
        int diff = c1.getEdgeCoordinates().size();
        int size = c2.getEdgeCoordinates().size();

        for(Coordinate coor: c1.getEdgeCoordinates()) {
            int i = 0;
            while(i < size && !(coor.getX() == c2.getEdgeCoordinates().get(i).getX() &&
                    coor.getY() == c2.getEdgeCoordinates().get(i).getY())) {
                i++;
            }

            if(i < size)
                diff--;
        }

        return diff;
    }

}
