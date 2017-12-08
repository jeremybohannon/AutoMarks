package com.automarks.Assignment.Assignment;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    private final String testFile = "testCases";
    private final StorageService storageService;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AssignmentController(StorageService storageService) {
        this.storageService = storageService;
    }

    //gets the assignment data
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getAssignment(@PathVariable long id){
        return getMethod(Routes.getRoute("Storage") + "/storage/assignment/" + id, 120);
    }

    //submission of the assignment
    @RequestMapping(value = "/{id}/submission/{userId}", method = RequestMethod.POST, produces = "application/json")
    public String submitAssignment(@PathVariable long id, @PathVariable long userId, @RequestParam("source") MultipartFile sourceFile){

        /*TODO
        * Pass id and userID as param
        *
        * FOR MVP
        *
        * set up post call to auto grader and wait for response
        * then send that response back to the front end
        * error handling
        *
        * wait for response from auto grader
        *   then do what we need to
        *
        * */
        String responseString = "";
        try {
            //get file from storage done

            //send to auto grader done
                // handle response
            //create submission
                //save submission file
                //tie to user
            //delete files done


            /**  GET FILE FROM STOREAGE**/
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet req = new HttpGet(Routes.getRoute("Storage") + "/storage/assignment/" + id);
            CloseableHttpResponse resp = client.execute(req);


            BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

            StringBuffer res = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                res.append(line);
            }

            client.close();

            res.toString();


            Assignment specAssign = mapper.readValue(res.toString(), Assignment.class);
//            storageService.getFile(testFile + "/" +specAssign.getFileId());
            File spec = storageService.getFileByUrl(Routes.getRoute("Storage") + "/storage/assignment/" + id + "/testCase", specAssign.getFileId(), "testCase");


            storageService.store(sourceFile);
            File source = storageService.getFile(sourceFile.getOriginalFilename());

            client = HttpClients.createDefault();
            HttpPost request = new HttpPost(Routes.getRoute("Grader"));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("assignment", Long.toString(id), ContentType.TEXT_PLAIN);
            builder.addTextBody("user", Long.toString(userId), ContentType.TEXT_PLAIN);
            builder.addBinaryBody("source", new FileInputStream(source),
                    ContentType.APPLICATION_OCTET_STREAM, sourceFile.getOriginalFilename());

            builder.addBinaryBody("spec", new FileInputStream(spec), ContentType.APPLICATION_OCTET_STREAM, "test");

            HttpEntity multipart = builder.build();
            request.setEntity(multipart);

            CloseableHttpResponse response = client.execute(request);

            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            client.close();
            responseString = result.toString();

            //handle response



            //send to submission



            //delete files

            storageService.deleteFile(sourceFile.getOriginalFilename());
            storageService.deleteFile("testCase/" + spec.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;








        //determine assignment
        //get test case files

        //receive submission file

        //call auto grader
        //store file upload and submission data in database

        //(done by another call)
            //receive outputs (done by another call)
            //store user submission in database
//        return "[ {\"id\" : 1, \"pass\" : true}, {\"id\" : 2, \"pass\" : false} ]";
    }

    @RequestMapping(value = "/create/{proId}", method = RequestMethod.POST, produces = "application/json")
    public String createAssignment(@PathVariable String proId, @RequestParam("name") String name, @RequestParam("description") String descript, @RequestParam("spec") MultipartFile specFile){
        long id = System.currentTimeMillis() % 1000;
        Assignment assignment = new Assignment();
        assignment.setId(id);
        assignment.setDescription(descript);
        assignment.setAssignmentName(name);
        //call grader to get cases
        String cases = "";
        try {

            storageService.store(specFile);
            File spec = storageService.getFile(specFile.getOriginalFilename());


            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost request = new HttpPost(Routes.getRoute("Grader"));

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("spec", new FileInputStream(spec), ContentType.APPLICATION_OCTET_STREAM, "test");

            HttpEntity multipart = builder.build();
            request.setEntity(multipart);

            CloseableHttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            client.close();
            cases = result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assignment.setCases(parseCases(cases));



        //call canvas api... and create a new assignment in our canvas sand box class


        //create assignment and store into db
            //call create assignment in storage service
        try {

            String assignJSON = mapper.writeValueAsString(assignment);
            storageService.store(specFile);
            File spec = storageService.getFile(specFile.getOriginalFilename());


            CloseableHttpClient client = HttpClients.createDefault();
            client = HttpClients.createDefault();
            HttpPost request = new HttpPost(Routes.getRoute("Storage")+ "/assignment/createOrUpdate");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("assign", assignJSON, ContentType.TEXT_PLAIN);
            builder.addBinaryBody("source", new FileInputStream(spec), ContentType.APPLICATION_OCTET_STREAM, "test");

            HttpEntity multipart = builder.build();
            request.setEntity(multipart);

            CloseableHttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            client.close();
            cases = result.toString();
        } catch(JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cases;
    }


    //get user scoring
//    @RequestMapping(value = "/{id}/scores/{userId}", method = RequestMethod.GET, produces = "application/json")
//    public String assignmentScore(@PathVariable long id, @PathVariable long userId){
//        //goes into database and get users info and finds the past assignment submission and returns
//
//        return "false";
//    }

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

    private String getMethod(String url, int timeout){
        HttpURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-length", "0");
            con.setUseCaches(false);
            con.setAllowUserInteraction(false);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            con.connect();
            int status = con.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            return "{\"error\" : \"true\"}";
        } catch (IOException ex) {
            return "{\"error\" : \"true\"}";
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {

                }
            }
        }
        return null;
    }
}
