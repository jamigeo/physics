package physics;

@FunctionalInterface
public interface GravitationsGesetz {
    double berechneGravitationskraft(double masse1, double masse2, double abstand);
}