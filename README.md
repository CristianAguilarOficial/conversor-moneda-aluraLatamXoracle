# Conversor de Monedas

Este proyecto es una aplicación de consola en Java que permite realizar conversiones entre diferentes monedas utilizando tasas de cambio obtenidas de una API externa.

## Funcionalidades

1. **Obtener tasas de cambio:**
   - Conexión a la API `https://v6.exchangerate-api.com/v6/MY+KEY/latest/USD` para obtener las tasas de cambio en tiempo real.

2. **Conversión de monedas:**
   - El usuario puede seleccionar una moneda de origen, una moneda de destino y el monto a convertir.
   - Realiza el cálculo basado en las tasas de cambio obtenidas de la API.

3. **Historial de conversiones:**
   - Guarda un registro de cada conversión realizada en un archivo de texto llamado `historial_conversiones.txt`.

4. **Interfaz amigable:**
   - Presenta un menú claro para la selección de monedas y permite repetir el proceso de conversión según las necesidades del usuario.

## Configuración adicional
**Descargar la biblioteca Gson 2.11.0**
**API utilizada: https://app.exchangerate-api.com/**

