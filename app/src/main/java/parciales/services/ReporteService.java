package parciales.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.collections4.Bag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import parciales.model.Usuario;
import parciales.model.UsuarioReporte;
import parciales.model.PortafolioReporte;
import parciales.model.TransaccionReporte;
// Esta clase se encarga de generar y guardar los reportes de los usuarios con la información requerida (UsuarioReporte)
public class ReporteService {
    private static final Logger logger = LogManager.getLogger(ReporteService.class);
    private static final String REPORTES_FILE_PATH = "app/src/main/resources/reportes.json";
    private final Gson gson;

    public ReporteService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void guardarReporte(Queue<UsuarioReporte> usuariosReportes) {
        try {
            if (usuariosReportes == null || usuariosReportes.isEmpty()) {
                logger.info("No hay reportes de usuarios para guardar");
                return;
            }
            
            logger.info("Iniciando guardado de reportes...");
            try (FileWriter writer = new FileWriter(REPORTES_FILE_PATH)) {
                gson.toJson(usuariosReportes, writer);
                logger.info("Se guardaron {} reportes en el archivo {}", usuariosReportes.size(), REPORTES_FILE_PATH);
            }
        } catch (IOException e) {
            logger.error("Error al guardar el archivo de reportes: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al guardar el archivo de reportes: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado al guardar reportes: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error inesperado al guardar reportes: " + e.getMessage());
        }
    }

    //Aqui se procesan los usuarios de la cola del main luego de los turnos, para obtener los datos finales
    // llegan en cola, es decir, en el orden que participaron en los turnos quedando en las posiciones originales,
    //
    public Queue<UsuarioReporte> usuariosProcesados(Queue<Usuario> reporteUsuarios) {
        try {
            if (reporteUsuarios == null || reporteUsuarios.isEmpty()) {
                logger.info("No hay usuarios para procesar en el reporte");
                return new LinkedList<>();
            }

            logger.info("Procesando {} usuarios para el reporte...", reporteUsuarios.size());
            Queue<UsuarioReporte> usuariosReportes = new LinkedList<>();
            String nombreReporte;
            double saldoUSDReporte;
            double valorPortafolioReporte;
            Bag<PortafolioReporte> portafolioReporte;
            Stack<TransaccionReporte> historialReporte;

            for (Usuario usuario : reporteUsuarios) {
                try {
                    nombreReporte = usuario.getName();
                    saldoUSDReporte = usuario.getSaldoUSD();
                    valorPortafolioReporte = usuario.getValorPortafolioUSD();
                    portafolioReporte = usuario.getPortafolioReporte(); 
                    historialReporte = usuario.getHistorialReporte(); 

                    UsuarioReporte usuarioReporte = new UsuarioReporte(nombreReporte, saldoUSDReporte, valorPortafolioReporte, portafolioReporte, historialReporte);
                    usuariosReportes.add(usuarioReporte);

                    logger.info("Usuario procesado para reporte: {} - Saldo: ${} USD - Portafolio: ${} USD", 
                        nombreReporte, saldoUSDReporte, valorPortafolioReporte);
                } catch (Exception e) {
                    logger.error("Error al procesar usuario {} para reporte: {}", usuario.getName(), e.getMessage());
                }
            }
            
            logger.info("Procesamiento de usuarios completado. {} reportes generados", usuariosReportes.size());
            return usuariosReportes;
        } catch (Exception e) {
            logger.error("Error inesperado al procesar usuarios para reporte: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al procesar usuarios para reporte: " + e.getMessage());
        }
    }

}
