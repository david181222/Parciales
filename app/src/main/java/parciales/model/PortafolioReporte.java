package parciales.model;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Esta clase tiene por objetivo filtrar el portafolio de un usuario solo para obtener nombre de la cripto y su cantidad,
// de modo que se pueda imprimir en el formato JSON
public class PortafolioReporte {
    private static final Logger logger = LogManager.getLogger(PortafolioReporte.class);
    private String nombreCriptomoneda;
    private int cantidadCriptomoneda;

    public PortafolioReporte(String nombreCriptomoneda, int cantidadCriptomoneda) {
        try {
            if (nombreCriptomoneda.isEmpty() || nombreCriptomoneda == null) {
                throw new Exception("El nombre de la criptomoneda no puede estar vacío");
            }
            if (cantidadCriptomoneda <= 0) {
                throw new Exception("La cantidad de la criptomoneda debe ser mayor a cero");
            }
            this.nombreCriptomoneda = nombreCriptomoneda;
            this.cantidadCriptomoneda = cantidadCriptomoneda;
        } catch (Exception e) {
            logger.error("Error al crear PortafolioReporte: " + e.getMessage());
            throw new IllegalArgumentException("Error al crear Portafolio para el reporte " + e.getMessage());
        }
    }

    public String getNombreCriptomoneda() {
        return nombreCriptomoneda;
    }

    public int getCantidadCriptomoneda() {
        return cantidadCriptomoneda;
    }

    @Override
    public String toString() {
        return "PortafolioReporte{" +
                "nombreCriptomoneda='" + nombreCriptomoneda + '\'' +
                ", cantidadCriptomoneda=" + cantidadCriptomoneda +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PortafolioReporte))
            return false;
        PortafolioReporte por = (PortafolioReporte) obj;
        if (!nombreCriptomoneda.equals(por.nombreCriptomoneda))
            return false;
        if (cantidadCriptomoneda != por.cantidadCriptomoneda)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCriptomoneda, cantidadCriptomoneda);
    }
}
