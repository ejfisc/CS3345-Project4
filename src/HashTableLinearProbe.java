public class HashTableLinearProbe<K, V> {
    
    //private static inner class HashEntry<K,V>
    private static class HashEntry<K,V> {
        private K key; //generic key
        private V value; //generic value
        private boolean isActive; //false if marked as deleted

        //constructor
        public HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
            isActive = true; //isActive is default to true
        }
    }

    private static final int DEFAULT_TABLE_CAPACITY = 3;
    private HashEntry<K,V> hashtable[]; //array "hashtable" to hold hash entries
    private int size; //number of hash entries
    private int capacity; //current table capacity 

    @SuppressWarnings("unchecked")
    //constructor
    public HashTableLinearProbe() {
        hashtable = new HashEntry[DEFAULT_TABLE_CAPACITY];
        capacity = DEFAULT_TABLE_CAPACITY;
        size = 0;
    }

    /*Insert value at key, rehashes if table is full,
    throw error message if key is invalid or null and 
    return true upon successful insertion or false if duplicate entry*/
    public boolean insert(K key, V value) {
        //check if key is null
        if(key == null)
            throw new IllegalArgumentException("Null Key Not Allowed");

        //check key type
        if(!validateKey(key))
            throw new IllegalArgumentException("Key Type " + key.getClass() + " Invalid");
        
        //rehash if table is full
        if(size >= capacity)
            rehash();

        int hv = getHashValue(key);

        int i;
        /*loop through the hash table using linear probing (index = (i+1)%capacity)
        until a valid, empty index is found unless key is already in the table*/
        for(i = hv; hashtable[i] != null; i = (i + 1) % capacity) {
            HashEntry<K,V> tmp = hashtable[i];
            K tmpKey = tmp.key;
            //check if duplicate entry
            if(tmpKey.equals(key)) {
                //check if duplicate entry is inactive
                if(!tmp.isActive) {
                    //mark the entry as active, increment the size and return true
                    tmp.isActive = true;
                    tmp.value = value; //set tmp value to new value
                    size++; //increment size
                    return true;
                } 
                else
                    return false; //else duplicate entry is active, insertion failed, return false
            }
        }

        //no duplicate entry found, initialize new entry and insert into hash table
        hashtable[i] = new HashEntry<>(key, value);
        size++; //increment size
        return true;
    }

    /*checks if the given key exists in the table and is not deleted, if yes return
    the value of the key, otherwise return null if not found */
    public V find(K key) {
        //check if key is null
        if(key == null)
            throw new IllegalArgumentException("Null Key Not Allowed");

        //check key type
        if(!validateKey(key))
            throw new IllegalArgumentException("Key Type Invalid");

        //for each entry in hash table, check if duplicate key and return value
        for(HashEntry<K,V> entry : hashtable) {
            //check if entry exists
            if(entry != null) {
                //check if entry is not deleted
                if(entry.isActive) {
                    K tmp = entry.key;
                    V val = entry.value;
                    //check if key matches
                    if(tmp.equals(key))
                        return val;
                }
            }
        }
        //key not found, return null
        return null;
    }

    /*performs lazy deletion by marking the given key as deleted
    return true if deleted, false if not found*/
    public boolean delete(K key) {
        //check if key is null
        if(key == null)
            throw new IllegalArgumentException("Null Key Not Allowed");

        //check key type
        if(!validateKey(key))
            throw new IllegalArgumentException("Key Type Invalid");

        //for each entry in hash table, check if duplicate key is found and mark for deletion
        for(HashEntry<K,V> entry : hashtable) {
            //check that current entry is not null
            if(entry != null) {
                K tmp = entry.key;
                //check if key matches
                if(tmp.equals(key)) {
                    //check if entry is already marked for deletion
                    if(!entry.isActive)
                        return false; //do nothing
                    else {
                        entry.isActive = false; //mark as deleted
                        size--; //decrement size of table
                        return true;
                    }
            }
            }
        }
        //key not found
        return false;
    }

    //returns the hash value for the given key, or -1 if not found
    public int getHashValue(K key) {
        //initialize hash value variable
        int hashval = 0;
        Class<?> c = key.getClass();
        if(c.equals(String.class)) {
            //hash function for string keys
            String k = key.toString();
            for(int i = 0; i < k.length(); i++)
                hashval = 37 * hashval + k.charAt(i);

            hashval %= capacity;
            //if negative hash value, add capacity to get index
            if(hashval < 0)
                hashval += capacity;
    
            return hashval;
        }
        else if(c.equals(Integer.class)) {
            //hash function for integer keys
            hashval = Math.abs((int)key); //take abs value of key (no negative indexes)
            return hashval % capacity;
        }

        //no invalid key type, no hash value found
        return -1;
    }

    //prints each value in the hash table array
    public void print() {
        for(int i = 0; i < capacity; i++) {
            //print the index
            System.out.print(i + ":");

            //temp hash entry to hold current entry
            HashEntry<K,V> tmp = hashtable[i];

            //if no entry at i, print a "blank", else print the key and value, if deleted, mark as such with a *
            if(tmp == null)
                System.out.print(" ____");
            else if(tmp.isActive)
                System.out.print(" Key " + tmp.key + " Value " + tmp.value);
            else
                System.out.print(" *Key " + tmp.key + " Value " + tmp.value);

            //print new line
            System.out.print("\n");
        }
    }

    //throws an exception for keys that aren't Integer or String
    private boolean validateKey(K key) {
        //get class of K object
        Class<?> c = key.getClass();
        //check that K is a String or Integer, otherwise return false, invalid key type
        if(c.equals(Integer.class) || c.equals(String.class))
            return true;
        else
            return false;
    }

    @SuppressWarnings("unchecked")
    /*Doubles the table size and rehashes everything to the new table,
    ignoring entries marked as deleted*/
    private void rehash() {
        capacity *= 2; //double table capacity
        HashEntry<K,V> tmp[] = hashtable; //copy current table to temp table
        hashtable = new HashEntry[capacity]; //initalize new hash table with new capacity
        //reset size to 0 because it will increment when the entries are inserted into the new table
        size = 0;
        
        //loop through the old table and insert every active entry into the new table
        for(HashEntry<K,V> entry : tmp) {
            //check that currenty entry is not null
            if(entry != null) {
                K key = entry.key;
                V value = entry.value;
                //ignore deleted entries
                if(entry.isActive)
                    this.insert(key, value);
                }
        }
    }
}
