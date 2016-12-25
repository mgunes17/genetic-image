package genetic.selection;

import genetic.Chromosome;

import java.util.List;
import java.util.Random;

public class OrderBasedSelection implements Selection {
    @Override
    public Chromosome selection(List<Chromosome> population) {
        Random random = new Random();
        int populationSize = population.size();
        int arraySize = (populationSize * (populationSize + 1)) / 2 + 1;
        int[] orderedSelectList = new int[arraySize];
        int selectRandom = random.nextInt(arraySize);
        int arrayPointer = 1;
        for (int i = 1; i < populationSize + 1; i++) {
            for (int j = 0; j < populationSize - i +1; j++) {
                orderedSelectList[arrayPointer] = i - 1;
                arrayPointer++;
            }
        }

        return population.get(orderedSelectList[selectRandom]);
    }
}
