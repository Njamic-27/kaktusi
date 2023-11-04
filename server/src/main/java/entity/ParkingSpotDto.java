package entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "parkingSpot")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
public class ParkingSpotDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Double latitude;

    private Double longitude;

    private ParkingSpotZone parkingSpotZone;

    private boolean occupied;

    private LocalDateTime occupiedTimestamp;
}
