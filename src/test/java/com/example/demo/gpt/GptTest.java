package com.example.demo.gpt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.gpt.service.GptService;

import java.io.IOException;

@SpringBootTest
public class GptTest {
	
	@Autowired
	GptService service;
	
	@Test
	public void gpt테스트() throws IOException, InterruptedException {
		
//		String 요약 = service.callGptApi("8월말에 일본 도쿄 여행을 계획하고 있어서요 \r\n"
//				+ "아무래도 한국보다 일본이 매장이나 제품수도 많고 \r\n"
//				+ "특히나 펜더 플레그쉽 스토어 꼭 가보고 싶은데 왠지 가면 지름신이 올거같아서 구입을 하지 않을까...\r\n"
//				+ "\r\n"
//				+ "근데 아무래도 해외에서 악기를 구입한적이 없어서 여러가지가 궁금합니다. (관세랄지 세금이랄지..)\r\n"
//				+ "\r\n"
//				+ "구입하게 되면 항공용으로 박스포장해달라고 할 예정인데 위탁수화물로 하려면 어떻게 하면 될까요?\r\n"
//				+ "구입하신분들보면 넥을 분해해서 가지고오셔서 다시 조립하시던데 왠지 새 악기를 분해하기가 좀 꺼려져서....\r\n"
//				+ "항공사는 에어부산이나 대한항공을 이용하려고하는데 어느 항공사가 좀더 나을까요?\r\n"
//				+ "\r\n"
//				+ "혹시나 사가지고 올때 팁이랄지 주의사항이랄지 그런게 있으면 미리 알고가고 싶어서 질문 드립니다!\r\n"
//				+ "\r\n"
//				+ "관세도 미화 800불까진 괜찮은걸로 아는데 만약 800불을 초과하게 되면 초과분에 대한 세금만 내는건지 \r\n"
//				+ "아니면 제품 금액에 대한 세금을 다 내는건지도 궁금합니다! 아시는 분 있으면 공유 부탁드립니다!\r\n"
//				+ "\r\n"
//				+ "미리 감사드립니다!\r\n"
//				+ "\r\n"
//				+ "아 그리고 엠프도 구입해서 오는것도 가능할까요? 엠프헤드만....\r\n"
//				+ "\r\n"
//				+ "모르는게 너무 많네요 ㅠ");
		
		String 요약 = service.callGptApi("20자도 안넘는 짦은 글");
		
		System.out.println(요약);
		
	}
}