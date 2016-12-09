import org.opencv.core.Mat;

import java.util.*;

/**
 * Created by mgunes on 06.12.2016.
 */
public class Life {
    private int populationSize;
    private List<Chromosome> population;
    // private List<Chromosome> newGeneration;
    private List<Pixel> pixels;
    private GeneticAlgorithms geneticAlgorithms;
    private Mat alphaChromosome;
    private int width;
    private int height;
    private int type;

    public Life(int populationSize, Mat alphaChromosome, int width, int height, int type, List<Pixel> pixels) {
        super();
        this.populationSize = populationSize;
        population = new ArrayList<Chromosome>();
        // newGeneration = new ArrayList<Chromosome>();
        geneticAlgorithms = new GeneticAlgorithms();
        this.width = width;
        this.height = height;
        this.type = type;
        this.alphaChromosome = alphaChromosome;
        this.pixels = pixels;
    }

    public void initialize() {
        double[] values;
        int fitness;

        for (int k = 0; k < populationSize; k++) {
            Chromosome chromosome = new Chromosome();
            Mat newImage = new Mat(height, width, type);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    values = createRandomColor();
                    newImage.put(j, i, values);
                }
            }
            chromosome.setImg(newImage);
            fitness = measureManhattan(alphaChromosome, newImage);
            chromosome.setFitness(fitness);
            population.add(chromosome);
        }
    }

    public void nextAge() {
        Chromosome parent1, parent2;
        Collections.sort(population, new FitnessComparator());
        //newGeneration.clear();
        List<Chromosome> newGeneration = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int fitness = 0;
        for (int i = 0; i < populationSize; i++) {
            Chromosome child = null;
            do {
                parent1 = selection();
                parent2 = selection();

                child = geneticAlgorithms.crossover(parent1.getImg(), parent2.getImg(), pixels);
                fitness = measureManhattan(alphaChromosome, child.getImg());
                child.setFitness(fitness);
            } while(map.containsKey(fitness) || parent1.getFitness() == parent2.getFitness());

            map.put(fitness, fitness);
            newGeneration.add(child);
        }
        // population.clear();
        population = newGeneration;
        /*for(Chromosome c: newGeneration) {
            population.add(c);
        }*/
        Collections.sort(population, new FitnessComparator());
        System.out.println(population.get(0).getFitness() + " " + population.get(populationSize - 1).getFitness());
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
        //System.out.println(orderedSelectList[selectRandom]);

        return population.get(orderedSelectList[selectRandom]);
        //return population.get(0);
    }


    public int measureManhattan(Mat img1, Mat img2) {
        int diff = 0;
        double[] values1 = new double[3];
        double[] values2 = new double[3];

        for (int i = 0; i < img1.height(); i++) {
            for (int j = 0; j < img2.width(); j++) {
                values1 = img1.get(i, j);
                values2 = img2.get(i, j);
                diff += Math.abs((values1[0] - values2[0])) + Math.abs((values1[1] - values2[1])) + Math.abs((values1[2] - values2[2]));
            }
        }
        return diff;
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

    /*public  void findFitness() {
        int fitness;
        for(Chromosome chromosome: population){
            fitness = measureManhattan(alphaChromosome, chromosome.getImg());
            chromosome.setFitness(fitness);
        }
    }*/


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

    /*public List<Chromosome> getNewGeneration() {
        return newGeneration;
    }*/

    /*public void setNewGeneration(List<Chromosome> newGeneration) {
        this.newGeneration = newGeneration;
    }*/
}