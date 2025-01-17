import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMoneda {

    // Variable para almacenar las tasas de cambio
    private static JsonObject conversionRates;

    // Función para obtener las tasas de cambio de la API
    public static void obtenerTasasDeCambio() {
        String apiUrl = "https://v6.exchangerate-api.com/v6/c38ac1172ee899b95f179c4c/latest/USD"; // URL de la API
        try {
            // Conexión a la API
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar la respuesta JSON
                String jsonResponse = response.toString();
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                conversionRates = jsonObject.getAsJsonObject("conversion_rates");
            } else {
                System.out.println("Error en la conexión: Código " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Función para almacenar el historial de conversiones
    public static void guardarHistorial(String historial) {
        try (FileWriter writer = new FileWriter("historial_conversiones.txt", true)) {
            writer.write(historial + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    // Función para realizar la conversión
    public static void realizarConversion() {
        Scanner scanner = new Scanner(System.in);

        // Definir las monedas permitidas
        Set<String> monedasPermitidas = Set.of("ARS","BOB","BRL","CAD","CLP","COP","CRC","CUP","DOP","GTQ","HNL","JMD","MXN","PAB","PEN","PYG","UYU","USD","VES","EUR","GBP","CHF","SEK","NOK","DKK","PLN","CZK","HUF","RUB", "TRY", "RON", "JPY", "CNY");

        // Filtrar las tasas de cambio desde la API
        Map<String, Double> tasasFiltradas = new HashMap<>();
        for (String moneda : monedasPermitidas) {
            if (conversionRates.has(moneda)) {
                tasasFiltradas.put(moneda, conversionRates.get(moneda).getAsDouble());
            }
        }

        boolean continuar = true; // Variable para controlar si se repite el proceso

        while (continuar) {
            // Mostrar las monedas disponibles solo la primera vez
            System.out.println("--------------------------------------------------");
            System.out.println("Monedas disponibles:");
            tasasFiltradas.forEach((codigo, tasa) -> {
                String nombreMoneda = Monedas.obtenerNombreMoneda(codigo);
                System.out.println(codigo + ": " + nombreMoneda);
            });

            // Pedir al usuario la moneda de origen
            System.out.println("--------------------------------------------------");
            System.out.print("Elige la moneda de origen (por ejemplo, COP): ");
            String monedaOrigen = scanner.nextLine().toUpperCase();
            System.out.println("--------------------------------------------------");

            // Pedir al usuario la moneda de destino
            System.out.print("Elige la moneda de destino (por ejemplo, USD): ");
            String monedaDestino = scanner.nextLine().toUpperCase();
            System.out.println("--------------------------------------------------");
            // Verificar que ambas monedas existen
            if (!tasasFiltradas.containsKey(monedaOrigen) || !tasasFiltradas.containsKey(monedaDestino)) {
                System.out.println("Una o ambas monedas no están disponibles.");
                return;
            }

            // Validación del monto ingresado
            double monto = 0;
            boolean montoValido = false;

            while (!montoValido) {
                try {
                    System.out.print("Ingresa el monto a convertir: ");
                    monto = scanner.nextDouble();

                    if (monto <= 0) {
                        System.out.println("El monto debe ser un valor positivo.");
                    } else {
                        montoValido = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, ingrese un valor numérico válido.");
                    scanner.next();  // Limpiar el buffer del scanner
                }
            }

            // Realizar la conversión
            double tasaOrigen = tasasFiltradas.get(monedaOrigen);
            double tasaDestino = tasasFiltradas.get(monedaDestino);

            double montoConvertido = monto * (tasaDestino / tasaOrigen);

            // Redondear el resultado a dos decimales
            BigDecimal resultadoRedondeado = new BigDecimal(montoConvertido).setScale(2, RoundingMode.HALF_UP);

            // Mostrar el resultado
            System.out.println(monto + " " + monedaOrigen + " es igual a " + resultadoRedondeado + " " + monedaDestino);

            // Guardar el historial de la conversión
            String historial = monto + " " + monedaOrigen + " es igual a " + resultadoRedondeado + " " + monedaDestino;
            guardarHistorial(historial);

            // Preguntar si el usuario desea hacer otra conversión
            System.out.print("¿Quieres realizar otra conversión? (sí/no): ");
            scanner.nextLine(); // Limpiar el buffer
            String respuesta = scanner.nextLine().toLowerCase();

            if (respuesta.equals("no")) {
                continuar = false;
                System.out.println("¡Gracias por usar el conversor!");
            }
        }
    }

    public static void main(String[] args) {
        obtenerTasasDeCambio(); // Obtener las tasas de cambio desde la API

        if (conversionRates != null) {
            realizarConversion(); // Realizar la conversión
        } else {
            System.out.println("No se pudieron obtener las tasas de cambio.");
        }
    }
}
