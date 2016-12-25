package genetic.crossover;

import genetic.Chromosome;
import genetic.distance.MeasureWithPixelMatching;
import kmeans.Pixel;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderBasedCrossover implements Crossover {
    private List<Pixel> pixels;
    private Chromosome alpha;

    public OrderBasedCrossover(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int width = parent1.getImg().width();
        int height = parent1.getImg().height();
        int type = parent1.getImg().type();
        Chromosome childChromosome = new Chromosome();
        Mat child1 = new Mat(height, width, type);
        Mat child2 = new Mat(height, width, type);

        Mat img1 = parent1.getImg();
        Mat img2 = parent2.getImg();

        Random random = new Random();
        MeasureWithPixelMatching measureWithPixelMatching = new MeasureWithPixelMatching();

        List<Integer> template = new ArrayList<>();

        for (int i = 0; i < width * height; i++) {
            template.add(random.nextInt(2));
        }

        List<Pixel> orderedPixelsForChild1 = new ArrayList<>();
        List<Pixel> orderedPixelsForChild2 = new ArrayList<>();
        double[] values;
        Pixel pixel;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (template.get(i * j + i) == 1) {
                    child1.put(i, j, img1.get(i, j));
                    child2.put(i, j, img2.get(i, j));
                } else {
                    pixel = new Pixel();
                    values = new double[3];
                    values[0] = img1.get(i, j)[0];
                    values[1] = img1.get(i, j)[1];
                    values[2] = img1.get(i, j)[2];
                    pixel.setB(values[0]);
                    pixel.setG(values[1]);
                    pixel.setR(values[2]);
                    orderedPixelsForChild1.add(pixel);
                    pixel = new Pixel();
                    values = new double[3];
                    values[0] = img2.get(i, j)[0];
                    values[1] = img2.get(i, j)[1];
                    values[2] = img2.get(i, j)[2];
                    pixel.setB(values[0]);
                    pixel.setG(values[1]);
                    pixel.setR(values[2]);
                    orderedPixelsForChild2.add(pixel);
                }
            }
        }

        List<Pixel> newPixelsForChild1 = new ArrayList<>();
        List<Pixel> newPixelsForChild2 = new ArrayList<>();
        int size = orderedPixelsForChild1.size();
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(count1 < size) {
                    values = new double[3];
                    values[0] = img1.get(i, j)[0];
                    values[1] = img1.get(i, j)[1];
                    values[2] = img1.get(i, j)[2];
                    if (orderedPixelsForChild1.get(count1).getB() == values[0]
                            && orderedPixelsForChild1.get(count1).getG() == values[1]
                            && orderedPixelsForChild1.get(count1).getR() == values[2]) {
                        newPixelsForChild1.add(orderedPixelsForChild1.get(count1));
                        count1++;
                    }
                }
                if(count2 < size) {
                    values = new double[3];
                    values[0] = img2.get(i, j)[0];
                    values[1] = img2.get(i, j)[1];
                    values[2] = img2.get(i, j)[2];
                    if (orderedPixelsForChild2.get(count2).getB() == values[0]
                            && orderedPixelsForChild2.get(count2).getG() == values[1]
                            && orderedPixelsForChild2.get(count2).getR() == values[2]) {
                        newPixelsForChild2.add(orderedPixelsForChild2.get(count2));
                        count2++;
                    }

                }
            }
        }

        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(count < size) {
                    if (template.get(i * j + j) == 0) {
                        values = new double[3];
                        values[0] = newPixelsForChild1.get(count).getB();
                        values[1] = newPixelsForChild1.get(count).getG();
                        values[2] = newPixelsForChild1.get(count).getR();
                        child1.put(i, j, values);
                        values = new double[3];
                        values[0] = newPixelsForChild2.get(count).getB();
                        values[1] = newPixelsForChild2.get(count).getG();
                        values[2] = newPixelsForChild2.get(count).getR();
                        child2.put(i, j, values);
                        count++;
                    }
                }
            }
        }

        Chromosome c1 = new Chromosome();
        c1.setImg(child1);

        Chromosome c2 = new Chromosome();
        c2.setImg(child2);

        int fitness1 = measureWithPixelMatching.findDistance(c1, alpha);
        int fitness2 = measureWithPixelMatching.findDistance(c2, alpha);

        if (fitness1 < fitness2) {
            childChromosome.setImg(child1);
        } else {
            childChromosome.setImg(child2);
        }
        childChromosome.mutation(pixels);
        return childChromosome;
    }

    public Chromosome getAlpha() {
        return alpha;
    }

    public void setAlpha(Chromosome alpha) {
        this.alpha = alpha;
    }
}
