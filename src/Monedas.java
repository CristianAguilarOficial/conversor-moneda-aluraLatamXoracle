import java.util.HashMap;
import java.util.Map;

public class Monedas {
    private static final Map<String, String> MONEDAS = new HashMap<>();
    
    static {
        // América
        MONEDAS.put("ARS", "Peso (Argentina)");
        MONEDAS.put("BOB", "Boliviano (Bolivia)");
        MONEDAS.put("BRL", "Real (Brasil)");
        MONEDAS.put("CAD", "Dólar (Canadá)");
        MONEDAS.put("CLP", "Peso (Chile)");
        MONEDAS.put("COP", "Peso (Colombia)");
        MONEDAS.put("CRC", "Colón (Costa Rica)");
        MONEDAS.put("CUP", "Peso (Cuba)");
        MONEDAS.put("DOP", "Peso (República Dominicana)");
        MONEDAS.put("GTQ", "Quetzal (Guatemala)");
        MONEDAS.put("HNL", "Lempira (Honduras)");
        MONEDAS.put("JMD", "Dólar (Jamaica)");
        MONEDAS.put("MXN", "Peso (México)");
        MONEDAS.put("PAB", "Balboa (Panamá)");
        MONEDAS.put("PEN", "Sol (Perú)");
        MONEDAS.put("PYG", "Guaraní (Paraguay)");
        MONEDAS.put("UYU", "Peso (Uruguay)");
        MONEDAS.put("USD", "Dólar (Estados Unidos, Ecuador y otros)");
        MONEDAS.put("VES", "Bolívar (Venezuela)");
        // Europa
        MONEDAS.put("EUR", "Euro (Unión Europea)");
        MONEDAS.put("GBP", "Libra Esterlina (Reino Unido)");
        MONEDAS.put("CHF", "Franco (Suiza)");
        MONEDAS.put("SEK", "Corona (Suecia)");
        MONEDAS.put("NOK", "Corona (Noruega)");
        MONEDAS.put("DKK", "Corona (Dinamarca)");
        MONEDAS.put("PLN", "Zloty (Polonia)");
        MONEDAS.put("CZK", "Corona (Chequia)");
        MONEDAS.put("HUF", "Forinto (Hungría)");
        MONEDAS.put("RUB", "Rublo (Rusia)");
        MONEDAS.put("TRY", "Lira (Turquía)");
        MONEDAS.put("RON", "Leu (Rumania)");
        // Asia
        MONEDAS.put("JPY", "Yen (Japón)");
        MONEDAS.put("CNY", "Yuan Renminbi (China)");
    }
    public static String obtenerNombreMoneda(String codigoMoneda) {
        return MONEDAS.getOrDefault(codigoMoneda, "Moneda no encontrada");
    }
}
