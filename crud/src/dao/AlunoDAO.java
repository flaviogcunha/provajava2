/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author gusta
 */
import factory.ConnectionFactory;
import modelo.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AlunoDAO {
    private Connection connection;
    
    public AlunoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    
    public boolean existAluno(String cpf) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/escola", "root", "klewsgkjgrtg");
        
        String sql = "SELECT COUNT(*) FROM aluno WHERE cpf = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, cpf);
        rs = stmt.executeQuery();
        
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Fechar conexão, statement e resultSet
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return false;
}
    
    public void adiciona(Aluno aluno){
    
    String dataNascimento = aluno.getData_nascimento();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDate = LocalDate.parse(dataNascimento,formatter);
    String dataFormatada = localDate.format(DateTimeFormatter.ofPattern("yyyy/-MM-dd"));
    
        String sql = "INSERT INTO aluno VALUES(?,?,?,?,?)";
        try{
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, dataFormatada);
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            
            
            stmt.execute();
            stmt.close();
            
        }
        catch (SQLException e){
            throw new RuntimeException("Erro ao adicionar aluno:"+e.getMessage());
        }
    }
    public List<Aluno> listar() {
        List<Aluno> listaAlunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setCpf(rs.getString("cpf"));
                aluno.setNome(rs.getString("nome"));
                aluno.setData_nascimento(rs.getString("data_nascimento"));
                aluno.setPeso(rs.getDouble("peso"));
                aluno.setAltura(rs.getDouble("altura"));
                listaAlunos.add(aluno);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage());
        }
        return listaAlunos;
    }

    public void delete(String cpf) {
        String sql = "DELETE FROM aluno WHERE cpf = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno: " + e.getMessage());
        }
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, data_nascimento = ?, peso = ?, altura = ? WHERE cpf = ?";
        try {
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(aluno.getData_nascimento(),formatter);
            String dataFormatada=dataNascimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, dataFormatada);
            stmt.setDouble(3, aluno.getPeso());
            stmt.setDouble(4, aluno.getAltura());
            stmt.setString(5, aluno.getCpf());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage());
        }
    }
    public Aluno consultar(String cpf) {
    String sql = "SELECT * FROM aluno WHERE cpf = ?";

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        

        if (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            String dataNascimento=rs.getString("data_nascimento");
            LocalDate dataFormatada= LocalDate.parse(dataNascimento,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            aluno.setData_nascimento(dataFormatada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            aluno.setData_nascimento(rs.getString("data_nascimento"));
            aluno.setPeso(rs.getDouble("peso"));
            aluno.setAltura(rs.getDouble("altura"));
            return aluno;
        } else {
            return null;
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao consultar aluno: " + e.getMessage());
    }
}
}



