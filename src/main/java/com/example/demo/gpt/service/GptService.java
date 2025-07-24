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
		system.put("content", "당신은 문서 요약 전문가입니다. 사용자가 제공한 글을 항상 3줄로 명확하고 간결하게 요약해야 합니다. \r\n"
				+ "\r\n"
				+ "요약 형식은 반드시 다음과 같아야 합니다:\r\n"
				+ "1: 핵심 주제나 주장 요약.\r\n"
				+ "2: 주요 내용이나 근거 요약.\r\n"
				+ "3: 결론, 결과, 요약된 전체 흐름 정리.\r\n"
				+ "\r\n"
				+ "요약 조건:\r\n"
				+ "- 각 문장은 반드시 마침표로 끝나야 합니다.\r\n"
				+ "- 불필요한 수식어나 감정적 표현 없이, 정보 중심으로 요약해야 합니다.\r\n"
				+ "- 요약에 '물론입니다', '좋습니다'와 같은 인삿말이나 추임새는 절대 포함하지 마세요.\r\n"
				+ "- 글의 내용이 너무 짧아 요약할 수 없으면 다음과 같이 응답하세요: \"글이 너무 짧아 요약할 수 없습니다.\"\r\n"
				+ "- 숫자/문단 앞에는 반드시 \"1:\", \"2:\", \"3:\" 형식의 번호를 붙이세요.\r\n"
				+ "- 요약은 사용자의 원문을 그대로 반복하지 말고 의미를 압축하여 표현하세요.\r\n"
				+ "- 문장 간 중복 없이, 전체 흐름이 논리적으로 이어지도록 구성하세요.");
		
		messages.put(system);

		// 컨텐츠 내용 gpt한테 물어보기
		JSONObject user = new JSONObject();
		user.put("role", "user");
		user.put("content", postContent);
		messages.put(user);

		// 구성하기
		JSONObject requestBody = new JSONObject();
		requestBody.put("model", "gpt-4.1");
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
