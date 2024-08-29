// 1) How can you suggest enhancement to this code snippet. 

public class ArrArgs {
    public static void main (String args[]) {
        try {
            int k=0;

            // Input validation: Check if there are any arguments passed
            if (args.length == 0) {
                System.out.println("There is no input value");
                return; //quit the program if there is no arguments passed
            }

            // Use while loop
            while (k < args.length) {
                System.out.println("Value of input is " + k + " and argument is " + args[k++]);
            }

        } catch (ArrayIndexOutOfBoundsException errorOc) {
            System.err.println("Error occurred when attempting to access an argument in array: "+errorOc.toString());
            System.err.println("Number of arguments passed: "+ args.length);
        }
    }
}

// Output:
// Eror occured java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0

// Suggestions:
// Add validation at user input

// Add clearer output for catch; java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0 is an error where the code tries to access an index of the array but it does not exist/out of bound of the array. Therefore adding a clearer output will help the user know why the code returns error.

//Use while loop instead of do-while. Because it will only run if there is arguments passed. If there is no arguments passed, the loop will not run. While loop will also check the condition before running the next code which can prevent unnecessary execution when there is no arguments.

