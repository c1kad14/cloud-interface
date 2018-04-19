package com.cloudinterface;

import com.example.cloudinterface.CloudApiClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mappings")
public class MappingsApiController {
    CloudApiClient client;
    public MappingsApiController() {
        client = new CloudApiClient();
    }

    @GetMapping("getlist")
    public String doGet() throws Exception {
        return client.doGet("mappings");
    }

    @PostMapping(value="add")
    public void doPost(@RequestBody String object) throws Exception {
        client.doPost("mappings", object);
    }
}
