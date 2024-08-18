package lab3_180311815_vladimirvidal;

import java.util.ArrayList;
import java.util.List;

public class Subway {
    private final int id;
    private final String name;
    private final List<Line> lines;
    private final List<Train> trains;
    private final List<Driver> drivers;

    public Subway(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la red de metro debe ser un número positivo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la red de metro no puede ser nulo o vacío.");
        }

        this.id = id;
        this.name = name;
        this.lines = new ArrayList<>();
        this.trains = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    public void addTrain(List<Train> trainList) {
        if (trainList == null || trainList.isEmpty()) {
            throw new IllegalArgumentException("La lista de trenes no puede ser nula o vacía.");
        }

        for (Train train : trainList) {
            if (!Train.isTrain(train)) {
                throw new IllegalArgumentException("El tren con ID " + train.getId() + " no cumple con las especificaciones.");
            }
            if (trainExists(train.getId())) {
                throw new IllegalArgumentException("Ya existe un tren con el ID " + train.getId() + " en la red de metro.");
            }
        }

        trains.addAll(trainList);
    }

    private boolean trainExists(int id) {
        return trains.stream().anyMatch(train -> train.getId() == id);
    }

    public List<Train> getTrains() {
        return new ArrayList<>(trains); // Retorna una copia de la lista para evitar modificaciones externas
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subway{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lines=" + lines.size() +
                ", trains=" + trains.size() +
                ", drivers=" + drivers.size() +
                '}';
    }
}