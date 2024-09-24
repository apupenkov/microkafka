package micro.qa.microkafka.db.repository;

import micro.qa.microkafka.db.model.UserEntity;

import java.util.UUID;

public interface UserRepository {

    UserEntity findByIdInUsers(UUID userId);

    UserEntity create(String userName, String password);
}
