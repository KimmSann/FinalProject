package com.example.demo.gpt.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GptService {

	@Value("${apikey}")
	private String API_KEY;

	private final String API_URL = "https://api.openai.com/v1/chat/completions";

	// gpt api 세줄요약 기능 
	public String callGptApi(String postContent) {
		HttpClient client = HttpClient.newHttpClient();

		JSONArray messages = new JSONArray();

		// 프롬프트 작성
		JSONObject system = new JSONObject();
		system.put("role", "system");
		system.put("content", "당신은 문서를 요약하는 전문가입니다."
				+ " 사용자가 보낸 글을 항상 3줄로 명확하게 요약해서 반환해야 합니다."
				+ "요약을 반환할 떄 절대 인삿말, 물론입니다 같은 추임새를 절대 넣지 말아야합니다"
				+ "요약을 할 떄 1. 내용, 2. 내용, 3. 내용 이렇게 정리해서 알려줘야합니다."
				+ "만약에 글이 20자도 안넘는 짦은 글이면 글이 너무 짧아 요약할 수 없습니다. 라고 알려줘야합니다.");
		
		messages.put(system);

		// 컨텐츠 내용 gpt한테 물어보기
		JSONObject user = new JSONObject();
		user.put("role", "user");
		user.put("content", postContent);
		messages.put(user);

		// 구성하기
		JSONObject requestBody = new JSONObject();
		requestBody.put("model", "gpt-4");
		requestBody.put("messages", messages);

		// 요청 구성
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(API_URL))
				.header("Authorization", "Bearer " + API_KEY)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
				.build();

		// api 호출
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String rawBody = response.body();

//			System.out.println("GPT 응답 원문:\n" + rawBody);

			JSONObject jsonResponse = new JSONObject(rawBody);

			// 가끔 키값이 이상하거나 에러걸리면 안됨 다른 키 값으로 바꿨는데 한번 더 에러나면 물어보기
			if (!jsonResponse.has("choices")) {
				if (jsonResponse.has("error")) {
					JSONObject error = jsonResponse.getJSONObject("error");
					String errorMessage = error.optString("message", "Unknown error");
					System.out.println(API_KEY);
					return "[GPT 오류] " + errorMessage;
				} else {
					return "[GPT 오류] 알 수 없는 응답 형식입니다.";
				}
			}

			// 정상 응답 처리
			return jsonResponse
					.getJSONArray("choices")
					.getJSONObject(0)
					.getJSONObject("message")
					.getString("content");

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "요약 중 예외가 발생했습니다.";
		}

	}
}
