package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Comic")
public class Comic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "ano")
    private int ano;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_protagonista", nullable = false)
    private Protagonista protagonista;

    public Comic() {}

    public Comic(String titulo, int ano, Autor autor, Protagonista protagonista) {
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.protagonista = protagonista;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public Protagonista getProtagonista() { return protagonista; }
    public void setProtagonista(Protagonista protagonista) { this.protagonista = protagonista; }
}