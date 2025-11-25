package inventario;

/**
 * Clase para productos físicos
 *
 * PRINCIPIO APLICADO: SRP (Single Responsibility Principle)
 * - Responsabilidad única: gestionar productos físicos y sus envíos
 * - No gestiona productos digitales ni descargas
 *
 * PRINCIPIO APLICADO: LSP (Liskov Substitution Principle)
 * - Puede sustituir a Producto sin romper el comportamiento
 * - Respeta el contrato de la interfaz Producto
 *
 * PRINCIPIO APLICADO: ISP (Interface Segregation Principle)
 * - Solo implementa IEnviable (necesario para productos físicos)
 * - NO implementa IDescargable (innecesario para productos físicos)
 *
 * ANTIPATRÓN EVITADO: Valores quemados
 * - costoPorKilometro es un parámetro, no un valor hardcoded
 * - Los factores de peso podrían venir de configuración
 */
public class ProductoFisico extends ProductoBase implements IEnviable {
    private final double peso;
    private final String dimensiones;
    private final double costoPorKilometro;

    /**
     * Constructor con VALIDACIONES y sin valores quemados
     */
    public ProductoFisico(String identificador, String nombre, double precio,
                          int cantidadStock, double peso, String dimensiones,
                          double costoPorKilometro) {
        super(identificador, nombre, precio, cantidadStock);

        // VALIDACIÓN: Peso positivo
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a cero");
        }

        // VALIDACIÓN: Dimensiones no vacías
        if (dimensiones == null || dimensiones.trim().isEmpty()) {
            throw new IllegalArgumentException("Las dimensiones no pueden estar vacías");
        }

        // VALIDACIÓN: Costo no negativo
        if (costoPorKilometro < 0) {
            throw new IllegalArgumentException("El costo por kilómetro no puede ser negativo");
        }

        this.peso = peso;
        this.dimensiones = dimensiones;
        this.costoPorKilometro = costoPorKilometro;
    }

    @Override
    public String obtenerTipo() {
        return "FÍSICO";
    }

    /**
     * ANTIPATRÓN EVITADO: Valores quemados en cálculos
     * - Los factores 1.5 y 1.2 deberían idealmente venir de configuración
     * - costoPorKilometro es parametrizado
     */
    @Override
    public double calcularCostoEnvio(String destino) {
        // Simulación de cálculo basado en destino
        int distanciaKm = calcularDistancia(destino);
        double costoBase = distanciaKm * costoPorKilometro; // ✅ Parametrizado

        // Recargo por peso (idealmente de configuración)
        if (peso > 10) {
            costoBase *= 1.5;  // Factor para peso alto
        } else if (peso > 5) {
            costoBase *= 1.2;  // Factor para peso medio
        }

        return costoBase;
    }

    @Override
    public double obtenerPeso() {
        return peso;
    }

    @Override
    public String obtenerDimensiones() {
        return dimensiones;
    }

    @Override
    public String prepararParaEnvio(String direccionDestino) {
        StringBuilder preparacion = new StringBuilder();
        preparacion.append("Preparando envío para: ").append(direccionDestino).append("\n");
        preparacion.append("Producto: ").append(obtenerNombre()).append("\n");
        preparacion.append("Peso: ").append(peso).append(" kg\n");
        preparacion.append("Dimensiones: ").append(dimensiones).append("\n");
        preparacion.append("Costo de envío: $").append(String.format("%.2f", calcularCostoEnvio(direccionDestino)));

        return preparacion.toString();
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("Peso: " + peso + " kg");
        System.out.println("Dimensiones: " + dimensiones);
        System.out.println("==============================================");
    }

    /**
     * ANTIPATRÓN EVITADO: Valores quemados en lógica de negocio
     * - En producción, esto consultaría una API o BD
     * - Aquí usamos hash para demostración sin hardcodear distancias específicas
     */
    private int calcularDistancia(String destino) {
        // En una implementación real, esto consultaría una API o base de datos
        // Aquí simulamos con un hash del destino para demostración
        return Math.abs(destino.hashCode() % 500) + 50;
    }
}