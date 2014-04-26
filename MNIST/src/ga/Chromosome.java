package ga;

public abstract class Chromosome implements Comparable<Chromosome>{
	public abstract int fitness();
	public abstract Chromosome[] crossOver(Chromosome partner);
	public abstract void mutate();
	public abstract void randomize();
	
	@Override
	public int compareTo(Chromosome o) {
		return Integer.compare(this.fitness(), o.fitness());
	}
}
