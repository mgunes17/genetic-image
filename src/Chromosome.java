import org.opencv.core.Mat;

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
