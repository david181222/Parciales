package parciales.model;

import java.util.Objects;

public class Criptomoneda {
    private String id;
    private String symbol;
    private String name;
    private String price_usd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
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
