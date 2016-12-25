package genetic.selection;

import genetic.Chromosome;

import java.util.List;

public interface Selection {
    Chromosome selection(List<Chromosome> population);
}
