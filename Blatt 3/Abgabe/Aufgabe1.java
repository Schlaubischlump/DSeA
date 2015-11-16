
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aufgabe1 {

	private static int random(int a, int b) {
		return (int)(a+Math.ceil(Math.random()*(b-a+1))-1);
	}

	public static void swap(int[] array, int i, int j) {
		int temp = array[j];
		array[j] = array[i];
		array[i] = temp;
	}

	public static int partition(int[] numbers, int left, int right, int pivotIndex) {
		int pivot = numbers[pivotIndex];
		swap(numbers,pivotIndex,right);
		int i = left;
		int j = right-1;
		
		while(i<=j) {
			if(numbers[i]>pivot) {
				swap(numbers,i,j);
				j--;
			} else
				i++;
		}
		swap(numbers,i, right);
		return i;
	}

	public static int select(int[] numbers, int left, int right, int k) {
		if (left == right)
			return numbers[left];
		
		int random = random(left,right);
		int i = partition(numbers,left,right, random);

		if (i == k) {
			return numbers[i];
		} else if (k < i) {
			return select(numbers, left, i-1, k);
		} else {
			return select(numbers,i+1,right, k);
		}
	}
	
	public static int select(int[] numbers, int k) {
		return select(numbers, 0, numbers.length-1, k-1);
	}

	public static void main(String...args) {

		// Parsen des Inputs
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String currentLine = null;
		int selectNo = -1;
		int noElemens = -1;

		try {
			currentLine = br.readLine();
			noElemens = Integer.parseInt(currentLine.split(" ")[0]);
			selectNo = Integer.parseInt(currentLine.split(" ")[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] numbers = new int[noElemens];

		try {
			for(int i = 0; i < noElemens; ++i) {
				numbers[i] = Integer.parseInt(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Hier den Aufruf der select Methode und 
		// die Ausgabe des $i$ kleinsten Elementes.
		// selectNo enhält dabei den Wert für i
		for(int i = 0; i<20;i++)
			System.out.println(select(numbers, selectNo));

	}
}