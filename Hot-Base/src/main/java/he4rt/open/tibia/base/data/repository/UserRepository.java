package he4rt.open.tibia.base.data.repository;

import he4rt.open.tibia.base.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    UserEntity findByName(String name);
    UserEntity findTopByName(String name);

}
