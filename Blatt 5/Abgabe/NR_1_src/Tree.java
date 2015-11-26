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
		

		for (int k = 2; k <= 6; k++ ) {
			final int[] sum = new int[10];
			int nMax = (int)Math.pow(10.0, (double)k);

			final int[] test = new int[nMax];

			for (int i=0; i < test.length; i++)
				test[i] = i+1;



			Thread[] threads = new Thread[10];
			for (int i = 0; i < 10; i++) {
				final int j = i;
				threads[i] = new Thread(new Runnable() {
					@Override
					public void run() {

						int[] numbers = test.clone();

						Random random = new Random();

						for (int i = 0; i < numbers.length; i++) {
							int p = random.nextInt(numbers.length);
							int temp = numbers[i];
							numbers[i] = numbers[p];
							numbers[p] = temp;
						}

						Tree<Integer> tree = new Tree<Integer>(numbers[0]);
						//System.out.print(";");
						
						for(int n = 1; n < nMax; n++) {
							tree.insert(numbers[n]);
						}
						System.out.print(".");
						sum[j] =tree.maxDepth();
						//System.out.print("+");
												
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