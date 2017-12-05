package com.automarks.storage.storageService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final Path rootLocation = Paths.get("FILESTORE");
    private final String testFile = "testCases";
    private final String userSubmission = "userSubmission";
    private final StorageService storageService;

    @Autowired
    StorageRepository assignmentRepository;
    UserRepository userRepository;
    ObjectMapper mapper = new ObjectMapper();

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Assignments
    @RequestMapping(value = "/assignment/createOrUpdate", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> assignmentCreate(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = null;
        try {
            assignment = mapper.readValue(mapPayload.toString(), Assignment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assignment = assignmentRepository.save(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET, produces = "application/json")
    public Assignment assignmentRead(@PathVariable long id){
        Assignment assignment = assignmentRepository.findById(id).get(0);
        return assignment;
    }

    @RequestMapping(value = "/assignment/delete", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> assignmentDelete(@RequestBody Map<String, Object> mapPayload){
        Assignment assignment = mapper.convertValue(mapPayload, Assignment.class);
        assignmentRepository.delete(assignment);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }


    //Type 1 = testCases | 2 = userSubmissions
    @RequestMapping(value = "/assignment/{id}/testCase", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getFile(@PathVariable long id) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        Assignment assignment = assignmentRead(id);
        Path f = rootLocation.resolve(testFile + "/" + assignment.getFileId());
        File file = new File(String.valueOf(f));

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .header("Content-disposition", "attachment; filename=Main.py")
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }

    @RequestMapping(value = "/assignment/submission", method = RequestMethod.POST, produces = "application/json")
    public String submitAssignment(@RequestParam("id") long assignID, @RequestParam("userId") long userId, @RequestParam("subId") long subId , @RequestParam("source") MultipartFile sourceFile){

        //Get Submisson Id
        //Create submission entry
            //add file key
            //time stamp
            //submission id
        //Store file with generated string

        //return
            //submission id
            // pass fail
       return "PASS";
    }

    //Submissions


    //Users

}
