package inventario;

/**
 * Clase principal que demuestra el uso del sistema de gestión de inventarios
 *
 * DEMOSTRACIÓN DE PRINCIPIOS SOLID:
 * - Muestra cómo todos los principios trabajan juntos
 * - Ejemplos prácticos de cada principio
 * - Casos de uso reales del sistema
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTIÓN DE INVENTARIOS - PRINCIPIOS SOLID  ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        // ═══════════════════════════════════════════════════════════════
        // CONFIGURACIÓN - Evitando valores quemados
        // ═══════════════════════════════════════════════════════════════
        ConfiguracionInventario config = new ConfiguracionInventario();
        // Alternativa: ConfiguracionInventario config = new ConfiguracionInventario(
        //     "https://custom.com", 0.75, 1.3, 1.6, 7.0, 12.0, new String[]{"PDF", "MP4"}
        // );

        // ═══════════════════════════════════════════════════════════════
        // PRINCIPIO: DIP (Dependency Inversion Principle)
        // - El gestor depende de la ABSTRACCIÓN IRepositorioInventario
        // - NO depende de la implementación concreta RepositorioInventarioMemoria
        // - Esto permite cambiar fácilmente a una BD sin modificar GestorInventario
        // ═══════════════════════════════════════════════════════════════
        IRepositorioInventario repositorio = new RepositorioInventarioMemoria();
        GestorInventario gestor = new GestorInventario(repositorio);

        System.out.println("═══ FASE 1: AGREGANDO PRODUCTOS AL INVENTARIO ═══\n");

        // ═══════════════════════════════════════════════════════════════
        // Crear productos físicos
        // PRINCIPIO: SRP - ProductoFisico solo gestiona productos físicos
        // ANTIPATRÓN EVITADO: Usar config.getCostoPorKmBase() en vez de 0.50 hardcoded
        // ═══════════════════════════════════════════════════════════════
        Producto laptop = new ProductoFisico(
                "PROD-001",
                "Laptop Dell XPS 15",
                1299.99,
                15,
                2.5,
                "35x25x2 cm",
                config.getCostoPorKmBase() // ✅ De configuración, no hardcoded
        );

        Producto mouse = new ProductoFisico(
                "PROD-002",
                "Mouse Logitech MX Master 3",
                99.99,
                50,
                0.3,
                "12x8x4 cm",
                config.getCostoPorKmBase() // ✅ De configuración
        );

        Producto teclado = new ProductoFisico(
                "PROD-003",
                "Teclado Mecánico Keychron K2",
                79.99,
                30,
                0.8,
                "35x12x3 cm",
                config.getCostoPorKmBase() // ✅ De configuración
        );

        // ═══════════════════════════════════════════════════════════════
        // Crear productos digitales
        // PRINCIPIO: SRP - ProductoDigital solo gestiona productos digitales
        // ANTIPATRÓN EVITADO: URL de config, no hardcoded
        // ═══════════════════════════════════════════════════════════════
        Producto software = new ProductoDigital(
                "PROD-004",
                "Licencia Microsoft Office 365",
                69.99,
                1000,
                150.5,
                "EXE",
                config.getUrlBaseDescarga() // ✅ De configuración
        );

        Producto ebook = new ProductoDigital(
                "PROD-005",
                "Curso Completo de Java",
                49.99,
                5000,
                2500.0,
                "MP4",
                config.getUrlBaseDescarga() // ✅ De configuración
        );

        Producto musica = new ProductoDigital(
                "PROD-006",
                "Álbum Digital - Jazz Collection",
                9.99,
                10000,
                120.0,
                "MP3",
                config.getUrlBaseDescarga() // ✅ De configuración
        );

        // ═══════════════════════════════════════════════════════════════
        // PRINCIPIO: OCP (Open/Closed Principle)
        // - Agregar productos sin modificar el código del gestor
        // - Si creamos ProductoServicio, funcionaría sin cambios aquí
        // ═══════════════════════════════════════════════════════════════
        gestor.agregarProducto(laptop);
        gestor.agregarProducto(mouse);
        gestor.agregarProducto(teclado);
        gestor.agregarProducto(software);
        gestor.agregarProducto(ebook);
        gestor.agregarProducto(musica);

        System.out.println("\n═══ FASE 2: LISTANDO TODOS LOS PRODUCTOS ═══");
        gestor.listarTodosLosProductos();

        System.out.println("\n═══ FASE 3: FILTRANDO PRODUCTOS POR TIPO ═══");
        gestor.listarProductosPorTipo("FÍSICO");
        gestor.listarProductosPorTipo("DIGITAL");

        System.out.println("\n═══ FASE 4: PROCESANDO VENTAS ═══\n");
        // ═══════════════════════════════════════════════════════════════
        // PRINCIPIO: LSP (Liskov Substitution Principle)
        // - Ambos tipos de productos pueden ser vendidos de la misma manera
        // - ProductoFisico y ProductoDigital son intercambiables
        // - El método procesarVenta() funciona con ambos sin saber cuál es
        // ═══════════════════════════════════════════════════════════════
        gestor.procesarVenta("PROD-001", 3);
        gestor.procesarVenta("PROD-004", 5);

        System.out.println("\n═══ FASE 5: ACTUALIZANDO STOCK ═══\n");
        gestor.actualizarStock("PROD-002", 25);  // Agregar stock
        gestor.actualizarStock("PROD-005", -10); // Retirar stock

        System.out.println("\n═══ FASE 6: OPERACIONES ESPECÍFICAS POR TIPO ═══\n");

        // ═══════════════════════════════════════════════════════════════
        // PRINCIPIO: ISP (Interface Segregation Principle)
        // - Solo productos físicos tienen método de envío (IEnviable)
        // - Solo productos digitales tienen método de descarga (IDescargable)
        // ═══════════════════════════════════════════════════════════════

        System.out.println("--- Preparando envío de producto físico ---");
        gestor.prepararEnvio("PROD-001", "Calle 123, Bogotá, Colombia");

        System.out.println("\n--- Intentando enviar producto digital (debe fallar) ---");
        gestor.prepararEnvio("PROD-004", "Calle 456, Medellín, Colombia");

        System.out.println("\n--- Activando licencia y generando descarga ---");
        Producto softwareRecuperado = repositorio.buscarProductoPorId("PROD-004").orElse(null);
        if (softwareRecuperado instanceof ProductoDigital) {
            ProductoDigital softwareDigital = (ProductoDigital) softwareRecuperado;
            softwareDigital.activarLicencia("USER-001");
            gestor.generarDescarga("PROD-004", "USER-001");
        }

        System.out.println("\n--- Intentando descargar producto físico (debe fallar) ---");
        gestor.generarDescarga("PROD-001", "USER-001");

        System.out.println("\n═══ FASE 7: ESTADÍSTICAS DEL INVENTARIO ═══");
        gestor.mostrarEstadisticas();

        System.out.println("\n═══ FASE 8: BÚSQUEDA DE PRODUCTOS ═══\n");
        System.out.println("--- Buscando producto por ID ---");
        gestor.mostrarProducto("PROD-003");

        System.out.println("\n═══ DEMOSTRACION DE PRINCIPIOS SOLID ═══\n");
        imprimirPrincipiosSOLID();
    }

    private static void imprimirPrincipiosSOLID() {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║          PRINCIPIOS SOLID IMPLEMENTADOS               ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        System.out.println("✓ SRP (Single Responsibility Principle):");
        System.out.println("  - ProductoFisico: Solo gestiona productos físicos");
        System.out.println("  - ProductoDigital: Solo gestiona productos digitales");
        System.out.println("  - RepositorioInventarioMemoria: Solo almacena productos");
        System.out.println("  - GestorInventario: Solo coordina operaciones\n");

        System.out.println("✓ OCP (Open/Closed Principle):");
        System.out.println("  - Se pueden agregar nuevos tipos de productos");
        System.out.println("  - No se modifica código existente");
        System.out.println("  - Ejemplo: ProductoPerecible, ProductoServicio, etc.\n");

        System.out.println("✓ LSP (Liskov Substitution Principle):");
        System.out.println("  - ProductoFisico y ProductoDigital son intercambiables");
        System.out.println("  - Ambos implementan Producto correctamente");
        System.out.println("  - El gestor los trata polimórficamente\n");

        System.out.println("✓ ISP (Interface Segregation Principle):");
        System.out.println("  - IEnviable: Solo para productos físicos");
        System.out.println("  - IDescargable: Solo para productos digitales");
        System.out.println("  - Ninguna clase implementa métodos que no usa\n");

        System.out.println("✓ DIP (Dependency Inversion Principle):");
        System.out.println("  - GestorInventario depende de IRepositorioInventario");
        System.out.println("  - No depende de implementaciones concretas");
        System.out.println("  - Fácil cambiar a base de datos sin modificar gestor\n");

        System.out.println("✓ Evita Antipatrón de Valores Quemados:");
        System.out.println("  - ConfiguracionInventario centraliza configuración");
        System.out.println("  - Valores parametrizados y configurables");
        System.out.println("  - Fácil mantenimiento y testing\n");

        System.out.println("═══════════════════════════════════════════════════════\n");
    }
}