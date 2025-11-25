package inventario;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase para productos digitales
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: gestionar productos digitales y sus descargas
 * - No gestiona productos físicos ni envíos
 *
 * PRINCIPIO APLICADO: LSP (Liskov Substitution Principle)
 * - Puede sustituir a Producto sin romper el comportamiento
 * - Respeta el contrato de la interfaz Producto
 *
 * PRINCIPIO APLICADO: ISP (Interface Segregation Principle)
 * - Solo implementa IDescargable (necesario para productos digitales)
 * - NO implementa IEnviable (innecesario para productos digitales)
 *
 * ANTIPATRÓN EVITADO: Valores quemados
 * - urlBaseDescarga es un parámetro, no hardcoded
 * - UUID generado dinámicamente, no fijo
 */
public class ProductoDigital extends ProductoBase implements IDescargable {
    private final double tamañoArchivoMB;
    private final String formato;
    private final String urlBaseDescarga; // ✅ Parametrizado, no hardcoded
    private final Set<String> licenciasActivas;

    /**
     * Constructor con VALIDACIONES completas
     * ANTIPATRÓN EVITADO: Constructor sin validar parámetros
     */
    public ProductoDigital(String identificador, String nombre, double precio,
                           int cantidadStock, double tamañoArchivoMB,
                           String formato, String urlBaseDescarga) {
        super(identificador, nombre, precio, cantidadStock);

        // VALIDACIÓN: Tamaño positivo
        if (tamañoArchivoMB <= 0) {
            throw new IllegalArgumentException("El tamaño del archivo debe ser mayor a cero");
        }

        // VALIDACIÓN: Formato no vacío
        if (formato == null || formato.trim().isEmpty()) {
            throw new IllegalArgumentException("El formato no puede estar vacío");
        }

        // VALIDACIÓN: URL no vacía
        if (urlBaseDescarga == null || urlBaseDescarga.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL base no puede estar vacía");
        }

        this.tamañoArchivoMB = tamañoArchivoMB;
        this.formato = formato;
        this.urlBaseDescarga = urlBaseDescarga;
        this.licenciasActivas = new HashSet<>();
    }

    @Override
    public String obtenerTipo() {
        return "DIGITAL";
    }

    /**
     * ANTIPATRÓN EVITADO: URLs hardcodeadas
     * - La URL base viene como parámetro
     * - El token se genera dinámicamente con UUID
     */
    @Override
    public String generarEnlaceDescarga() {
        // Genera un enlace único temporal de descarga
        String token = UUID.randomUUID().toString(); // ✅ Dinámico, no hardcoded
        return urlBaseDescarga + "/download/" + obtenerIdentificador() + "?token=" + token;
    }

    @Override
    public double obtenerTamañoArchivo() {
        return tamañoArchivoMB;
    }

    @Override
    public String obtenerFormato() {
        return formato;
    }

    /**
     * Verificación de licencia con VALIDACIÓN
     */
    @Override
    public boolean verificarLicencia(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            return false;
        }
        return licenciasActivas.contains(usuarioId);
    }

    /**
     * Método para activar una licencia para un usuario
     * VALIDACIÓN incluida
     */
    public void activarLicencia(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío");
        }
        licenciasActivas.add(usuarioId);
    }

    /**
     * Método para revocar una licencia
     */
    public void revocarLicencia(String usuarioId) {
        licenciasActivas.remove(usuarioId);
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Tamaño: " + tamañoArchivoMB + " MB");
        System.out.println("Formato: " + formato);
        System.out.println("Licencias activas: " + licenciasActivas.size());
        System.out.println("==============================================");
    }

    /**
     * Los productos digitales tienen stock "ilimitado" pero lo registramos
     * para estadísticas o control de licencias
     */
    @Override
    public void actualizarStock(int cantidad) {
        super.actualizarStock(cantidad);
    }
}