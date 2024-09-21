package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.UserEntity;
import micro.qa.microkafka.jpa.JpaService;

import java.util.UUID;

public class UserRepositoryImpl extends JpaService implements UserRepository {

    public UserRepositoryImpl() {
        super();
    }

    @Override
    public UserEntity findByUserId(UUID username) {
        return entityManager.find(UserEntity.class, username);
    }
}
