package he4rt.open.tibia.base.data.repository;

import he4rt.open.tibia.base.data.entity.PlayerAbilityEntity;
import he4rt.open.tibia.base.data.entity.PlayerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PlayerAbilityRepository extends CrudRepository<PlayerAbilityEntity, Long> {

    Set<PlayerAbilityEntity> findAllByPlayer(PlayerEntity player);

}
