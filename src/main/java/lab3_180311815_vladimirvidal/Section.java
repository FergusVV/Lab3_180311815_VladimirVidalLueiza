package lab3_180311815_vladimirvidal;

/**
 * Representa una sección o tramo entre dos estaciones en el sistema de metro.
 */
public class Section {
    private final Station point1;
    private final Station point2;
    private final double distance;
    private final double cost;

    /**
     * Constructor de una sección.
     *
     * @param point1   La estación de inicio de la sección.
     * @param point2   La estación final de la sección.
     * @param distance La distancia entre las dos estaciones (debe ser un número positivo).
     * @param cost     El costo de recorrer esta sección (debe ser un número no negativo).
     * @throws IllegalArgumentException si alguno de los parámetros es inválido.
     */
    public Section(Station point1, Station point2, double distance, double cost) {
        if (point1 == null || point2 == null) {
            throw new IllegalArgumentException("Las estaciones no pueden ser nulas.");
        }
        if (point1.equals(point2)) {
            throw new IllegalArgumentException("Las estaciones de inicio y fin deben ser diferentes.");
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("La distancia debe ser un número positivo.");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }

        this.point1 = point1;
        this.point2 = point2;
        this.distance = distance;
        this.cost = cost;
    }

    /**
     * Obtiene la estación de inicio de la sección.
     *
     * @return La estación de inicio.
     */
    public Station getPoint1() {
        return point1;
    }

    /**
     * Obtiene la estación final de la sección.
     *
     * @return La estación final.
     */
    public Station getPoint2() {
        return point2;
    }

    /**
     * Obtiene la distancia entre las dos estaciones.
     *
     * @return La distancia entre las estaciones.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Obtiene el costo asociado a la sección.
     *
     * @return El costo de la sección.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Devuelve una representación en cadena de la sección.
     *
     * @return Una cadena que representa la sección, incluyendo las estaciones, la distancia y el costo.
     */

    @Override
    public String toString() {
        return "Section{" +
                "point1=" + point1.getName() +
                ", point2=" + point2.getName() +
                ", distance=" + distance +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Double.compare(section.distance, distance) == 0 &&
                Double.compare(section.cost, cost) == 0 &&
                point1.equals(section.point1) &&
                point2.equals(section.point2);
    }


}