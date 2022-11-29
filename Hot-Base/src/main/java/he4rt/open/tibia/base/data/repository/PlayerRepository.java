package he4rt.open.tibia.base.data.repository;

import he4rt.open.tibia.base.data.entity.PlayerEntity;
import he4rt.open.tibia.base.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {

    PlayerEntity findByNameAndUser(String name, UserEntity userEntity);

    List<PlayerEntity> findAllByUserAndWorldIdIn(UserEntity userEntity, List<Integer> worlds);

    List<PlayerEntity> findAllByUser(UserEntity userEntity);

}
