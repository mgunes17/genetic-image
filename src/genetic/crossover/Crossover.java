package genetic.crossover;

import genetic.Chromosome;

/**
 * Created by mgunes on 25.12.2016.
 */
public interface Crossover {
    Chromosome crossover(Chromosome parent1, Chromosome parent2);
}
