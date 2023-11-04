package com.example.kaktusi.service;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.example.kaktusi.repository.ParkingSpotRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final ParkingSpotRepository parkingSpotRepository;
    private EventHubConsumerAsyncClient consumerClient;

    public EventService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public void handleEvent(String id, boolean occupy, String time) {
        if (occupy) {
            parkingSpotRepository.occupySpot(id);
        } else {
            parkingSpotRepository.freeSpot(id);
        }
    }
    @Scheduled(fixedRate = 1000)
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
            String timeStr = event.substring(event.indexOf("Time") + 7, event.indexOf("Time") + 12);
            handleEvent(id, isOccupied, timeStr);
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
