import java.util.HashMap;

/*
 * LRU cache implementation - Cache will have fixed size and which ever node is least recently accessed will be evicted from the cache 
 * If any item is accessed (or) added (or) updated,this will come to the top of the cache. So, that last element is always least accessed 
 * Using this approach we can safely remove the last element from cache when the size is full
 * 
 * We can use hashMap to get the retrieval O(1) time and Using linkedList insertion and deletion also in O(1) time. However to traverse 
 * the linkedList takes O(n) time, so we can use doubly linkedList where node has both key, value. So, that we can easily get retrieval
 * update,insert, delete operation in cache in O(1) time. 
 */
public class LRUCache {
	
	HashMap<Integer, Node> map;
	Node node;
	Node head;
	Node tail;
	int capacity;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LRUCache cache = new LRUCache(2); //setting the cache capacity
		cache.setCache(1, 10);
		cache.setCache(2, 20);
		System.out.println("value for key 1 "+cache.getVal(1));
		
		cache.setCache(3, 30);
		
		System.out.println("value for key 2 "+cache.getVal(2));
		cache.setCache(4, 40);
		
		System.out.println("value for key 1 "+cache.getVal(1));
		System.out.println("value for key 3 "+cache.getVal(3));
		System.out.println("value for key 4 "+cache.getVal(4));
	}
	

	LRUCache (int capacity){
		this.capacity = capacity;
		map = new HashMap<Integer, Node>(capacity);
		head = new Node(0,0);
		tail = new Node(0,0);
		head.next = tail;
		tail.pre= head; 
		head.pre = null;
		tail.next = null;
	}
		
	//adding the node in the first
	public void addToHead(Node node) {
		node.next = head.next;
		node.next.pre = node;
		node.pre = head;
		head.next = node;
	}
	
	//delete the node and map the pointers to pre & next of node respectively
	public void deleteNode(Node node) {		
		node.next = node.pre.next;
		node.pre = node.next.pre;
		node = null;
	}
	
	

	public int getVal(int key) {
		
		if(map.containsKey(key)) { // return the value if present in cache and add it to the head as it's accessed
			Node temp = map.get(key);
			deleteNode(temp);
			addToHead(temp);
			return temp.value;
		} else {
			return -1;
		}
	}
	
	public void setCache(int key, int value) {
		
		if(map.get(key)!=null) { //if key present in cache, overwrite the value and add it in the beginning
			Node temp = map.get(key);
			temp.value = value;
			deleteNode(temp);
			addToHead(temp);			
		}else if(map.size() == capacity) { // if the size if full, remove the node before tail and add the new node in the head
			map.remove(tail.pre.key);
			deleteNode(tail.pre);
			Node new_node = new Node(key, value);
			addToHead(new_node);
			map.put(key, new_node);
		} else { // add the node to thead
			Node new_node = new Node(key, value);
			addToHead(new_node);
			map.put(key, new_node);
		}
	}
}



//Node construction
class Node{
	
	int key;
	int value;
	Node next;
	Node pre;
	
	Node(int key, int val){		
		this.key = key;
		this.value = val;
	}
	
	
}
