package genetic.life;

import genetic.Chromosome;
import genetic.crossover.OrderBasedCrossover;
import genetic.distance.MeasureWithPixelMatching;
import genetic.selection.OrderBasedSelection;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Random;

public class LifeForAlpha extends Life {
    private List<Pixel> pixels;

    public LifeForAlpha(int populationSize, Chromosome alphaChromosome, int width, int height, int type, List<Pixel> pixels) {
        super(width, height, type, populationSize, alphaChromosome);
        OrderBasedCrossover orderBasedCrossover = new OrderBasedCrossover(pixels);
        orderBasedCrossover.setAlpha(alphaChromosome);
        setCrossover(orderBasedCrossover);
        setSelection(new OrderBasedSelection());
        setDistanceMeasure(new MeasureWithPixelMatching());
        this.pixels = pixels;
    }

    @Override
    public void initialize() {
        int fitness;

        for (int k = 0; k < getPopulationSize(); k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(getHeight(), getWidth(), getType());
            Mat copyAlpha = getAlphaChromosome().getImg().clone();
            newImage = getNewChild(copyAlpha);

            chromosome.setImg(newImage);
            fitness = getDistanceMeasure().findDistance(getAlphaChromosome(), chromosome);
            chromosome.setFitness(fitness);
            getPopulation().add(chromosome);
        }
    }

    public void nextAge() {
        super.nextAge();
    }

    private Mat getNewChild(Mat alpha){
        int count = alpha.rows() * alpha.cols();
        int randomX1, randomY1, randomX2, randomY2;
        int height = alpha.rows();
        int width = alpha.cols();
        double[] tempValues;

        Random random = new Random();

        for(int i = 0; i < count; i++){
            randomX1 = random.nextInt(height);
            randomY1 = random.nextInt(width);
            randomX2 = random.nextInt(height);
            randomY2 = random.nextInt(width);

            tempValues = alpha.get(randomX2, randomY2);
            alpha.put(randomX2, randomY2, alpha.get(randomX1, randomY1));
            alpha.put(randomX1, randomY1, tempValues);
        }

        return alpha;
    }
}
