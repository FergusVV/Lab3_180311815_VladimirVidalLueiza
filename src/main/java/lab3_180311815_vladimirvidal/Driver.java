package lab3_180311815_vladimirvidal;

public class Driver {
    private final int id;
    private final String name;
    private final String trainMaker;

    /**
     * Constructor para crear un conductor.
     * @param id ID único del conductor.
     * @param name Nombre completo del conductor.
     * @param trainMaker Fabricante de tren para el cual el conductor está habilitado.
     * @throws IllegalArgumentException si alguno de los parámetros es inválido.
     */
    public Driver(int id, String name, String trainMaker) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del conductor debe ser un número positivo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del conductor no puede ser nulo o vacío.");
        }
        if (trainMaker == null || trainMaker.trim().isEmpty()) {
            throw new IllegalArgumentException("El fabricante de tren no puede ser nulo o vacío.");
        }

        this.id = id;
        this.name = name;
        this.trainMaker = trainMaker;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTrainMaker() {
        return trainMaker;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainMaker='" + trainMaker + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}