import org.opencv.core.Mat;

import java.util.List;
import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */
public class Chromosome implements Comparable<Chromosome> {
    private final static int mutationRate = 50;
    private Mat img;
    private int fitness;

    public Chromosome() {
        super();
    }

    public void mutation(List<Pixel> pixels) {
        Random random = new Random();
        int mutationChance = random.nextInt(100);

        if (mutationChance < mutationRate) {
            int mutantCount = random.nextInt(100) + 100;
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

    @Override
    public int compareTo(Chromosome o) {
        int f = o.getFitness();
        return this.getFitness() - f;
    }
}
