package genetic.life;

import genetic.Chromosome;
import kmeans.Pixel;
import org.opencv.core.Mat;
import view.GlobalParameters;

import java.util.*;

public class LifeForPixel extends Life {
    private List<Pixel> pixels;

    public LifeForPixel(List<Pixel> pixels) {
        setCrossover(GlobalParameters.crossover);
        setSelection(GlobalParameters.selection);
        setDistanceMeasure(GlobalParameters.distanceMeasure);
        this.pixels = pixels;
    }

    public void initialize() {
        double[] values;
        int fitness;

        for (int k = 0; k < GlobalParameters.populationSize; k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(GlobalParameters.height, GlobalParameters.width, GlobalParameters.type);
            for (int i = 0; i < GlobalParameters.width; i++) {
                for (int j = 0; j < GlobalParameters.height; j++) {
                    values = createRandomColor();
                    newImage.put(j, i, values);
                }
            }
            chromosome.setImg(newImage);
            fitness = getDistanceMeasure().findDistance(GlobalParameters.alpha, chromosome);
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