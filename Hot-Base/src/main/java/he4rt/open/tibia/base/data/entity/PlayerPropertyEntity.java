package he4rt.open.tibia.base.data.entity;

import he4rt.open.tibia.base.data.enums.PropertyEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player_property")
@ToString
@Builder
public class PlayerPropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "player", referencedColumnName = "id")
    @ManyToOne
    private PlayerEntity player;

    @Enumerated(EnumType.STRING)
    private PropertyEnum propertyEnum;

    private long value;

}
