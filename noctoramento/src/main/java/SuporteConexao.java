import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

public class SuporteConexao {

    Conexao conexaoMySQL = new Conexao();
    JdbcTemplate con = conexaoMySQL.getConexaoMySql();

    public Integer contarUsuarioExistente(String email, String senha){

        String sql = "SELECT COUNT(*) FROM Usuario WHERE emailUsuario = ? AND senhaUsuario = ?;";

        try {
            Integer countLocal = con.queryForObject(sql, Integer.class, email, senha);
            return countLocal;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (con != null){
                try{
                    con.getDataSource().getConnection().close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer capturarIdEmpresa(String email, String senha){

        String sql = "SELECT fkEmpresa FROM Usuario WHERE emailUsuario = ? AND senhaUsuario = ?;";

        try {
            Integer idEmpresa = con.queryForObject(sql, Integer.class, email, senha);
            return idEmpresa;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (con != null){
                try{
                    con.getDataSource().getConnection().close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public Empresa cadastrarEmpresa(Integer idEmpresa){

        String sql = "SELECT * FROM Empresa WHERE idEmpresa = ?;";

        try {
            Empresa empresa = con.queryForObject(sql, new BeanPropertyRowMapper<>(Empresa.class), idEmpresa);
            return empresa;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null){
                try{
                    con.getDataSource().getConnection().close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

}
