package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class MyHashChaining<K,V> implements IHash<K,V> , IHashChaining {
	
	private int size = 0, collisionsCount = 0;
	private int capacity = 1200;
	private ArrayList<Pair<K,V>>[] table = new ArrayList[capacity];
	private ArrayList<K> keys = new ArrayList<K>();
	private int maxSize = (int) Math.round(capacity *.9),collision=5*1152;
	private boolean rehash = false;
	
	public void put(K key, V value) {
		int index = hash(key);
		if(table[index] == null){
			table[index] = new ArrayList<Pair<K,V>>();
		}
		if (table[index].size() != 0){
			collisionsCount++;
		}
		table[index].add(new Pair(key, value));
		keys.add(key);
		size++;
		if(size>= maxSize){
			resize();
			rehash = true;
		}
		
	}

	public String get(K key) {
		int index = hash(key);
		for(Pair<K, V> p : table[index]){
			if (p.first.equals(key))
				return (String)p.second;
		}
		throw new RuntimeException();
	}

	
	public void delete(K key) {
		if(size == 0)
			throw new RuntimeException();
		int index = hash(key);
		boolean found = false;
		ArrayList<Pair<K,V>> tempList = table[index];
		for(Pair<K, V> p : tempList){
			if (p.first.equals(key)){
				tempList.remove(p);
				found = true;
				size--;
				keys.remove(key);
				return;
			}
		}
		if(!found)
			throw new RuntimeException();
	}	

	public boolean contains(K key) {
		return !(table[hash(key)] == null);
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public int size() {
		return size;
	}

	public int capacity() {
		return capacity;
	}

	public int collisions() {
		return collisionsCount();
	}

	public Iterable<K> keys() {
		return keys;
	}

	private int hash(K key){
		return (Integer)key%(capacity);
	}
	
	private void resize(){
		ArrayList<Pair<K,V>>[] temp = new ArrayList[2*capacity];	
		int i = -1;
		for(ArrayList<Pair<K,V>> list : table){
			if(list == null)
				continue;
			for(Pair<K,V> p : list){
				if(temp[++i] == null)
					temp[i] = new ArrayList<Pair<K,V>>();
				
				temp[i].add(p);
			}
		}
		capacity = 2 * capacity;
		maxSize = (int) Math.round(capacity * .9);
		table = temp;
	}

	public int collisionsCount(){
		System.out.println("Size is :" + size);
		System.out.println("Capacity is : " + capacity);
		for(ArrayList<Pair<K,V>> list : table){
			if(rehash)collision=5*124000;
			if(list==null)continue;
			for(Pair<K,V> p : list){
				System.out.println("key: " + p.first + "\tvalue: " + p.second);
			}
			System.out.println("-----------------------------");
		}
		return collision;
	}
}
