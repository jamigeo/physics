package physics;

import java.util.Locale;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class Main {

    private static final double M1 = 5.972e24;  // Masse 1 in kg (z.B. Erde)
    private static final double M2 = 7.348e22;  // Masse 2 in kg (z.B. Mond)
    private static final double R = 3.844e8;    // Abstand in Metern

    public static void main(String[] args) {
        // Aufruf der Test-Klasse, um die test.txt zu erstellen
        runTest();

        // Berechnung der Gravitationskraft
        double gravitationsKonstante = NaturKonstanten.GRAVITATIONSKONSTANTE; // Gravitationskonstante in m^3 kg^-1 s^-2
        GravitationsGesetz gravitationsGesetz = (m1, m2, r) -> gravitationsKonstante * (m1 * m2) / (r * r);

        double kraft = berechneGravitationskraft(M1, M2, R, gravitationsGesetz);

        // Schreibe die Ergebnisse in eine Datei
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.txt"), StandardCharsets.UTF_8))) {
            writer.write(String.format(Locale.US, "Die Gravitationskraft zwischen den beiden Massen beträgt: %.2e N", kraft));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Main-Klasse ausgeführt und Ergebnis in result.txt geschrieben.");

        // Ausführen des Python-Skripts
        runPythonScript();
    }

    // Methode zur Berechnung der Gravitationskraft unter Verwendung eines Lambda-Ausdrucks
    public static double berechneGravitationskraft(double masse1, double masse2, double abstand, GravitationsGesetz gesetz) {
        return gesetz.berechneGravitationskraft(masse1, masse2, abstand);
    }

    // Methode, die die Test-Klasse über Reflexion ausführt
    public static void runTest() {
        try {
            // Verwende Reflection, um die Test-Klasse auszuführen
            Class<?> testClass = Class.forName("physics.Test");
            Method mainMethod = testClass.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode zum Ausführen des Python-Skripts
    public static void runPythonScript() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "physics/scripts/read_result.py");
            pb.redirectErrorStream(true);
            Process p = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            System.out.println("Python-Skript-Ausgabe:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = p.waitFor();
            System.out.println("Python-Skript beendet mit Code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}