package DAO;
import factory.ConnectionFactory;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Produto;

public class ProdutoDAO {

    public void create(Produto produto) {
        String sql = "INSERT INTO produtos (valor, nome, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getNome());
            stmt.setInt(4, produto.getQuantidade());

            stmt.execute();
            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> read() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                double valor = rs.getDouble("valor");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");

                Produto produto = new Produto(codigo, valor, nome, quantidade);
                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public Produto read(int codigo) {
        Produto produto = null;
        String sql = "SELECT * FROM produtos WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double valor = rs.getDouble("valor");
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");

                produto = new Produto(codigo, valor, nome, quantidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public void update(Produto produto) {
        String sql = "UPDATE produtos SET valor = ?, nome = ?, quantidade = ? WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, produto.getValor());
            stmt.setString(2, produto.getNome());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getCodigo());

            stmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void delete(int codigo) {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
            System.out.println("Produto excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
