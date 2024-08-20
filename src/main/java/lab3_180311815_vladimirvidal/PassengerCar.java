package lab3_180311815_vladimirvidal;

public class PassengerCar {
    private final int id;
    private final int passengerCapacity;
    private final String model;
    private final String trainMaker;
    private final String carType;

    /**
     * Constructor para PassengerCar.
     *
     * @param id                El identificador único del carro.
     * @param passengerCapacity La capacidad de pasajeros del carro.
     * @param model             El modelo del carro.
     * @param trainMaker        El fabricante del tren.
     * @param carType           El tipo de carro ("terminal" o "central").
     * @throws IllegalArgumentException si alguno de los parámetros es inválido.
     */
    public PassengerCar(int id, int passengerCapacity, String model, String trainMaker, String carType) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del carro debe ser un número positivo.");
        }
        if (passengerCapacity <= 0) {
            throw new IllegalArgumentException("La capacidad de pasajeros debe ser un número positivo.");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede ser nulo o vacío.");
        }
        if (trainMaker == null || trainMaker.trim().isEmpty()) {
            throw new IllegalArgumentException("El fabricante del tren no puede ser nulo o vacío.");
        }
        if (!"terminal".equals(carType) && !"central".equals(carType)) {
            throw new IllegalArgumentException("El tipo de carro debe ser 'terminal' o 'central'.");
        }

        this.id = id;
        this.passengerCapacity = passengerCapacity;
        this.model = model;
        this.trainMaker = trainMaker;
        this.carType = carType;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public String getModel() {
        return model;
    }

    public String getTrainMaker() {
        return trainMaker;
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public String toString() {
        return "PassengerCar{" +
                "id=" + id +
                ", passengerCapacity=" + passengerCapacity +
                ", model='" + model + '\'' +
                ", trainMaker='" + trainMaker + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerCar that = (PassengerCar) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}