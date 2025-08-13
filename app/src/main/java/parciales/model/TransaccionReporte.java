package parciales.model;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//El objetivo de esta clase es filtrar las transacciones del historial, para obtener los datos filtrados y organizados para
// poder imprimir este tipo de objeto en el archivo JSON con el formato como tal. Ya que Gson debe mapear el objeto para imprimirlo en el archivo

public class TransaccionReporte {
    private static final Logger logger = LogManager.getLogger(TransaccionReporte.class);
    private String estatus;
    private String tipoTransaccion;
    private String nombreCriptomoneda;
    private String simboloCriptomoneda;
    private int cantidad;
    private double precioUSD;
    private double valorTransaccionCOP;

    public TransaccionReporte(String estatus, String tipoTransaccion, String nombreCriptomoneda,
            String simboloCriptomoneda, int cantidad, double precioUSD, double valorTransaccionCOP) {

        try {
            if (estatus.isEmpty() | estatus == null) {
                throw new Exception("El estatus no puede estar vacío");
            }
            if (tipoTransaccion.isEmpty() | tipoTransaccion == null) {
                throw new Exception("El tipo de transacción no puede estar vacío");
            }
            if (nombreCriptomoneda.isEmpty() | nombreCriptomoneda == null) {
                throw new Exception("El nombre de la criptomoneda no puede estar vacío");
            }
            if (simboloCriptomoneda.isEmpty() | simboloCriptomoneda == null) {
                throw new Exception("El símbolo de la criptomoneda no puede estar vacío");
            }
            if (cantidad <= 0) {
                throw new Exception("La cantidad debe ser mayor a cero");
            }
            if (precioUSD <= 0) {
                throw new Exception("El precio en USD debe ser mayor a cero");
            }
            if (valorTransaccionCOP <= 0) {
                throw new Exception("El valor de la transacción en COP debe ser mayor a cero");
            }
            logger.info("Creando transacción para reporte");

            this.estatus = estatus;
            this.tipoTransaccion = tipoTransaccion;
            this.nombreCriptomoneda = nombreCriptomoneda;
            this.simboloCriptomoneda = simboloCriptomoneda;
            this.cantidad = cantidad;
            this.precioUSD = precioUSD;
            this.valorTransaccionCOP = valorTransaccionCOP;

        } catch (Exception e) {
            logger.error("Error al crear transacción para el reporte");
            throw new IllegalArgumentException("Error al crear la transacción para el reporte: " + e.getMessage());
        }
    }

    public String getTipoTransaccion() {
        return this.tipoTransaccion;
    }

    public String getNombreCriptomoneda() {
        return this.nombreCriptomoneda;
    }

    public String getSimboloCriptomoneda() {
        return this.simboloCriptomoneda;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public double getPrecioUSD() {
        return this.precioUSD;
    }

    public double getValorTransaccionCOP() {
        return this.valorTransaccionCOP;
    }

    public String getEstatus() {
        return this.estatus;
    }

   @Override
    public String toString() {
        return "TransaccionReporte{" +
                "estatus='" + estatus + '\'' +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", nombreCriptomoneda='" + nombreCriptomoneda + '\'' +
                ", simboloCriptomoneda='" + simboloCriptomoneda + '\'' +
                ", cantidad=" + cantidad +
                ", precioUSD=" + precioUSD +
                ", valorTransaccionCOP=" + valorTransaccionCOP +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof TransaccionReporte))
            return false;
        TransaccionReporte transaccionR = (TransaccionReporte) obj;
        if (!estatus.equals(transaccionR.estatus))
            return false;
        if (!tipoTransaccion.equals(transaccionR.tipoTransaccion))
            return false;
        if (!nombreCriptomoneda.equals(transaccionR.nombreCriptomoneda))
            return false;
        if (!simboloCriptomoneda.equals(transaccionR.simboloCriptomoneda))
            return false;
        if (cantidad != transaccionR.cantidad)
            return false;
        if (precioUSD != transaccionR.precioUSD)
            return false;
        return valorTransaccionCOP == transaccionR.valorTransaccionCOP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(estatus, tipoTransaccion, nombreCriptomoneda, simboloCriptomoneda, cantidad, precioUSD, valorTransaccionCOP);
    }

}
