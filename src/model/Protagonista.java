package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Protagonista")
public class Protagonista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "poder", length = 200)
    private String poder;

    public Protagonista() {}

    public Protagonista(String nome, String poder) {
        this.nome = nome;
        this.poder = poder;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getPoder() { return poder; }
    public void setPoder(String poder) { this.poder = poder; }

    @Override
    public String toString() {
        return id + " - " + nome + " | Poder: " + poder;
    }
}