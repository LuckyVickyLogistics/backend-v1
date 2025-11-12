package com.luckylogistics.ai.application.external;

import com.luckylogistics.ai.application.dto.AiPromptCreatedCommand;
import com.luckylogistics.ai.application.dto.AiPromptResult;

public interface AiPromptGenerator {

	AiPromptResult generatePrompt(AiPromptCreatedCommand command);

}
