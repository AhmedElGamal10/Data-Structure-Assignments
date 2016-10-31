package eg.edu.alexu.csd.filestructure.avl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class MyDictionary<T extends Comparable<T>> implements IDictionary {
	private MyAVLTree<String> avlTree;
	private int size;
	private List<String> wordList;

	//default constructor
	public MyDictionary() {
		avlTree = new MyAVLTree<String>();
		size = 0;
	}

	//loads the word lis from the file
	public void load(final File file) {
		try {
			wordList = readFile(file);
		} catch (IOException e) {

		}

		for (String s : wordList){
			insert(s);
		}
	}

	//inserts a word into the dictionary
	public boolean insert(final String word) {
		if (exists(word)){
			return false;
		}
		else {
			size++;
			avlTree.insert(word);
			return true;
		}

	}

	//check the existence of some word
	public boolean exists(final String word) {
		return avlTree.search(word);
	}
	
	//delete word from dictionary
	public boolean delete(final String word) {
		if (exists(word)) {
			size--;
			avlTree.delete(word);
			return true;
		}
		return false;
	}

	//return size :3
	public int size() {
		return size;
	}

	//return height
	public int height() {
		return avlTree.height();
	}
	
	//loads the file into wordList and returns it
	private List<String> readFile(final File fin) throws IOException {
		List<String> input = new LinkedList<String>();
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		while ((line = br.readLine()) != null) {
			input.add(line);
		}
		br.close();
		return input;
	}
}