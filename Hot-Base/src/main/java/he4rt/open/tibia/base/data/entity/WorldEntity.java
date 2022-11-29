package he4rt.open.tibia.base.data.entity;

import he4rt.open.tibia.base.data.enums.LocationEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "world")
@ToString
@Builder
public class WorldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;
    private String ip;
    private int port;

    private int pvptype;

    @Enumerated(EnumType.STRING)
    private LocationEnum location;

}
