package app;

// just because java sucks
class Tuple {

    int zero, one, two;

    Tuple (int zero, int one, int two){

        this.zero = zero;
        this.one  = one;
        this.two  = two;
    }
    
    @Override
    public int hashCode() {
    	return (zero+1000*one+1000*1000*two);
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
