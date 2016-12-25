package genetic.life;

import genetic.Chromosome;
import genetic.crossover.Crossover;
import genetic.distance.DistanceMeasure;
import genetic.distance.MeasureWithEdgeMatching;
import genetic.selection.Selection;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 24.12.2016.
 */
public abstract class Life {
    private List<Chromosome> population;
    private Chromosome alphaChromosome;
    private Crossover crossover;
    private DistanceMeasure distanceMeasure;
    private Selection selection;
    private int width;
    private int height;
    private int type;
    private int populationSize;

    public abstract void initialize();

    public Life(int width, int height, int type, int populationSize, Chromosome alphaChromosome) {
        population = new ArrayList<Chromosome>();
        this.alphaChromosome = alphaChromosome;
        this.populationSize = populationSize;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public Mat findBestChromosome() {
        int bestFitness = getPopulation().get(0).getFitness();
        Mat bestMat = getPopulation().get(0).getImg();

        for (Chromosome chromosome : new ArrayList<Chromosome>()) {
            if (chromosome.getFitness() < bestFitness) {
                bestFitness = chromosome.getFitness();
                bestMat = chromosome.getImg();
            }
        }
        return bestMat;
    }

    public void nextAge() {
        Chromosome parent1, parent2;
        Collections.sort(getPopulation(), new LifeForPixel.FitnessComparator());
        System.out.println(getPopulation().get(0).getFitness() + " " + getPopulation().get(getPopulationSize() - 1).getFitness());

        List<Chromosome> newGeneration = new ArrayList<>();
        int fitness = 0;

        for (int i = 0; i < getPopulationSize(); i++) {
            parent1 = selection.selection(population);
            parent2 = selection.selection(population);

            Chromosome child = crossover.crossover(parent1, parent2);
            fitness = distanceMeasure.findDistance(alphaChromosome, child);
            child.setFitness(fitness);
            newGeneration.add(child);
        }

        setPopulation(newGeneration);
        System.out.println(getPopulation().get(0).getFitness() + " " + getPopulation().get(getPopulationSize() - 1).getFitness());
    }

    //getter-setter
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public Chromosome getAlphaChromosome() {
        return alphaChromosome;
    }

    public void setAlphaChromosome(Chromosome alphaChromosome) {
        this.alphaChromosome = alphaChromosome;
    }

    public Crossover getCrossover() {
        return crossover;
    }

    public void setCrossover(Crossover crossover) {
        this.crossover = crossover;
    }

    public DistanceMeasure getDistanceMeasure() {
        return distanceMeasure;
    }

    public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
        this.distanceMeasure = distanceMeasure;
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }
}
