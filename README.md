# API Playground – Conversor de Moneda (Java + Swing)

Proyecto educativo que demuestra consumo de APIs REST en Java usando un conversor de monedas como caso práctico. La aplicación es una pequeña aplicación de escritorio con UI basada en Swing que obtiene tasas de cambio desde ExchangeRate API, las almacena en caché y permite convertir valores entre monedas.

---

## Contenido

- [Descripción](#descripción)  
- [Características](#características)  
- [Tecnologías](#tecnologías)  
- [Requisitos](#requisitos)  
- [Instalación y ejecución](#instalación-y-ejecución)  
- [Configuración (API Key)](#configuración-api-key)  
- [Caché de tasas](#caché-de-tasas)  
- [Manejo de errores y recomendaciones](#manejo-de-errores-y-recomendaciones)  
- [Estructura del proyecto](#estructura-del-proyecto)  
- [Mejoras futuras](#mejoras-futuras)  
- [Contribuciones](#contribuciones)  
- [Licencia](#licencia)

---

## Descripción

Esta aplicación sirve como laboratorio para practicar:
- Peticiones HTTP a servicios REST.
- Parsing de JSON con Gson.
- Separación de responsabilidades (cliente API — servicio — UI).
- Uso de Swing para interfaces de escritorio en Java moderno (JDK 21).

Ideal para estudiantes y desarrolladores que quieren un ejemplo sencillo y extensible de integración con APIs externas sin depender de frameworks pesados.

---

## Características

- Conversión entre monedas usando tasas reales.
- Carga dinámica de monedas soportadas desde la API.
- Interfaz gráfica ligera con JComboBox para seleccionar monedas.
- Muestra nombre completo + código ISO (ej. Pesos mexicanos (MXN)).
- Caché de tasas por moneda base (TTL configurable).
- Manejo básico de errores de red y respuesta.

---

## Tecnologías

- Java 21 (JDK 21 LTS)  
- Swing (interfaz de usuario)  
- Maven (gestión de dependencias y build)  
- Gson (parseo JSON)  
- java.net.http.HttpClient (cliente HTTP)  
- ExchangeRate API (servicio de tasas)

---

## Requisitos

- JDK 21 instalado y configurado.
- Maven para compilar (opcional si ejecutas desde tu IDE).
- API Key válida de ExchangeRate API.

Registro para la API Key: https://www.exchangerate-api.com/

---

## Instalación y ejecución

1. Clonar el repositorio:
   git clone https://github.com/EzequielAngel0/conversorDeMoneda.git
2. Entrar al directorio del proyecto:
   cd conversorDeMoneda
3. Configurar la API Key (ver sección siguiente).
4. Compilar con Maven:
   mvn clean package
5. Ejecutar la aplicación desde el IDE (Main.java) o desde consola:
   java -jar target/api-playground-1.0.0.jar

Nota: Asegúrate de que el jar generado tenga el `Main-Class` configurado en el POM o que uses el plugin adecuado para crear un jar ejecutable (shade/assembly) si lo necesitas.

---

## Configuración (API Key)

La aplicación requiere una API Key de ExchangeRate API. Se recomienda proporcionar la clave mediante una variable de entorno:

Nombre de la variable: `EXCHANGE_RATE_API_KEY`

Ejemplos:

- Linux / macOS (Bash / Zsh, temporal para la sesión actual):
  export EXCHANGE_RATE_API_KEY="TU_API_KEY_AQUI"

- Windows (Command Prompt, temporal):
  set EXCHANGE_RATE_API_KEY=TU_API_KEY_AQUI

- Windows PowerShell (temporal):
  $env:EXCHANGE_RATE_API_KEY="TU_API_KEY_AQUI"

Si no se encuentra la variable, la aplicación debería mostrar un error indicando que la API Key no está configurada.

---

## Caché de tasas

- La caché se organiza por moneda base (por ejemplo, tasas con base `USD`).
- Tiempo de vida por defecto: 10 minutos (TTL).
- Objetivo: reducir el número de llamadas a la API y mejorar la experiencia del usuario.
- Comportamiento: si la tasa requerida está en caché y aún no expiró, la app usa la caché; si expiró o no existe, la app consulta la API y actualiza la caché.

---

## Manejo de errores y recomendaciones

- Si hay fallo de red o la API responde con error, la app muestra un mensaje claro en la UI y sugiere reintentar.
- Si la API Key es inválida o excede cuota, la app debe mostrar el mensaje de error recibido de la API.
- Recomendación: probar con diferentes monedas, y verificar logs (si están habilitados) para depurar respuestas HTTP/JSON.

---

## Estructura del proyecto

src/main/java/com/example/apiplayground/
- api/
  - ExchangeRateApiClient.java
- model/
  - CurrencyItem.java
  - ExchangeRatesResponse.java
  - SupportedCodesResponse.java
- service/
  - CurrencyConverterService.java
- ui/
  - AppFrame.java
- Main.java

---

## Mejoras futuras

- Indicador en la UI si la tasa proviene de caché o de la API.
- Historial de conversiones.
- Persistencia de preferencias del usuario (monedas favoritas, última sesión).
- Empaquetado como instalador o jar “todo-en-uno” (fat jar).
- Migración opcional a JavaFX para UI más moderna.
- Pruebas unitarias para la lógica de conversión y caching.
- Soporte configurable del TTL (archivo de configuración o preferencia de usuario).

---

## Contribuciones

Este proyecto está pensado como ejercicio educativo y es bienvenido el feedback o mejoras. Para contribuir:
1. Hacer fork del repositorio.
2. Crear una nueva rama feature/mi-mejora.
3. Hacer commits claros y abrir un Pull Request describiendo los cambios.

---

## Licencia

Proyecto de uso educativo y libre para experimentación personal.

---
