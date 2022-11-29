package he4rt.open.tibia.base.data.repository;

import he4rt.open.tibia.base.data.entity.PlayerEntity;
import he4rt.open.tibia.base.data.entity.PlayerPropertyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerPropertyRepository extends CrudRepository<PlayerPropertyEntity, Long> {

    Set<PlayerPropertyEntity> findAllByPlayer(PlayerEntity player);

}
