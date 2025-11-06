package com.luckylogistics.slack.infrastructure.external.slack;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.luckylogistics.slack.application.external.SlackClient;
import com.luckylogistics.slack.application.dto.OrderCreatedResult;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;

@Component
public class SlackClientImpl implements SlackClient {

	private final MethodsClient client;
	private final DateTimeFormatter formatter;

	public SlackClientImpl(@Value("${slack.bot.token}") String botToken) {
		this.client = Slack.getInstance().methods(botToken);
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Seoul"));
	}

	@Override
	public void sendMessage(OrderCreatedResult command, String receiverEmail, String aiPrompt) {
		try {
			String userId = client.usersLookupByEmail(r -> r.email(receiverEmail)).getUser().getId();
			String channelId = client.conversationsOpen(r -> r.users(List.of(userId))).getChannel().getId();

			client.chatPostMessage(r -> r
				.channel(channelId)
				.text("주문이 생성되었습니다.")
				.attachments(List.of(createAttachment(command, aiPrompt)))
			);
		} catch (IOException | SlackApiException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean existsByEmail(String email) {
		try {
			return client.usersLookupByEmail(r -> r.email(email)).getUser() != null;
		} catch (IOException | SlackApiException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private Attachment createAttachment(OrderCreatedResult command, String aiPrompt) {
		return Attachment.builder()
			.color("#36A64F")
			.fields(List.of(
				createField("*주문ID* : ", command.orderId().toString()),
				createField("*주문자정보* : ", String.format("%s(%s)", command.customerName(), command.customerEmail())),
				createField("*주문시간* : ", formatter.format(command.orderedAt())),
				createField("*상품정보* : ", String.format("%s(%s)", command.productName(), command.quantity())),
				createField("*요청사항* : ", command.request()),
				createField("*발송지* : ", command.startPoint()),
				createField("*경유지* : ", String.join(", ", command.waypoints())),
				createField("*도착지* : ", command.endPoint()),
				createField("*배송담당자* : ",
					String.format("%s(%s)", command.deliveryManagerName(), command.deliveryManagerEmail())),
				createField("", "위 내용을 기반으로 도출된 최종 발송 시한은 *%s* 입니다.".formatted(formatter.format(Instant.parse(aiPrompt))))
			))
			.build();
	}

	private Field createField(String title, String value) {
		return Field.builder()
			.value(title + value)
			.valueShortEnough(false)
			.build();
	}

}
