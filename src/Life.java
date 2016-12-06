import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgunes on 06.12.2016.
 */
public class Life {
    private int populationCount;
    private List<Chromosome> population;
    private List<Chromosome> newGeneration;
    private GeneticAlgorithms geneticAlgorithms;
    private Mat alphaChromosome;

    public Life(int populationCount, Mat alphaChromosome) {
        super();
        this.populationCount = populationCount;
        population = new ArrayList<Chromosome>();
        newGeneration = new ArrayList<Chromosome>();
        geneticAlgorithms = new GeneticAlgorithms();
        this.alphaChromosome = alphaChromosome;
    }

    public void initialize() {

    }

    public void nextAge() {
        //fitness değerlerini hesapla

        //yeni nesil oluşturma
            //seçilim + crossover



    }

    public int measureManhattan(Mat img1, Mat img2) {
        int diff = 0;
        double [] values1 = new double[3];
        double [] values2 = new double[3];

        for(int i = 0; i < img1.height(); i++) {
            for(int j = 0; j < img2.width(); j++) {
                values1 = img1.get(i, j);
                values2 = img2.get(i, j);
                diff += (values1[0] - values2[0]) + (values1[1] - values2[1]) + (values1[3] - values2[3]);
            }
        }

        return diff;
    }

    public void findBestChromosome() {

    }

    //getter-setter
    public int getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(int populationCount) {
        this.populationCount = populationCount;
    }

    public List<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public List<Chromosome> getNewGeneration() {
        return newGeneration;
    }

    public void setNewGeneration(List<Chromosome> newGeneration) {
        this.newGeneration = newGeneration;
    }
}
