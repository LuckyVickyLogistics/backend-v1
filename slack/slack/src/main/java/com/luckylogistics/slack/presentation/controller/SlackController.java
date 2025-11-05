package com.luckylogistics.slack.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.slack.application.SlackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/slack-messages")
@RequiredArgsConstructor
public class SlackController {

	private final SlackService slackService;

}
