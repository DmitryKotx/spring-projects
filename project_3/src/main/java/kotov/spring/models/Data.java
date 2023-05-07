package kotov.spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "data")
public class Data {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    @NotNull
    @Min(-100)
    @Max(100)
    private double value;
    @Column(name = "raining")
    @NotNull
    private boolean raining;
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_name", referencedColumnName = "name")
    @NotNull
    private Sensor sensor;

    public Data(double value, boolean raining) {
        this.value = value;
        this.raining = raining;
    }
    public Data() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
