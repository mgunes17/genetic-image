import org.opencv.core.Mat;

import java.util.List;
import java.util.Random;

public class GeneticAlgorithms {

    public Chromosome crossover(Mat parent1, Mat parent2, List<Pixel> pixels) {
        int width = parent1.width();
        int height = parent1.height();
        int type = parent1.type();
        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(height, width, type);

        Random random = new Random();
        int lowerLimitX = (int) (0.4 * width);
        int upperLimitX = (int) (0.6 * width);

        int crossoverPoint = random.nextInt(upperLimitX - lowerLimitX) + lowerLimitX;

        for (int i = 0; i < crossoverPoint; i++) {
            for (int j = 0; j < height; j++) {
                double[] values = parent1.get(j, i);
                child.put(j, i, values);
            }
        }

        for (int i = crossoverPoint; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double[] values = parent2.get(j, i);
                child.put(j, i, values);
            }
        }

        childChromosome.setImg(child);
        //childChromosome.mutation(pixels);
        return childChromosome;
    }


}
