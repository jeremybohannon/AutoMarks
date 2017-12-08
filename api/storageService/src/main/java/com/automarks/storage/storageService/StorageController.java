package com.automarks.storage.storageService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
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
import java.util.*;

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final Path rootLocation = Paths.get("FILESTORE");
    private final String testFile = "testCases";
    private final String userSubmission = "userSubmission";
    private final StorageService storageService;

    @Autowired
    StorageRepository assignmentRepository;
    @Autowired
    UserRepository userRepository;
    ObjectMapper mapper = new ObjectMapper();
    FileStore fs = new FileStore();

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Assignments
    /**TODO Add file add here*/
    @RequestMapping(value = "/assignment/createOrUpdate", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> assignmentCreate(@RequestParam("assign") String mapPayload, @RequestParam("source") MultipartFile sourceFile){
        Assignment assignment = null;
        try {
            assignment = mapper.readValue(mapPayload, Assignment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add file key
        String uuid = java.util.UUID.randomUUID().toString();
        storageService.store(sourceFile);

        File source = storageService.getFile(sourceFile.getOriginalFilename());
        String fileExten = FilenameUtils.getExtension(sourceFile.getOriginalFilename());
        //add new file name and store file
        String fileName = uuid + "." + fileExten;

        //Store file with generated string
        assignment.setFileId(fileName);
        fs.save(fileName, source);
        assignmentRepository.save(assignment);
        //delete temp files
        storageService.deleteFile(sourceFile.getOriginalFilename());
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
        File file = storageService.rootLocation.resolve(assignment.getFileId()).toFile();
        fs.get(assignment.getFileId()).writeTo(file);

        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity x = ResponseEntity
                .ok()
                .header("Content-disposition", "attachment; filename=Main.py")
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));

        storageService.deleteFile(file.getName());

        return x;

    }

    //Submissions

    @RequestMapping(value = "/assignment/submission", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> submitAssignment(@RequestParam("assignID") long assignID, @RequestParam("studentId") String userId, @RequestParam("cases") String cases, @RequestParam("score") int score, @RequestParam("source") MultipartFile sourceFile){
        //Get User information
        User user = userRepository.findBySchoolID(userId).get(0);
        Assignment assignment = assignmentRepository.findById(assignID).get(0);

        //add file key
        String uuid = java.util.UUID.randomUUID().toString();
        storageService.store(sourceFile);

        File source = storageService.getFile(sourceFile.getOriginalFilename());
        String fileExten = FilenameUtils.getExtension(sourceFile.getOriginalFilename());
        //add new file name and store file
        String fileName = uuid + "." + fileExten;

        //Store file with generated string
        fs.save(fileName, source);
        //Create submission entry
        //create and set submission
        Submissions submission = new Submissions();
        submission.setFile(fileName);
        submission.setDate(new Date());
        submission.setAssignment(assignment);
        submission.setCases(parseCases(cases));
        submission.setScore(score);

        //Update User Information
        if(user.getSubmissions().size() <= 0){
            ArrayList<Submissions> subs = new ArrayList<>();
            user.setSubmissions(subs);
        }else {
            user.addSubmission(submission);
        }
        userRepository.save(user);
        //delete temp file
        storageService.deleteFile(sourceFile.getOriginalFilename());
       //Return non-failure message
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

    //Users
        //create and update
    @RequestMapping(value = "/user/createOrUpdate", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> userCreate(@RequestBody Map<String, Object> mapPayload){
        User user = mapper.convertValue(mapPayload, User.class);
        userRepository.save(user);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("success", "true");
        return dataMap;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST, produces = "application/json")
    public User getUser(@PathVariable String id){
        User user = userRepository.findBySchoolID(id).get(0);
        return user;
    }

    //DATABASE SEED

    @RequestMapping(value = "/seed", method = RequestMethod.POST, produces = "application/json")
    public User getUser(){

        //student
        User user = new User();
        user.setFirstName("Andrew");
        user.setLastName("Schlesinger");
        user.setSchoolID("800827630");
        user.setLogin("aschles4");
        user.setPassword("pass1234");
        user.setStudent(true);
        user.setProffesor(false);

        userRepository.save(user);

        //professor
        user.setFirstName("Richard");
        user.setLastName("Ilson");
        user.setSchoolID("80082000");
        user.setLogin("rIlson4");
        user.setPassword("pass1234");
        user.setStudent(false);
        user.setProffesor(true);

        userRepository.save(user);


        return user;
    }


    //Helping functions
    private ArrayList<Case> parseCases(String x){
        ArrayList<Case> cases = new ArrayList<>();
        try {
//            TypeFactory typeFactory = mapper.getTypeFactory();
//            cases = mapper.convertValue(x, typeFactory.constructCollectionType(ArrayList.class, Case.class));
            JsonNode valuesNode = mapper.readTree(x).get("cases");
            for (JsonNode node : valuesNode) {
                cases.add(mapper.readValue(node.toString(), Case.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cases;
    }

}
