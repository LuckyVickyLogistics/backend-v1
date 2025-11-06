package com.luckylogistics.ai.application.external;

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.GeminiPromptResult;

public interface GeminiClient {

	GeminiPromptResult generatePrompt(AiPromptCreatedCommand command);

}
