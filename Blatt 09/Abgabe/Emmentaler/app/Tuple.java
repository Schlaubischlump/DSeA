package app;

// just because java sucks
class Tuple {

	int zero, one, two;
	static final int maxSize = 1000;

	Tuple (int zero, int one, int two){

		if (zero > maxSize || one > maxSize || two > maxSize)
			throw new IllegalArgumentException("maximale Größe "+maxSize+" Überschritten");
		this.zero = zero;
		this.one  = one;
		this.two  = two;
	}

	@Override
	public int hashCode() {
		return (zero+maxSize*one+maxSize*maxSize*two);
		//return new int[] {zero, one, two}.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	public void print() {
		System.out.println("x = "+zero+" y = "+one+" z = "+two);
	}
}
