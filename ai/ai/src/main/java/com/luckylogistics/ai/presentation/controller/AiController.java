package com.luckylogistics.ai.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.luckylogistics.ai.application.service.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AiController {

	private final AiService aiService;

}
