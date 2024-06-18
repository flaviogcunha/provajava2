/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author gusta
 */

import java.time.LocalDate;

public class HistoricoPeso {
    private int id;
    private LocalDate data;
    private double peso;
    private String cpf;
    private double altura;

    public HistoricoPeso() {}

    public HistoricoPeso(LocalDate data, double peso, String cpf, int id,double altura) {
        this.id=id;
        this.data = data;
        this.peso = peso;
        this.cpf = cpf;
        this.altura=altura;
    }

    public LocalDate getData() {
        return data;
    }
    
    public double getaltura(){
        return altura;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    
    public void setAltura(double altura){
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }
    
    public int getid(){
        return id;
    }
    
    public void setid(int id){
        this.id=id;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}



