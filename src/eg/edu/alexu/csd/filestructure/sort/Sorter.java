package eg.edu.alexu.csd.filestructure.sort;
import java.util.ArrayList;

public class Sorter<T extends Comparable<T>> implements ISort<T> {

	@Override
	public IHeap<T> heapSort(ArrayList<T> unordered) {
		MyHeap<T> heap = new MyHeap<T>();
		heap.build(unordered);
		for (int i = 0; i < unordered.size() - 1; i++) {
			heap.extract();
		}
		heap.setSize(unordered.size());
		return heap;
	}

	@Override
	public void sortSlow(ArrayList<T> unordered) {
		for (int c = 0; c < unordered.size(); c++) {
			for (int d = 0; d < unordered.size() - 1; d++) {
				if (unordered.get(d).compareTo(unordered.get(d + 1)) == 1) {
					T tmp = unordered.get(d);
					unordered.set(d, unordered.get(d + 1));
					unordered.set(d + 1, tmp);
				}
			}
		}
	}

	@Override
	public void sortFast(ArrayList<T> unordered) {
		quickSort(0, unordered.size() - 1, unordered);
	}

	private void quickSort(int Lindex, int Uindex, ArrayList<T> list) {

		if (Lindex >= Uindex) {
			return;
		}

		int low = Lindex;
		int up = Uindex;

		T pivot = list.get(low + (up - low) / 2);

		while (low <= up) {
			while ((list.get(low).compareTo(pivot)) < 0) {
				low++;
			}

			while ((list.get(up).compareTo(pivot)) > 0) {
				up--;
			}

			if (low <= up) {
				T tmp = list.get(low);
				list.set(low, list.get(up));
				list.set(up, tmp);
				low++;
				up--;
			}
		}
		if (Lindex < up) {
			quickSort(Lindex, up, list);
		}
		if (low < Uindex) {
			quickSort(low, Uindex, list);
		}
	}
}