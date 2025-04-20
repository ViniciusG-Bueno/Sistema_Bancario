package DAO;

import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Produto;
import models.Empresa;

public class EmpresaDAO {

    public void create(Empresa empresa) {
        String sql = "INSERT INTO empresas (nome, cnpj) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getMySQLConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());

            stmt.executeUpdate();
            System.out.println("Empresa criada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Empresa read(String cnpj) throws SQLException {
        String sql = "SELECT * FROM empresas WHERE cnpj = ?";
        Empresa empresa = null;
    
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String nome = rs.getString("nome");
                empresa = new Empresa(nome, cnpj);
    
                List<String> emails = listarEmailsEmpresa(cnpj); //falta fazer a consulta/metodo para buscar os emails dessa empresa
                empresa.setEmails(emails);
    
                List<Produto> produtos = getProdutosEmpresa(cnpj); //falta fazer a consulta/metodo para buscar os produtos dessa empresa
                for (Produto produto : produtos) {
                    empresa.cadastrarProduto(produto);
                }
            }
        }
        return empresa;
    }
    

    public void update(Empresa empresa) throws SQLException {
        String sql = "UPDATE empresas SET nome = ? WHERE cnpj = ?";
    
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.executeUpdate();
        }
    }
    

    public void deletar(String cnpj) throws SQLException {
        String sql = "DELETE FROM empresas WHERE cnpj = ?";
    
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            stmt.executeUpdate();
        }
    }
    
    private int getEmpresaIdByCnpj(String cnpj) throws SQLException {
        String sql = "SELECT id FROM empresas WHERE cnpj = ?";
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Empresa com CNPJ " + cnpj + " não encontrada.");
                }
            }
        }
    }
    
    public boolean adicionarEmail(String cnpj, String email) throws SQLException {
        int empresaId = getEmpresaIdByCnpj(cnpj);
        String sql = "INSERT INTO emails_empresa (empresa_id, email) VALUES (?, ?)";

    try (Connection conn = ConnectionFactory.getMySQLConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, empresaId);
        stmt.setString(2, email);
        stmt.executeUpdate();
        return true;
    } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("E-mail já cadastrado para essa empresa.");
        return false;
    }
    }

    public boolean removerEmail(String cnpj, String email) throws SQLException {
        int empresaId = getEmpresaIdByCnpj(cnpj);
        String sql = "DELETE FROM emails_empresa WHERE empresa_id = ? AND email = ?";
    
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empresaId);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<String> listarEmailsEmpresa(String cnpj) throws SQLException {
        int empresaId = getEmpresaIdByCnpj(cnpj);
        List<String> emails = new ArrayList<>();
        String sql = "SELECT email FROM emails_empresa WHERE empresa_id = ?";
    
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("E-mails da empresa " + cnpj + ":");
            while (rs.next()) {
                emails.add(rs.getString("email"));
                System.out.println("Email: " + rs.getString("email"));
            }
        }
        return emails;
    }

    public List<Produto> getProdutosEmpresa(String cnpj) throws SQLException {
        int empresaId = getEmpresaIdByCnpj(cnpj);
        String sql = "SELECT p.codigo, p.nome, p.valor, p.quantidade FROM produtos p JOIN empresa_produtos ep ON ep.produto_id = p.codigo WHERE ep.empresa_id = ?";

        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empresaId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Produtos da empresa " + cnpj + ":");
            while (rs.next()) {
                Produto produto = new Produto(
                rs.getInt("codigo"),
                rs.getDouble("valor"),
                rs.getString("nome"),
                rs.getInt("quantidade")
                );
                produtos.add(produto);
                System.out.println("Produto: " + rs.getString("nome"));
            }
        }
        return produtos;
    }
}