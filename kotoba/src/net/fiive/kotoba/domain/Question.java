package net.fiive.kotoba.domain;

import java.io.Serializable;

public class Question implements Serializable {

	private static final long serialVersionUID = -7612746857877036642L;
	
	private String value;
	private String translation;
	
	
	public Question(String value, String answer) {
		super();
		this.value = value;
		this.translation = answer;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getTranslation() {
		return translation;
	}


	public void setTranslation(String translation) {
		this.translation = translation;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((translation == null) ? 0 : translation.hashCode());
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
		if (translation == null) {
			if (other.translation != null)
				return false;
		} else if (!translation.equals(other.translation))
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
		return "Question [value=" + value + ", translation=" + translation + "]";
	}
	
	
	
	
	
	
	

}
