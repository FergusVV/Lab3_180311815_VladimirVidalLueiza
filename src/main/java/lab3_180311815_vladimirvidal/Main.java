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

            // Añade esto al final del método main o en un nuevo método de prueba

            System.out.println("\nPruebas de la clase Train:");

            try {
                // Crear carros de pasajeros para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloT1", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloC1", "FabricanteX", "central");
                PassengerCar centralCar2 = new PassengerCar(3, 60, "ModeloC2", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(4, 50, "ModeloT2", "FabricanteX", "terminal");

                // Prueba 1: Crear un tren válido
                List<PassengerCar> validCarList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, centralCar2, terminalCar2));
                Train validTrain = new Train(1, "FabricanteX", 100, validCarList);
                System.out.println("Tren válido creado: " + validTrain);

                // Prueba 2: Intentar crear un tren con ID negativo
                try {
                    Train invalidIdTrain = new Train(-1, "FabricanteY", 120, validCarList);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear tren con ID negativo: " + e.getMessage());
                }

                // Prueba 3: Intentar crear un tren con velocidad cero
                try {
                    Train zeroSpeedTrain = new Train(2, "FabricanteZ", 0, validCarList);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear tren con velocidad cero: " + e.getMessage());
                }

                // Prueba 4: Intentar crear un tren con configuración inválida de carros (sin terminales)
                try {
                    List<PassengerCar> invalidCarList = new ArrayList<>(Arrays.asList(centralCar1, centralCar2));
                    Train invalidConfigTrain = new Train(3, "FabricanteW", 80, invalidCarList);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al crear tren con configuración inválida de carros: " + e.getMessage());
                }

                // Prueba 5: Crear un tren con el mínimo de carros (2 terminales)
                List<PassengerCar> minCarList = new ArrayList<>(Arrays.asList(terminalCar1, terminalCar2));
                Train minTrain = new Train(4, "FabricanteV", 90, minCarList);
                System.out.println("Tren con mínimo de carros creado: " + minTrain);

                // Prueba 6: Verificar que no se puede modificar la lista de carros desde fuera
                List<PassengerCar> carList = validTrain.getCars();
                carList.add(new PassengerCar(5, 70, "ModeloExtra", "FabricanteX", "central"));
                System.out.println("Número de carros después de intentar modificar: " + validTrain.getCars().size());

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de Train: " + e.getMessage());
                e.printStackTrace();
            }


            System.out.println("\nPruebas de addCar:");

            try {
                // Crear un nuevo tren válido para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloT1", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloC1", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(3, 50, "ModeloT2", "FabricanteX", "terminal");
                List<PassengerCar> initialCarList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, terminalCar2));
                Train testTrain = new Train(1, "FabricanteX", 100, initialCarList);

                System.out.println("Tren inicial: " + testTrain);

                // Crear un nuevo carro central
                PassengerCar newCentralCar = new PassengerCar(4, 55, "ModeloC2", "FabricanteX", "central");

                // Añadir el nuevo carro en una posición válida (entre los carros centrales)
                boolean addedSuccessfully = testTrain.addCar(newCentralCar, 1);
                System.out.println("Carro añadido con éxito en posición 1: " + addedSuccessfully);
                System.out.println("Tren después de añadir carro: " + testTrain);

                // Intentar añadir un carro terminal en una posición no permitida
                PassengerCar newTerminalCar = new PassengerCar(5, 45, "ModeloT3", "FabricanteX", "terminal");
                try {
                    testTrain.addCar(newTerminalCar, 2);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir carro terminal en posición incorrecta: " + e.getMessage());
                }

                // Intentar añadir un carro de un fabricante diferente
                PassengerCar wrongMakerCar = new PassengerCar(6, 50, "ModeloW", "FabricanteY", "central");
                try {
                    testTrain.addCar(wrongMakerCar, 2);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al añadir carro de fabricante diferente: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de addCar: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\nPruebas de removeCar:");

            try {
                // Crear un nuevo tren válido para las pruebas
                PassengerCar terminalCar1 = new PassengerCar(1, 50, "ModeloT1", "FabricanteX", "terminal");
                PassengerCar centralCar1 = new PassengerCar(2, 60, "ModeloC1", "FabricanteX", "central");
                PassengerCar centralCar2 = new PassengerCar(3, 60, "ModeloC2", "FabricanteX", "central");
                PassengerCar terminalCar2 = new PassengerCar(4, 50, "ModeloT2", "FabricanteX", "terminal");
                List<PassengerCar> initialCarList = new ArrayList<>(Arrays.asList(terminalCar1, centralCar1, centralCar2, terminalCar2));
                Train testTrain = new Train(1, "FabricanteX", 100, initialCarList);

                System.out.println("Tren inicial: " + testTrain);

                // Remover un carro central
                PassengerCar removedCar = testTrain.removeCar(1);
                System.out.println("Carro removido: " + removedCar);
                System.out.println("Tren después de remover carro central: " + testTrain);

                // Intentar remover un carro terminal
                try {
                    testTrain.removeCar(0);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al remover carro terminal: " + e.getMessage());
                }

                // Intentar remover con posición inválida
                try {
                    testTrain.removeCar(5);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al remover con posición inválida: " + e.getMessage());
                }

                // Remover hasta quedar con el mínimo de carros
                testTrain.removeCar(1);
                System.out.println("Tren con mínimo de carros: " + testTrain);

                // Intentar remover cuando solo quedan dos carros
                try {
                    testTrain.removeCar(0);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error esperado al remover con solo dos carros: " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Error inesperado durante las pruebas de removeCar: " + e.getMessage());
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