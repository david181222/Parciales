package parciales.services;

import java.util.LinkedList;
import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import parciales.model.Criptomoneda;
import parciales.model.Transaccion;
import parciales.model.Usuario;

//Esta clase tiene por objetivo desencolar las transacciones hechas en el main, de modo que podemos validarlas y procesar las operaciones en los usuarios
public class TransactionProcessorService {

    public static final Logger logger = LogManager.getLogger(TransactionProcessorService.class);

    private static Queue<Transaccion> transacciones = new LinkedList<>(); // Se elige una cola para procesarlas en orden
                                                                          // de llegada (FIFO)

    public static void meterTransaccion(Transaccion transaccion) {
        try {
            if (transaccion == null) {
                throw new IllegalArgumentException("La transacción no puede ser nula");
            }
            transacciones.add(transaccion);
            logger.info("Transacción agregada a la cola: {} {} de {} por {}", 
                transaccion.getTipoTransaccion(), 
                transaccion.getCantidadCripto(), 
                transaccion.getCriptomoneda().getSymbol(), 
                transaccion.getUsuario().getName());
        } catch (Exception e) {
            logger.error("Error al agregar transacción a la cola: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al procesar la transacción: " + e.getMessage());
        }
    }

    public static Transaccion sacarTransaccion() {
        try {
            Transaccion transaccion = transacciones.poll();
            if (transaccion != null) {
                logger.info("Transacción extraída de la cola: {} {} de {} por {}", 
                    transaccion.getTipoTransaccion(), 
                    transaccion.getCantidadCripto(), 
                    transaccion.getCriptomoneda().getSymbol(), 
                    transaccion.getUsuario().getName());
            }
            return transaccion;
        } catch (Exception e) {
            logger.error("Error al extraer transacción de la cola: {}", e.getMessage());
            return null;
        }
    }

    public static boolean transaccionesEnCola() {
        return !transacciones.isEmpty();
    }

    public static Transaccion procesarSiguienteTransaccion() {
        try {
            Transaccion transaccion = sacarTransaccion();
            if (transaccion == null) {
                logger.info("No hay más transacciones en la cola para procesar");
                return null;
            }
            logger.info("Procesando siguiente transacción...");
            return transaccion;
        } catch (Exception e) {
            logger.error("Error al procesar siguiente transacción: {}", e.getMessage());
            return null;
        }
    }

    public static boolean ejecutarTransaccion(Transaccion transaccion) {
        try {
            if (transaccion == null) {
                throw new IllegalArgumentException("La transacción no puede ser nula");
            }
            
            Usuario usuario = transaccion.getUsuario();
            Criptomoneda cripto = transaccion.getCriptomoneda();
            int cantidad = transaccion.getCantidadCripto();
            String tipo = transaccion.getTipoTransaccion();

            logger.info("Ejecutando transacción: {} {} de {} por {}", tipo, cantidad, cripto.getSymbol(), usuario.getName());

            if (tipo.equals("Compra")) {
                return ejecutarCompra(usuario, cripto, cantidad, transaccion);
            } else if (tipo.equals("Venta")) {
                return ejecutarVenta(usuario, cripto, cantidad, transaccion);
            }
            
            logger.error("Tipo de transacción no válido: {}", tipo);
            return false;
        } catch (Exception e) {
            logger.error("Error al ejecutar transacción: {}", e.getMessage());
            return false;
        }
    }

    private static boolean ejecutarCompra(Usuario usuario, Criptomoneda cripto, int cantidad, Transaccion transaccion) {
        try {
            int fluctuacion = (int) (Math.random() * 20) - 10; // Entre el -10% y el 10%
            double precioFluctuado = Double.parseDouble(cripto.getPrice_usd()) * (1 + fluctuacion / 100.0);
            double precioTotalEnCOP = (precioFluctuado * cantidad) * 4000;

            logger.info("Intentando compra: {} {} de {} por ${} COP", cantidad, cripto.getSymbol(), usuario.getName(), precioTotalEnCOP);

            // Validar si el usuario tiene dinero para la transaccion
            double saldoUsuarioCOP = usuario.getSaldoCOP();

            if (saldoUsuarioCOP >= precioTotalEnCOP) {
                usuario.disminuirSaldo(precioTotalEnCOP);

                for (int i = 0; i < cantidad; i++) {
                    usuario.getPortafolio().add(cripto);
                }
                transaccion.setEstatus("Valida");
                usuario.getHistorial().push(transaccion);

                logger.info("Compra exitosa: {} {} de {} por {}", cantidad, cripto.getSymbol(), usuario.getName(), precioTotalEnCOP);
                System.out.println("Se completo la compra de " + transaccion.getCriptomoneda().getSymbol() + " en cantidad: "
                        + transaccion.getCantidadCripto() + " por el usuario: " + usuario.getName());
                return true;
            } else {
                transaccion.setEstatus("Invalida");
                usuario.getHistorial().push(transaccion);
                logger.error("Compra fallida por saldo insuficiente: Usuario {} tiene ${} COP, necesita ${} COP", 
                    usuario.getName(), saldoUsuarioCOP, precioTotalEnCOP);
                throw new IllegalArgumentException("No se pudo completar la compra.");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir precio de criptomoneda: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al procesar el precio de la criptomoneda: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en ejecutarCompra: {}", e.getMessage());
            throw e;
        }
    }

    private static boolean ejecutarVenta(Usuario usuario, Criptomoneda cripto, int cantidad, Transaccion transaccion) {
        try {
            int cantidadCriptosEnPortafolio = usuario.getPortafolio().getCount(cripto);

            logger.info("Intentando venta: {} {} de {} (disponible: {})", cantidad, cripto.getSymbol(), usuario.getName(), cantidadCriptosEnPortafolio);

            // Validar si el usuario tiene suficientes criptomonedas en su portafolio
            if (cantidadCriptosEnPortafolio >= cantidad && cantidad > 0) {

                int fluctuacion = (int) (Math.random() * 20) - 10; // Entre -10% y +10%
                double precioFluctuado = Double.parseDouble(cripto.getPrice_usd()) * (1 + fluctuacion / 100.0);
                double precioTotalUSD = precioFluctuado * cantidad;

                double precioTotalCOP = precioTotalUSD * 4000;
                usuario.aumentarSaldo(precioTotalCOP);

                for (int i = 0; i < cantidad; i++) {
                    usuario.getPortafolio().remove(cripto);
                }
                transaccion.setEstatus("Valida");
                usuario.getHistorial().push(transaccion);

                logger.info("Venta exitosa: {} {} de {} por ${} COP", cantidad, cripto.getSymbol(), usuario.getName(), precioTotalCOP);
                System.out.println("Se completó la venta de " + transaccion.getCriptomoneda().getSymbol() + " en cantidad: "
                        + transaccion.getCantidadCripto() + " por el usuario: " + usuario.getName());
                return true;
            } else {
                transaccion.setEstatus("Invalida");
                usuario.getHistorial().push(transaccion);
                logger.error("Venta fallida por cantidad insuficiente: Usuario {} tiene {} {}, quiere vender {}", 
                    usuario.getName(), cantidadCriptosEnPortafolio, cripto.getSymbol(), cantidad);
                throw new IllegalArgumentException("No se pudo completar la venta.");
            }
        } catch (NumberFormatException e) {
            logger.error("Error al convertir precio de criptomoneda: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al procesar el precio de la criptomoneda: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en ejecutarVenta: {}", e.getMessage());
            throw e;
        }
    }

}
