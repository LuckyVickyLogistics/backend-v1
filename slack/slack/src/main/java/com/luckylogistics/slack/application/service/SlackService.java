package com.luckylogistics.slack.application;

import org.springframework.stereotype.Service;

import com.luckylogistics.slack.domain.repository.SlackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackService {

	private final SlackRepository slackRepository;

}
