package com.example.generated.controller;
import com.example.generated.service.impl.WorksServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping("/api/works")
//public class WorkController {
//
//    @Autowired
//    private WorksServiceImpl workService;

//    @GetMapping("/type-counts")
//    public Map<String, Object> getTypeCounts() {
//        List<Map<String, Object>> typeCounts = workService.getTypeCounts();
//        return Map.of("group_by", typeCounts);
//    }
//
//
//    @GetMapping("/details")
//    public ResponseEntity<?> getWorkDetails(@RequestParam String type) {
//        Object details = workService.getWorkDetails(type);
//        return ResponseEntity.ok(details);
//    }
//}
