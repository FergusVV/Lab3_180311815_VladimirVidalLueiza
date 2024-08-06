package lab3_180311815_vladimirvidal;

import java.util.ArrayList;
import java.util.List;

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