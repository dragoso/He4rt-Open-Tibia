package he4rt.open.tibia.base.data.entity;

import he4rt.open.tibia.base.core.tibia.DirectionEnum;
import he4rt.open.tibia.base.core.tibia.Position;
import he4rt.open.tibia.base.data.enums.*;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player")
@ToString
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private int worldId;

    private long health;
    private long maxHealth;
    private long mana;
    private long maxMana;

    @Enumerated(EnumType.STRING)
    private DirectionEnum directionEnum;
    protected int x;
    protected int y;
    protected int z;
    private int townId;

    private float capacity;
    private boolean reports;

    @Enumerated(EnumType.STRING)
    private GenderEnum genderEnum;
    @Enumerated(EnumType.STRING)
    private ChaseEnum chaseEnum;
    @Enumerated(EnumType.STRING)
    private FightEnum fightEnum;
    @Enumerated(EnumType.STRING)
    private GMEnum gmEnum;
    @Enumerated(EnumType.STRING)
    private JobEnum jobEnum;

    @ToString.Exclude
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne
    private UserEntity user;

    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "player")
    private PlayerLookEntity look;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "player", fetch = FetchType.EAGER)
    private Set<PlayerAbilityEntity> abilitys;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "player", fetch = FetchType.EAGER)
    private Set<PlayerPropertyEntity> property;

    @Transient
    private Position position;

    @Transient
    private WorldEntity worldEntity;


    public Position getPosition() {

        if (Objects.isNull(position)) {
            position = Position.builder().x(x).y(y).z(z).build();
        }

        return position;
    }

    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
        z = position.getZ();
        this.position = position;
    }


    public PlayerAbilityEntity getAbilityByType(AbilityEnum abilityEnum) {

        return abilitys.stream().filter(a -> Objects.equals(a.getAbilityEnum(), abilityEnum)).findFirst().orElse(null);

    }

    public PlayerPropertyEntity getPropertyByType(PropertyEnum propertyEnum) {

        return property.stream().filter(a -> Objects.equals(a.getPropertyEnum(), propertyEnum)).findFirst().orElse(null);

    }

}
