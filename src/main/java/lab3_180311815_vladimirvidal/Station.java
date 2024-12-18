package lab3_180311815_vladimirvidal;

/**
 * Representation de una estaciona en una red de metro.
 */
public class Station {
    private final int id;
    private final String name;
    private final char type;
    private final int stopTime;

    /**
     * Constructs a new Station.
     *
     * @param id       The unique identifier of the station.
     * @param name     The name of the station.
     * @param type     The type of the station:
     *                 'r for regular,
     *                 'm' for maintenance,
     *                 'c' for combination,
     *                 't' for terminal.
     * @param stopTime The time (in seconds) a train stops at this station.
     * @throws IllegalArgumentException if the station type is invalid or if other parameters are invalid.
     */
    public Station(int id, String name, char type, int stopTime) {
        if (id < 0) {
            throw new IllegalArgumentException("Station ID must be a positive integer.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be null or empty.");
        }
        if (type != 'r' && type != 'm' && type != 'c' && type != 't') {
            throw new IllegalArgumentException("Invalid station type. Must be 'r', 'm', 'c', or 't'.");
        }
        if (stopTime < 0) {
            throw new IllegalArgumentException("Stop time cannot be negative.");
        }

        this.id = id;
        this.name = name;
        this.type = type;
        this.stopTime = stopTime;
    }

    /**
     * Obtiene el identificador único de la estación.
     *
     * @return El identificador único de la estación.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la estación.
     *
     * @return El nombre de la estación.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el tipo de la estación.
     *
     * @return El tipo de la estación como un carácter.
     */
    public char getType() {
        return type;
    }

    /**
     * Obtiene el tiempo de parada en la estación.
     *
     * @return El tiempo de parada en minutos.
     */
    public int getStopTime() {
        return stopTime;
    }

    /**
     * Devuelve una representación en cadena de la estación.
     *
     * @return Una cadena que representa la estación, incluyendo su ID, nombre, tipo y tiempo de parada.
     */

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", stopTime=" + stopTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}