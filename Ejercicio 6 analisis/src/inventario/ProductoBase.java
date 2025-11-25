package inventario;

/**
 * Clase abstracta base que implementa comportamiento común de todos los productos
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: gestionar atributos COMUNES de productos
 * - No mezcla lógica de productos físicos o digitales
 *
 * PRINCIPIO APLICADO: DRY (Don't Repeat Yourself)
 * - Evita duplicación de código entre ProductoFisico y ProductoDigital
 * - Código común centralizado en un solo lugar
 *
 * ANTIPATRÓN EVITADO: Validaciones inexistentes
 * - Todas las validaciones están presentes
 * - No acepta valores inválidos (null, negativos, vacíos)
 */
public abstract class ProductoBase implements Producto {
    // Atributos inmutables (final) para seguridad
    private final String identificador;
    private final String nombre;
    private final double precio;
    private int cantidadStock; // Mutable porque cambia con ventas

    /**
     * Constructor con VALIDACIONES completas
     * ANTIPATRÓN EVITADO: Constructor sin validaciones
     */
    protected ProductoBase(String identificador, String nombre, double precio, int cantidadStock) {
        // VALIDACIÓN 1: Identificador no vacío
        if (identificador == null || identificador.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador no puede estar vacío");
        }

        // VALIDACIÓN 2: Nombre no vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        // VALIDACIÓN 3: Precio no negativo
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        // VALIDACIÓN 4: Stock no negativo
        if (cantidadStock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        this.identificador = identificador;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }

    @Override
    public String obtenerIdentificador() {
        return identificador;
    }

    @Override
    public String obtenerNombre() {
        return nombre;
    }

    @Override
    public double obtenerPrecio() {
        return precio;
    }

    @Override
    public int obtenerCantidadStock() {
        return cantidadStock;
    }

    /**
     * Actualiza el stock con VALIDACIÓN
     * ANTIPATRÓN EVITADO: Operaciones sin validar que dejan estado inconsistente
     */
    @Override
    public void actualizarStock(int cantidad) {
        int nuevoStock = this.cantidadStock + cantidad;

        // VALIDACIÓN: No permitir stock negativo
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }

        this.cantidadStock = nuevoStock;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("==============================================");
        System.out.println("ID: " + identificador);
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: $" + String.format("%.2f", precio));
        System.out.println("Stock: " + cantidadStock + " unidades");
        System.out.println("Tipo: " + obtenerTipo());
    }
}