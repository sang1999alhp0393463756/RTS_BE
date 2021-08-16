package com.example.demo123.controller.client;


import com.example.demo123.dto.response.SliderResponse;
import com.example.demo123.entity.Slider;
import com.example.demo123.repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/client/sliders")
public class FileSlidersController {
    @Autowired
    SliderRepository sliderRepository;

    @GetMapping("/showSlider")
    public ResponseEntity<?> showAllSlider() {
        List<Slider> sliderList = sliderRepository.findAll();
        return ResponseEntity.ok(sliderList);
    }

}
