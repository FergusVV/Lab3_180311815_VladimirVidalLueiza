package lab3_180311815_vladimirvidal;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        testMultipleLines();
    }

    private static void testMultipleLines() {
        try {
            System.out.println("Iniciando prueba de múltiples líneas de Metro...");

            // Crear estaciones
            Station estacionCentral = new Station(1, "Estación Central", 'r', 30);
            Station universidadDeChile = new Station(2, "Universidad de Chile", 'c', 45);
            Station santaLucia = new Station(3, "Santa Lucía", 'r', 30);
            Station universidadCatolica = new Station(4, "Universidad Católica", 'r', 30);
            Station baquedano = new Station(5, "Baquedano", 'c', 45);
            Station losLeones = new Station(6, "Los Leones", 'r', 30);
            Station tobalaba = new Station(7, "Tobalaba", 'c', 45);
            Station manquehue = new Station(8, "Manquehue", 'r', 30);
            Station losDominicos = new Station(9, "Los Dominicos", 't', 60);

            // Crear líneas
            Line lineaRoja = new Line(1, "Línea Roja", "Eléctrico");
            Line lineaVerde = new Line(2, "Línea Verde", "Eléctrico");
            Line lineaAzul = new Line(3, "Línea Azul", "Eléctrico");

            // Añadir secciones a la línea Roja
            lineaRoja.addSection(new Section(estacionCentral, universidadDeChile, 2.5, 100.0));
            lineaRoja.addSection(new Section(universidadDeChile, santaLucia, 1.8, 80.0));
            lineaRoja.addSection(new Section(santaLucia, universidadCatolica, 1.5, 70.0));
            lineaRoja.addSection(new Section(universidadCatolica, baquedano, 2.0, 90.0));

            // Añadir secciones a la línea Verde
            lineaVerde.addSection(new Section(baquedano, losLeones, 3.0, 110.0));
            lineaVerde.addSection(new Section(losLeones, tobalaba, 2.7, 100.0));
            lineaVerde.addSection(new Section(tobalaba, manquehue, 4.2, 150.0));
            lineaVerde.addSection(new Section(manquehue, losDominicos, 3.5, 130.0));

            // Añadir secciones a la línea Azul (circular)
            lineaAzul.addSection(new Section(tobalaba, baquedano, 5.0, 180.0));
            lineaAzul.addSection(new Section(baquedano, estacionCentral, 4.8, 170.0));
            lineaAzul.addSection(new Section(estacionCentral, tobalaba, 6.2, 200.0));
            // Ya no necesitamos llamar a setCircular(true)

            // Lista de todas las líneas
            List<Line> lineasMetro = new ArrayList<>();
            lineasMetro.add(lineaRoja);
            lineasMetro.add(lineaVerde);
            lineasMetro.add(lineaAzul);

            // Calcular y mostrar la longitud de cada línea
            for (Line linea : lineasMetro) {
                System.out.println("\nLínea: " + linea.getName());
                System.out.println("Longitud total: " + linea.lineLength() + " unidades");
                System.out.println("Es circular: " + (linea.isCircular() ? "Sí" : "No"));
                System.out.println("Estación inicial: " + linea.getSections().get(0).getPoint1().getName());
                System.out.println("Estación final: " + linea.getLastStation().getName());
            }

            // Calcular la longitud total de todas las líneas
            double longitudTotalRed = lineasMetro.stream().mapToDouble(Line::lineLength).sum();
            System.out.println("\nLongitud total de la red de metro: " + longitudTotalRed + " unidades");

            // Pruebas de lineSectionLength
            System.out.println("\nPruebas de lineSectionLength:");

            // Línea Roja
            System.out.println("Línea Roja:");
            System.out.println("Distancia de Estación Central a Santa Lucía: "
                    + lineaRoja.lineSectionLength("Estación Central", "Santa Lucía") + " unidades");
            System.out.println("Distancia de Universidad de Chile a Baquedano: "
                    + lineaRoja.lineSectionLength("Universidad de Chile", "Baquedano") + " unidades");

            // Línea Verde
            System.out.println("\nLínea Verde:");
            System.out.println("Distancia de Baquedano a Tobalaba: "
                    + lineaVerde.lineSectionLength("Baquedano", "Tobalaba") + " unidades");
            System.out.println("Distancia de Los Leones a Los Dominicos: "
                    + lineaVerde.lineSectionLength("Los Leones", "Los Dominicos") + " unidades");

            // Línea Azul (circular)
            System.out.println("\nLínea Azul (circular):");
            System.out.println("Distancia de Tobalaba a Estación Central: "
                    + lineaAzul.lineSectionLength("Tobalaba", "Estación Central") + " unidades");
            System.out.println("Distancia de Estación Central a Tobalaba: "
                    + lineaAzul.lineSectionLength("Estación Central", "Tobalaba") + " unidades");
            // Pruebas de lineCost
            System.out.println("\nPruebas de lineCost:");

            for (Line linea : lineasMetro) {
                System.out.println("\n" + linea.getName() + (linea.isCircular() ? " (Circular)" : "") + ":");
                System.out.println("Estación pivote: " + linea.getSections().get(0).getPoint1().getName());
                System.out.println("Secciones:");
                for (Section section : linea.getSections()) {
                    System.out.println("  " + section.getPoint1().getName() + " a " + section.getPoint2().getName() +
                            ": " + section.getCost() + " unidades monetarias");
                }
                System.out.println("Costo total: " + linea.lineCost() + " unidades monetarias");
            }
            System.out.println("\nPruebas adicionales:");

            try {
                System.out.println("Costo de Estación Central a Baquedano (Línea Roja): "
                        + lineaRoja.lineSectionCost("Estación Central", "Baquedano") + " unidades monetarias");
            } catch (IllegalArgumentException e) {
                System.out.println("Error esperado: " + e.getMessage());
            }

            try {
                System.out.println("Costo de Baquedano a Estación Central (Línea" + lineaVerde.getName()+ ": " +
                        + lineaVerde.lineSectionCost("Baquedano", "Los Dominicos") + " unidades monetarias");
            } catch (IllegalArgumentException e) {
                System.out.println("Error esperado: " + e.getMessage());
            }

            System.out.println("\nPrueba de múltiples líneas completada exitosamente.");

            System.out.println("\nPruebas de lineAddSection:");

            // Imprimir secciones originales
            System.out.println("Secciones originales de Línea Roja:");
            imprimirSecciones(lineaRoja);

            // Crear una nueva sección
            Station nuevaEstacion = new Station(10, "Nueva Estación", 'r', 30);
            Section nuevaSeccion = new Section(lineaRoja.getLastStation(), nuevaEstacion, 2.0, 85.0);

            // Añadir la nueva sección a la línea
            Line lineaRojaActualizada = lineaRoja.lineAddSection(nuevaSeccion);

            System.out.println("\nDespués de añadir nueva sección:");
            imprimirSecciones(lineaRojaActualizada);

            // Intentar añadir una sección equivalente (mismas estaciones en orden inverso)
            Section seccionEquivalente = new Section(nuevaEstacion, lineaRoja.getLastStation(), 2.1, 90.0);
            Line lineaRojaActualizadaDuplicada = lineaRojaActualizada.lineAddSection(seccionEquivalente);

            System.out.println("\nDespués de intentar añadir sección equivalente:");
            imprimirSecciones(lineaRojaActualizadaDuplicada);

            // Añadir una sección diferente
            Station otraEstacion = new Station(11, "Otra Estación", 'r', 35);
            Section seccionDiferente = new Section(nuevaEstacion, otraEstacion, 1.5, 75.0);
            Line lineaRojaActualizadaNueva = lineaRojaActualizadaDuplicada.lineAddSection(seccionDiferente);

            System.out.println("\nDespués de añadir una sección diferente:");
            imprimirSecciones(lineaRojaActualizadaNueva);
            System.out.println("\nPruebas de isLine:");

// Prueba para Línea Roja (válida, no circular)
            System.out.println("Línea Roja es válida: " + Line.isLine(lineaRoja));

// Prueba para Línea Azul (válida, circular)
            System.out.println("Línea Azul (circular) es válida: " + Line.isLine(lineaAzul));

// Crear y probar una línea inválida (secciones no conectadas)
            List<Section> seccionesNoConectadas = new ArrayList<>();
            seccionesNoConectadas.add(new Section(new Station(1, "A", 'r', 30), new Station(2, "B", 'r', 30), 1.0, 50.0));
            seccionesNoConectadas.add(new Section(new Station(3, "C", 'r', 30), new Station(4, "D", 'r', 30), 1.0, 50.0));
            Line lineaInvalida = new Line(4, "Línea Inválida", "Eléctrico", seccionesNoConectadas);
            System.out.println("Línea con secciones no conectadas es válida: " + Line.isLine(lineaInvalida));

// Crear y probar una línea inválida (IDs de estaciones repetidos)
            List<Section> seccionesIdRepetido = new ArrayList<>();
            seccionesIdRepetido.add(new Section(new Station(1, "A", 'r', 30), new Station(2, "B", 'r', 30), 1.0, 50.0));
            seccionesIdRepetido.add(new Section(new Station(2, "B", 'r', 30), new Station(1, "C", 'r', 30), 1.0, 50.0));
            Line lineaIdRepetido = new Line(5, "Línea ID Repetido", "Eléctrico", seccionesIdRepetido);
            System.out.println("\nLínea con IDs de estaciones repetidos:");
            for (int i = 0; i < lineaIdRepetido.getSections().size(); i++) {
                Section s = lineaIdRepetido.getSections().get(i);
                System.out.println("Sección " + i + ": " + s.getPoint1().getId() + " (" + s.getPoint1().getName() +
                        ") -> " + s.getPoint2().getId() + " (" + s.getPoint2().getName() + ")");
            }
            System.out.println("Línea con IDs de estaciones repetidos es válida: " + Line.isLine(lineaIdRepetido));


            System.out.println("\nPruebas de PassengerCar:");

            try {
                PassengerCar carroTerminal = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                System.out.println("Carro terminal creado: " + carroTerminal);

                PassengerCar carroCentral = new PassengerCar(2, 60, "ModeloB", "FabricanteX", "central");
                System.out.println("Carro central creado: " + carroCentral);

                // Intentar crear un carro con tipo inválido
                PassengerCar carroInvalido = new PassengerCar(3, 55, "ModeloC", "FabricanteY", "invalido");
            } catch (IllegalArgumentException e) {
                System.out.println("Error esperado al crear carro inválido: " + e.getMessage());
            }

// Intentar crear un carro con capacidad negativa
            try {
                PassengerCar carroCapacidadNegativa = new PassengerCar(4, -10, "ModeloD", "FabricanteZ", "central");
            } catch (IllegalArgumentException e) {
                System.out.println("Error esperado al crear carro con capacidad negativa: " + e.getMessage());
            }



            System.out.println("\nPruebas de la clase Train:");

            try {
                // Crear carros para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(3, 50, "ModeloA", "FabricanteX", "terminal");
                List<PassengerCar> initialCarList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, terminalCar2));

                Train testTrain = new Train(1, "FabricanteX", 100, initialCarList);
                System.out.println("Tren inicial: " + testTrain);

                // Pruebas de addCar
                System.out.println("\nPruebas de addCar:");

                PassengerCar newCentralCar = new PassengerCar(4, 55, "ModeloA", "FabricanteX", "central");
                boolean addedSuccessfully = testTrain.addCar(newCentralCar, 1);
                System.out.println("Carro añadido con éxito en posición 1: " + addedSuccessfully);
                System.out.println("Tren después de añadir carro: " + testTrain);

                PassengerCar newTerminalCar = new PassengerCar(5, 45, "ModeloA", "FabricanteX", "terminal");
                boolean addedTerminalSuccessfully = testTrain.addCar(newTerminalCar, 2);
                System.out.println("Carro terminal añadido con éxito en posición 2: " + addedTerminalSuccessfully);

                PassengerCar wrongMakerCar = new PassengerCar(6, 50, "ModeloA", "FabricanteY", "central");
                try {
                    testTrain.addCar(wrongMakerCar, 2);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir carro de fabricante diferente: " + e.getMessage());
                }

                // Pruebas de removeCar
                System.out.println("\nPruebas de removeCar:");
                System.out.println("Tren antes de remover: " + testTrain);

                PassengerCar removedCar = testTrain.removeCar(1);
                System.out.println("Carro removido: " + (removedCar != null ? removedCar : "No se pudo remover"));
                System.out.println("Tren después de remover carro central: " + testTrain);

                PassengerCar removedTerminal = testTrain.removeCar(0);
                System.out.println("Carro terminal removido: " + (removedTerminal != null ? removedTerminal : "No se pudo remover"));

                try {
                    testTrain.removeCar(5);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al remover con posición inválida: " + e.getMessage());
                }

                while (testTrain.getCars().size() > 2) {
                    PassengerCar removed = testTrain.removeCar(1);
                    System.out.println("Carro removido: " + (removed != null ? removed : "No se pudo remover"));
                }
                System.out.println("Tren con mínimo de carros: " + testTrain);

                try {
                    testTrain.removeCar(0);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al remover con solo dos carros: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Train: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nPruebas de la clase Train:");

            try {
                // Crear carros para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar centralCar2 = new PassengerCar(3, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(4, 50, "ModeloA", "FabricanteX", "terminal");

                // Tren válido
                List<PassengerCar> validCarList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, centralCar2, terminalCar2));
                Train validTrain = new Train(1, "FabricanteX", 100, validCarList);
                System.out.println("Tren válido creado: " + validTrain);
                System.out.println("Es un tren válido: " + Train.isTrain(validTrain));

                // Tren con mínimo de carros (dos terminales)
                List<PassengerCar> minCarList = new ArrayList<>(Arrays.asList(terminalCar1, terminalCar2));
                Train minTrain = new Train(2, "FabricanteX", 90, minCarList);
                System.out.println("Tren con mínimo de carros creado: " + minTrain);
                System.out.println("Es un tren válido: " + Train.isTrain(minTrain));

                // Intentar crear un tren inválido (no debería lanzar excepción, pero isTrain debería devolver false)
                List<PassengerCar> invalidCarList = new ArrayList<>(Arrays.asList(centralCar1, terminalCar1, terminalCar2));
                Train invalidTrain = new Train(3, "FabricanteX", 110, invalidCarList);
                System.out.println("Tren inválido creado: " + invalidTrain);
                System.out.println("Es un tren válido: " + Train.isTrain(invalidTrain));

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Train: " + e.getMessage());
                e.printStackTrace();
            }
            //´++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            System.out.println("\nPruebas de fetchCapacity:");
            try {
                // Crear carros para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar centralCar = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(3, 55, "ModeloA", "FabricanteX", "terminal");

                // Crear un tren con tres carros
                List<PassengerCar> carList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar, terminalCar2));
                Train train = new Train(1, "FabricanteX", 100, carList);

                // Calcular y mostrar la capacidad inicial
                System.out.println("Capacidad inicial del tren: " + train.fetchCapacity());

                // Añadir un carro y recalcular la capacidad
                PassengerCar newCar = new PassengerCar(4, 70, "ModeloA", "FabricanteX", "central");
                train.addCar(newCar, 2);
                System.out.println("Capacidad después de añadir un carro: " + train.fetchCapacity());

                // Remover un carro y recalcular la capacidad
                train.removeCar(1);
                System.out.println("Capacidad después de remover un carro: " + train.fetchCapacity());

            } catch (Exception e) {
                System.out.println("Error durante las pruebas de fetchCapacity: " + e.getMessage());
            }

            //*******************************************************

            System.out.println("\nPruebas de la clase Driver:");
            try {
                // Crear un conductor válido
                Driver driver1 = new Driver(1, "Juan Pérez", "FabricanteX");
                System.out.println("Conductor creado: " + driver1);

                // Intentar crear un conductor con ID negativo
                try {
                    new Driver(-1, "Ana García", "FabricanteY");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear conductor con ID negativo: " + e.getMessage());
                }

                // Intentar crear un conductor con nombre vacío
                try {
                    new Driver(2, "", "FabricanteZ");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear conductor con nombre vacío: " + e.getMessage());
                }

                // Intentar crear un conductor con fabricante nulo
                try {
                    new Driver(3, "Carlos Rodríguez", null);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear conductor con fabricante nulo: " + e.getMessage());
                }

                // Crear dos conductores con el mismo nombre pero diferente ID
                Driver driver2 = new Driver(2, "Juan Pérez", "FabricanteY");
                System.out.println("Segundo conductor creado: " + driver2);
                System.out.println("¿Son iguales los conductores? " + driver1.equals(driver2));

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Driver: " + e.getMessage());
                e.printStackTrace();
            }

            //----------------------------------------


            System.out.println("\nPruebas de la clase Subway:");
            try {
                // Crear una red de metro válida
                Subway subway1 = new Subway(1, "Metro de Santiago");
                System.out.println("Red de metro creada: " + subway1);

                // Intentar crear una red de metro con ID negativo
                try {
                    new Subway(-1, "Metro Inválido");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear red de metro con ID negativo: " + e.getMessage());
                }

                // Intentar crear una red de metro con nombre vacío
                try {
                    new Subway(2, "");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear red de metro con nombre vacío: " + e.getMessage());
                }

                // Crear dos redes de metro con el mismo nombre pero diferente ID
                Subway subway2 = new Subway(2, "Metro de Santiago");
                System.out.println("Segunda red de metro creada: " + subway2);
                System.out.println("¿Son iguales las redes de metro? " + subway1.equals(subway2));

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Subway: " + e.getMessage());
                e.printStackTrace();
            }

            //------------------------------

            System.out.println("\nPruebas de Subway - addTrain:");
            try {
                Subway subway = new Subway(1, "Metro de Prueba");

                // Crear trenes válidos
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(3, 50, "ModeloA", "FabricanteX", "terminal");
                List<PassengerCar> carList1 = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, terminalCar2));
                Train validTrain1 = new Train(1, "FabricanteX", 100, carList1);

                PassengerCar terminalCar3 = new PassengerCar(4, 55, "ModeloB", "FabricanteY", "terminal");
                PassengerCar centralCar2 = new PassengerCar(5, 65, "ModeloB", "FabricanteY", "central");
                PassengerCar terminalCar4 = new PassengerCar(6, 55, "ModeloB", "FabricanteY", "terminal");
                List<PassengerCar> carList2 = new ArrayList<>(Arrays.asList(terminalCar3, centralCar2, terminalCar4));
                Train validTrain2 = new Train(2, "FabricanteY", 110, carList2);

                // Añadir lista de trenes válidos
                List<Train> validTrainList = new ArrayList<>(Arrays.asList(validTrain1, validTrain2));
                subway.addTrain(validTrainList);
                System.out.println("Trenes añadidos. Número de trenes en la red: " + subway.getTrains().size());

                // Intentar añadir un tren con ID duplicado
                Train duplicateIdTrain = new Train(1, "FabricanteZ", 120, carList1);
                try {
                    subway.addTrain(Arrays.asList(duplicateIdTrain));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir tren con ID duplicado: " + e.getMessage());
                }

                // Intentar añadir un tren inválido (sin carros terminales en los extremos)
                List<PassengerCar> invalidCarList = new ArrayList<>(Arrays.asList(centralCar1, centralCar2, centralCar1));
                Train invalidTrain = new Train(3, "FabricanteZ", 120, invalidCarList);
                try {
                    subway.addTrain(Arrays.asList(invalidTrain));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir tren inválido: " + e.getMessage());
                }

                System.out.println("Número final de trenes en la red: " + subway.getTrains().size());

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Subway - addTrain: " + e.getMessage());
                e.printStackTrace();
            }

            //---------------------------------

            System.out.println("\nPruebas de Subway - addLine y addDriver:");
            try {
                Subway subway = new Subway(1, "Metro de Prueba");

                // Crear líneas válidas
                Station station1 = new Station(1, "Estación 1", 'r', 30);
                Station station2 = new Station(2, "Estación 2", 'r', 30);
                Station station3 = new Station(3, "Estación 3", 't', 45);
                Section section1 = new Section(station1, station2, 5.0, 100.0);
                Section section2 = new Section(station2, station3, 4.0, 80.0);
                List<Section> sections1 = new ArrayList<>(Arrays.asList(section1, section2));
                Line line1 = new Line(1, "Línea 1", "Eléctrico", sections1);

                Station station4 = new Station(4, "Estación 4", 'r', 30);
                Station station5 = new Station(5, "Estación 5", 't', 45);
                Section section3 = new Section(station3, station4, 3.0, 70.0);
                Section section4 = new Section(station4, station5, 6.0, 120.0);
                List<Section> sections2 = new ArrayList<>(Arrays.asList(section3, section4));
                Line line2 = new Line(2, "Línea 2", "Eléctrico", sections2);

                // Añadir líneas válidas
                List<Line> validLines = new ArrayList<>(Arrays.asList(line1, line2));
                subway.addLine(validLines);
                System.out.println("Líneas añadidas. Número de líneas en la red: " + subway.getLines().size());

                // Intentar añadir una línea con ID duplicado
                Line duplicateLine = new Line(1, "Línea Duplicada", "Eléctrico", sections1);
                try {
                    subway.addLine(Arrays.asList(duplicateLine));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir línea con ID duplicado: " + e.getMessage());
                }

                // Crear conductores válidos
                Driver driver1 = new Driver(1, "Juan Pérez", "FabricanteX");
                Driver driver2 = new Driver(2, "María García", "FabricanteY");

                // Añadir conductores válidos
                List<Driver> validDrivers = new ArrayList<>(Arrays.asList(driver1, driver2));
                subway.addDriver(validDrivers);
                System.out.println("Conductores añadidos. Número de conductores en la red: " + subway.getDrivers().size());

                // Intentar añadir un conductor con ID duplicado
                Driver duplicateDriver = new Driver(1, "Pedro López", "FabricanteZ");
                try {
                    subway.addDriver(Arrays.asList(duplicateDriver));
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir conductor con ID duplicado: " + e.getMessage());
                }

                System.out.println("Número final de líneas en la red: " + subway.getLines().size());
                System.out.println("Número final de conductores en la red: " + subway.getDrivers().size());

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Subway - addLine y addDriver: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nPruebas extendidas de Subway - toString:");
            try {
                Subway subway = new Subway(1, "Metro de Ciudad Grande");

                // Crear estaciones para múltiples líneas
                Station station1 = new Station(1, "Central", 't', 60);
                Station station2 = new Station(2, "Plaza Mayor", 'r', 30);
                Station station3 = new Station(3, "Universidad", 'c', 45);
                Station station4 = new Station(4, "Parque", 'r', 30);
                Station station5 = new Station(5, "Estadio", 'c', 45);
                Station station6 = new Station(6, "Aeropuerto", 't', 60);
                Station station7 = new Station(7, "Mantenimiento Norte", 'm', 0);

                // Crear secciones para la Línea 1 (no circular)
                Section section1 = new Section(station1, station2, 3.0, 100.0);
                Section section2 = new Section(station2, station3, 2.5, 80.0);
                Section section3 = new Section(station3, station4, 4.0, 120.0);

                // Crear secciones para la Línea 2 (circular)
                Section section4 = new Section(station3, station5, 5.0, 150.0);
                Section section5 = new Section(station5, station6, 6.0, 180.0);
                Section section6 = new Section(station6, station3, 7.0, 200.0);

                // Crear líneas
                List<Section> sectionsLine1 = new ArrayList<>(Arrays.asList(section1, section2, section3));
                Line line1 = new Line(1, "Línea Principal", "Eléctrico", sectionsLine1);

                List<Section> sectionsLine2 = new ArrayList<>(Arrays.asList(section4, section5, section6));
                Line line2 = new Line(2, "Línea Circular", "Magnético", sectionsLine2);

                // Añadir líneas al subway
                subway.addLine(Arrays.asList(line1, line2));

                // Crear y añadir trenes
                PassengerCar car1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar car2 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                PassengerCar car3 = new PassengerCar(3, 50, "ModeloA", "FabricanteX", "terminal");
                List<PassengerCar> cars1 = new ArrayList<>(Arrays.asList(car1, car2, car3));
                Train train1 = new Train(1, "FabricanteX", 100, cars1);

                PassengerCar car4 = new PassengerCar(4, 55, "ModeloB", "FabricanteY", "terminal");
                PassengerCar car5 = new PassengerCar(5, 65, "ModeloB", "FabricanteY", "central");
                PassengerCar car6 = new PassengerCar(6, 65, "ModeloB", "FabricanteY", "central");
                PassengerCar car7 = new PassengerCar(7, 55, "ModeloB", "FabricanteY", "terminal");
                List<PassengerCar> cars2 = new ArrayList<>(Arrays.asList(car4, car5, car6, car7));
                Train train2 = new Train(2, "FabricanteY", 120, cars2);

                subway.addTrain(Arrays.asList(train1, train2));

                // Crear y añadir conductores
                Driver driver1 = new Driver(1, "Juan Pérez", "FabricanteX");
                Driver driver2 = new Driver(2, "María García", "FabricanteY");
                Driver driver3 = new Driver(3, "Carlos López", "FabricanteX");
                subway.addDriver(Arrays.asList(driver1, driver2, driver3));

                // Imprimir la representación en string del subway
                System.out.println(subway.toString());

                // Pruebas adicionales
                System.out.println("\nVerificaciones adicionales:");
                String subwayString = subway.toString();
                System.out.println("¿Incluye estación de mantenimiento? " + subwayString.contains("Mantenimiento Norte"));
                System.out.println("¿Muestra la Línea Circular? " + subwayString.contains("Línea Circular"));
                System.out.println("¿Incluye todos los conductores? " +
                        (subwayString.contains("Juan Pérez") && subwayString.contains("María García") && subwayString.contains("Carlos López")));
                System.out.println("¿Muestra las capacidades de los trenes? " +
                        (subwayString.contains("Capacidad total: 160") && subwayString.contains("Capacidad total: 240")));

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas extendidas de Subway - toString: " + e.getMessage());
                e.printStackTrace();
            }

            //-----------------------------------------------

            System.out.println("\nPruebas de Subway - assignTrainToLine:");
            try {
                Subway subway = new Subway(1, "Metro de Prueba");

                // Crear una línea
                Station station1 = new Station(1, "Estación 1", 'r', 30);
                Station station2 = new Station(2, "Estación 2", 'r', 30);
                Section section = new Section(station1, station2, 5.0, 100.0);
                List<Section> sections = new ArrayList<>(Arrays.asList(section));
                Line line = new Line(1, "Línea 1", "Eléctrico", sections);

                // Crear un tren
                PassengerCar car1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                PassengerCar car2 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "terminal");
                List<PassengerCar> cars = new ArrayList<>(Arrays.asList(car1, car2));
                Train train = new Train(1, "FabricanteX", 100, cars);

                // Añadir línea y tren al subway
                subway.addLine(Arrays.asList(line));
                subway.addTrain(Arrays.asList(train));

                // Asignar tren a línea
                subway.assignTrainToLine(train, line);
                System.out.println("Tren asignado a línea");

                // Verificar asignación
                Integer assignedLineId = subway.getLineForTrain(train.getId());
                System.out.println("Línea asignada al tren: " + (assignedLineId != null ? assignedLineId : "No asignado"));

                // Imprimir representación del subway para ver las asignaciones
                System.out.println(subway.toString());

            } catch (Exception e) {
                System.out.println("Error durante las pruebas de assignTrainToLine: " + e.getMessage());
                e.printStackTrace();
            }

            //------------------------------------------

            System.out.println("\nPruebas extendidas de Subway - assignDriverToTrain:");
            try {
                Subway subway = new Subway(1, "Metro de Prueba");

                // Crear trenes (asegurándose de que cada uno tenga al menos dos carros)
                Train train1 = new Train(1, "FabricanteX", 100, Arrays.asList(
                        new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal"),
                        new PassengerCar(2, 60, "ModeloA", "FabricanteX", "terminal")
                ));
                Train train2 = new Train(2, "FabricanteY", 120, Arrays.asList(
                        new PassengerCar(3, 55, "ModeloB", "FabricanteY", "terminal"),
                        new PassengerCar(4, 65, "ModeloB", "FabricanteY", "terminal")
                ));

                // Crear conductores
                Driver driver1 = new Driver(1, "Juan Pérez", "FabricanteX");
                Driver driver2 = new Driver(2, "María García", "FabricanteY");
                Driver driver3 = new Driver(3, "Carlos López", "FabricanteX");

                // Añadir trenes y conductores al subway
                subway.addTrain(Arrays.asList(train1, train2));
                subway.addDriver(Arrays.asList(driver1, driver2, driver3));

                // Prueba 1: Asignación exitosa
                subway.assignDriverToTrain(train1, driver1, "10:30");
                System.out.println("1. Asignación exitosa: Conductor 1 asignado a Tren 1");

                // Prueba 2: Asignación a otro tren
                subway.assignDriverToTrain(train2, driver2, "11:00");
                System.out.println("2. Asignación exitosa: Conductor 2 asignado a Tren 2");

                // Prueba 3: Intento de asignar conductor a tren que no existe
                Train nonExistentTrain = new Train(3, "FabricanteZ", 90, Arrays.asList(
                        new PassengerCar(5, 50, "ModeloC", "FabricanteZ", "terminal"),
                        new PassengerCar(6, 60, "ModeloC", "FabricanteZ", "terminal")
                ));
                try {
                    subway.assignDriverToTrain(nonExistentTrain, driver3, "12:00");
                } catch (IllegalArgumentException e) {
                    System.out.println("3. Error esperado: " + e.getMessage());
                }

                // Prueba 4: Intento de asignar conductor que no existe
                Driver nonExistentDriver = new Driver(4, "Ana Martínez", "FabricanteX");
                try {
                    subway.assignDriverToTrain(train1, nonExistentDriver, "13:00");
                } catch (IllegalArgumentException e) {
                    System.out.println("4. Error esperado: " + e.getMessage());
                }

                // Prueba 5: Intento de asignar conductor con fabricante incompatible
                try {
                    subway.assignDriverToTrain(train1, driver2, "14:00");
                } catch (IllegalArgumentException e) {
                    System.out.println("5. Error esperado: " + e.getMessage());
                }

                // Prueba 6: Reasignación de conductor (sobrescritura de asignación anterior)
                subway.assignDriverToTrain(train1, driver3, "15:00");
                System.out.println("6. Reasignación exitosa: Conductor 3 asignado a Tren 1");

                // Prueba 7: Asignación con formato de hora diferente
                subway.assignDriverToTrain(train2, driver2, "9:45 AM");
                System.out.println("7. Asignación con formato de hora diferente exitosa");

                // Imprimir representación final del subway para ver todas las asignaciones
                System.out.println("\nEstado final de las asignaciones:");
                System.out.println(subway.toString());

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de assignDriverToTrain: " + e.getMessage());
                e.printStackTrace();
            }

            //---------------------------------------------
                try {
                    System.out.println("\nIniciando pruebas de whereIsTrain...");

                    Subway subway = new Subway(1, "Metro de Prueba");

                    // Crear estaciones y secciones
                    Station estacion1 = new Station(1, "Estación Central", 't', 60);
                    Station estacion2 = new Station(2, "Plaza Mayor", 'r', 30);
                    Station estacion3 = new Station(3, "Universidad", 'c', 45);
                    Station estacion4 = new Station(4, "Parque", 'r', 30);
                    Station estacion5 = new Station(5, "Terminal Norte", 't', 60);

                    Section seccion1 = new Section(estacion1, estacion2, 5.0, 100.0);
                    Section seccion2 = new Section(estacion2, estacion3, 4.0, 80.0);
                    Section seccion3 = new Section(estacion3, estacion4, 6.0, 120.0);
                    Section seccion4 = new Section(estacion4, estacion5, 5.0, 100.0);

                    List<Section> secciones = Arrays.asList(seccion1, seccion2, seccion3, seccion4);
                    Line linea1 = new Line(1, "Línea 1", "Eléctrico", secciones);

                    subway.addLine(Arrays.asList(linea1));

                    // Crear y añadir tren
                    PassengerCar carro1 = new PassengerCar(1, 50, "ModeloA", "FabricanteX", "terminal");
                    PassengerCar carro2 = new PassengerCar(2, 60, "ModeloA", "FabricanteX", "central");
                    PassengerCar carro3 = new PassengerCar(3, 50, "ModeloA", "FabricanteX", "terminal");
                    List<PassengerCar> carros = Arrays.asList(carro1, carro2, carro3);
                    Train tren1 = new Train(1, "FabricanteX", 60, carros); // Velocidad: 60 km/h

                    subway.addTrain(Arrays.asList(tren1));
                    subway.assignTrainToLine(tren1, linea1);

                    // Crear y añadir conductor
                    Driver conductor1 = new Driver(1, "Juan Pérez", "FabricanteX");
                    subway.addDriver(Arrays.asList(conductor1));

                    // Asignar conductor al tren con hora de partida
                    subway.assignDriverToTrain(tren1, conductor1, "10:00");

                    // Crear fechas para las pruebas
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 10);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date departureTime = calendar.getTime();

                    // Pruebas
                    System.out.println("Prueba 1 (10 minutos antes de partir):");
                    calendar.add(Calendar.MINUTE, -10);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPrueba 2 (1 minuto después de partir):");
                    calendar.setTime(departureTime);
                    calendar.add(Calendar.MINUTE, 1);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPrueba 3 (6 minutos después de partir):");
                    calendar.setTime(departureTime);
                    calendar.add(Calendar.MINUTE, 6);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPrueba 4 (11 minutos después de partir):");
                    calendar.setTime(departureTime);
                    calendar.add(Calendar.MINUTE, 11);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPrueba 5 (40 minutos después de partir):");
                    calendar.setTime(departureTime);
                    calendar.add(Calendar.MINUTE, 40);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPrueba 6 (60 minutos después de partir):");
                    calendar.setTime(departureTime);
                    calendar.add(Calendar.MINUTE, 60);
                    System.out.println(subway.whereIsTrain(tren1, calendar.getTime()));

                    System.out.println("\nPruebas de whereIsTrain completadas.");

                } catch (Exception e) {
                    System.out.println("Error inesperado durante las pruebas de whereIsTrain: " + e.getMessage());
                    e.printStackTrace();
                }




        } catch (Exception e) {
            System.out.println("Error inesperado durante la prueba: " + e.getMessage());
            e.printStackTrace();
        }


    }

    private static void imprimirSecciones(Line linea) {
        System.out.println("Número de secciones: " + linea.getSections().size());
        for (int i = 0; i < linea.getSections().size(); i++) {
            Section section = linea.getSections().get(i);
            System.out.println("Sección " + (i + 1) + ":");
            System.out.println("  Estación inicial: " + section.getPoint1().getName() + " (ID: " + section.getPoint1().getId() + ")");
            System.out.println("  Estación final: " + section.getPoint2().getName() + " (ID: " + section.getPoint2().getId() + ")");
            System.out.println("  Distancia: " + section.getDistance() + " unidades");
            System.out.println("  Costo: " + section.getCost() + " unidades monetarias");
        }
    }
}