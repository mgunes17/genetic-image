package genetic.life;

import genetic.Chromosome;
import genetic.Coordinate;
import genetic.crossover.EdgeCrossover;
import genetic.distance.DistanceMeasure;
import genetic.distance.MeasureWithEdgeMatching;
import genetic.selection.OrderBasedSelection;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ercan on 24.12.2016.
 */

public class LifeForEdge extends Life {
    public LifeForEdge(int populationSize, Chromosome alphaChromosome, int width, int height, int type) {
        super(width, height, type, populationSize, alphaChromosome);
        setCrossover(new EdgeCrossover());
        setSelection(new OrderBasedSelection());
        setDistanceMeasure(new MeasureWithEdgeMatching());
    }

    public void initialize() {
        Coordinate coordinate;
        int fitness;

        for (int k = 0; k < getPopulationSize(); k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(getHeight(), getWidth(), getType());
            List<Coordinate> edges = new ArrayList<>();

            for (int i = 0; i < getAlphaChromosome().getEdgeCoordinates().size(); i++){
                coordinate = createRandomCoordinates();
                newImage.put(coordinate.getX(), coordinate.getY(), 255.0);
                edges.add(coordinate);
            }

            chromosome.setImg(newImage);
            chromosome.setEdgeCoordinates(edges);
            fitness = getDistanceMeasure().findDistance(chromosome, getAlphaChromosome());
            chromosome.setFitness(fitness);
            getPopulation().add(chromosome);
        }
    }

    public void nextAge() {
        super.nextAge();
    }

    private Coordinate  createRandomCoordinates(){
        Random random = new Random();
        int randomX = random.nextInt(getWidth());
        int randomY = random.nextInt(getHeight());
        return new Coordinate(randomY, randomX);
    }

}
