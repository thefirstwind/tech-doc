import java.util.LinkedHashMap;
import java.util.Map;

public class Case001_LRULinkedHashMap {

    static class LRULinkedHashMap<K, V> extends LinkedHashMap<K,V>{
        private int capacity;
        private static final long serialVersionUID = 1L;

        public LRULinkedHashMap(int capacity){
            super(16, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected  boolean removeEldestEntry(Map.Entry<K,V> eldest){
            return size()> capacity;
        }

        public static void main(String[] args){
            Map<Integer,Integer> map = new LRULinkedHashMap<>(4);
            map.put(9,3);
            map.put(7,4);
            map.put(5,9);
            map.put(3,4);
            map.put(6,6);

            for(Integer key: map.keySet()){
                System.out.println(key);
            }
        }
    }
}
