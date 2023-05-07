package kotov.spring.controllers;

import jakarta.validation.Valid;
import kotov.spring.dto.DataDTO;
import kotov.spring.models.Data;
import kotov.spring.services.DataService;
import kotov.spring.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class DataController {
    private final DataService dataService;
    private final ModelMapper modelMapper;
    @Autowired
    public DataController(DataService dataService, ModelMapper modelMapper) {
        this.dataService = dataService;
        this.modelMapper = modelMapper;
    }
    @GetMapping()
    public List<DataDTO> getData() {
        return dataService.findAll().stream().map(this::convertToDataDTO).toList();
    }
    @GetMapping("/rainyDaysCount")
    public List<DataDTO> getRainyData() {
        return dataService.findRaining(true).stream().map(this::convertToDataDTO)
                .collect(Collectors.toList());
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addData(@RequestBody @Valid DataDTO dataDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                stringBuilder.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new DataNotCreatedException(stringBuilder.toString());
        }
        Data data = convertToData(dataDTO);
        dataService.save(data);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @ExceptionHandler
    public ResponseEntity<DataErrorResponse> exception(DataNotCreatedException e) {
        DataErrorResponse response = new DataErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    public Data convertToData(DataDTO dataDTO) {
        return modelMapper.map(dataDTO, Data.class);
    }
    public DataDTO convertToDataDTO(Data data) {
        return modelMapper.map(data, DataDTO.class);
    }
}
