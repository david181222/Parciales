package parciales.model;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Clase que define una criptomoneda con los datos obtenidos de la API
public class Criptomoneda {

    public static final Logger logger = LogManager.getLogger(Criptomoneda.class);
    private String id;
    private String symbol;
    private String name;
    private String price_usd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("El ID de la criptomoneda no puede ser nulo o vacío");
            }
            this.id = id;
            logger.info("ID establecido para criptomoneda: {}", id);
        } catch (Exception e) {
            logger.error("Error al establecer ID de criptomoneda: {}", e.getMessage());
            throw e;
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        try {
            if (symbol == null || symbol.trim().isEmpty()) {
                throw new IllegalArgumentException("El símbolo de la criptomoneda no puede ser nulo o vacío");
            }
            this.symbol = symbol;
            logger.info("Símbolo establecido para criptomoneda: {}", symbol);
        } catch (Exception e) {
            logger.error("Error al establecer símbolo de criptomoneda: {}", e.getMessage());
            throw e;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la criptomoneda no puede ser nulo o vacío");
            }
            this.name = name;
            logger.info("Nombre establecido para criptomoneda: {}", name);
        } catch (Exception e) {
            logger.error("Error al establecer nombre de criptomoneda: {}", e.getMessage());
            throw e;
        }
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        try {
            if (price_usd == null || price_usd.trim().isEmpty()) {
                throw new IllegalArgumentException("El precio en USD no puede ser nulo o vacío");
            }
            // Validar que el precio sea un número válido
            try {
                double precio = Double.parseDouble(price_usd);
                if (precio < 0) {
                    throw new IllegalArgumentException("El precio no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El precio debe ser un número válido");
            }
            
            this.price_usd = price_usd;
            logger.info("Precio establecido para criptomoneda {}: ${} USD", this.symbol, price_usd);
        } catch (Exception e) {
            logger.error("Error al establecer precio de criptomoneda: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public String toString() {
        return "Criptomoneda{" +
                "id='" + id + '\'' +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", price_usd='" + price_usd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Criptomoneda))
            return false;
        Criptomoneda criptomoneda = (Criptomoneda) obj;
        if (!id.equals(criptomoneda.id))
            return false;
        if (!symbol.equals(criptomoneda.symbol))
            return false;
        if (!name.equals(criptomoneda.name))
            return false;
        return price_usd.equals(criptomoneda.price_usd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol, name, price_usd);
    }
}
