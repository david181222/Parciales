package parciales.services;

import java.util.LinkedList;
import java.util.Queue;

import parciales.model.Criptomoneda;
import parciales.model.Usuario;

public class TransactionProcessorService {
    static Queue<Transaccion> transacciones = new LinkedList<>();

    public static void meterTransaccion(Transaccion transaccion){
        transacciones.add(transaccion);
    }

    public static Transaccion sacarTransaccion(){
        return transacciones.poll();
    }

    
}
