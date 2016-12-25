package genetic.life;

import genetic.Chromosome;
import genetic.crossover.UniformCrossover;
import genetic.distance.MeasureWithPixelMatching;
import genetic.selection.OrderBasedSelection;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.*;

public class LifeForPixel extends Life {
    private List<Pixel> pixels;

    public LifeForPixel(int populationSize, Chromosome alphaChromosome, int width, int height, int type, List<Pixel> pixels) {
        super(width, height, type, populationSize, alphaChromosome);
        UniformCrossover crossover = new UniformCrossover(pixels);
        crossover.setAlpha(alphaChromosome);
        setCrossover(crossover);
        setSelection(new OrderBasedSelection());
        setDistanceMeasure(new MeasureWithPixelMatching());
        this.pixels = pixels;
    }

    public void initialize() {
        double[] values;
        int fitness;

        for (int k = 0; k < getPopulationSize(); k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(getHeight(), getWidth(), getType());
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
                    values = createRandomColor();
                    newImage.put(j, i, values);
                }
            }
            chromosome.setImg(newImage);
            fitness = getDistanceMeasure().findDistance(getAlphaChromosome(), chromosome);
            chromosome.setFitness(fitness);
            getPopulation().add(chromosome);
        }
    }

    public void nextAge() {
        super.nextAge();
    }

    public double[] createRandomColor() {
        Random random = new Random();
        int randomElement = random.nextInt(pixels.size());
        Pixel pixel = pixels.get(randomElement);
        double[] values = new double[3];

        values[0] = pixel.getB();
        values[1] = pixel.getG();
        values[2] = pixel.getR();
        return values;
    }

    public static class FitnessComparator implements Comparator<Chromosome> {
        public int compare(Chromosome c1, Chromosome c2) {
            return c1.compareTo(c2);
        }
    }
}