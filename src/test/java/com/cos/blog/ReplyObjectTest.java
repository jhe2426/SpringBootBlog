package com.cos.blog;

import com.cos.blog.model.Reply;
import org.junit.Test;

public class ReplyObjectTest {
	
	@Test
	public void toSringTest() {
		Reply reply = Reply.builder()
				.id(1L)
				.user(null)
				.board(null)
				.content("안녕")
				.build();
		
		System.out.println(reply);
	}
}
