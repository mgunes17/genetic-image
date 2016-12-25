package genetic.distance;

import genetic.Chromosome;
import org.opencv.core.Mat;

public class MeasureWithPixelMatching implements DistanceMeasure {
    @Override
    public int findDistance(Chromosome c1, Chromosome c2) {
        int diff = c1.getImg().rows() * c1.getImg().cols();
        int height = c1.getImg().rows();
        int width = c1.getImg().cols();
        Mat img1 = c1.getImg();
        Mat img2 = c2.getImg();

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(img1.get(i, j)[0] == img2.get(i, j)[0]
                        && img1.get(i, j)[1] == img2.get(i, j)[1]
                        && img1.get(i, j)[2] == img2.get(i, j)[2]) {
                    diff--;
                }
            }
        }
        return diff;
    }
}
