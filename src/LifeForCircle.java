import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by ercan on 24.12.2016.
 */

public class LifeForCircle {
    private int populationSize;
    private List<Chromosome> population;
    private GeneticAlgorithms geneticAlgorithms;
    private Chromosome alphaChromosome;
    private int width;
    private int height;
    private int type;

    public LifeForCircle(int populationSize, Chromosome alphaChromosome, int width, int height, int type) {
        super();
        this.populationSize = populationSize;
        population = new ArrayList<>();
        geneticAlgorithms = new GeneticAlgorithms();
        this.width = width;
        this.height = height;
        this.type = type;
        this.alphaChromosome = alphaChromosome;
    }

    public void initialize() {
        Coordinate coordinate;
        int fitness;

        for (int k = 0; k < populationSize; k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(width, height, type);
            List<Coordinate> edges = new ArrayList<>();

            for (int i = 0; i < alphaChromosome.getEdgeCoordinates().size(); i++){
                coordinate = createRandomCoordinates();
                newImage.put(coordinate.getY(), coordinate.getX(), 255.0);
                edges.add(coordinate);
            }

            fitness = measureFitness(alphaChromosome.getEdgeCoordinates(), edges);
            chromosome.setImg(newImage);
            chromosome.setFitness(fitness);
            chromosome.setEdgeCoordinates(edges);
            population.add(chromosome);
        }
    }

    public void nextAge() {
        Chromosome parent1, parent2;
        Collections.sort(population, new Life.FitnessComparator());
        System.out.println(population.get(0).getFitness() + " " + population.get(populationSize - 1).getFitness());

        List<Chromosome> newGeneration = new ArrayList<>();
        int fitness;

        for (int i = 0; i < populationSize; i++) {
            parent1 = selection();
            parent2 = selection();

            Chromosome child = geneticAlgorithms.crossoverForCircle(parent1, parent2);
            fitness = measureFitness(alphaChromosome.getEdgeCoordinates(), child.getEdgeCoordinates());
            child.setFitness(fitness);
            newGeneration.add(child);
        }
        population = newGeneration;
    }

    public Chromosome selection() {
        Random random = new Random();
        int arraySize = (population.size() * (population.size() + 1)) / 2 + 1;
        int[] orderedSelectList = new int[arraySize];
        int selectRandom = random.nextInt(arraySize);
        int arrayPointer = 1;
        for (int i = 1; i < populationSize + 1; i++) {
            for (int j = 0; j < populationSize - i +1; j++) {
                orderedSelectList[arrayPointer] = i - 1;
                arrayPointer++;
            }
        }

        return population.get(orderedSelectList[selectRandom]);
    }


    public Mat findBestChromosome() {
        int bestFitness = population.get(0).getFitness();
        Mat bestMat = population.get(0).getImg();

        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() < bestFitness) {
                bestFitness = chromosome.getFitness();
                bestMat = chromosome.getImg();
            }
        }
        return bestMat;
    }


    private int measureFitness(List<Coordinate> edges1, List<Coordinate> edges2) {
        int diff = 0;

        for(int i = 0; i < edges1.size(); i++){
            diff += Math.abs(edges1.get(i).getX() - edges2.get(i).getX()) + Math.abs(edges1.get(i).getY() - edges2.get(i).getY());
        }

        return diff;
    }

    private Coordinate  createRandomCoordinates(){
        Random random = new Random();
        int randomX = random.nextInt(width);
        int randomY = random.nextInt(height);
        return new Coordinate(randomX, randomY);
    }


}
