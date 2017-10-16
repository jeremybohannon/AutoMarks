package com.automarks.storage.storageService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    StorageRepository storageRepository;
    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/assignment/createOrUpdate", method = RequestMethod.POST)
    public Map<String, Object> assignmentCreate(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = mapper.convertValue(mapPayload, Assignment.class);
        assignment = storageRepository.save(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET)
    public String assignmentRead(@PathVariable long id){
        //REMOVE BELOW AFTER DB SEED
        Assignment assignment = new Assignment();
        assignment.setId(id);
        assignment.setDescription("Create a greater or less than function");
        assignment.setFileId("alksjdklvawoeiea");
        assignment.addCase(1,"isLess(10,15) should return true", 10);
        assignment.addCase(2,"isLess(10,15) should return false", 10);

//        Assignment assignment = storageRepository.findOne(id);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(assignment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/assignment/delete", method = RequestMethod.POST)
    public Map<String, Object> assignmentDelete(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = mapper.convertValue(mapPayload, Assignment.class);
        storageRepository.delete(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

}
