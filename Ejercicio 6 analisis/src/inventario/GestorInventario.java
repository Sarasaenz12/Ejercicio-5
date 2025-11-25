package inventario;

import java.util.List;

/**
 * Coordinador principal del sistema de inventario
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: coordinar operaciones de gestión de inventario
 * - No almacena productos (eso lo hace el repositorio)
 * - No implementa lógica de productos (eso lo hacen ProductoFisico/Digital)
 *
 * PRINCIPIO APLICADO: DIP (Dependency Inversion Principle)
 * - Depende de IRepositorioInventario (abstracción)
 * - NO depende de RepositorioInventarioMemoria (implementación concreta)
 * - Inyección de dependencias por constructor
 *
 * PRINCIPIO APLICADO: OCP (Open/Closed Principle)
 * - Abierto a extensión: funciona con cualquier tipo de Producto
 * - Cerrado a modificación: agregar ProductoServicio no requiere cambios aquí
 *
 * PRINCIPIO APLICADO: LSP (Liskov Substitution Principle)
 * - Trata a ProductoFisico y ProductoDigital polimórficamente
 * - Los métodos funcionan con cualquier implementación de Producto
 *
 * ANTIPATRÓN EVITADO: God Object
 * - Esta clase NO hace todo, solo coordina
 * - Delega responsabilidades a otras clases
 */
public class GestorInventario {
    private final IRepositorioInventario repositorio;

    /**
     * Constructor con Inyección de Dependencias
     *
     * PRINCIPIO: DIP (Dependency Inversion Principle)
     * - Recibe la abstracción IRepositorioInventario
     * - No crea el repositorio internamente
     * - Permite cambiar implementación sin modificar esta clase
     *
     * BENEFICIO TESTING:
     * - Fácil inyectar un mock: new GestorInventario(repoMock)
     */
    public GestorInventario(IRepositorioInventario repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo");
        }
        this.repositorio = repositorio;
    }

    /**
     * PRINCIPIO: OCP - funciona con cualquier Producto
     * PRINCIPIO: LSP - ProductoFisico y ProductoDigital son intercambiables
     */
    public void agregarProducto(Producto producto) {
        try {
            repositorio.agregarProducto(producto);
            System.out.println("✓ Producto agregado exitosamente: " + producto.obtenerNombre());
        } catch (Exception e) {
            System.err.println("✗ Error al agregar producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(String identificador) {
        try {
            repositorio.eliminarProducto(identificador);
            System.out.println("✓ Producto eliminado exitosamente");
        } catch (Exception e) {
            System.err.println("✗ Error al eliminar producto: " + e.getMessage());
        }
    }

    public void mostrarProducto(String identificador) {
        repositorio.buscarProductoPorId(identificador).ifPresentOrElse(
                Producto::mostrarInformacion,
                () -> System.out.println("✗ No se encontró el producto con ID: " + identificador)
        );
    }

    public void listarTodosLosProductos() {
        List<Producto> productos = repositorio.obtenerTodosLosProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos en el inventario");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        INVENTARIO COMPLETO                 ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("Total de productos: " + productos.size() + "\n");

        productos.forEach(Producto::mostrarInformacion);
    }

    public void listarProductosPorTipo(String tipo) {
        List<Producto> productos = repositorio.obtenerProductosPorTipo(tipo);

        if (productos.isEmpty()) {
            System.out.println("No hay productos del tipo: " + tipo);
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   PRODUCTOS TIPO: " + tipo.toUpperCase());
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("Total: " + productos.size() + "\n");

        productos.forEach(Producto::mostrarInformacion);
    }

    public void actualizarStock(String identificador, int cantidad) {
        repositorio.buscarProductoPorId(identificador).ifPresentOrElse(
                producto -> {
                    try {
                        producto.actualizarStock(cantidad);
                        String operacion = cantidad > 0 ? "agregadas" : "retiradas";
                        System.out.println("✓ Stock actualizado: " + Math.abs(cantidad) + " unidades " + operacion);
                        System.out.println("  Stock actual: " + producto.obtenerCantidadStock());
                    } catch (Exception e) {
                        System.err.println("✗ Error al actualizar stock: " + e.getMessage());
                    }
                },
                () -> System.out.println("✗ No se encontró el producto con ID: " + identificador)
        );
    }

    /**
     * PRINCIPIO: LSP (Liskov Substitution Principle)
     * - Este método funciona con CUALQUIER tipo de Producto
     * - ProductoFisico y ProductoDigital son intercambiables
     * - Ambos respetan el contrato de Producto.actualizarStock()
     */
    public void procesarVenta(String identificador, int cantidad) {
        repositorio.buscarProductoPorId(identificador).ifPresentOrElse(
                producto -> {
                    if (producto.obtenerCantidadStock() < cantidad) {
                        System.out.println("✗ Stock insuficiente para la venta");
                        return;
                    }

                    try {
                        producto.actualizarStock(-cantidad);
                        double total = producto.obtenerPrecio() * cantidad;

                        System.out.println("\n╔════════════════════════════════════════════╗");
                        System.out.println("║           VENTA PROCESADA                  ║");
                        System.out.println("╚════════════════════════════════════════════╝");
                        System.out.println("Producto: " + producto.obtenerNombre());
                        System.out.println("Cantidad: " + cantidad);
                        System.out.println("Precio unitario: $" + String.format("%.2f", producto.obtenerPrecio()));
                        System.out.println("Total: $" + String.format("%.2f", total));
                        System.out.println("Stock restante: " + producto.obtenerCantidadStock());
                        System.out.println("==============================================\n");
                    } catch (Exception e) {
                        System.err.println("✗ Error al procesar venta: " + e.getMessage());
                    }
                },
                () -> System.out.println("✗ No se encontró el producto con ID: " + identificador)
        );
    }

    /**
     * PRINCIPIO: ISP (Interface Segregation Principle)
     * - Método específico para productos que implementan IEnviable
     * - Solo productos físicos tienen esta capacidad
     * - Verifica el tipo en tiempo de ejecución
     */
    public void prepararEnvio(String identificador, String direccion) {
        repositorio.buscarProductoPorId(identificador).ifPresentOrElse(
                producto -> {
                    if (producto instanceof IEnviable) {
                        IEnviable productoEnviable = (IEnviable) producto;
                        System.out.println("\n" + productoEnviable.prepararParaEnvio(direccion));
                    } else {
                        System.out.println("✗ Este producto no es enviable (es digital)");
                    }
                },
                () -> System.out.println("✗ No se encontró el producto con ID: " + identificador)
        );
    }

    /**
     * PRINCIPIO: ISP (Interface Segregation Principle)
     * - Método específico para productos que implementan IDescargable
     * - Solo productos digitales tienen esta capacidad
     * - Verifica el tipo en tiempo de ejecución
     */
    public void generarDescarga(String identificador, String usuarioId) {
        repositorio.buscarProductoPorId(identificador).ifPresentOrElse(
                producto -> {
                    if (producto instanceof IDescargable) {
                        IDescargable productoDescargable = (IDescargable) producto;

                        if (productoDescargable.verificarLicencia(usuarioId)) {
                            String enlace = productoDescargable.generarEnlaceDescarga();
                            System.out.println("\n╔════════════════════════════════════════════╗");
                            System.out.println("║        ENLACE DE DESCARGA GENERADO        ║");
                            System.out.println("╚════════════════════════════════════════════╝");
                            System.out.println("Producto: " + producto.obtenerNombre());
                            System.out.println("Formato: " + productoDescargable.obtenerFormato());
                            System.out.println("Tamaño: " + productoDescargable.obtenerTamañoArchivo() + " MB");
                            System.out.println("Enlace: " + enlace);
                            System.out.println("==============================================\n");
                        } else {
                            System.out.println("✗ El usuario no tiene licencia para este producto");
                        }
                    } else {
                        System.out.println("✗ Este producto no es descargable (es físico)");
                    }
                },
                () -> System.out.println("✗ No se encontró el producto con ID: " + identificador)
        );
    }

    public void mostrarEstadisticas() {
        List<Producto> todosProductos = repositorio.obtenerTodosLosProductos();
        List<Producto> productosF = repositorio.obtenerProductosPorTipo("FÍSICO");
        List<Producto> productosD = repositorio.obtenerProductosPorTipo("DIGITAL");

        double valorTotalInventario = todosProductos.stream()
                .mapToDouble(p -> p.obtenerPrecio() * p.obtenerCantidadStock())
                .sum();

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║       ESTADÍSTICAS DEL INVENTARIO         ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println("Total de productos: " + todosProductos.size());
        System.out.println("Productos físicos: " + productosF.size());
        System.out.println("Productos digitales: " + productosD.size());
        System.out.println("Valor total del inventario: $" + String.format("%.2f", valorTotalInventario));
        System.out.println("==============================================\n");
    }
}