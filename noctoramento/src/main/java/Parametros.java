import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Parametros {

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoMySql();
    ParametrosConexao parametrosConexao = new ParametrosConexao();

    private Integer idParametros;
    private Integer tempoSegCapturaDeDados;
    private Integer tempoSegAlertas;
    private Double urgenteUsoCpu;
    private Double urgenteUsoDisco;
    private Double urgenteUsoMemoriaRam;
    private Double alertaUsoCpu;
    private Double alertaUsoDisco;
    private Double alertaUsoMemoriaRam;
    private Integer fkEmpresa;

    public Parametros(Integer idParametros, Integer tempoSegCapturaDeDados, Integer tempoSegAlertas, Double urgenteUsoCpu, Double urgenteUsoDisco, Double urgenteUsoMemoriaRam, Double alertaUsoCpu, Double alertaUsoDisco, Double alertaUsoMemoriaRam) {
        this.idParametros = idParametros;
        this.tempoSegCapturaDeDados = tempoSegCapturaDeDados;
        this.tempoSegAlertas = tempoSegAlertas;
        this.urgenteUsoCpu = urgenteUsoCpu;
        this.urgenteUsoDisco = urgenteUsoDisco;
        this.urgenteUsoMemoriaRam = urgenteUsoMemoriaRam;
        this.alertaUsoCpu = alertaUsoCpu;
        this.alertaUsoDisco = alertaUsoDisco;
        this.alertaUsoMemoriaRam = alertaUsoMemoriaRam;
    }

    public Parametros(){
    }


    public void alertar(Double usoCpu, Double usoDisco, Double usoMemoriaRam, Integer fkRegistro, Integer fkNotebook){

        String alerta = "";
        Boolean emitirAlerta = false;

        if (usoCpu > alertaUsoCpu && usoCpu < urgenteUsoCpu){
            alerta += ("Cpu está em estado de alerta: " + usoCpu + "\n");
            emitirAlerta = true;
        } else if (usoCpu > alertaUsoCpu && usoCpu > urgenteUsoCpu){
            alerta += ("Cpu está em estado crítico: " + usoCpu + "\n");
            emitirAlerta = true;
        }

        if (usoDisco > alertaUsoDisco && usoDisco < urgenteUsoDisco){
            alerta += ("Utilização do disco está em estado de alerta: " + usoDisco + "\n");
            emitirAlerta = true;
        } else if (usoDisco > alertaUsoDisco && usoDisco > urgenteUsoDisco){
            alerta += ("Utilização do disco está em estado crítico: " + usoDisco + "\n");
            emitirAlerta = true;
        }

        if (usoMemoriaRam > alertaUsoMemoriaRam && usoMemoriaRam < urgenteUsoMemoriaRam){
            alerta += ("Utilização da memória RAM está em estado de alerta: " + usoMemoriaRam + "\n");
            emitirAlerta = true;
        } else if (usoMemoriaRam > alertaUsoMemoriaRam && usoMemoriaRam > urgenteUsoMemoriaRam){
            alerta += ("Utilização da memória RAM está em estado crítico: " + usoMemoriaRam + "\n");
            emitirAlerta = true;
        }

        final String alertaFinal = alerta;

        if (emitirAlerta){

            // Código para enviar notificação via slack e notificar no console

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    System.out.println(alertaFinal);
                    Slack.EnviarAlertaSlack(alertaFinal);
                    Thread.sleep(tempoSegAlertas * 1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            });

            // Insert no banco mysql

            con.update("INSERT INTO Alerta (fkParametros, fkEmpresaParametros) VALUES (?, ?);",
                    idParametros, fkEmpresa);

            // Insert no SQL Server



        }

    }

    public void insertParametros (){
        con.update("INSERT INTO Parametros (tempoSegCapturaDeDados, tempoSegAlertas, urgenteUsoCpu, urgenteUsoDisco, urgenteUsoMemoriaRam,\n" +
                        "    alertaUsoCpu, alertaUsoDisco, alertaUsoMemoriaRam, fkEmpresa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
                tempoSegCapturaDeDados, tempoSegAlertas, urgenteUsoCpu, urgenteUsoDisco, urgenteUsoMemoriaRam, alertaUsoCpu, alertaUsoDisco, alertaUsoMemoriaRam, fkEmpresa);
    }

    public Integer getidParametros() {
        return idParametros;
    }

    public void setidParametros(Integer idParametros) {
        this.idParametros = idParametros;
    }

    public Integer getTempoSegCapturaDeDados() {
        return tempoSegCapturaDeDados;
    }

    public void setTempoSegCapturaDeDados(Integer tempoSegCapturaDeDados) {
        this.tempoSegCapturaDeDados = tempoSegCapturaDeDados;
    }

    public Integer getTempoSegAlertas() {
        return tempoSegAlertas;
    }

    public void setTempoSegAlertas(Integer tempoSegAlertas) {
        this.tempoSegAlertas = tempoSegAlertas;
    }

    public Double getUrgenteUsoCpu() {
        return urgenteUsoCpu;
    }

    public void setUrgenteUsoCpu(Double urgenteUsoCpu) {
        this.urgenteUsoCpu = urgenteUsoCpu;
    }

    public Double getUrgenteUsoDisco() {
        return urgenteUsoDisco;
    }

    public void setUrgenteUsoDisco(Double urgenteUsoDisco) {
        this.urgenteUsoDisco = urgenteUsoDisco;
    }

    public Double getUrgenteUsoMemoriaRam() {
        return urgenteUsoMemoriaRam;
    }

    public void setUrgenteUsoMemoriaRam(Double urgenteUsoMemoriaRam) {
        this.urgenteUsoMemoriaRam = urgenteUsoMemoriaRam;
    }

    public Double getAlertaUsoCpu() {
        return alertaUsoCpu;
    }

    public void setAlertaUsoCpu(Double alertaUsoCpu) {
        this.alertaUsoCpu = alertaUsoCpu;
    }

    public Double getAlertaUsoDisco() {
        return alertaUsoDisco;
    }

    public void setAlertaUsoDisco(Double alertaUsoDisco) {
        this.alertaUsoDisco = alertaUsoDisco;
    }

    public Double getAlertaUsoMemoriaRam() {
        return alertaUsoMemoriaRam;
    }

    public void setAlertaUsoMemoriaRam(Double alertaUsoMemoriaRam) {
        this.alertaUsoMemoriaRam = alertaUsoMemoriaRam;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    @Override
    public String toString() {
        return "Parametros{" +
                "parametrosConexao=" + parametrosConexao +
                ", idParametros=" + idParametros +
                ", tempoSegCapturaDeDados=" + tempoSegCapturaDeDados +
                ", tempoSegAlertas=" + tempoSegAlertas +
                ", urgenteUsoCpu=" + urgenteUsoCpu +
                ", urgenteUsoDisco=" + urgenteUsoDisco +
                ", urgenteUsoMemoriaRam=" + urgenteUsoMemoriaRam +
                ", alertaUsoCpu=" + alertaUsoCpu +
                ", alertaUsoDisco=" + alertaUsoDisco +
                ", alertaUsoMemoriaRam=" + alertaUsoMemoriaRam +
                ", fkEmpresa=" + fkEmpresa +
                '}';
    }
}
