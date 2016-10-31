package eg.edu.alexu.csd.filestructure.hash;

public class Demo {

	public static void main(String[] args) {
		MyHashDouble<Integer, String> ch = new MyHashDouble<>();
		
		for(int i=0; i<1000; i++){
			int key = (i+100000) * 12345;
			ch.put(key, String.valueOf(i));
		}
		
		System.out.println("collisions are: " + ch.collisions());
		System.out.println("size is: " + ch.size());
	}

}

