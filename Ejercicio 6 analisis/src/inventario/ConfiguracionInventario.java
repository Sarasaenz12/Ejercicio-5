package inventario;

/**
 * Clase para centralizar configuraciones del sistema
 *
 * ANTIPATRÓN EVITADO: Valores Quemados (Magic Numbers / Hardcoded Values)
 *
 * PROBLEMA QUE RESUELVE:
 * ❌ MAL: double costo = distancia * 0.50;  // ¿De dónde sale 0.50?
 * ✅ BIEN: double costo = distancia * config.getCostoPorKmBase();
 *
 * BENEFICIOS:
 * 1. Centralización: Todos los valores en un solo lugar
 * 2. Mantenibilidad: Cambiar un valor no requiere buscar en todo el código
 * 3. Testing: Fácil probar con diferentes configuraciones
 * 4. Documentación: Los nombres de variables documentan su propósito
 * 5. Flexibilidad: Constructor parametrizado para personalización
 */
public class ConfiguracionInventario {
    // ✅ Configuración de URLs - NO hardcoded en las clases
    private final String urlBaseDescarga;

    // ✅ Configuración de costos - Parametrizados
    private final double costoPorKmBase;
    private final double factorPesoMedio;
    private final double factorPesoAlto;

    // ✅ Configuración de límites - Configurables
    private final double pesoMedioKg;
    private final double pesoAltoKg;

    // ✅ Configuración de formatos - Array dinámico
    private final String[] formatosDigitalesPermitidos;

    /**
     * Constructor con valores por defecto
     * VENTAJA: Facilita creación con valores sensatos
     */
    public ConfiguracionInventario() {
        this.urlBaseDescarga = "https://mitienda.com/api/v1";
        this.costoPorKmBase = 0.50;
        this.factorPesoMedio = 1.2;
        this.factorPesoAlto = 1.5;
        this.pesoMedioKg = 5.0;
        this.pesoAltoKg = 10.0;
        this.formatosDigitalesPermitidos = new String[]{"PDF", "MP4", "MP3", "ZIP", "EXE", "APK"};
    }

    /**
     * Constructor parametrizado para máxima flexibilidad
     * VENTAJA: Testing con diferentes configuraciones
     * VENTAJA: Personalización por cliente o entorno
     */
    public ConfiguracionInventario(String urlBaseDescarga, double costoPorKmBase,
                                   double factorPesoMedio, double factorPesoAlto,
                                   double pesoMedioKg, double pesoAltoKg,
                                   String[] formatosDigitalesPermitidos) {
        this.urlBaseDescarga = urlBaseDescarga;
        this.costoPorKmBase = costoPorKmBase;
        this.factorPesoMedio = factorPesoMedio;
        this.factorPesoAlto = factorPesoAlto;
        this.pesoMedioKg = pesoMedioKg;
        this.pesoAltoKg = pesoAltoKg;
        this.formatosDigitalesPermitidos = formatosDigitalesPermitidos;
    }

    // Getters - Acceso controlado a la configuración

    public String getUrlBaseDescarga() {
        return urlBaseDescarga;
    }

    public double getCostoPorKmBase() {
        return costoPorKmBase;
    }

    public double getFactorPesoMedio() {
        return factorPesoMedio;
    }

    public double getFactorPesoAlto() {
        return factorPesoAlto;
    }

    public double getPesoMedioKg() {
        return pesoMedioKg;
    }

    public double getPesoAltoKg() {
        return pesoAltoKg;
    }

    /**
     * Retorna copia del array para prevenir modificaciones externas
     * ANTIPATRÓN EVITADO: Exponer array mutable
     */
    public String[] getFormatosDigitalesPermitidos() {
        return formatosDigitalesPermitidos.clone();
    }

    /**
     * Método de utilidad para validar formatos
     */
    public boolean esFormatoValido(String formato) {
        if (formato == null) return false;

        for (String formatoPermitido : formatosDigitalesPermitidos) {
            if (formatoPermitido.equalsIgnoreCase(formato)) {
                return true;
            }
        }
        return false;
    }
}