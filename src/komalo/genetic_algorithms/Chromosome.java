package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Chromosome<GeneType> {

	private Map<Integer, GeneType> genes;
	
	private int length;
	
	public Chromosome(int length) {
		genes = new TreeMap<>();
		
		this.length = length;
	}

	public GeneType getGeneAt(int index) {
		return genes.get(index);
	}

	public void setGeneAt(int index, GeneType value) {
		genes.put(index, value);
	}
	
	public int length(){
		return this.length;
	}
}
