package micro.qa.microkafka.db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class JpaService {

    protected final EntityManager entityManager;

    public JpaService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("micro-pay-test");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    protected <T> void persist(T entity) {
        tx(em -> em.persist(entity));
    }

    protected <T> void remove(T entity) {
        tx(em -> em.remove(entity));
    }

    protected <T> T merge(T entity) {
        return txWithResult(em -> em.merge(entity));
    }

    protected <T> T txWithResult(Function<EntityManager, T> function) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            T result = function.apply(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    protected void tx(Consumer<EntityManager> consumer) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            consumer.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public <T> void removeDetachedEntity(T detachedEntity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T managedEntity = entityManager.merge(detachedEntity);
            entityManager.remove(managedEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
