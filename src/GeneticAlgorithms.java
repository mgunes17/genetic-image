import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */
public class GeneticAlgorithms {

    public Chromosome crossover(Mat parent1, Mat parent2, List<Pixel> pixels) {
        int width = parent1.width();
        int height = parent1.height();
        int type = parent1.type();
        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(height, width, type);

        Random random = new Random();

        List<Integer> template = new ArrayList<>();

        for(int i = 0; i < width * height; i++) {
            template.add(random.nextInt(2));
        }

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(template.get(i * j + i) == 0) {
                    double[] values = parent1.get(i,j);
                    child.put(i, j, values);
                } else {
                    double[] values = parent2.get(i,j);
                    child.put(i, j, values);
                }
            }
        }

        childChromosome.setImg(child);
        childChromosome.mutation(pixels);
        return childChromosome;
    }

    public Chromosome crossoverForCircle(Chromosome parent1, Chromosome parent2) {

        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
        int edgeSize = parent1.getEdgeCoordinates().size();

        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(width, height, type);
        List<Coordinate> edges = new ArrayList<>();

        List<Integer> template = new ArrayList<>();

        Random random = new Random();
        for(int i = 0; i < edgeSize; i++) {
            template.add(random.nextInt(2));
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
        //childChromosome.mutationForCircle();
        return childChromosome;
    }
}