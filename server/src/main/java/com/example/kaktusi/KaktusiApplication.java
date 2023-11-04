package com.example.kaktusi;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.example.kaktusi.entity.ParkingSpotDto;
import com.example.kaktusi.service.ParkingSpotService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class KaktusiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaktusiApplication.class, args);

		String connectionString = "Endpoint=sb://cbq-hackathon.servicebus.windows.net/;SharedAccessKeyName=n;SharedAccessKey=/3u+79mnjCx4qBqPjW1P0qn1DSMNRCRLV+AEhJgp4IM=;EntityPath=team4"; // Replace with your Event Hub connection string
		String eventHubName = "team4"; // Replace with your Event Hub name
		String consumerGroupName = "$Default"; // Default consumer group

		// Create an EventHubConsumerAsyncClient
		EventHubConsumerAsyncClient consumerClient = new EventHubClientBuilder()
				.connectionString(connectionString, eventHubName)
				.consumerGroup(consumerGroupName)
				.buildAsyncConsumerClient();

		// Subscribe to events and process them
		consumerClient.receive().subscribe(eventData -> {

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
