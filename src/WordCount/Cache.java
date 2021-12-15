package WordCount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.*;


/*
    Alejandro Lopez
    Operating Systems Project 3
    Nova Southeastern University
 */


public class Cache
{
    private static final int SIZE = 5; //The size of the cache makes it so that it can only hold 5 elements and the least used gets dequed
    private Map<String, String> map = new HashMap<String, String>(); //The hash map being used to fill with the counting the words information

    private PriorityQueue<Element> pq = new PriorityQueue<Element>(SIZE, new Comparator(){ //Priority queue to check when the cache goes over the determined size
        @Override
        //Compares whether one object had more usage than the other
        public int compare(Object arg0, Object arg1) {
            //Ordering the elements as per timestamp.
            if (! (arg0 instanceof Element) || !(arg1 instanceof Element))
                return 0;
            Element e1 = (Element) arg0;
            Element e2 = (Element) arg1;
            return e1.getTimestamp().compareTo(e2.getTimestamp()); //Comparing the times when the elements where used
        }
    });

    private void insert(Element e) {
        System.out.println("Received Element: "+e.getValue()); //The file that was moved into the cache
        pq.offer(e);
    }

    //Method to remove the least used element
    private String remove() {
        Element leastUsed = pq.poll();
        if (leastUsed != null) {
            System.out.println("Removing least used element:"+leastUsed.getValue());
            System.out.println("This element was last used:"+leastUsed.getTimestamp());
            return leastUsed.getValue();
        }
        return "";
    }

    private void update(String mostRecentEleKey) {
        //update priority queue with most recent access.
        //Internal data structure on PriorityQueue is Heap and it is partially sorted.
        //This means, any update on the elements means to delete them and add them again.

        Iterator<Element> pqIterator = pq.iterator();
        while(pqIterator.hasNext()) {
            Element e = pqIterator.next();
            if(e.getValue().equals(mostRecentEleKey)) {
                pqIterator.remove();
                break;
            }
        }
        Element mostRecent = new Element();
        mostRecent.setTimestamp(new Date());
        mostRecent.setValue(mostRecentEleKey);
        insert(mostRecent);
    }

    //Method to acquire the element inside the Hash Map using its key
    public String get(String key) {
        String value = map.get(key);

        return value;
    }

    //This method puts data into the cache (the cache is made up of a hash map and a priority queue)
    public void put(String key, String value) {
        System.out.println("Before put opertaion, map size:"+map.size());
        if (map.containsKey(key)) { //Check if the hash map contains the desired value
            System.out.println("Cache hit on key:"+key+", nothing to insert!");
            update(key); //Update the priority queue
            map.put(key, value); //Put the new value of the contents of the file inside the hash map using that key: the key is the file's name
        } else {
            if(map.size() >= SIZE) { //If the size of the Hash Map goes over the SET Cache value
                String leastUsedKey = remove(); //Remove the least used key from the queue
                map.remove(leastUsedKey); //Remove the least used key from the Hash Map
            }
            System.out.println("Element not present in Cache: "+key); //Display if that key (file) was not inside the cache
            Element e = new Element();
            e.setValue(key);
            e.setTimestamp(new Date());
            insert(e);
            map.put(key,value);
        }
        System.out.println("After put operation, following stats are generated:");
        System.out.println("Least used element:"+pq.peek().getValue()+", last used at:"+pq.peek().getTimestamp());
        System.out.println("map size:"+map.size());
    }

}
