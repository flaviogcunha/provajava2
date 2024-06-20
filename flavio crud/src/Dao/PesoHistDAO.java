/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Dao;

import Factory.ConnectionFactory;
import Modelo.PesoHist;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Date;

public class PesoHistDAO {

    private Connection connection;

    public PesoHistDAO() {
        this.connection = new ConnectionFactory().getConnection(); // Inicializa a conexão corretamente
    }

    public void adicionar(PesoHist historico) {
        String sql = "INSERT INTO weighthist (data,altura , peso, cpf) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(historico.getDataCadastro()));
            stmt.setDouble(2, historico.getPeso());
            stmt.setDouble(3, historico.getAltura());
            stmt.setString(4, historico.getCpfAluno());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar histórico de peso: " + e.getMessage());
        }
    }

    public List<PesoHist> consultarPorCliente(String cpfAluno) {
    List<PesoHist> historicos = new ArrayList<>();
    String sql = "SELECT * FROM weighthist WHERE cpf = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpfAluno);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PesoHist historico = new PesoHist();
                // Obtendo a data como java.sql.Date
                Date dataSql = rs.getDate("data");
                // Convertendo java.sql.Date para LocalDate
                LocalDate dataLocalDate = dataSql.toLocalDate();
                historico.setDataCadastro(dataLocalDate);
                historico.setAltura(rs.getDouble("altura"));
                historico.setPeso(rs.getDouble("peso"));
                historico.setCpfAluno(rs.getString("cpf"));
                historicos.add(historico);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao consultar históricos de peso por cliente: " + e.getMessage());
    }
    return historicos;
}

    public void salvarPesoHistorico(String cpf, float peso,float altura, LocalDate data) {
        String sql = "INSERT INTO weighthist (cpf, peso, altura, data) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vendas", "root", "klewsgkjgrtg");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setFloat(2, peso);
            stmt.setFloat(3, altura);
            stmt.setDate(4, java.sql.Date.valueOf(data));
            

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar histórico de peso: " + e.getMessage());
        }
    }
}