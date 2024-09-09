package physics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {
    public static void main(String[] args) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("test.txt"))) {
            // Beispielhafte Daten, die du an das Python-Skript weitergeben möchtest
            double mass1 = 5.972e24;  // Masse der Erde in kg
            double mass2 = 7.348e22;  // Masse des Mondes in kg
            double distance = 3.844e8;  // Abstand Erde-Mond in m

            // Berechnung der Gravitationskraft (als Beispiel)
            double gravitationalConstant = 6.67430e-11;  // Gravitationskonstante in m^3 kg^-1 s^-2
            double force = gravitationalConstant * (mass1 * mass2) / (distance * distance);

            // Schreibe die Daten in die Datei
            writer.println("Mass1: " + mass1);
            writer.println("Mass2: " + mass2);
            writer.println("Distance: " + distance);
            writer.println("Gravitational Force: " + force);

            System.out.println("Test-Datei erfolgreich erstellt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}