package lab3_180311815_vladimirvidal;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private final int id;
    private final String trainMaker;
    private final int speed;
    private final List<PassengerCar> cars;

    /**
     * Constructor for Train.
     * @param id Unique identifier for the train.
     * @param trainMaker Manufacturer of the train.
     * @param speed Speed of the train (must be positive).
     * @param carList List of PassengerCar objects.
     * @throws IllegalArgumentException if any parameter is invalid or if the car list doesn't meet the requirements.
     */
    public Train(int id, String trainMaker, int speed, List<PassengerCar> carList) {
        if (id <= 0) {
            throw new IllegalArgumentException("Train ID debe ser positivo");
        }
        if (trainMaker == null || trainMaker.trim().isEmpty()) {
            throw new IllegalArgumentException("Train maker no puede ser nulo o vacio.");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed debe ser positivo");
        }
        if (carList == null || carList.size() < 2) {
            throw new IllegalArgumentException("Car list dene contener 2 o mas.");
        }

        this.id = id;
        this.trainMaker = trainMaker;
        this.speed = speed;
        this.cars = new ArrayList<>(carList);
    }
    public static boolean isValidCarConfiguration(List<PassengerCar> carList) {
        if (carList == null || carList.size() < 2) {
            return false;
        }
        if (!carList.get(0).getCarType().equals("terminal") || !carList.get(carList.size() - 1).getCarType().equals("terminal")) {
            return false;
        }
        String model = carList.get(0).getModel();
        String trainMaker = carList.get(0).getTrainMaker();
        for (int i = 1; i < carList.size() - 1; i++) {
            PassengerCar car = carList.get(i);
            if (!car.getCarType().equals("central") || !car.getModel().equals(model) || !car.getTrainMaker().equals(trainMaker)) {
                return false;
            }
        }
        return carList.get(0).getModel().equals(model) && carList.get(0).getTrainMaker().equals(trainMaker) &&
                carList.get(carList.size() - 1).getModel().equals(model) && carList.get(carList.size() - 1).getTrainMaker().equals(trainMaker);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTrainMaker() {
        return trainMaker;
    }

    public int getSpeed() {
        return speed;
    }

    public List<PassengerCar> getCars() {
        return new ArrayList<>(cars); // Return a copy to preserve encapsulation
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", trainMaker='" + trainMaker + '\'' +
                ", speed=" + speed +
                ", numberOfCars=" + cars.size() +
                '}';
    }

    /**
     * Añade un carro de pasajeros al tren en la posición especificada.
     * @param car El carro a añadir.
     * @param position La posición donde añadir el carro (0-indexado).
     * @return true si el carro se añadió con éxito, false en caso contrario.
     * @throws IllegalArgumentException si los parámetros son inválidos o la operación no es posible.
     */
    public boolean addCar(PassengerCar car, int position) {
        if (car == null) {
            throw new IllegalArgumentException("El carro no puede ser null.");
        }
        if (position < 0 || position > cars.size()) {
            throw new IllegalArgumentException("Posición inválida.");
        }
        if (!car.getTrainMaker().equals(this.trainMaker)) {
            throw new IllegalArgumentException("El fabricante del carro no coincide con el del tren.");
        }

        List<PassengerCar> tempList = new ArrayList<>(cars);
        tempList.add(position, car);

        if (isValidCarConfiguration(tempList)) {
            cars.clear();
            cars.addAll(tempList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Remueve un carro de pasajeros del tren en la posición especificada.
     * @param position La posición del carro a remover (0-indexado).
     * @return El carro removido, o null si no se pudo remover.
     * @throws IllegalArgumentException si la posición es inválida o la operación no es posible.
     */
    public PassengerCar removeCar(int position) {
        if (position < 0 || position >= cars.size()) {
            throw new IllegalArgumentException("Posición inválida.");
        }
        if (cars.size() <= 2) {
            throw new IllegalArgumentException("No se puede remover carro. El tren debe tener al menos dos carros.");
        }

        List<PassengerCar> tempList = new ArrayList<>(cars);
        PassengerCar removedCar = tempList.remove(position);

        if (isValidCarConfiguration(tempList)) {
            cars.clear();
            cars.addAll(tempList);
            return removedCar;
        } else {
            return null;
        }
    }

    /**
     * Calcula y retorna la capacidad máxima de pasajeros del tren.
     * @return La capacidad total de pasajeros como un entero.
     */
    public int fetchCapacity() {
        int totalCapacity = 0;
        for (PassengerCar car : cars) {
            totalCapacity += car.getPassengerCapacity();
        }
        return totalCapacity;
    }


    public static boolean isTrain(Train train) {
        return train != null && isValidCarConfiguration(train.getCars());
    }
}