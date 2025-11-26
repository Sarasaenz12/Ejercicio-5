package inventario.interfaces;

public interface IDescargable {
    String generarEnlaceDescarga();
    double obtenerTamanoArchivo();
    boolean verificarLicencia(String usuarioId);
}