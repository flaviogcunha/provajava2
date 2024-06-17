/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Dao.WeightHistDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author conta
 */

public class WeightHist {
    private LocalDate dataCadastro; // Data de registro do peso
    private double altura;
    private double peso;
    private String cpfAluno;
    

    public WeightHist() {}

    public WeightHist(LocalDate dataCadastro, double altura, double peso, String cpfAluno) {
        this.dataCadastro = dataCadastro;
        this.peso = peso;
        this.cpfAluno = cpfAluno;
        this.altura = altura;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }


}
