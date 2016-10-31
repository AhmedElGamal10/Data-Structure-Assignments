package eg.edu.alexu.csd.filestructure.avl;

public class MyAVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	private MyNode root;
	public MyAVLTree() {
		root = null;
	}

	public void insert(final T key) {
		root = insert(root, key);
	}

	public boolean delete(final T key) {
		if (search(key)) {
			root = delete(root, key);
			return true;
		} else{
			return false;
		}
	}

	public boolean search(final T key) {
		MyNode local = root;
		while (local != null) {
			if (local.getValue().compareTo(key) == 0){
				return true;
			}
			else if (local.getValue().compareTo(key) > 0){
				local = local.left;
			}
			else{
				local = local.right;
			}
		}
		return false;
	}

	public int height() {
		if (root == null){
			return 0;
		}

		return root.height;
	}

	public INode<T> getTree() {
		return root;
	}

	private MyNode rightRotate(final MyNode y) {
		MyNode temp1 = y.left;
		MyNode temp2 = temp1.right;

		temp1.right = y;
		y.left = temp2;

		y.height = max(height(y.left), height(y.right)) + 1;
		temp1.height = max(height(temp1.left), height(temp1.right)) + 1;

		return temp1;
	}

	private MyNode leftRotate(final MyNode x) {
		MyNode temp1 = x.right;
		MyNode temp2 = temp1.left;

		temp1.left = x;
		x.right = temp2;

		x.height = max(height(x.left), height(x.right)) + 1;
		temp1.height = max(height(temp1.left), height(temp1.right)) + 1;

		return temp1;
	}

	private int getBalance(final MyNode node) {
		if (node == null) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	private MyNode insert(final MyNode node, final T key) {

		if (node == null) {
			return (new MyNode(key));
		}

		if (key.compareTo(node.getValue()) < 0) {
			node.left = insert(node.left, key);
		} else {
			node.right = insert(node.right, key);
		}

		node.height = max(height(node.left), height(node.right)) + 1;

		int balance = getBalance(node);

		if (balance > 1 && key.compareTo(node.left.value) < 0) {
			return rightRotate(node);
		}

		if (balance < -1 && key.compareTo(node.right.value) > 0) {
			return leftRotate(node);
		}

		if (balance > 1 && key.compareTo(node.left.value) > 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		if (balance < -1 && key.compareTo(node.right.value) < 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}
		return node;
	}

	private MyNode minValueNode(final MyNode node) {
		MyNode temp = node;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp;
	}

	private MyNode delete(MyNode node, final T key) {

		if (node == null) {
			return node;
		}

		if (key.compareTo(node.getValue()) < 0) {
			node.left = delete(node.left, key);
			} else if (key.compareTo(node.getValue()) > 0) {
			node.right = delete(node.right, key);
		}

		else {

			if ((node.left == null) || (node.right == null)) {
				MyNode temp = null;
				if (temp == node.left) {
					temp = node.right;
				} else {
					temp = node.left;
				}

				if (temp == null) {
					temp = node;
					node = null;
				} else {
					node = temp;
				}
			} else {

				MyNode temp = minValueNode(node.right);
				node.value = temp.value;
				node.right = delete(node.right, temp.getValue());
			}
		}

		if (node == null) {
			return node;
		}

		node.height = max(height(node.left), height(node.right)) + 1;
		int balance = getBalance(node);

		if (balance > 1 && getBalance(node.left) >= 0) {
			return rightRotate(node);
		}

		if (balance > 1 && getBalance(node.left) < 0) {
			node.left = leftRotate(node.left);
			return rightRotate(node);
		}

		if (balance < -1 && getBalance(node.right) <= 0) {
			return leftRotate(node);
		}

		if (balance < -1 && getBalance(node.right) > 0) {
			node.right = rightRotate(node.right);
			return leftRotate(node);
		}

		return node;
	}

	private int height(final MyNode node) {
		if (node == null) {
			return 0;
		}
		return node.height;
	}

	private int max(int a, int b) {
		return (a > b) ? a : b;
	}

	private class MyNode implements INode<T> {
		private T value;
		MyNode left, right;
		private int height;

		public MyNode(final T key) {
			value = key;
			height = 1;
		}

		public INode<T> getLeftChild() {
			return left;
		}

		public INode<T> getRightChild() {
			return right;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		// public int getHeight() {
		// return height;
		// }
		//
		// public void setHeight(int height) {
		// this.height = height;
		// }
		//
		// public void setLeftChild(final MyNode node) {
		// this.left = node;
		// }
		//
		// public void setRightChild(final MyNode node) {
		// this.right = node;
		// }

	}
}
