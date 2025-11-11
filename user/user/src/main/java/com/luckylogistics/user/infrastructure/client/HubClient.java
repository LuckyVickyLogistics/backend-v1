package com.luckylogistics.user.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub")
public interface HubClient {

	@GetMapping("/api/v1/hubs/{hub_name}")
	HubResponse getHubByName(@PathVariable("name") String name);

	record HubResponse(UUID id, String name) {}
}
