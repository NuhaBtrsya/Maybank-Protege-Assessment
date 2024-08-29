public class StringBufferError {
    public static void main(String[] args) {
        try {
            StringBuffer sb = new StringBuffer();

            //enter infinite loop
            while (true) {
                sb.append("The quick brown fox jumps over the lazy dog."); //Using a long string to consume more memory
            }

        } catch (OutOfMemoryError e) {
            System.out.println("Encountered error - OutOfMemoryError: " + e.getMessage());
        }
    }
}

/*
How to resolve without using Garbage Collector (GC) :

Most common ways of resolving without using GC is flushing the buffer after a number of loops. Buffer is the memory that stores characters in a string that is stored in StringBuffer. Buffer flushing enables to release current memory allocated to the StringBuffer. With this, the buffer size will be reduced and can improve the performance of a code execution.

Example code using buffer flushing: 

public class StringBufferError {
    public static void main(String[] args) {
        try {
            StringBuffer sb = new StringBuffer();
            int i = 0;

            //enter infinite loop
            while (true) {
                sb.append("The quick brown fox jumps over the lazy dog.");
                i++;

                //execute buffer flushing at every 1000 loop
                if (i % 1000 == 0) {
                    sb.setLength(0); 
                }
            }

        } catch (OutOfMemoryError e) {
            System.out.println("Encountered error - OutOfMemoryError: " + e.getMessage());
        }
    }
}

How to resolve  using Garbage Collector (GC) :

Call method System.gc(). This method automatically free up the allocated memory space. This way there won't be any out of memory errors since it will always clear the memory space used.

Example code:
public class StringBufferError {
    public static void main(String[] args) {
        try {
            StringBuffer sb = new StringBuffer();

            //enter infinite loop
            while (true) {
                sb.append("The quick brown fox jumps over the lazy dog."); //Using a long string to consume more memory

                System.gc(); //free up the allocated memory space previously
            }

        } catch (OutOfMemoryError e) {
            System.out.println("Encountered error - OutOfMemoryError: " + e.getMessage());
        }
    }
}

 */