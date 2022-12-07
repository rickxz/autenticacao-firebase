package com.example.autenticacaofirebase;

public class Aluno {
    private String nome;
    private double nota1;
    private double nota2;
    public Aluno(String n, double n1, double n2) {
        this.nome = n; this.nota1 = n1; this.nota2 = n2;
    }
    public Aluno() {}
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getNota1() {
        return this.nota1;
    }
    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }
    public double getNota2() {
        return this.nota2;
    }
    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }
}