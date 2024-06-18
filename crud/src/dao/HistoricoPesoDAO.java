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
import modelo.HistoricoPeso;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPesoDAO {
    private Connection connection;

    public HistoricoPesoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvarPesoHistorico(String cpf, double peso,double altura, LocalDate data) throws SQLException {
        String sql = "INSERT INTO historico_peso (cpf, peso, altura, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/escola", "root", "Corinthians13");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setDouble(2, peso);
            stmt.setDouble(3, altura);
            stmt.setDate(4, java.sql.Date.valueOf(data));
            

            stmt.executeUpdate();
        }
    }

        public void adicionar(HistoricoPeso historico) {
        String sql = "INSERT INTO weighthist (data,altura , peso, cpf) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(historico.getData()));
            stmt.setDouble(2, historico.getPeso());
            stmt.setDouble(3, historico.getaltura());
            stmt.setString(4, historico.getCpf());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar histórico de peso: " + e.getMessage());
        }
    }

    public List<HistoricoPeso> consultarPorAluno(String cpf) {
        List<HistoricoPeso> historicos = new ArrayList<>();
        String sql = "SELECT * FROM historico_peso WHERE cpf = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistoricoPeso historico = new HistoricoPeso();
                Date dataSql = rs.getDate("data");
                LocalDate dataLocalDate = dataSql.toLocalDate();
                historico.setData(dataLocalDate);
                historico.setPeso(rs.getDouble("peso"));
                historico.setAltura(rs.getDouble("altura"));
                historico.setCpf(rs.getString("cpf"));
                historicos.add(historico);
            }
            rs.close();
            stmt.close();
            return historicos;
            
            
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar históricos de peso por aluno: " + e.getMessage());
        }
    }
}
