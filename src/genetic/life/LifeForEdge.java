package genetic.life;

import genetic.Chromosome;
import genetic.Coordinate;
import org.opencv.core.Mat;
import view.GlobalParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LifeForEdge extends Life {
    public LifeForEdge() {
        setCrossover(GlobalParameters.crossover);
        setSelection(GlobalParameters.selection);
        setDistanceMeasure(GlobalParameters.distanceMeasure);
    }

    public void initialize() {
        Coordinate coordinate;
        int fitness;

        for (int k = 0; k < GlobalParameters.populationSize; k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(GlobalParameters.height, GlobalParameters.width, GlobalParameters.type);
            List<Coordinate> edges = new ArrayList<>();

            for (int i = 0; i < GlobalParameters.alpha.getEdgeCoordinates().size(); i++){
                coordinate = createRandomCoordinates();
                newImage.put(coordinate.getX(), coordinate.getY(), 255.0);
                edges.add(coordinate);
            }

            chromosome.setImg(newImage);
            chromosome.setEdgeCoordinates(edges);
            fitness = getDistanceMeasure().findDistance(chromosome, GlobalParameters.alpha);
            chromosome.setFitness(fitness);
            getPopulation().add(chromosome);
        }
    }

    public void nextAge() {
        super.nextAge();
    }

    private Coordinate  createRandomCoordinates(){
        Random random = new Random();
        int randomX = random.nextInt(GlobalParameters.width);
        int randomY = random.nextInt(GlobalParameters.height);
        return new Coordinate(randomY, randomX);
    }

}
