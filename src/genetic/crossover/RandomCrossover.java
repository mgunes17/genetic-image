package genetic.crossover;

import genetic.Chromosome;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCrossover implements Crossover{
    @Override
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
        Chromosome childChromosome = new Chromosome();
        Mat child = new Mat(height, width, type);

        Random random = new Random();

        List<Integer> template = new ArrayList<>();

        int randomX = random.nextInt(height);
        int randomY = random.nextInt(width);

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(i < randomX || (i == randomX && j < randomY)){
                    double[] values = parent1.getImg().get(i,j);
                    child.put(i, j, values);
                } else {
                    double[] values = parent2.getImg().get(i,j);
                    child.put(i, j, values);
                }
            }
        }

        childChromosome.setImg(child);
        childChromosome.mutation();
        return childChromosome;
    }
}
