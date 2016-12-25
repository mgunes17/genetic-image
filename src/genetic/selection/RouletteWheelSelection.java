package genetic.selection;

import genetic.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * Created by ercan on 25.12.2016.
 */
public class RouletteWheelSelection implements Selection{

    @Override
    public Chromosome selection(List<Chromosome> population) {
        Random random = new Random();
        int populationSize = population.size();

        int totalFitness = 0;
        for (int i = 0; i < populationSize; i++) {
            totalFitness += population.get(i).getFitness();
        }

        int[] powerArray = new int[populationSize];

        int rouletteSize = 0;
        for(int i = 0; i < populationSize; i++){
            powerArray[i] = totalFitness / population.get(i).getFitness();
            rouletteSize += powerArray[i];
        }

        int[] rouletteArray = new int[rouletteSize];
        int pointer = 0;
        for(int i = 0; i < populationSize; i++){
            for(int j = 0; j < powerArray[i]; j++){
                rouletteArray[pointer] = i;
                pointer++;
            }
        }
        int selectRandom = random.nextInt(rouletteSize);

        return population.get(rouletteArray[selectRandom]);
    }
}
