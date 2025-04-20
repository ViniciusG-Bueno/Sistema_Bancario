package DAO;

import factory.ConnectionFactory;
import models.ContaBancaria;
import models.ContaCorrente;
import models.ContaPoupanca;

import java.sql.*;

public class ContaBancariaDAO {

    public int create(ContaBancaria conta) {
        String sql = "INSERT INTO contas_bancarias (titular, saldo, conta, agencia, senha, tipo, tarifa, rendimento_mensal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setString(1, conta.getTitular());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getConta());
            stmt.setInt(4, conta.getAgencia());
            stmt.setString(5, conta.getSenha());

            if (conta instanceof ContaCorrente) {
                stmt.setString(6, "corrente");
                stmt.setDouble(7, ((ContaCorrente) conta).getTarifa());
                stmt.setNull(8, Types.DECIMAL);
            } else if (conta instanceof ContaPoupanca) {
                stmt.setString(6, "poupanca");
                stmt.setNull(7, Types.DECIMAL);
                stmt.setDouble(8, ((ContaPoupanca) conta).getRendimentoMensal());
            } else {
                throw new IllegalArgumentException("Tipo de conta bancária desconhecido.");
            }

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                conta.setId(id);
                return id;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ContaBancaria read(int id) {
        String sql = "SELECT * FROM contas_bancarias WHERE id = ?";
    
        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                if ("corrente".equalsIgnoreCase(tipo)) {
                    return new ContaCorrente(
                        rs.getInt("id"),
                        rs.getString("titular"),
                        rs.getDouble("saldo"),
                        rs.getInt("conta"),
                        rs.getInt("agencia"),
                        rs.getString("senha")
                    );
                } else if ("poupanca".equalsIgnoreCase(tipo)) {
                    return new ContaPoupanca(
                        rs.getInt("id"),
                        rs.getString("titular"),
                        rs.getDouble("saldo"),
                        rs.getInt("conta"),
                        rs.getInt("agencia"),
                        rs.getString("senha")
                    );
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }

    public void update(ContaBancaria conta) {
        String sql = "UPDATE contas_bancarias SET titular = ?, saldo = ?, conta = ?, agencia = ?, senha = ?, tipo = ?, tarifa = ?, rendimento_mensal = ? WHERE id = ?";
    
        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, conta.getTitular());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getConta());
            stmt.setInt(4, conta.getAgencia());
            stmt.setString(5, conta.getSenha());
    
            if (conta instanceof ContaCorrente) {
                stmt.setString(6, "corrente");
                stmt.setDouble(7, ((ContaCorrente) conta).getTarifa());
                stmt.setNull(8, Types.DECIMAL);
            } else if (conta instanceof ContaPoupanca) {
                stmt.setString(6, "poupanca");
                stmt.setNull(7, Types.DECIMAL);
                stmt.setDouble(8, ((ContaPoupanca) conta).getRendimentoMensal());
            } else {
                throw new IllegalArgumentException("Tipo de conta desconhecido.");
            }
    
            stmt.setInt(9, conta.getId());
    
            stmt.executeUpdate();
            System.out.println("Conta atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM contas_bancarias WHERE id = ?";
    
        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Conta removida com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
