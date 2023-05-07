package kotov.spring.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull
    private String name;
    @OneToMany(mappedBy = "sensor")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Data> dataSet;
    public Sensor(String name) {
        this.name = name;
    }
    public Sensor() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Data> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<Data> dataSet) {
        this.dataSet = dataSet;
    }
}
