package inventario.servicios;

public class Configuracion {
    // 1. Constructor privado para evitar instanciación (Regla java:S1118)
    private Configuracion() {
        throw new IllegalStateException("Clase de utilidad");
    }

    public static final double COSTO_BASE_KM = 0.50;
    public static final double IMPUESTO_BASE = 0.19;
    public static final String URL_BASE_DESCARGA = "https://api.mitienda.com/v1/downloads/";
    public static final int STOCK_MINIMO_ALERTA = 5;
}