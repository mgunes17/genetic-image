package genetic.crossover;

import genetic.Chromosome;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 25.12.2016.
 */
public class PixelCrossover implements Crossover {
    private List<Pixel> pixels;

    public PixelCrossover(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
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
                    double[] values = parent1.getImg().get(i,j);
                    child.put(i, j, values);
                } else {
                    double[] values = parent2.getImg().get(i,j);
                    child.put(i, j, values);
                }
            }
        }

        childChromosome.setImg(child);
        childChromosome.mutation(pixels);
        return childChromosome;
    }
}
