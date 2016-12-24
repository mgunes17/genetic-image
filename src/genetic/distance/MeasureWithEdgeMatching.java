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
        boolean found = false;

        for(Coordinate coor: c1.getEdgeCoordinates()) {
            found = false;
            for(Coordinate coor2: c2.getEdgeCoordinates()) {
                if (coor.getX() == coor2.getX() && coor.getY() == coor2.getY())
                    found = true;
            }

            if(found == true)
                diff--;
        }

        return diff;
    }

}
