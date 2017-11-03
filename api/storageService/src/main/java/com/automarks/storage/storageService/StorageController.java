package com.automarks.storage.storageService;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final Path rootLocation = Paths.get("FILESTORE");
    private final String testFile = "testCases";
    private final String userSubmission = "userSubmission";

    @Autowired
    StorageRepository storageRepository;
    ObjectMapper mapper = new ObjectMapper();



    //Assignments
    @RequestMapping(value = "/assignment/createOrUpdate", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> assignmentCreate(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = mapper.convertValue(mapPayload, Assignment.class);
        assignment = storageRepository.save(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET, produces = "application/json")
    public Assignment assignmentRead(@PathVariable long id){
        Assignment assignment = storageRepository.findById(id).get(0);
        return assignment;
    }

    @RequestMapping(value = "/assignment/delete", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> assignmentDelete(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = mapper.convertValue(mapPayload, Assignment.class);
        storageRepository.delete(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }


    //Type 1 = testCases | 2 = userSubmissions
//    @RequestMapping(value = "/assignment/{id}/testCase", method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> getFile(@PathVariable long id) throws IOException {
//
//        HttpHeaders headers = new HttpHeaders();
//        Assignment assignment = assignmentRead(id);
//        Path f = rootLocation.resolve(testFile + "/" + assignment.getFileId());
//        File file = new File(String.valueOf(f));
//
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//        return ResponseEntity
//                .ok()
//                .header("Content-disposition", "attachment; filename=Main.py")
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new InputStreamResource(new FileInputStream(file)));
//    }

    //Submissions


    //Users

}
