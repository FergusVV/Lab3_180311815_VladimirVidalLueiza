package lab3_180311815_vladimirvidal;

import java.util.*;

public class Subway {
    private final int id;
    private final String name;
    private final List<Line> lines;
    private final List<Train> trains;
    private final List<Driver> drivers;
    private final Map<Integer, Integer> trainToLineAssignments;
    private final Map<Integer, DriverAssignment> driverAssignments;

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
        this.trainToLineAssignments = new HashMap<>();
        this.driverAssignments = new HashMap<>();
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

    public void addLine(List<Line> lineList) {
        if (lineList == null || lineList.isEmpty()) {
            throw new IllegalArgumentException("La lista de líneas no puede ser nula o vacía.");
        }

        for (Line line : lineList) {
            if (!Line.isLine(line)) {
                throw new IllegalArgumentException("La línea con ID " + line.getId() + " no es válida.");
            }
            if (lineExists(line.getId())) {
                throw new IllegalArgumentException("Ya existe una línea con el ID " + line.getId() + " en la red de metro.");
            }
        }

        lines.addAll(lineList);
    }

    private boolean lineExists(int id) {
        return lines.stream().anyMatch(line -> line.getId() == id);
    }

    public void addDriver(List<Driver> driverList) {
        if (driverList == null || driverList.isEmpty()) {
            throw new IllegalArgumentException("La lista de conductores no puede ser nula o vacía.");
        }

        for (Driver driver : driverList) {
            if (driverExists(driver.getId())) {
                throw new IllegalArgumentException("Ya existe un conductor con el ID " + driver.getId() + " en la red de metro.");
            }
        }

        drivers.addAll(driverList);
    }

    public void assignTrainToLine(Train train, Line line) {
        if (!trains.contains(train) || !lines.contains(line)) {
            throw new IllegalArgumentException("El tren o la línea no existen en esta red de metro.");
        }

        trainToLineAssignments.put(train.getId(), line.getId());
    }

    public Integer getLineForTrain(int trainId) {
        return trainToLineAssignments.get(trainId);
    }

    public void assignDriverToTrain(Train train, Driver driver, String departureTime) {
        if (!trains.contains(train)) {
            throw new IllegalArgumentException("El tren no existe en esta red de metro.");
        }
        if (!drivers.contains(driver)) {
            throw new IllegalArgumentException("El conductor no existe en esta red de metro.");
        }
        if (!train.getTrainMaker().equals(driver.getTrainMaker())) {
            throw new IllegalArgumentException("El fabricante del tren y del conductor no coinciden.");
        }

        driverAssignments.put(train.getId(), new DriverAssignment(driver, departureTime));
    }


    public String whereIsTrain(Object trainIdentifier, Date time) {
        Train train = getTrainById(trainIdentifier);

        if (train == null) {
            return "Tren no encontrado.";
        }

        Integer lineId = trainToLineAssignments.get(train.getId());
        if (lineId == null) {
            return "El tren no está asignado a ninguna línea.";
        }

        Line line = getLineById(lineId);
        if (line == null) {
            return "Línea no encontrada.";
        }

        DriverAssignment driverAssignment = driverAssignments.get(train.getId());
        if (driverAssignment == null) {
            return "El tren no tiene un conductor asignado.";
        }

        long departureTimeMillis = parseTime(driverAssignment.departureTime);
        long currentTimeMillis = time.getTime() % (24 * 60 * 60 * 1000);

        if (currentTimeMillis < departureTimeMillis) {
            currentTimeMillis += 24 * 60 * 60 * 1000;
        }

        System.out.println("Hora de partida (ms): " + departureTimeMillis);
        System.out.println("Hora actual (ms): " + currentTimeMillis);

        if (currentTimeMillis < departureTimeMillis) {
            return "El tren aún no ha partido de la estación inicial: " + line.getSections().get(0).getPoint1().getName();
        }

        long elapsedMinutes = (currentTimeMillis - departureTimeMillis) / (60 * 1000);
        System.out.println("Minutos transcurridos: " + elapsedMinutes);

        long totalTripTime = calculateTotalTripTime(line, train);
        System.out.println("Tiempo total del recorrido: " + totalTripTime + " minutos");

        if (totalTripTime == 0) {
            return "Error: No se pudo calcular el tiempo total del recorrido.";
        }

        long cyclesCompleted = elapsedMinutes / totalTripTime;
        long remainingMinutes = elapsedMinutes % totalTripTime;

        System.out.println("Ciclos completados: " + cyclesCompleted);
        System.out.println("Minutos restantes en el ciclo actual: " + remainingMinutes);

        long accumulatedMinutes = 0;
        Station nextStation = null;
        long timeToNextStation = 0;

        for (int i = 0; i < line.getSections().size(); i++) {
            Section section = line.getSections().get(i);
            long travelTime = calculateTravelTime(section, train);
            System.out.println("Tiempo de viaje para la sección: " + travelTime + " minutos");

            if (accumulatedMinutes + travelTime > remainingMinutes) {
                // El tren está en esta sección
                double progress = (double)(remainingMinutes - accumulatedMinutes) / travelTime;
                timeToNextStation = (long) ((1 - progress) * travelTime);
                nextStation = section.getPoint2();
                System.out.println("Tren en sección. Progreso: " + (progress * 100) + "%");
                return String.format("El tren está entre %s y %s (%.1f%% del trayecto) en la línea %s. " +
                                "Ha completado %d ciclos. " +
                                "Llegará a %s en aproximadamente %d minutos.",
                        section.getPoint1().getName(), section.getPoint2().getName(),
                        progress * 100, line.getName(), cyclesCompleted,
                        nextStation.getName(), timeToNextStation);
            }

            accumulatedMinutes += travelTime;
            System.out.println("Tiempo acumulado: " + accumulatedMinutes + " minutos");

            // Añadir tiempo de parada en la estación
            long stopTime = section.getPoint2().getStopTime() / 60; // convertir segundos a minutos
            if (accumulatedMinutes + stopTime > remainingMinutes) {
                // El tren está en esta estación
                System.out.println("Tren en estación: " + section.getPoint2().getName());
                nextStation = (i < line.getSections().size() - 1) ? line.getSections().get(i + 1).getPoint2() : line.getSections().get(0).getPoint1();
                timeToNextStation = calculateTravelTime(line.getSections().get((i + 1) % line.getSections().size()), train);

                // Aquí es donde implementamos la mejora
                if (remainingMinutes - accumulatedMinutes == 0) {
                    return String.format("El tren está en la estación %s de la línea %s. " +
                                    "Ha completado %d ciclos. " +
                                    "Está a punto de partir hacia %s.",
                            section.getPoint2().getName(), line.getName(), cyclesCompleted,
                            nextStation.getName());
                } else {
                    return String.format("El tren está en la estación %s de la línea %s. " +
                                    "Ha completado %d ciclos. " +
                                    "Partirá hacia %s en aproximadamente %d minutos.",
                            section.getPoint2().getName(), line.getName(), cyclesCompleted,
                            nextStation.getName(), remainingMinutes - accumulatedMinutes);
                }
            }

            accumulatedMinutes += stopTime;
            System.out.println("Tiempo acumulado después de la parada: " + accumulatedMinutes + " minutos");
        }

        // Si hemos llegado aquí, el tren está en la estación final
        nextStation = line.getSections().get(0).getPoint1();
        timeToNextStation = calculateTravelTime(line.getSections().get(0), train);
        return String.format("El tren está en la estación final %s de la línea %s. " +
                        "Ha completado %d ciclos. " +
                        "Iniciará un nuevo recorrido hacia %s en aproximadamente %d minutos.",
                line.getSections().get(line.getSections().size() - 1).getPoint2().getName(),
                line.getName(), cyclesCompleted,
                nextStation.getName(), totalTripTime - remainingMinutes);
    }

    private long calculateTotalTripTime(Line line, Train train) {
        long totalTime = 0;
        for (Section section : line.getSections()) {
            totalTime += calculateTravelTime(section, train);
            totalTime += section.getPoint2().getStopTime() / 60; // Añadir tiempo de parada en minutos
        }
        return totalTime;
    }

    private long calculateTravelTime(Section section, Train train) {
        // Calcula el tiempo de viaje en minutos basado en la distancia de la sección y la velocidad del tren
        return (long) Math.ceil((section.getDistance() / train.getSpeed()) * 60);
    }

    private long parseTime(String time) {
        String[] parts = time.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de tiempo inválido. Use HH:mm");
        }
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return ((hours * 60L + minutes) * 60 * 1000) % (24 * 60 * 60 * 1000); // Convertir a milisegundos desde la medianoche
    }




    private Train getTrainById(Object trainIdentifier) {
        if (trainIdentifier instanceof Train) {
            return (Train) trainIdentifier;
        } else if (trainIdentifier instanceof Integer) {
            int trainId = (Integer) trainIdentifier;
            for (Train train : trains) {
                if (train.getId() == trainId) {
                    return train;
                }
            }
        }
        return null;
    }

    private Line getLineById(int lineId) {
        for (Line line : lines) {
            if (line.getId() == lineId) {
                return line;
            }
        }
        return null;
    }


    private static class DriverAssignment {
        Driver driver;
        String departureTime;

        DriverAssignment(Driver driver, String departureTime) {
            this.driver = driver;
            this.departureTime = departureTime;
        }

        @Override
        public String toString() {
            return "Conductor: " + driver.getName() + ", Hora de partida: " + departureTime;
        }
    }

    private boolean driverExists(int id) {
        return drivers.stream().anyMatch(driver -> driver.getId() == id);
    }

    public List<Line> getLines() {
        return new ArrayList<>(lines);
    }

    public List<Driver> getDrivers() {
        return new ArrayList<>(drivers);
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
        StringBuilder sb = new StringBuilder();
        sb.append("Red de Metro: ").append(name).append(" (ID: ").append(id).append(")\n\n");

        sb.append("LINEAS:\n");
        for (Line line : lines) {
            sb.append("  Línea: ").append(line.getName()).append(" (ID: ").append(line.getId()).append(")\n");
            sb.append("  Tipo de riel: ").append(line.getRailType()).append("\n");
            sb.append("  Estaciones y Secciones:\n");
            List<Section> sections = line.getSections();
            for (int i = 0; i < sections.size(); i++) {
                Section section = sections.get(i);
                Station station = section.getPoint1();
                sb.append("    Estación: ").append(station.getName())
                        .append(" (ID: ").append(station.getId()).append(")\n");
                sb.append("      Tipo: ").append(getStationType(station.getType()))
                        .append(", Tiempo de parada: ").append(station.getStopTime()).append(" segundos\n");

                sb.append("    Sección ").append(i + 1).append(":\n");
                sb.append("      Distancia: ").append(section.getDistance()).append(" unidades\n");
                sb.append("      Costo: ").append(section.getCost()).append(" unidades monetarias\n");
            }
            // Añadir la última estación
            Station lastStation = sections.get(sections.size() - 1).getPoint2();
            sb.append("    Estación: ").append(lastStation.getName())
                    .append(" (ID: ").append(lastStation.getId()).append(")\n");
            sb.append("      Tipo: ").append(getStationType(lastStation.getType()))
                    .append(", Tiempo de parada: ").append(lastStation.getStopTime()).append(" segundos\n");
            sb.append("\n");
        }

        sb.append("TRENES:\n");
        for (Train train : trains) {
            sb.append("  Tren ID: ").append(train.getId()).append("\n");
            sb.append("    Fabricante: ").append(train.getTrainMaker()).append("\n");
            sb.append("    Velocidad: ").append(train.getSpeed()).append("\n");
            sb.append("    Número de carros: ").append(train.getCars().size()).append("\n");
            sb.append("    Capacidad total: ").append(train.fetchCapacity()).append(" pasajeros\n\n");
        }

        sb.append("CONDUCTORES:\n");
        for (Driver driver : drivers) {
            sb.append("  Conductor ID: ").append(driver.getId()).append("\n");
            sb.append("    Nombre: ").append(driver.getName()).append("\n");
            sb.append("    Fabricante habilitado: ").append(driver.getTrainMaker()).append("\n\n");
        }

        sb.append("\nASIGNACIONES DE TRENES A LINEAS:\n");
        for (Map.Entry<Integer, Integer> entry : trainToLineAssignments.entrySet()) {
            sb.append("  Tren ID: ").append(entry.getKey())
                    .append(" asignado a Línea ID: ").append(entry.getValue()).append("\n");
        }

        sb.append("\nASIGNACIONES DE CONDUCTORES A TRENES:\n");
        for (Map.Entry<Integer, DriverAssignment> entry : driverAssignments.entrySet()) {
            sb.append("  Tren ID: ").append(entry.getKey())
                    .append(", ").append(entry.getValue()).append("\n");
        }

        return sb.toString();
    }

    private String getStationType(char type) {
        switch (type) {
            case 'r': return "Regular";
            case 'm': return "Mantenimiento";
            case 'c': return "Combinación";
            case 't': return "Terminal";
            default: return "Desconocido";
        }
    }
}