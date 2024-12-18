package lab3_180311815_vladimirvidal;

import java.util.*;
import java.io.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Subway subway;

    public static void main(String[] args) {
        subway = new Subway(1, "Metro de Santiago");
        System.setProperty("file.encoding", "UTF-8");
        while (true) {
            printMainMenu();
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    loadInformation();
                    break;
                case 2:
                    visualizeCurrentState();
                    break;
                case 3:
                    interactWithSystem();
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }


    private static void printMainMenu() {
        System.out.println("\n### Sistema Metro - Inicio ###");
        System.out.println("1. Cargar información del sistema de metro");
        System.out.println("2. Visualizar estado actual del sistema de metro");
        System.out.println("3. Interactuar con el sistema de metro");
        System.out.println("4. Salir del programa");
        System.out.print("Seleccione una opción: ");
    }

    private static void loadInformation() {
        while (true) {
            System.out.println("\n### Sistema Metro - Cargar información del sistema de metro ###");
            System.out.println("1. Creación de una línea de metro básica (cargar archivo lineas.txt)");
            System.out.println("2. Combinaciones entre carros (cargar archivo carros.txt)");
            System.out.println("3. Definición de trenes con distintos número de carros (cargar archivo trenes.txt)");
            System.out.println("4. Conductores asignados a una Línea (cargar archivo conductores.txt)");
            System.out.println("5. Retorno al menú de Inicio");
            System.out.print("Seleccione una opción: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    loadLines();
                    break;
                case 2:
                    List<PassengerCar> loadedCars = loadPassengerCars();
                    System.out.println("Se cargaron " + loadedCars.size() + " carros de pasajeros.");
                    break;
                case 3:
                    loadTrains();
                    break;
                case 4:
                    loadDrivers();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    private static void loadLines() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab3_180311815_vladimirvidal/lineas.txt"));
            String line = reader.readLine();
            int totalLines = Integer.parseInt(line.trim());  // Lee el número total de líneas

            for (int i = 0; i < totalLines; i++) {
                List<Station> stations = new ArrayList<>();
                List<Section> sections = new ArrayList<>();
                int lineId = -1;
                String lineName = "";
                String railyName = "";

                while (!(line = reader.readLine()).equals("<LINEA>")) {
                    // Salta líneas en blanco o no relevantes
                }

                // Lee estaciones
                while (!(line = reader.readLine()).equals("Secciones")) {
                    if (line.equals("Estaciones")) continue;
                    String[] parts = line.split(";");
                    if (parts.length == 4) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        char type = parts[2].charAt(0);
                        int stopTime = Integer.parseInt(parts[3]);
                        stations.add(new Station(id, name, type, stopTime));
                    }
                }

                // Lee secciones
                while (!(line = reader.readLine()).equals("Linea")) {
                    String[] parts = line.split(";");
                    if (parts.length == 4) {
                        int stationId1 = Integer.parseInt(parts[0]);
                        int stationId2 = Integer.parseInt(parts[1]);
                        double distance = Double.parseDouble(parts[2]);
                        double cost = Double.parseDouble(parts[3]);
                        Station station1 = findStationById(stations, stationId1);
                        Station station2 = findStationById(stations, stationId2);
                        if (station1 != null && station2 != null) {
                            sections.add(new Section(station1, station2, distance, cost));
                        }
                    }
                }

                // Lee información de la línea
                line = reader.readLine();
                String[] lineParts = line.split(";");
                if (lineParts.length == 3) {
                    lineId = Integer.parseInt(lineParts[0]);
                    lineName = lineParts[1];
                    railyName = lineParts[2];
                }

                // Crea y añade la línea al subway
                if (lineId != -1 && !lineName.isEmpty()&& !railyName.isEmpty()) {
                    Line newLine = new Line(lineId, lineName, railyName);
                    for (Section section : sections) {
                        newLine.addSection(section);
                    }
                    subway.addLine(Arrays.asList(newLine));
                    System.out.println("Línea cargada con éxito: " + lineName);
                } else {
                    System.out.println("No se pudo crear la línea debido a datos incompletos.");
                }

                while (!(line = reader.readLine()).equals("</LINEA>")) {
                    // Lee hasta el final de la definición de la línea
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de líneas: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al procesar los datos de las líneas: " + e.getMessage());
        }
    }



    private static Station findStationById(List<Station> stations, int id) {
        for (Station station : stations) {
            if (station.getId() == id) {
                return station;
            }
        }
        return null;
    }

    private static List<PassengerCar> loadPassengerCars() {
        List<PassengerCar> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab3_180311815_vladimirvidal/carros.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    int capacity = Integer.parseInt(parts[1]);
                    String model = parts[2];
                    String maker = parts[3];
                    String type = parts[4];
                    cars.add(new PassengerCar(id, capacity, model, maker, type));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de carros: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de los datos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear un carro de pasajeros: " + e.getMessage());
        }
        return cars;
    }

    private static void loadTrains() {
        List<PassengerCar> allCars = loadPassengerCars();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab3_180311815_vladimirvidal/trenes.txt"))) {
            String line;
            List<Train> trains = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String maker = parts[1];
                    int speed = Integer.parseInt(parts[2]);
                    int stationStaytime = Integer.parseInt(parts[3]);

                    List<PassengerCar> trainCars = new ArrayList<>();
                    String[] carIds = parts[4].split(",");
                    for (String carId : carIds) {
                        int cId = Integer.parseInt(carId);
                        PassengerCar car = allCars.stream()
                                .filter(c -> c.getId() == cId)
                                .findFirst()
                                .orElse(null);
                        if (car != null) {
                            trainCars.add(car);
                        }
                    }

                    try {
                        Train train = new Train(id, maker, speed, stationStaytime, trainCars);
                        trains.add(train);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error al crear el tren: " + e.getMessage());
                    }
                } else {
                    System.out.println("Formato de línea inválido: " + line);
                }
            }
            subway.addTrain(trains);
            System.out.println("Se cargaron " + trains.size() + " trenes.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de trenes: " + e.getMessage());
        }
    }

    private static void loadDrivers() {
        try {
            List<Driver> drivers = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab3_180311815_vladimirvidal/conductores.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 3) {
                    System.out.println("Formato de conductor inválido: " + line);
                    continue;
                }
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String trainMaker = parts[2].trim();

                drivers.add(new Driver(id, name, trainMaker));
            }
            reader.close();
            subway.addDriver(drivers);
            System.out.println("Conductores cargados con éxito.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de conductores: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error al procesar los datos de los conductores: " + e.getMessage());
        }
    }

    private static void visualizeCurrentState() {
        System.out.println("\n### Sistema Metro - Visualización del estado actual del sistema de metros ###");
        System.out.println(subway.toString());
    }

    private static void interactWithSystem() {
        while (true) {
            System.out.println("\n### Sistema Metro - Interactuar con el sistema de metros ###");
            System.out.println("1. Obtener el largo total de una línea");
            System.out.println("2. Determinar el trayecto entre una estación origen y final");
            System.out.println("3. Determinar el costo total de recorrer una línea");
            System.out.println("4. Determinar el costo de un trayecto entre estación origen y final");
            System.out.println("5. Verificar si una línea cumple con las restricciones especificadas");
            System.out.println("6. Añadir un carro de pasajeros a un tren");
            System.out.println("7. Remover un carro de pasajeros de un tren");
            System.out.println("8. Verificar si un tren cumple con las especificaciones");
            System.out.println("9. Obtener la capacidad máxima de pasajeros de un tren");
            System.out.println("10. Determinar la ubicación de un tren a partir de una hora indicada");
            System.out.println("11. Armar el recorrido del tren a partir de una hora especificada");
            System.out.println("12. Asignar un tren a una línea");
            System.out.println("13. Asignar un conductor a un tren");
            System.out.println("14. Listar todas las líneas");
            System.out.println("15. Listar todos los trenes");
            System.out.println("16. Listar todos los conductores");
            System.out.println("17. Retorno al menú de Inicio");
            System.out.print("Seleccione una opción: ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    getLineLengthInteraction();
                    break;
                case 2:
                    getLineSectionLengthInteraction();
                    break;
                case 3:
                    getLineCostInteraction();
                    break;
                case 4:
                    getLineSectionCostInteraction();
                    break;
                case 5:
                    isLineInteraction();
                    break;
                case 6:
                    addCarToTrainInteraction();
                    break;
                case 7:
                    removeCarFromTrainInteraction();
                    break;
                case 8:
                    isTrainInteraction();
                    break;
                case 9:
                    getTrainCapacityInteraction();
                    break;
                case 10:
                    whereIsTrainInteraction();
                    break;
                case 11:
                    trainPathInteraction();
                    break;
                case 12:
                    assignTrainToLineInteraction();
                    break;
                case 13:
                    assignDriverToTrainInteraction();
                    break;
                case 14:
                    listAllLines();
                    break;
                case 15:
                    listAllTrains();
                    break;
                case 16:
                    listAllDrivers();
                    break;
                case 17:
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }

    // Implementaciones de las interacciones específicas

    private static void getLineLengthInteraction() {
        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);
        if (line != null) {
            System.out.println("Longitud total de la línea: " + line.lineLength() + " unidades");
        } else {
            System.out.println("Línea no encontrada.");
        }
    }

    private static void getLineSectionLengthInteraction() {
        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);
        Scanner scanner = new Scanner(System.in);
        if (line != null) {
            System.out.print("Ingrese el nombre de la estación de origen: ");
            String origin = scanner.nextLine();
            System.out.print("Ingrese el nombre de la estación de destino: ");
            String destination = scanner.nextLine();
            try {
                double length = line.lineSectionLength(origin, destination);
                System.out.println("Longitud del trayecto: " + length + " unidades");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Línea no encontrada.");
        }
    }

    private static void getLineCostInteraction() {
        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);
        if (line != null) {
            System.out.println("Costo total de recorrer la línea: " + line.lineCost() + " unidades monetarias");
        } else {
            System.out.println("Línea no encontrada.");
        }
    }

    private static void getLineSectionCostInteraction() {
        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);
        Scanner scanner = new Scanner(System.in);
        if (line != null) {
            System.out.print("Ingrese el nombre de la estación de origen: ");
            String origin = scanner.nextLine();
            System.out.print("Ingrese el nombre de la estación de destino: ");
            String destination = scanner.nextLine();
            try {
                double cost = line.lineSectionCost(origin, destination);
                System.out.println("Costo del trayecto: " + cost + " unidades monetarias");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Línea no encontrada.");
        }
    }

    private static void isLineInteraction() {
        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);
        if (line != null) {
            boolean isValid = Line.isLine(line);
            System.out.println("¿La línea es válida? " + (isValid ? "Sí" : "No"));
        } else {
            System.out.println("Línea no encontrada.");
        }
    }

    private static void addCarToTrainInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            // Aquí deberías implementar la lógica para crear un nuevo PassengerCar
            // y añadirlo al tren en una posición específica
            System.out.println("Funcionalidad no implementada completamente.");
        } else {
            System.out.println("Tren no encontrado.");
        }
    }

    private static void removeCarFromTrainInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            System.out.print("Ingrese la posición del carro a remover: ");
            int position = getIntInput();
            try {
                PassengerCar removedCar = train.removeCar(position);
                if (removedCar != null) {
                    System.out.println("Carro removido: " + removedCar);
                } else {
                    System.out.println("No se pudo remover el carro.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Tren no encontrado.");
        }
    }

    private static void whereIsTrainInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            System.out.print("Ingrese la hora (formato HH:mm): ");
            String timeString = scanner.next();
            try {
                Date time = parseTime(timeString);
                String location = subway.whereIsTrain(train, time);
                System.out.println(location);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Tren no encontrado.");
        }
    }



    private static void trainPathInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            System.out.print("Ingrese la hora (formato HH:mm): ");
            String timeString = scanner.next();
            try {
                Date time = parseTime(timeString);
                List<Station> path = subway.trainPath(train, time);
                System.out.println("Recorrido del tren:");
                for (Station station : path) {
                    System.out.println("- " + station.getName());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Tren no encontrado.");
        }
    }

    private static void isTrainInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            boolean isValid = Train.isTrain(train);
            System.out.println("¿El tren es válido? " + (isValid ? "Sí" : "No"));
        } else {
            System.out.println("Tren no encontrado.");
        }
    }

    private static void getTrainCapacityInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);
        if (train != null) {
            int capacity = train.fetchCapacity();
            System.out.println("La capacidad total del tren es: " + capacity + " pasajeros");
        } else {
            System.out.println("Tren no encontrado.");
        }
    }

    private static void assignTrainToLineInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);

        System.out.print("Ingrese el ID de la línea: ");
        int lineId = getIntInput();
        Line line = findLineById(lineId);

        if (train != null && line != null) {
            try {
                subway.assignTrainToLine(train, line);
                System.out.println("Tren asignado a la línea exitosamente.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error al asignar el tren: " + e.getMessage());
            }
        } else {
            System.out.println("Tren o línea no encontrados.");
        }
    }

    private static void assignDriverToTrainInteraction() {
        System.out.print("Ingrese el ID del tren: ");
        int trainId = getIntInput();
        Train train = findTrainById(trainId);

        System.out.print("Ingrese el ID del conductor: ");
        int driverId = getIntInput();
        Driver driver = findDriverById(driverId);

        if (train != null && driver != null) {
            System.out.print("Ingrese la hora de partida (formato HH:mm): ");
            String departureTime = scanner.next();
            try {
                subway.assignDriverToTrain(train, driver, departureTime);
                System.out.println("Conductor asignado al tren exitosamente.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error al asignar el conductor: " + e.getMessage());
            }
        } else {
            System.out.println("Tren o conductor no encontrados.");
        }
    }

    private static void listAllLines() {
        List<Line> lines = subway.getLines();
        if (lines.isEmpty()) {
            System.out.println("No hay líneas registradas.");
        } else {
            System.out.println("Líneas registradas:");
            for (Line line : lines) {
                System.out.println(line);
            }
        }
    }

    private static void listAllTrains() {
        List<Train> trains = subway.getTrains();
        if (trains.isEmpty()) {
            System.out.println("No hay trenes registrados.");
        } else {
            System.out.println("Trenes registrados:");
            for (Train train : trains) {
                System.out.println(train);
            }
        }
    }

    private static void listAllDrivers() {
        List<Driver> drivers = subway.getDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No hay conductores registrados.");
        } else {
            System.out.println("Conductores registrados:");
            for (Driver driver : drivers) {
                System.out.println(driver);
            }
        }
    }

    private static Driver findDriverById(int id) {
        for (Driver driver : subway.getDrivers()) {
            if (driver.getId() == id) {
                return driver;
            }
        }
        return null;
    }
    private static Date parseTime(String timeString) {
        String[] parts = timeString.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm");
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Hora inválida");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // Métodos auxiliares
    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static Line findLineById(int id) {
        for (Line line : subway.getLines()) {
            if (line.getId() == id) {
                return line;
            }
        }
        return null;
    }

    private static Train findTrainById(int id) {
        for (Train train : subway.getTrains()) {
            if (train.getId() == id) {
                return train;
            }
        }
        return null;
    }

}