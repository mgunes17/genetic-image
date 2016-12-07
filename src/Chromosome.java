import org.opencv.core.Mat;

import java.util.Random;

/**
 * Created by mgunes on 06.12.2016.
 */
public class Chromosome {
    private final static int mutationRate = 50;
    private Mat img;
    private int fitness;

    public Chromosome() {
        super();
    }


    public void mutation() {
        Random random = new Random();
        int mutationChance = random.nextInt(100);

        if(mutationChance < mutationRate){
            int mutantCount = random.nextInt(100) + 100;
            int mutantX, mutantY, imageWidth, imageHeight;
            int randomBlue, randomGreen, randomRed;
            int [] values = new int [3];

            imageHeight = img.height();
            imageWidth = img.width();

            for(int i= 0; i < mutantCount; i++){
                mutantX = random.nextInt(imageWidth);
                mutantY = random.nextInt(imageHeight);

                randomBlue = random.nextInt(255);
                randomGreen = random.nextInt(255);
                randomRed = random.nextInt(255);
                values[0] = randomBlue;
                values[1] = randomGreen;
                values[2] = randomRed;

                img.put(mutantX, mutantY, values);
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
}
