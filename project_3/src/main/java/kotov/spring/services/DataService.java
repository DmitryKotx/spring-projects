package kotov.spring.services;

import kotov.spring.models.Data;
import kotov.spring.models.Sensor;
import kotov.spring.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataService {
    private final SensorsService sensorsService;
    private final DataRepository dataRepository;
    @Autowired
    public DataService(SensorsService sensorsService, DataRepository dataRepository) {
        this.sensorsService = sensorsService;
        this.dataRepository = dataRepository;
    }
    public List<Data> findAll() {
        return dataRepository.findAll();
    }
    public List<Data> findRaining(Boolean raining) {
        return dataRepository.findAllByRaining(raining);
    }
    public Data findOne(int id) {
        return dataRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Data data) {
        Sensor sensor = sensorsService.findOne(data.getSensor().getName()).orElse(null);
        data.setSensor(sensor);
        data.setCreatedAt(LocalDateTime.now());
        dataRepository.save(data);
    }
}
