package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;

public class MyHeap<T extends Comparable<T>> implements IHeap<T> {
	private ArrayList<INode<T>> heap;
	private int size;

	public MyHeap() {
		heap = new ArrayList<INode<T>>();
		size = 0;
	}

	@Override
	public INode<T> getRoot() {
		if (size == 0)
			return null;
		return heap.get(0);
	}

	@Override
	public int size() {
		return size;
	}

	public void heapify(INode<T> node) {
		if (size == 0 || size == 1) {
			return;
		}
		INode<T> largest = node;
		INode<T> left = node.getLeftChild();
		INode<T> right = node.getRightChild();
		boolean flag = false;

		if (left != null && left.getValue().compareTo(node.getValue()) > 0) {
			largest = left;
			flag = true;
		}

		if (right != null && right.getValue().compareTo(largest.getValue()) > 0) {
			largest = right;
			flag = true;
		}

		if (flag) {
			swap(node, largest);
			heapify(largest);
		}

	}

	@Override
	public T extract() {
		if (size == 0)
			return null;
		if (size == 1) {
			size--;
			return heap.get(0).getValue();
		}

		T oldRoot = heap.get(0).getValue();
		swap(heap.get(0), heap.get(size - 1));
		size--;
		heapify(heap.get(0));
		return oldRoot;
	}

	@Override
	public void insert(T element) {
		
		if (heap.size() == size) {
			heap.add(new MyNode(element, size++));
		} else {
			heap.set(size, new MyNode(element, size++));
		}

		INode<T> tmp = heap.get(size - 1);

		while (tmp.getParent() != null && tmp.getParent().getValue().compareTo(tmp.getValue()) < 0) {
			swap(tmp, tmp.getParent());
			tmp = tmp.getParent();
		}

	}

	@Override
	public void build(Collection<T> unordered) {
//		if(unordered.size() == 0)
//			throw new RuntimeException();
		size = unordered.size();
		heap.clear();
    
    
    int index = 0;
    
    for(T node : unordered){
    	heap.add(new MyNode(node,index++));
    }
    
		for(int i=(size)/2 - 1 ; i>=0 ; i--){
			heapify(heap.get(i));
		}
	}

	public void setSize(int size) {
		this.size = size;
	}

	private void swap(INode<T> node1, INode<T> node2) {
		T temp = node1.getValue();
		node1.setValue(node2.getValue());
		node2.setValue(temp);
	}

	private class MyNode implements INode<T> {
		T value;
		int index;

		public MyNode(T element, int index) {
			this.value = element;
			this.index = index;
		}

		@Override
		public INode<T> getLeftChild() {

			if ((index * 2) + 1 < size) {
				return heap.get((index * 2) + 1);
			}
			return null;
		}

		@Override
		public INode<T> getRightChild() {
			if ((index * 2) + 2 < size) {
				return heap.get((index * 2) + 2);
			}
			return null;
		}

		@Override
		public INode<T> getParent() {
			if (index > 0) {
				return heap.get((index - 1) / 2);
			}
			return null;
		}

		@Override
		public T getValue() {
			return value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;
		}

	}

}
