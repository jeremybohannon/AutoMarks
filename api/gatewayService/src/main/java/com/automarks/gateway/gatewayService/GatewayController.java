package com.automarks.gateway.gatewayService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@RestController
@RequestMapping("/")
public class GatewayController {

    final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "assignment/{id}", method = RequestMethod.GET)
    public String getAssignment(@PathVariable long id){
        return getMethod("http://localhost:8080/storage/assignment/" + id, 120);
    }

    @RequestMapping(value = "assignment/{id}/submit", method = RequestMethod.GET)
    public String submitAssignment(@PathVariable long id){
        return "[ {\"id\" : 1, \"pass\" : true}, {\"id\" : 2, \"pass\" : false} ]";
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
