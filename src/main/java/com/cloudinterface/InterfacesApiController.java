package com.cloudinterface;

import com.example.cloudinterface.CloudApiClient;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/interfaces")
public class InterfacesApiController {

    CloudApiClient client;
    public InterfacesApiController() {
        client = new CloudApiClient();
    }
    @PostMapping(value="add")
    public void doPost(@RequestBody String object) throws Exception {
        client.doPost("interfaces", object);
    }

    @GetMapping("getlist")
    public String doGet() throws Exception {
        return client.doGet("interfaces");
    }
}
