import org.opencv.core.Mat;

import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */
public class GeneticAlgorithms {

    public Chromosome crossover(Mat parent1, Mat parent2) {
        int width = parent1.width();
        int height = parent1.height();
        int type = parent1.type();
        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(width, height, type);

        Random random = new Random();
        int lowerLimitX = (int) (0.4 * width);
        int upperLimitX = (int) (0.6 * width);
        int lowerLimitY = (int) (0.4 * height);
        int upperLimitY = (int) (0.6 * height);

        int crossoverPointX = random.nextInt(upperLimitX - lowerLimitX) + lowerLimitX;
        int crossoverPointY = random.nextInt(upperLimitY - lowerLimitY) + lowerLimitY;

        for(int i = 0; i < crossoverPointX; i++){
            for(int j = 0; j < crossoverPointY; j++){
                child.put(i, j, parent1.get(i, j));
            }
        }

        for(int i = crossoverPointX; i < width; i++){
            for(int j = crossoverPointY; j < height; j++){
                child.put(i, j, parent2.get(i, j));
            }
        }

        childChromosome.setImg(child);
        childChromosome.mutation();
        return childChromosome;
    }
}
