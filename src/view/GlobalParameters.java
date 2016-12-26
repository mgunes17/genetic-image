package view;

import genetic.Chromosome;
import genetic.crossover.Crossover;
import genetic.distance.DistanceMeasure;
import genetic.selection.Selection;
import kmeans.Pixel;

import java.util.List;

public class GlobalParameters {
    public static int populationSize;
    public static int mutationRate;
    public static int mutationCount;
    public static int colorCount;
    public static int height;
    public static int width;
    public static int type;
    public static Crossover crossover;
    public static Selection selection;
    public static DistanceMeasure distanceMeasure;
    public static Chromosome alpha;
    public static List<Pixel> pixels;
}
