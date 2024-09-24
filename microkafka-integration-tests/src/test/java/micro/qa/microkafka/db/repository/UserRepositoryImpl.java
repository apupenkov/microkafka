package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.UserEntity;
import micro.qa.microkafka.db.jpa.JpaService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

public class UserRepositoryImpl extends JpaService implements UserRepository {

    public UserRepositoryImpl() {
        super();
    }

    @Override
    public UserEntity findByIdInUsers(UUID uuid) {
        return entityManager.find(UserEntity.class, uuid);
    }

    @Override
    public UserEntity create(String userName, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(password);
        user.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        persist(user);
        return user;
    }
}
