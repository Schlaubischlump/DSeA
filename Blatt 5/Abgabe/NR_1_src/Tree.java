import java.util.ArrayList;
import java.util.Random;

// Wrapper class, there is no need for customization
class Tree<T extends Comparable<T>> extends AbstractTree<T> {
	
	public Tree(T key) {
		root = new Node<T>(key);
	}

	public boolean insert(T key) {
		return root.insert(key);
	}

	public boolean isIn(T key) {
		return root.isIn(key);
	}

	// all the traversal methods! :D |--> :(
	public void preorder() {
		root.preorder();
	}

	public void inorder() {
		root.inorder();
	}

	public void postorder() {
		root.postorder();
	}

	// recursive depth method
	public int maxDepth() {
		return root.maxDepth();
	}

	public static void main(String args[]) {

		// new Integer tree (put in 2 as root key)
		Tree<Integer> T = new Tree<Integer>(2);
		// put in 3 (true)
		System.out.println(T.insert(3));
		// put in 3 (false - keys are unique!)
		System.out.println(T.insert(3));
		// 2 in tree (true)
		System.out.println(T.isIn(2));
		
		System.out.println("---------Preorder---------------");
		T.preorder();
		System.out.println("---------Inorder----------------");
		T.inorder();
		System.out.println("---------Postorder--------------");
		T.postorder();
		
		
		
		//ArrayList<Integer> numbersOrigin;
		//Random random;
		
		//int[] sum;
		
		for (int k = 2; k <= 6; k++ ) {
			final int[] sum = new int[10];
			//numbersOrigin = new ArrayList<Integer>();
			//random = new Random();
			final int nMax = (int)Math.pow(10.0, (double)k);
			
			//for (int n=1;n <nMax;n++ ) {
				//numbersOrigin.add(n);
				//int ranNum = random.nextInt(nMax);
				//if (tree.isIn(ranNum))
					//n--;
				//else
					//tree.insert(ranNum);
			//}
			
			
			/*for (int i = 0; i < 10; i++) {
				//Tree<Integer> tree = new Tree<Integer>(random.nextInt(nMax));
				ArrayList<Integer> numbers = new ArrayList<Integer>(numbersOrigin);
				int index = random.nextInt(numbers.size());
				Tree<Integer> tree = new Tree<Integer>(numbers.get(index));
				numbers.remove(index);
				System.out.print(";");//Tree<Integer> tree = new Tree<Integer>(random.nextInt(nMax));
				ArrayList<Integer> numbers = new ArrayList<Integer>(numbersOrigin);
				int index = random.nextInt(numbers.size());
				Tree<Integer> tree = new Tree<Integer>(numbers.get(index));
				numbers.remove(index);
				System.out.print(";");
				
				//for(int n = 1; n < nMax; n++) {
				while(numbers.size()>0) {
					index = random.nextInt(numbers.size());
					tree.insert(numbers.get(index));
					numbers.remove(index);
				//}
				}
				System.out.print(".");//tree.maxDepth());
				sum +=tree.maxDepth();
				System.out.print("+");
				
				//for(int n = 1; n < nMax; n++) {
				while(numbers.size()>0) {
					index = random.nextInt(numbers.size());
					tree.insert(numbers.get(index));
					numbers.remove(index);
				//}
				}
				System.out.print(".");//tree.maxDepth());
				sum +=tree.maxDepth();
				System.out.print("+");
			}
			*/
			Thread[] threads = new Thread[10];
			for (int i = 0; i < 10; i++) {
				final int j = i;
				threads[i] = new Thread(new Runnable() {
					@Override
					public void run() {

						Random random = new Random();
						ArrayList<Integer> numbers = new ArrayList<Integer>();
						for (int n=1;n <nMax;n++ ) {
							numbers.add(n);
						}
						
						int index = random.nextInt(numbers.size());
						Tree<Integer> tree = new Tree<Integer>(numbers.get(index));
						numbers.remove(index);
						System.out.print(";");
						
						//for(int n = 1; n < nMax; n++) {
						while(numbers.size()>0) {
							index = random.nextInt(numbers.size());
							tree.insert(numbers.get(index));
							numbers.remove(index);
						//}
						}
						System.out.print(".");//tree.maxDepth());
						sum[j] =tree.maxDepth();
						System.out.print("+");
												
					}
					
				});
				threads[i].start();
			}
			for (Thread thread: threads)
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			int result = 0;
			for (int i: sum)
				result += i;
			
			System.out.println();
			System.out.println("k = "+k+" durchschnittliche Tiefe = "+result/10);
		}
		
	}
}