package genetic.crossover;

import genetic.Chromosome;
import genetic.distance.MeasureWithPixelMatching;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 25.12.2016.
 */
public class UniformCrossover implements Crossover {
    private List<Pixel> pixels;
    private Chromosome alpha;

    public UniformCrossover(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
        Chromosome childChromosome = new Chromosome();
        Mat child1 = new Mat(height, width, type);
        Mat child2 = new Mat(height, width, type);

        Random random = new Random();
        MeasureWithPixelMatching measureWithPixelMatching = new MeasureWithPixelMatching();

        List<Integer> template = new ArrayList<>();

        for(int i = 0; i < width * height; i++) {
            template.add(random.nextInt(2));
        }

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(template.get(i * j + i) == 0) {
                    double[] values = parent1.getImg().get(i,j);
                    child1.put(i, j, values);
                    child2.put(i, j, values);
                } else {
                    double[] values = parent2.getImg().get(i,j);
                    child2.put(i, j, values);
                    child1.put(i, j, values);
                }
            }
        }

        Chromosome c1 = new Chromosome();
        c1.setImg(child1);

        Chromosome c2 = new Chromosome();
        c2.setImg(child2);

        int fitness1 = measureWithPixelMatching.findDistance(c1, alpha);
        int fitness2 = measureWithPixelMatching.findDistance(c2, alpha);

        if(fitness1 < fitness2){
            childChromosome.setImg(child1);
        } else {
            childChromosome.setImg(child2);
        }
        childChromosome.mutation(pixels);
        return childChromosome;
    }

    public Chromosome getAlpha() {
        return alpha;
    }

    public void setAlpha(Chromosome alpha) {
        this.alpha = alpha;
    }
}
