package parciales.interfaces;

import parciales.model.Criptomoneda;

public interface ITransaccion {
    void setTipoTransaccion(String tipoTransaccion);
    void setCriptomoneda(Criptomoneda criptomoneda);
    void setCantidadCripto(int cantidadCriptomoneda);

}
