package inventario.interfaces;

//PRINCIPIO ISP (Interface Segregation Principle):
//Aquí está la clave de ISP. Se separaron las capacidades en interfaces pequeñas.
//Solo para productos físicos.

public interface IEnviable {
    double calcularCostoEnvio(String destino);
    double obtenerPeso();
    String prepararParaEnvio(String direccionDestino);
}