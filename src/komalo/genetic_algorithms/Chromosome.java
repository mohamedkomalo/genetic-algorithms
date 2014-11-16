package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.List;

public class Chromosome<GeneType> {

	List<GeneType> genes;

	public Chromosome(int length) {
		genes = new ArrayList<>(length);
	}

	public GeneType getGeneAt(int index) {
		return genes.get(index);
	}

	public void setGeneAt(int index, GeneType value) {
		genes.add(index, value);
	}
}
