package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import factory.ConnectionFactory;
import models.Pessoa;
public class PessoaDAO {

    public void create(Pessoa pessoa){
        String sql = "INSERT INTO pessoas (nome, idade, endereco, cpf) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ){
        
            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setString(3, pessoa.getEndereco());
            stmt.setString(4, pessoa.getCpf());

            stmt.execute();

            System.out.println("Pessoa criada com Sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pessoa> read(){
        // read
        return null;
    }

    public void update(){
        // update
    }

    public void delete(){
        // delete
    }
}
