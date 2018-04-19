package com.cloudinterface;

import com.example.cloudinterface.CloudApiClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nodes")
public class NodesApiController {
    CloudApiClient client;
    public NodesApiController() {
        client = new CloudApiClient();
    }

    @GetMapping("getlist")
    public String doGet() throws Exception {
        return client.doGet("nodes");
    }
}
