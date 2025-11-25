package inventario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para operaciones de inventario
 *
 * PRINCIPIO APLICADO: DIP (Dependency Inversion Principle)
 * - Esta es la ABSTRACCIÓN para operaciones de repositorio
 * - GestorInventario depende de esta interfaz, NO de la implementación concreta
 * - Permite cambiar fácilmente de memoria a BD sin afectar GestorInventario
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: definir contrato de almacenamiento
 * - No mezcla lógica de negocio
 *
 * BENEFICIO: Inyección de Dependencias
 * - El gestor recibe la implementación por constructor
 * - Fácil crear mocks para testing
 */
public interface IRepositorioInventario {
    void agregarProducto(Producto producto);
    void eliminarProducto(String identificador);
    Optional<Producto> buscarProductoPorId(String identificador);
    List<Producto> buscarProductosPorNombre(String nombre);
    List<Producto> obtenerTodosLosProductos();
    List<Producto> obtenerProductosPorTipo(String tipo);
    boolean existeProducto(String identificador);
    int contarProductos();
}