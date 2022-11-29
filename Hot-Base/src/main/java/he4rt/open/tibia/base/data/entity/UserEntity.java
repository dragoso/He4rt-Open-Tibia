package he4rt.open.tibia.base.data.entity;

import he4rt.open.tibia.base.data.enums.LocationEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@ToString
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column
    private String banned;
    private LocalDateTime tempBan;
    private LocalDate birthday;

    private int premiumDays;

    @Enumerated(EnumType.STRING)
    private LocationEnum langue;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<PlayerEntity> players;

}
