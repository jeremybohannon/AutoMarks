package com.automarks.gateway.gatewayService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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


@RestController
@RequestMapping("/api/v1")
public class GatewayController {

    private final StorageService storageService;
    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public GatewayController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value = "/assignment/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getAssignment(@PathVariable long id){
        return getMethod(Routes.getRoute("Assignment") + "/assignment/" + id, 120);
    }

    @RequestMapping(value = "/assignment/{id}/submission/{userId}", method = RequestMethod.POST, produces = "application/json")
    public String submitAssignment(@PathVariable long id, @PathVariable long userId, @RequestParam("file") MultipartFile file){
//        receives file and user information
        String responseString = "";
        try {

            storageService.store(file);
            File source = storageService.getFile(file.getOriginalFilename());

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost request = new HttpPost(Routes.getRoute("Assignment") + "/assignment/"+id+"/submission/"+userId);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("source", new FileInputStream(source),
                    ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename());

            HttpEntity multipart = builder.build();
            request.setEntity(multipart);

            CloseableHttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            storageService.deleteFile(file.getOriginalFilename());

            client.close();
            responseString = result.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;
    }


    @RequestMapping(value = "/assignment/create/{proId}", method = RequestMethod.POST, produces = "application/json")
    public String createAssignment(@PathVariable String proId, @RequestParam("file") MultipartFile file){
        //pass create assignment
        //file

        //name
        //discrip
        //spec file

        //calling /assignment/create

        return "";
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
