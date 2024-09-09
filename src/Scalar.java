package physics;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Scalar {
    public static void main(String... args) {
        Consumer<String[]> isScalar = (strings) -> {
            Pattern pattern = Pattern.compile("^(.+?)\\1+$");
            for (String str : strings) {
                if (pattern.matcher(str).matches()) {
                    System.out.println(str + " is a repeated pattern.");
                } else {
                    System.out.println(str + " is not a repeated pattern.");
                }
            }
        };

        isScalar.accept(new String[]{"....|....|....|", "_'_'_'_'_", "1,2,3,4,5"});
        isScalar.accept(new String[]{".", "..", "..."});
        isScalar.accept(args);

        // Handle user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number to represent as a scalar: ");
        
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            StringBuilder scalarRepresentation = new StringBuilder();
            for (int i = 0; i < number; i++) {
                scalarRepresentation.append("|");
            }
            System.out.println("Scalar representation: " + scalarRepresentation.toString());
        } else {
            System.out.println("Invalid input. Please enter an integer.");
        }
        
        scanner.close();
    }
}