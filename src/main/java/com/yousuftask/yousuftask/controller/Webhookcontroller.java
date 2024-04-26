package com.yousuftask.yousuftask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.yousuftask.yousuftask.model.WebhookResponse;
import com.yousuftask.yousuftask.model.WebhookRequest;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class Webhookcontroller {

    private final RestTemplate restTemplate;

    public Webhookcontroller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/webhook")
public WebhookResponse handleWebhookRequest(@RequestBody WebhookRequest request) {
    
    
    
    // Extract order ID from request
    String orderId = request.getOrderId();

    String apiUrl = "https://orderstatusapi-dot-organization-project-311520.uc.r.appspot.com/api/getOrderStatus";
    
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, request, String.class);

    String shipmentDateFormatted = null;
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
        String responseBody = responseEntity.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);
        String shipmentDate = jsonObject.getString("shipmentDate");
        
        // Convert shipmentDate
        LocalDateTime dateTime = LocalDateTime.parse(shipmentDate, DateTimeFormatter.ISO_DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy");
        shipmentDateFormatted = dateTime.format(formatter);
    } else {
        shipmentDateFormatted = "Invalid ID";
    }

    //Response
    WebhookResponse response = new WebhookResponse();
    response.setMessage("Your order " + orderId + " will be delivered on " + shipmentDateFormatted);
    return response;
}
}