package ExemploAluno;

import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    
    private Aluno aluno;
    List<Double> notas = new ArrayList<>();

    public Avaliacao(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        this.aluno = aluno;
    }

    public void addNota(double nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve estar entre 0 e 10");
        }
        // Adiciona a nota à lista de notas do aluno
        notas.add(nota);
    }

    public double calcularMedia() {
        if (notas.isEmpty()) {
            throw new IllegalStateException("Nenhuma nota adicionada para calcular a média");
        }
        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.size();
    }

    public String getSituacao() {
        double media = calcularMedia();
        if (media >= 7) {
            return "Aprovado";
        } else if (media >= 4) {
            return "Recuperação";
        } else {
            return "Reprovado";
        }
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "aluno=" + aluno +
                ", notas=" + notas +
                ", media=" + calcularMedia() +
                ", situacao='" + getSituacao() + '\'' +
                '}';
    }
}
