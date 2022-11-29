package he4rt.open.tibia.base.data.repository;

import he4rt.open.tibia.base.data.entity.WorldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends JpaRepository<WorldEntity, Long> {

    WorldEntity findByPort(Integer port);

}
