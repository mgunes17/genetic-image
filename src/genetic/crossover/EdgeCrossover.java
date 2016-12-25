package genetic.crossover;

import genetic.Chromosome;
import genetic.Coordinate;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 25.12.2016.
 */
public class EdgeCrossover implements Crossover {
    @Override
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
        int edgeSize = parent1.getEdgeCoordinates().size();

        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(height, width, type);
        List<Coordinate> edges = new ArrayList<>();

        List<Integer> template = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < edgeSize; i++) {
            template.add(random.nextInt(2));
        }

        for(int i = 0; i < parent1.getImg().height(); i++) {
            for(int j = 0; j < parent1.getImg().width(); j++) {
                child.put(i, j, 0);
            }
        }

        Coordinate coordinate;
        for(int i = 0; i < edgeSize; i++) {
            if(template.get(i) == 0) {
                coordinate = parent1.getEdgeCoordinates().get(i);
                edges.add(coordinate);
                child.put(coordinate.getX(), coordinate.getY(), 255.0);
            } else {
                coordinate = parent2.getEdgeCoordinates().get(i);
                edges.add(coordinate);
                child.put(coordinate.getX(), coordinate.getY(), 255.0);
            }
        }


        childChromosome.setImg(child);
        childChromosome.setEdgeCoordinates(edges);
        childChromosome.mutationForCircle();
        return childChromosome;
    }
}
