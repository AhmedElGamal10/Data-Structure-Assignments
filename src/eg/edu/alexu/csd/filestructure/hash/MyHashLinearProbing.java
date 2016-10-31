package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class MyHashLinearProbing<K, V> implements IHash<K, V>, IHashLinearProbing {
	private int size = 0, collisionsCount = 0;
	private int capacity = 1200;
	private boolean full = false;
	
	private Pair<K,V>[] table = new Pair[capacity];
	private ArrayList<K> keys = new ArrayList<K>();
	private boolean rehash = false;
	private boolean calledByResize = false;
	
	public void put(K key, V value) {
//		if(contains(key)){
//			for(Pair<K,V> p : table){
//				if(p != null){
//					if(p.first.equals(key)){
//						p.second = value;
//						return;
//					}
//				}
//			}
//		}
		int h = key.hashCode();
		int index = h%capacity;
		//System.out.println("index is: " + index);
		int i=0;
		boolean resized = false;
		
		if(size >= capacity){
			rehash = true;
			resize(key,value);
			return;
		}
		
		while (table[index] != null){
			i++;
			collisionsCount ++;
			index = (h+i) % capacity;
			if(i == capacity){
				rehash = true;
				resize(key, value);
			}
		}
		if(i != 0)
			collisionsCount ++;
		table[index] = new Pair<K,V> (key, value);
		
		if(!keys.contains(key))
			keys.add(key);
		size++;
	}

	private boolean keyExist(K key) {
		for(int i=0; i<keys.size() ; i++){
			if(keys.contains(key)){
				
			}
		}
		return false;
	}

	private void resize(K key, V value) {
		collisionsCount += (capacity+1);
		capacity *= 2;
		Pair<K,V>[] temp = table;
		table = new Pair[capacity];
		
		size = 0;
		for(Pair<K,V> p : temp){
			if(p != null)
				put(p.first, p.second);
		}
		put(key,value);
	}

	public String get(K key) {
		if(key == (null))
			throw new RuntimeException();
		for(int i=0 ; i<capacity ; i++){
			if(table[i] == null)
				continue;
			if(table[i].first.equals(key))
				return (String)table[i].second;
		}
		return null;		
	}

	public void delete(K key) {
		for(int i=0 ; i<table.length ; i++){
			if(table[i] == null)
				continue;
			if(table[i].first.equals(key)){
				table[i] = null;
				size--;
				keys.remove(key);
				return;
			}
		}		
	}

	public boolean contains(K key) {
		for(Pair<K,V> p : table){
			if(p != null){
				if(p.first.equals(key)){
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean isEmpty() {
		return size() == 0;
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
		System.out.println(keys.size());
		return keys;
	}

	private int hash(K key){
		return (Integer)key%(capacity);
	}

	
	private int collisionsCount() {
		return collisionsCount;
	}
	
	public void print() {
		for(Pair<K,V> p : table){
			if(p != null){
				System.out.println("key: " + p.first + " value: " + p.second);
				System.out.println("--------------------------------------");
			}
			else{
				System.out.println();
				System.out.println("--------------------------------------");
			}
		}
	}
		
	
}
