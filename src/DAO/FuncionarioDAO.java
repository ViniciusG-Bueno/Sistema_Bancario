package DAO;
import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.ContaCorrente;
import models.Funcionario;

public class FuncionarioDAO {

    public void create(Funcionario funcionario) {
        PessoaDAO pessoaDAO = new PessoaDAO();
        ContaBancariaDAO contaDAO = new ContaBancariaDAO();

        int pessoaId = pessoaDAO.create(funcionario);

        int contaId = contaDAO.create(funcionario.getContaSalario());

        String sql = "INSERT INTO funcionarios (pessoa_id, cargo, salario, conta_salario_id) VALUES (?, ?, ?, ?)";
        
        try (
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

           stmt.setInt(1, pessoaId);
           stmt.setString(2, funcionario.getCargo());
           stmt.setDouble(3, funcionario.getSalario());
           stmt.setInt(4, contaId);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Funcionario> read() {
        String sql = "SELECT f.id, f.cargo, f.salario, f.conta_salario_id, p.nome, p.idade, p.endereco, p.cpf FROM funcionarios f JOIN pessoas p ON f.pessoa_id = p.id";

        List<Funcionario> funcionarios = new ArrayList<>();

        try(
            Connection conn = ConnectionFactory.getMySQLConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        ){
            while(rs.next()){
                ContaCorrente conta = new ContaCorrente(rs.getInt("conta_salario_id"));
                
                System.out.println("----------------------------------");
                System.out.println("Funcionario id: "+rs.getInt("id"));
                System.out.println("Nome: "+rs.getString("nome"));
                System.out.println("Idade: "+rs.getInt("idade"));
                System.out.println("Endereco: "+rs.getString("endereco"));
                System.out.println("CPF: "+rs.getString("cpf"));
                System.out.println("Cargo: "+rs.getString("cargo"));
                System.out.println("Salario: R$ "+rs.getDouble("salario"));
                System.out.println("Conta: "+rs.getInt("conta_salario_id"));
                System.out.println("----------------------------------");
                
                Funcionario f = new Funcionario(
                    rs.getString("nome"), 
                    rs.getInt("idade"), 
                    rs.getString("endereco"), 
                    rs.getString("cpf"), 
                    rs.getString("cargo"), 
                    rs.getDouble("salario"), 
                    conta
                );

                
                f.setId(rs.getInt("id"));
                funcionarios.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionarios;
}

    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET cargo=?, salario=? WHERE id=?";

        try(
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setString(1, funcionario.getNome());
            stmt.setInt(2, funcionario.getIdade());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setString(4, funcionario.getCpf());
            stmt.setString(5, funcionario.getCargo());
            stmt.setDouble(6, funcionario.getSalario());
            stmt.setInt(7, funcionario.getId());

            int update_instances = stmt.executeUpdate();

            if(update_instances == 0){
                System.out.println("Funcionario nao encontrado!");
                return;
            }

            System.out.println("Funcionario Editado com Sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Funcionario funcionario) {
        String sql = "DELETE FROM funcionarios WHERE id=?";

        try(
            Connection conn = ConnectionFactory.getMySQLConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, funcionario.getId());

            int update_instances = stmt.executeUpdate();

            if(update_instances == 0){
                System.out.println("Funcionario nao encontrado!");
                return;
            }

            System.out.println("Funcionario Deletado com Sucesso! \nFuncionarios deletados: "+update_instances);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
