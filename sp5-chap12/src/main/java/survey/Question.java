package survey;

import java.util.Collections;
import java.util.List;

public class Question {

	private String title; //질문 제목
	private List<String> options; //답변 옵션 보관
	
	public Question(String title, List<String> options) {
		this.title = title;
		this.options = options;
	}
	//주관식 답변을 위한 생성자
	public Question(String title) {
		this(title, Collections.<String>emptyList());
	}

	public String getTitle() {
		return title;
	}

	public List<String> getOptions() {
		return options;
	}
	
	public boolean isChoice() {
		return options != null && !options.isEmpty();
	}
	
	
	
}
