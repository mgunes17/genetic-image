package genetic.life;

import genetic.Chromosome;
import genetic.crossover.Crossover;
import genetic.distance.DistanceMeasure;
import genetic.distance.MeasureWithEdgeMatching;
import genetic.selection.Selection;
import org.opencv.core.Mat;
import view.GlobalParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.RunnableFuture;

/**
 * Created by mgunes on 24.12.2016.
 */
public abstract class Life  implements Runnable {
    private List<Chromosome> population;
    private Crossover crossover;
    private DistanceMeasure distanceMeasure;
    private Selection selection;

    public abstract void initialize();

    public Life() {
        population = new ArrayList<Chromosome>();
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

    @Override
    public void run() {
        while(true) {
            nextAge();
        }
    }

    public void nextAge() {
        Chromosome parent1, parent2;
        Collections.sort(getPopulation(), new LifeForPixel.FitnessComparator());
        System.out.println(getPopulation().get(0).getFitness() + " " + getPopulation().get(GlobalParameters.populationSize - 1).getFitness());

        List<Chromosome> newGeneration = new ArrayList<>();
        int fitness = 0;

        for (int i = 0; i < GlobalParameters.populationSize; i++) {
            parent1 = selection.selection(population);
            parent2 = selection.selection(population);

            Chromosome child = crossover.crossover(parent1, parent2);
            fitness = distanceMeasure.findDistance(GlobalParameters.alpha, child);
            child.setFitness(fitness);
            newGeneration.add(child);
        }

        setPopulation(newGeneration);
        System.out.println(getPopulation().get(0).getFitness() + " " + getPopulation().get(GlobalParameters.populationSize - 1).getFitness());
    }

    //getter-setter

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
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
