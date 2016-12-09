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

        /////
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

        /////

       /* int lowerLimitX = (int) (0.4 * width);
        int upperLimitX = (int) (0.6 * width);

        int crossoverPoint = random.nextInt(upperLimitX - lowerLimitX) + lowerLimitX;
        //int crossoverPoint = height / 2;

        for (int i = 0; i < crossoverPoint; i++) {
            for (int j = 0; j < width; j++) {
                double[] values = parent1.get(i,j);
                child.put(i, j, values);
            }
        }

        for (int i = crossoverPoint; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double[] values = parent2.get(i, j);
                child.put(i, j, values);
            }
        }*/

        childChromosome.setImg(child);
        childChromosome.mutation(pixels);
        return childChromosome;
    }


}
