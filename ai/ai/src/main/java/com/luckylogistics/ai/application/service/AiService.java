package com.luckylogistics.ai.application.service;

import org.springframework.stereotype.Service;

import com.luckylogistics.ai.domain.repository.AiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

	private final AiRepository aiRepository;

}
