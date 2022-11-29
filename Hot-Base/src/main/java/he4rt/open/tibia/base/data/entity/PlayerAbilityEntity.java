package he4rt.open.tibia.base.data.entity;

import he4rt.open.tibia.base.data.enums.AbilityEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player_ability")
@ToString
@Builder
public class PlayerAbilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne
    private PlayerEntity player;

    @Enumerated(EnumType.STRING)
    private AbilityEnum abilityEnum;

    private int level;
    private double exp;

    @Transient
    private int bonus = 0;

    public long getLevelFull() {
        return level + bonus;
    }

}
