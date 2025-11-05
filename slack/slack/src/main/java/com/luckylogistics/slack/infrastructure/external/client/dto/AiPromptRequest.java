package com.luckylogistics.slack.infrastructure.external.client.dto;

import java.time.LocalTime;
import java.util.List;

public record AiPromptRequest(String productName,

							  String request,

							  String startPoint,

							  List<String> waypoints,

							  String endPoint,

							  LocalTime deliveryManagerStartTime,

							  LocalTime deliveryManagerEndTime) {
}
