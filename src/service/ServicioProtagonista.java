package service;

import config.HibernateConfig;
import model.Protagonista;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServicioProtagonista {

    // UPDATE (usando lower() para evitar problemas entre "Spider-Man" y "Spider-man")
    public Protagonista actualizarNome(String nomeVello, String novoNome) {
        Transaction tx = null;
        Protagonista actualizado = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Protagonista p = session.createQuery(
                            "FROM Protagonista WHERE lower(nome) = :n", Protagonista.class)
                    .setParameter("n", nomeVello.trim().toLowerCase())
                    .uniqueResult();

            if (p != null) {
                p.setNome(novoNome);
                session.update(p);
                actualizado = p;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro actualizando protagonista: " + e.getMessage());
        }
        return actualizado;
    }

    // DELETE
    public void borrarTodos() {
        Transaction tx = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM Protagonista").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.out.println("Erro borrando protagonistas: " + e.getMessage());
        }
    }
}