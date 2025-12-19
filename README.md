API Playground – Currency Converter (Java + Swing)

Proyecto educativo para probar consumo de APIs REST en Java, usando un conversor de monedas como caso práctico.

La aplicación:
- Consume la ExchangeRate API
- Usa Swing para la interfaz gráfica
- Maneja JSON con Gson
- Implementa caché de tasas (TTL)
- Se ejecuta como aplicación de escritorio

==================================================

TECNOLOGÍAS USADAS

- Java 21 (JDK 21 LTS)
- Swing (UI de escritorio)
- Maven (gestión de dependencias)
- Gson (parseo JSON)
- ExchangeRate API (tasas de cambio)
- HTTP Client (java.net.http)

==================================================

ESTRUCTURA DEL PROYECTO

src/main/java/com/example/apiplayground/
├── api/
│   └── ExchangeRateApiClient.java
├── model/
│   ├── CurrencyItem.java
│   ├── ExchangeRatesResponse.java
│   └── SupportedCodesResponse.java
├── service/
│   └── CurrencyConverterService.java
├── ui/
│   └── AppFrame.java
└── Main.java

==================================================

FUNCIONALIDADES

- Conversión entre monedas con tasas reales
- Selección de monedas mediante ComboBox
- Nombre completo + código ISO (ej. Pesos mexicanos (MXN))
- Carga dinámica de monedas soportadas desde la API
- Caché de tasas con tiempo de vida (TTL)
- Manejo básico de errores de red

==================================================

REQUISITO: API KEY

Este proyecto requiere una API Key de ExchangeRate API.

Registro:
https://www.exchangerate-api.com/

==================================================

CONFIGURACIÓN DE LA API KEY

Variable de entorno recomendada:

EXCHANGE_RATE_API_KEY=TU_API_KEY_AQUI

En Windows (temporal):
set EXCHANGE_RATE_API_KEY=TU_API_KEY

==================================================

EJECUCIÓN DEL PROYECTO

Desde IntelliJ:
- Usar JDK 21
- Ejecutar Main.java

Desde consola (opcional):
mvn clean package
java -jar target/api-playground-1.0.0.jar

==================================================

CACHÉ DE TASAS

- Caché por moneda base
- Tiempo de vida: 10 minutos
- Reduce llamadas a la API
- Mejora rendimiento

==================================================

OBJETIVO DEL PROYECTO

- Practicar consumo de APIs REST en Java
- Entender flujos HTTP + JSON
- Separar responsabilidades (API / Service / UI)
- Aprender buenas prácticas sin frameworks pesados

==================================================

MEJORAS FUTURAS

- Indicador cache/API
- Historial de conversiones
- Persistencia de preferencias
- Empaquetado ejecutable
- Migración opcional a JavaFX

==================================================

LICENCIA

Proyecto de uso educativo y libre para experimentación personal.
