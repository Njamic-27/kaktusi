package entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "parkingSpotEvent")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public class ParkingSpotEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column
    boolean isOccupied;

    @Column
    String time;


}
