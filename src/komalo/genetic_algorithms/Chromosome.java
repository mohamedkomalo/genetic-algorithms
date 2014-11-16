package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Chromosome<GeneType> {

	Map<Integer, GeneType> genes;

	public Chromosome(int length) {
		genes = new TreeMap<>();
	}

	public GeneType getGeneAt(int index) {
		return genes.get(index);
	}

	public void setGeneAt(int index, GeneType value) {
		genes.put(index, value);
	}
}
