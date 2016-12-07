import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */
public class Life {
    private int populationSize;
    private List<Chromosome> population;
    private List<Chromosome> newGeneration;
    private GeneticAlgorithms geneticAlgorithms;
    private Mat alphaChromosome;
    private int width;
    private int height;
    private int type;

    public Life(int populationSize, Mat alphaChromosome, int width, int height, int type) {
        super();
        this.populationSize = populationSize;
        population = new ArrayList<Chromosome>();
        newGeneration = new ArrayList<Chromosome>();
        geneticAlgorithms = new GeneticAlgorithms();
        this.width = width;
        this.height = height;
        this.type = type;
        this.alphaChromosome = alphaChromosome;
    }

    public void initialize() {
        int [] values;
        int fitness;

        for(int k = 0; k < populationSize; k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(height, width, type);
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    values = createRandomColor();
                    newImage.put(i, j, values);
                }
            }
            chromosome.setImg(newImage);
            fitness = measureManhattan(alphaChromosome, newImage);
            chromosome.setFitness(fitness);
            population.add(chromosome);
        }
    }

    public void nextAge() {
        //fitness değerlerini hesapla
        findFitness();

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

    public Mat findBestChromosome() {
        int bestFitness = population.get(0).getFitness();
        Mat bestMat = population.get(0).getImg();

        for(Chromosome chromosome: population){
            if(chromosome.getFitness() < bestFitness){
                bestFitness = chromosome.getFitness();
                bestMat = chromosome.getImg();
            }
        }
        return bestMat;
    }

    public int [] createRandomColor(){
        Random random = new Random();
        int randomBlue, randomGreen, randomRed;
        int [] values = new int [3];

        randomBlue = random.nextInt(255);
        randomGreen = random.nextInt(255);
        randomRed = random.nextInt(255);
        values[0] = randomBlue;
        values[1] = randomGreen;
        values[2] = randomRed;
        return  values;
    }

    public  void findFitness() {
        int fitness;
        for(Chromosome chromosome: population){
            fitness = measureManhattan(alphaChromosome, chromosome.getImg());
            chromosome.setFitness(fitness);
        }
    }


    //getter-setter

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
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
