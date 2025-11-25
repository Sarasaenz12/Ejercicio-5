package inventario;

/**
 * Interfaz segregada para productos que requieren envío físico
 *
 * PRINCIPIO APLICADO: ISP (Interface Segregation Principle)
 * - Solo los productos físicos implementan esta interfaz
 * - Los productos digitales NO están obligados a implementar estos métodos
 * - Evita el antipatrón "Fat Interface" (interfaz gorda)
 *
 * UBICACIÓN EN ARQUITECTURA:
 * - ProductoFisico implementa esta interfaz
 * - ProductoDigital NO la implementa
 */
public interface IEnviable {
    double calcularCostoEnvio(String destino);
    double obtenerPeso();
    String obtenerDimensiones();
    String prepararParaEnvio(String direccionDestino);
}