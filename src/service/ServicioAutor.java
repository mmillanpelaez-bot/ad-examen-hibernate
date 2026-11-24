package service;

import config.HibernateConfig;
import model.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioAutor {

    // UPDATE por nombre completo ("Stan Lee") y devuelve el Autor actualizado
    public Autor actualizarNomePorNomeCompleto(String nomeCompletoVello, String novoNome, String novoApelido) {
        Transaction tx = null;
        Autor actualizado = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            String[] partes = nomeCompletoVello.trim().split("\\s+", 2);
            Autor a = session.createQuery(
                            "FROM Autor WHERE nome = :n AND apelidos = :ap", Autor.class)
                    .setParameter("n", partes[0])
                    .setParameter("ap", partes.length > 1 ? partes[1] : "")
                    .uniqueResult();

            if (a != null) {
                a.setNome(novoNome);
                a.setApelidos(novoApelido);
                session.update(a);
                actualizado = a;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro actualizando autor: " + e.getMessage());
        }
        return actualizado;
    }

    // UPDATE desde un objeto Autor (útil si te piden restaurar desde serializado/XML)
    public void actualizarAutor(Autor autorDatos) {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(autorDatos);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro actualizando autor: " + e.getMessage());
        }
    }

    // DELETE
    public void borrarTodos() {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM Autor").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro borrando autores: " + e.getMessage());
        }
    }
}