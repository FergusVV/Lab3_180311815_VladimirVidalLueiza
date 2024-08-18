package lab3_180311815_vladimirvidal;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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