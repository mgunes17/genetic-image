import org.opencv.core.Mat;

import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */


public class Chromosome implements Comparable<Chromosome> {
    private final static int mutationRate = 30;
    private Mat img;
    private int fitness;
    private List<Coordinate> edgeCoordinates;

    public Chromosome() {
        super();
    }

    public void mutation(List<Pixel> pixels) {
        Random random = new Random();
        int mutationChance = random.nextInt(100);

        if (mutationChance < mutationRate) {
            int mutantCount = random.nextInt(10) + 20;
            int mutantX, mutantY, imageWidth, imageHeight;
            double[] values = new double[3];

            imageHeight = img.height();
            imageWidth = img.width();

            for (int i = 0; i < mutantCount; i++) {
                mutantX = random.nextInt(imageWidth);
                mutantY = random.nextInt(imageHeight);

                int randomElement = random.nextInt(pixels.size());
                Pixel pixel = pixels.get(randomElement);

                values[0] = pixel.getB();
                values[1] = pixel.getG();
                values[2] = pixel.getR();

                img.put(mutantY, mutantX, values);
            }
        }
    }

    public void mutationForCircle() {
        Random random = new Random();
        int mutationChance = random.nextInt(100);

        if (mutationChance < mutationRate) {
            int mutantCount = random.nextInt(10) + 20;
            int mutantX, mutantY, imageWidth, imageHeight;
            int unMutate;

            imageHeight = img.height();
            imageWidth = img.width();

            for (int i = 0; i < mutantCount; i++) {
                mutantX = random.nextInt(imageWidth);
                mutantY = random.nextInt(imageHeight);

                unMutate = random.nextInt(edgeCoordinates.size());
                edgeCoordinates.set(unMutate, new Coordinate(mutantY, mutantX));
                img.put(mutantY, mutantX, 255.0);
            }
        }
    }

    //getter-setter
    public Mat getImg() {
        return img;
    }

    public void setImg(Mat img) {
        this.img = img;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public List<Coordinate> getEdgeCoordinates() {
        return edgeCoordinates;
    }

    public void setEdgeCoordinates(List<Coordinate> edgeCoordinates) {
        this.edgeCoordinates = edgeCoordinates;
    }

    @Override
    public int compareTo(Chromosome o) {
        int f = o.getFitness();
        return this.getFitness() - f;
    }
}