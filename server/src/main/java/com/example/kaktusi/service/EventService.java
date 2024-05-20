package com.example.kaktusi.service;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.example.kaktusi.entity.OccupyLog;
import com.example.kaktusi.repository.OccupyLogRepository;
import com.example.kaktusi.repository.ParkingSpotRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EventService {
    private final ParkingSpotRepository parkingSpotRepository;
    private final OccupyLogRepository occupyLogRepository;
    private EventHubConsumerAsyncClient consumerClient;

    public EventService(ParkingSpotRepository parkingSpotRepository, OccupyLogRepository occupyLogRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.occupyLogRepository = occupyLogRepository;
    }

    public void handleEvent(String id, boolean occupy, String time) {
        if (occupy) { //isOccupied iz false u true
            parkingSpotRepository.occupySpot(id);
        } else {
            parkingSpotRepository.freeSpot(id);
        }
        OccupyLog occupyLog = new OccupyLog();
        occupyLog.setParkingSpotId(id);
        LocalTime localTime = LocalTime.parse(time);
        occupyLog.setTimestamp(localTime);
        occupyLog.setOccupied(occupy);
        occupyLog.setTimeNow(LocalDateTime.now());
        occupyLogRepository.save(occupyLog);
    }
    //@Scheduled(fixedRate = 1000)
    public void startEventProcessing() {
        String connectionString = "Endpoint=sb://cbq-hackathon.servicebus.windows.net/;SharedAccessKeyName=n;SharedAccessKey=/3u+79mnjCx4qBqPjW1P0qn1DSMNRCRLV+AEhJgp4IM=;EntityPath=team4";
        String eventHubName = "team4";
        String consumerGroupName = "$Default";

        // Create an EventHubConsumerAsyncClient
        consumerClient = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .consumerGroup(consumerGroupName)
                .buildAsyncConsumerClient();

        // Subscribe to events and process them
        consumerClient.receive().subscribe(eventData -> {
            String event = eventData.getData().getBodyAsString();
            String id = event.substring(event.indexOf("Id") + 5, event.indexOf("IsOccupied") - 3);
            String isOccupiedStr = event.substring(event.indexOf("IsOccupied") + 12, event.indexOf("Time") - 2);
            boolean isOccupied = Boolean.parseBoolean(isOccupiedStr);
            String timeValue= "";
            try {
                // Create an ObjectMapper
                ObjectMapper objectMapper = new ObjectMapper();

                // Parse the JSON string
                JsonNode jsonNode = objectMapper.readTree(event);

                // Extract the "Time" value as a string
                timeValue = jsonNode.get("Time").asText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(timeValue.length() == 3) {
                timeValue = "0" + timeValue;
                timeValue = timeValue.substring(0, 3) + "0" +  timeValue.substring(3);
            } else if(timeValue.length() == 4 && timeValue.charAt(1) == ':') {
                timeValue = "0" + timeValue;
            } else if (timeValue.length() == 4) {
                timeValue = timeValue.substring(0, 3) + "0" + timeValue.substring(3);
            }
            handleEvent(id, isOccupied, timeValue);
            System.out.println("Received event: " + eventData.getData().getBodyAsString());
           });

        // Keep the application running to receive events
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
