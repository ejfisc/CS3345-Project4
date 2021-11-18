public class Main {
    public static void main(String[] args) {
        //test block for Integer keys
        HashTableLinearProbe<Integer, String> intTable = new HashTableLinearProbe<>();
        System.out.println("Integer Key Tests:");
        //tests that getHashValue calculates the correct hash value for the corresponding key
        System.out.println(intTable.getHashValue(1));
        System.out.println(intTable.getHashValue(2));
        System.out.println(intTable.getHashValue(3));
        System.out.println(intTable.getHashValue(4));
        System.out.println(intTable.insert(1, "insert test 1"));
        System.out.println(intTable.insert(2, "insert test 2"));
        //shows the current table after above inserts, both should return true
        intTable.print();
        System.out.println(intTable.insert(3, "insert test 3"));
        System.out.println(intTable.insert(4, "insert test 4"));
        //shows the table after 2 more inserts (both should return true), requiring a rehash
        intTable.print();
        System.out.println(intTable.delete(2));
        System.out.println(intTable.delete(4));
        //shows the table after lazy deletion of keys 2 and 4, both should return true
        intTable.print();
        System.out.println(intTable.insert(3, "prexisting insert test"));
        System.out.println(intTable.insert(4, "re-insert deleted key test"));
        /*shows the table after insertion of 3, which already exists in the table so insert should return
        false and 4 which has been previously marked for deletion so insert should return true*/
        intTable.print();
        System.out.println(intTable.delete(2));
        System.out.println(intTable.delete(5));
        /*shows the table after lazy deletion of 2 which has previously been marked for deletion
        so delete should return false and 5 which is not in the table so delete should return false
        table should look the same*/
        intTable.print();
        /*tests the find method, 2 is in the table but marked for deletion so find should return null, 
        3 is in the table and not deleted so find should return "insert test 3", 5 is not in the table so find 
        should return null*/
        System.out.println(intTable.find(2));
        System.out.println(intTable.find(3));
        System.out.println(intTable.find(5));

        System.out.println("--------------------------------------------------------");
        
        //test block for String keys
        HashTableLinearProbe<String, String> stringTable = new HashTableLinearProbe<>();
        System.out.println("String key Tests:");
        //tests that getHashValue calculates the correct hash value for the corresponding key
        System.out.println(stringTable.getHashValue("One"));
        System.out.println(stringTable.getHashValue("Two"));
        System.out.println(stringTable.getHashValue("Three"));
        System.out.println(stringTable.getHashValue("Four"));
        System.out.println(stringTable.insert("One", "insert test 1"));
        System.out.println(stringTable.insert("Two", "insert test 2"));
        //shows the current table after above inserts (both should return true)
        stringTable.print();
        System.out.println(stringTable.insert("Three", "insert test 3"));
        System.out.println(stringTable.insert("Four", "insert test 4"));
        //shows the table after 2 more inserts (both should return true), requiring a rehash
        stringTable.print();
        System.out.println(stringTable.delete("Two"));
        System.out.println(stringTable.delete("Four"));
        /*shows the table after lazy deletion of keys "Two" and "Four"
        both should return true */
        stringTable.print();
        System.out.println(stringTable.insert("Three", "preexisting insert test"));
        System.out.println(stringTable.insert("Four", "re-insert deleted key test"));
        /*shows the table after insertion of "Three", which already exists in the table so insert should 
        return false and "Four" which has been previously marked for deletion so insert should return true*/
        stringTable.print();
        System.out.println(stringTable.delete("Two"));
        System.out.println(stringTable.delete("Five"));
        /*shows the table after lazy deletion of "Two" which has previously been marked for deletion
        so delete should return false and "Five" which is not in the table so delete should return false
        table should look the same*/
        stringTable.print();
        /*tests the find method, "Two" is in the table but marked for deletion so find should return null, 
        "Three" is in the table and not deleted so find should return "insert test 3", "Five" 
        is not in the table so find should return null*/
        System.out.println(stringTable.find("Two"));
        System.out.println(stringTable.find("Three"));
        System.out.println(stringTable.find("Five"));

        //test null and invalid type exceptions
        HashTableLinearProbe<Double, String> doubleTable = new HashTableLinearProbe<>();
        System.out.println(doubleTable.insert(2.5, "This will result in an error"));
        //to test the null exception, comment out the line throwing the invalid type exception
        System.out.println(doubleTable.insert(null, "This will also result in an error"));
    }
}
