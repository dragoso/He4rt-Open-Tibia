package he4rt.open.tibia.base.data.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "player_look")
@ToString
@Builder
public class PlayerLookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "player", referencedColumnName = "id")
    private PlayerEntity player;

    private int lookType;
    private int lookAdddons;
    private int lookBody;
    private int lookFeet;
    private int lookHead;
    private int lookLegs;

}
