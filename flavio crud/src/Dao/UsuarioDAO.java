/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Factory.ConnectionFactory;
import Modelo.Cliente;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author conta
 */
public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Cliente cliente) {
        String sql = "INSERT INTO cliente (cpf, nome, data_de_nascimento, peso, altura) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getData_de_nascimento());
            stmt.setFloat(4, cliente.getPeso());
            stmt.setFloat(5, cliente.getAltura());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, data_de_nascimento = ?, peso = ?, altura = ? WHERE cpf = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getData_de_nascimento());
            stmt.setFloat(3, cliente.getPeso());
            stmt.setFloat(4, cliente.getAltura());
            stmt.setString(5, cliente.getCpf());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public Cliente consultar(String cpf) {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            Cliente cliente = null;
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));
                cliente.setData_de_nascimento(rs.getString("data_de_nascimento"));
                cliente.setPeso(rs.getFloat("peso"));
                cliente.setAltura(rs.getFloat("altura"));
            }
            rs.close();
            stmt.close();
            return cliente;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar cliente: " + e.getMessage());
        }
    }
    
    public void deletar(String cpf) {
    String sqlDeleteWeightHist = "DELETE FROM weighthist WHERE cpf = ?";
    String sqlDeleteCliente = "DELETE FROM cliente WHERE cpf = ?";
    PreparedStatement stmtWeightHist = null;
    PreparedStatement stmtCliente = null;

    try {
        // Start a transaction
        connection.setAutoCommit(false);

        // Delete from weighthist
        stmtWeightHist = connection.prepareStatement(sqlDeleteWeightHist);
        stmtWeightHist.setString(1, cpf);
        stmtWeightHist.executeUpdate();

        // Delete from cliente
        stmtCliente = connection.prepareStatement(sqlDeleteCliente);
        stmtCliente.setString(1, cpf);
        stmtCliente.executeUpdate();

        // Commit the transaction
        connection.commit();
    } catch (SQLException e) {
        if (connection != null) {
            try {
                // Rollback the transaction in case of error
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage());
    } finally {
        if (stmtWeightHist != null) {
            try {
                stmtWeightHist.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmtCliente != null) {
            try {
                stmtCliente.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }
}