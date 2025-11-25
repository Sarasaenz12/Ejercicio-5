package inventario;
/**
 * Interfaz base para todos los productos del inventario
 *
 * PRINCIPIO APLICADO: DIP (Dependency Inversion Principle)
 * - Esta es la ABSTRACCIÓN de la que dependen otras clases
 * - El GestorInventario depende de esta interfaz, no de clases concretas
 *
 * PRINCIPIO APLICADO: ISP (Interface Segregation Principle)
 * - Contiene solo los métodos COMUNES a todos los productos
 * - No fuerza implementaciones innecesarias
 */
public interface Producto {
    String obtenerIdentificador();
    String obtenerNombre();
    double obtenerPrecio();
    int obtenerCantidadStock();
    void actualizarStock(int cantidad);
    String obtenerTipo();
    void mostrarInformacion();
}