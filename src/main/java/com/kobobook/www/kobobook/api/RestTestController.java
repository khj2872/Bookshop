//package com.kobobook.www.kobobook.api;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/test")
//@AllArgsConstructor
//@Slf4j
//public class RestTestController {
//
//    private RestTemplate restTemplate;
//
//    @GetMapping("")
//    public ResponseEntity<Map<String, Object>> test() {
//
//        Map<String, Object> returnMap = new HashMap<>();
//
//        String result = restTemplate.getForObject(
//                "http://localhost:8090/test/{id}", String.class, "아야아어");
//
//
//
//
//        return new ResponseEntity<>(returnMap, HttpStatus.OK);
//    }
//
//}
