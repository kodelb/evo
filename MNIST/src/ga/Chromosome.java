package ga;

public interface Chromosome {
	public int fitness();
	public Chromosome[] crossOver(Chromosome partner);
	public void mutate();
}
