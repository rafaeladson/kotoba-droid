package net.fiive.kotoba.domain;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.io.Serializable;

public class Question implements Serializable {

	private static final long serialVersionUID = -7612746857877036642L;

	private Long id;
	private String value;
	private String answer;



	public Question( @Nullable Long id, String value, String answer) {
		Preconditions.checkNotNull(value, "Value cannot be null");
		Preconditions.checkNotNull(answer, "Answer cannot be null");
		this.id = id;
		this.value = value;
		this.answer = answer;
	}
	
	public Question(String value, String answer) {
		this(null, value, answer);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Question [value=" + value + ", answer=" + answer + "]";
	}
	
	
	
	
	
	
	

}
