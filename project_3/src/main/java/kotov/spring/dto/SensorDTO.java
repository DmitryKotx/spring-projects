package kotov.spring.dto;

import jakarta.validation.constraints.NotNull;

public class SensorDTO {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
