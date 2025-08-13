package parciales.interfaces;

import parciales.model.Criptomoneda;

//Interfaz que establece unos métodos obligatorios para la clase Transaccion
public interface ITransaccion {
    void setTipoTransaccion(String tipoTransaccion);

    void setCriptomoneda(Criptomoneda criptomoneda);

    void setCantidadCripto(int cantidadCriptomoneda);

}
