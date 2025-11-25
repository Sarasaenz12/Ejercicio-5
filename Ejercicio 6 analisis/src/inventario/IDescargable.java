package inventario;

/**
 * Interfaz segregada para productos digitales descargables
 *
 * PRINCIPIO APLICADO: ISP (Interface Segregation Principle)
 * - Solo los productos digitales implementan esta interfaz
 * - Los productos físicos NO están obligados a implementar estos métodos
 * - Cada interfaz tiene un propósito específico
 *
 * UBICACIÓN EN ARQUITECTURA:
 * - ProductoDigital implementa esta interfaz
 * - ProductoFisico NO la implementa
 */
public interface IDescargable {
    String generarEnlaceDescarga();
    double obtenerTamañoArchivo();
    String obtenerFormato();
    boolean verificarLicencia(String usuarioId);
}