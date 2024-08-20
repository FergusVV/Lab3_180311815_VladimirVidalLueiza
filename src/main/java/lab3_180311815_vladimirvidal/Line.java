package lab3_180311815_vladimirvidal;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una línea de metro en el sistema.
 */
public class Line {
    private final int id;
    private final String name;
    private final String railType;
    private final List<Section> sections;

    /**
     * Constructor para crear una nueva línea de metro.
     *
     * @param id       El identificador único de la línea.
     * @param name     El nombre de la línea.
     * @param railType El tipo de riel utilizado en la línea.
     * @throws IllegalArgumentException Si el id es negativo o si el nombre o tipo de riel es nulo o vacío.
     */

    public Line(int id, String name, String railType) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID de la línea debe ser un número positivo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la línea no puede ser nulo o vacío.");
        }
        if (railType == null || railType.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de riel no puede ser nulo o vacío.");
        }

        this.id = id;
        this.name = name;
        this.railType = railType;
        this.sections = new ArrayList<>();
    }


    /**
     * Añade una sección a la línea.
     *
     * @param section La sección a añadir.
     * @throws IllegalArgumentException Si la sección es nula.
     */

    public void addSection(Section section) {
        if (section == null) {
            throw new IllegalArgumentException("La sección no puede ser nula.");
        }

        if (sections.isEmpty()) {
            sections.add(section);
        } else {
            Section lastSection = sections.get(sections.size() - 1);
            if (!lastSection.getPoint2().equals(section.getPoint1())) {
                throw new IllegalArgumentException("La nueva sección debe conectarse con la última estación de la línea.");
            }
            sections.add(section);
        }
    }

    /**
     * Verifica si la línea es circular comparando la primera y la última estación.
     *
     * @return true si la línea es circular, false en caso contrario.
     */
    public boolean isCircular() {
        if (sections.size() < 2) {
            return false;
        }
        Station firstStation = sections.get(0).getPoint1();
        Station lastStation = sections.get(sections.size() - 1).getPoint2();
        return firstStation.equals(lastStation);
    }

    /**
     * Calcula la longitud total de la línea.
     *
     * @return La longitud total de la línea en la unidad de medida de las secciones.
     */
    public double lineLength() {
        double totalLength = 0;

        if (sections.isEmpty()) {
            return totalLength;
        }

        boolean isCircular = isCircular();
        Station pivotStation = sections.get(0).getPoint1();
        boolean startCounting = !isCircular;

        for (Section section : sections) {
            if (isCircular && section.getPoint1().equals(pivotStation)) {
                if (startCounting) {
                    // Hemos dado una vuelta completa, terminamos
                    break;
                }
                startCounting = true;
            }

            if (startCounting) {
                totalLength += section.getDistance();
            }
        }

        return totalLength;
    }

    public Station getLastStation() {
        if (sections.isEmpty()) {
            return null;
        }
        return sections.get(sections.size() - 1).getPoint2();
    }

    /**
     * Calcula la longitud del trayecto entre dos estaciones dadas.
     *
     * @param station1Name Nombre de la estación de origen.
     * @param station2Name Nombre de la estación de destino.
     * @return La longitud total del trayecto entre las estaciones dadas.
     * @throws IllegalArgumentException si alguna de las estaciones no se encuentra en la línea.
     */
    public double lineSectionLength(String station1Name, String station2Name) {
        double length = 0;
        boolean startCounting = false;
        boolean found = false;

        for (Section section : sections) {
            if (section.getPoint1().getName().equals(station1Name)) {
                startCounting = true;
            }

            if (startCounting) {
                length += section.getDistance();
            }

            if (section.getPoint2().getName().equals(station2Name)) {
                found = true;
                break;
            }

            // Si es una línea circular y hemos llegado al final, volver al principio
            if (isCircular() && section == sections.get(sections.size() - 1)) {
                section = sections.get(0);
            }
        }

        if (!found) {
            throw new IllegalArgumentException("No se pudo encontrar un trayecto entre las estaciones dadas.");
        }

        return length;
    }

    /**
     * Calcula el costo total de recorrer la línea completa.
     *
     * @return El costo total de recorrer la línea.
     */
    public double lineCost() {
        if (sections.isEmpty()) {
            return 0;
        }

        double totalCost = 0;
        Station pivotStation = sections.get(0).getPoint1(); // Establecemos la estación pivote
        boolean startCounting = true;

        for (Section section : sections) {
            if (section.getPoint1().equals(pivotStation) && !startCounting) {
                // Hemos vuelto a la estación pivote, terminamos el recorrido
                break;
            }

            if (startCounting) {
                totalCost += section.getCost();
            }

            if (section.getPoint2().equals(pivotStation)) {
                // Si es una línea circular, comenzamos a contar desde aquí
                startCounting = true;
            }
        }

        return totalCost;
    }

    /**
     * Calcula el costo de un trayecto entre una estación origen y una final.
     *
     * @param station1Name Nombre de la estación de origen.
     * @param station2Name Nombre de la estación de destino.
     * @return El costo del trayecto entre las estaciones especificadas.
     * @throws IllegalArgumentException si no se encuentra un trayecto válido entre las estaciones.
     */
    public double lineSectionCost(String station1Name, String station2Name) {
        double cost = 0;
        boolean startCounting = false;
        boolean foundPath = false;

        for (Section section : sections) {
            if (section.getPoint1().getName().equals(station1Name)) {
                startCounting = true;
            }

            if (startCounting) {
                cost += section.getCost();
            }

            if (section.getPoint2().getName().equals(station2Name)) {
                if (startCounting) {
                    foundPath = true;
                    break;
                }
            }

            // Si es una línea circular y hemos llegado al final, volvemos al inicio
            if (isCircular() && section == sections.get(sections.size() - 1)) {
                section = sections.get(0);
            }
        }

        if (!foundPath) {
            throw new IllegalArgumentException("No se pudo encontrar un trayecto entre las estaciones dadas.");
        }

        return cost;
    }

    /**
     * Añade un nuevo tramo a la línea si no existe ya una sección equivalente.
     * Dos secciones se consideran equivalentes si conectan las mismas estaciones,
     * sin importar el orden.
     *
     * @param newSection La sección a añadir.
     * @return Una nueva línea con la sección añadida, o la línea original si ya existe una sección equivalente.
     */
    public Line lineAddSection(Section newSection) {
        for (Section existingSection : sections) {
            if (SectionsEqual(existingSection, newSection)) {
                // Ya existe una sección equivalente, retornar la línea sin cambios
                return this;
            }
        }

        // La sección no existe, crear una nueva línea con la sección añadida
        List<Section> newSections = new ArrayList<>(this.sections);
        newSections.add(newSection);
        return new Line(this.id, this.name, this.railType, newSections);
    }

    /**
     * Comprueba si dos secciones son equivalentes.
     * Dos secciones son equivalentes si conectan las mismas estaciones, sin importar el orden.
     */
    private boolean SectionsEqual(Section s1, Section s2) {
        return (s1.getPoint1().getId() == s2.getPoint1().getId() && s1.getPoint2().getId() == s2.getPoint2().getId()) ||
                (s1.getPoint1().getId() == s2.getPoint2().getId() && s1.getPoint2().getId() == s2.getPoint1().getId());
    }

    // Constructor privado para crear una nueva línea con secciones específicas
    public Line(int id, String name, String railType, List<Section> sections) {
        this.id = id;
        this.name = name;
        this.railType = railType;
        this.sections = new ArrayList<>(sections);
    }

    public static boolean isLine(Line line) {
        if (line == null || line.sections == null || line.sections.isEmpty()) {
            System.out.println("La línea es nula o no tiene secciones.");
            return false;
        }

        // Variables para rastrear el ID de las estaciones
        Integer previousStationId = null;
        boolean isCircular = line.isCircular();

        // Lista para verificar si hay IDs de estaciones repetidos
        List<Integer> stationIds = new ArrayList<>();

        for (int i = 0; i < line.sections.size(); i++) {
            Section currentSection = line.sections.get(i);
            int startId = currentSection.getPoint1().getId();
            int endId = currentSection.getPoint2().getId();

            // Verificar si las estaciones están conectadas correctamente
            if (previousStationId != null && !previousStationId.equals(startId)) {
                System.out.println("Error de conexión entre secciones " + (i - 1) + " y " + i);
                return false;
            }

            // Manejo especial para líneas circulares en la última sección
            if (isCircular && i == line.sections.size() - 1) {
                if (endId != stationIds.get(0)) {
                    System.out.println("Error: La línea circular no cierra correctamente.");
                    return false;
                }
            } else {
                // Verificar IDs repetidos en estaciones para líneas no circulares
                if (stationIds.contains(startId)) {
                    System.out.println("ID repetido detectado: " + startId);
                    return false;
                }
                stationIds.add(startId);
            }

            previousStationId = endId;
        }

        // Añadir la última estación para líneas no circulares y verificar repetición
        if (!isCircular && stationIds.contains(previousStationId)) {
            System.out.println("ID repetido detectado en la última estación: " + previousStationId);
            return false;
        }

        System.out.println("La línea es válida.");
        return true;
    }





    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRailType() {
        return railType;
    }

    public List<Section> getSections() {
        return new ArrayList<>(sections); // Retorna una copia para evitar modificaciones externas
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", railType='" + railType + '\'' +
                ", sections=" + sections.size() +
                ", isCircular=" + isCircular() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return id == line.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}