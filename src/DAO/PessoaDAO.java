package DAO;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Pessoa;

public class PessoaDAO {

    public int create(Pessoa pessoa) {
        String sql = "INSERT INTO pessoas (nome, idade, endereco, cpf) VALUES (?, ?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setString(3, pessoa.getEndereco());
            stmt.setString(4, pessoa.getCpf());

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pessoa.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Pessoa> read() {
        String sql = "SELECT * FROM pessoas";
        List<Pessoa> pessoas = new ArrayList<>();

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("endereco"),
                    rs.getString("cpf")
                );
                pessoa.setId(rs.getInt("id"));
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pessoas;
    }

    public void update(Pessoa pessoa) {
        String sql = "UPDATE pessoas SET nome = ?, idade = ?, endereco = ?, cpf = ? WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setString(3, pessoa.getEndereco());
            stmt.setString(4, pessoa.getCpf());
            stmt.setInt(5, pessoa.getId());

            int updated = stmt.executeUpdate();
            if (updated == 0) {
                System.out.println("Pessoa não encontrada!");
            } else {
                System.out.println("Pessoa atualizada com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Pessoa pessoa) {
        String sql = "DELETE FROM pessoas WHERE id = ?";

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, pessoa.getId());

            int deleted = stmt.executeUpdate();
            if (deleted == 0) {
                System.out.println("Pessoa não encontrada!");
            } else {
                System.out.println("Pessoa removida com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}