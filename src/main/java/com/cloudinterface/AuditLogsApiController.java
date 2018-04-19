package com.cloudinterface;

import com.example.cloudinterface.CloudApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auditlogs")
public class AuditLogsApiController {
    CloudApiClient client;
    public AuditLogsApiController() {
        client = new CloudApiClient();
    }

    @GetMapping("getlist")
    public String doGet() throws Exception {
        return client.doGet("auditlogs");
    }
}
