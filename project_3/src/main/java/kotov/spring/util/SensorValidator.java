package kotov.spring.util;

import kotov.spring.models.Sensor;
import kotov.spring.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private  final SensorsService sensorsService;
    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor person = (Sensor) o;
        if(sensorsService.findOne(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
