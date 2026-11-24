package service;

import config.HibernateConfig;
import model.Autor;
import model.Comic;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class ServicioComic {

    // INSERT (guarda Comic y en cascada Autor/Protagonista si son nuevos)
    public void inserirComics(List<Comic> comics) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            for (Comic c : comics) {
                session.saveOrUpdate(c);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro inserindo cómics: " + e.getMessage());
        }
    }

    // READ (lista agrupada por Autor con el formato exacto del enunciado)
    public void listarPorAutor() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Autor> autores = session.createQuery("FROM Autor ORDER BY id", Autor.class).list();
            if (autores.isEmpty()) {
                System.out.println("(Sen datos nas táboas)");
                return;
            }
            for (Autor a : autores) {
                System.out.println("-------------------------------------------------------------------------------------------------");
                System.out.println("Autor: " + a.getNome() + " " + a.getApelidos());
                System.out.println("Comics:");
                for (Comic c : a.getComics()) {
                    System.out.println("\t" + c.getTitulo() + " (" + c.getAno() + ")");
                    System.out.println("\tProtagonista: " + c.getProtagonista().getNome()
                            + " + poder: " + c.getProtagonista().getPoder());
                }
                System.out.println("-------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro listando: " + e.getMessage());
        }
    }

    public List<Comic> lerTodos() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM Comic ORDER BY id", Comic.class).list();
        } catch (Exception e) {
            System.out.println("Erro lendo cómics: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // DELETE (borra primero Comic porque tiene las Foreign Keys)
    public void borrarTodos() {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM Comic").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro borrando cómics: " + e.getMessage());
        }
    }
}