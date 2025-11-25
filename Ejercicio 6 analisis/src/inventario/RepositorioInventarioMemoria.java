package inventario;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación concreta del repositorio en memoria
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: almacenar y recuperar productos
 * - No gestiona lógica de negocio
 * - No calcula costos ni procesa ventas
 *
 * PRINCIPIO APLICADO: DIP (Dependency Inversion Principle)
 * - Implementa la abstracción IRepositorioInventario
 * - Puede ser reemplazado por RepositorioInventarioDB sin afectar GestorInventario
 *
 * ANTIPATRÓN EVITADO: God Object
 * - Esta clase SOLO almacena, no hace todo
 */
public class RepositorioInventarioMemoria implements IRepositorioInventario {
    private final Map<String, Producto> productos;

    public RepositorioInventarioMemoria() {
        this.productos = new HashMap<>();
    }

    /**
     * Agregar producto con VALIDACIONES
     * ANTIPATRÓN EVITADO: Operaciones sin validar
     */
    @Override
    public void agregarProducto(Producto producto) {
        // VALIDACIÓN 1: Producto no nulo
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        // VALIDACIÓN 2: No duplicados
        if (productos.containsKey(producto.obtenerIdentificador())) {
            throw new IllegalArgumentException("Ya existe un producto con el ID: "
                    + producto.obtenerIdentificador());
        }

        productos.put(producto.obtenerIdentificador(), producto);
    }

    /**
     * Eliminar producto con VALIDACIONES
     */
    @Override
    public void eliminarProducto(String identificador) {
        // VALIDACIÓN 1: ID no vacío
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío");
        }

        // VALIDACIÓN 2: Producto existe
        if (!productos.containsKey(identificador)) {
            throw new IllegalArgumentException("No existe un producto con el ID: " + identificador);
        }

        productos.remove(identificador);
    }

    /**
     * Buscar por ID - retorna Optional para manejo seguro de null
     * ANTIPATRÓN EVITADO: Retornar null directamente
     */
    @Override
    public Optional<Producto> buscarProductoPorId(String identificador) {
        if (identificador == null || identificador.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(productos.get(identificador));
    }

    /**
     * Buscar por nombre con validación
     */
    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String nombreBusqueda = nombre.toLowerCase();
        return productos.values().stream()
                .filter(p -> p.obtenerNombre().toLowerCase().contains(nombreBusqueda))
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return new ArrayList<>(productos.values());
    }

    /**
     * Filtrar por tipo
     */
    @Override
    public List<Producto> obtenerProductosPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return productos.values().stream()
                .filter(p -> p.obtenerTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existeProducto(String identificador) {
        return identificador != null && productos.containsKey(identificador);
    }

    @Override
    public int contarProductos() {
        return productos.size();
    }
}