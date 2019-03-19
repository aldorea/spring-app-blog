package com.uv.dbcds.restcomment.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Comment {
	@Min(0)
	private int id;

	@NotNull
	@Size(min = 1)
	private String name;

	@Email
	private String email;

	@NotNull
	@Size(min = 1)
	private String text;

	@JsonIgnore
	private Message parent;

	public Comment() {
	}

	public Comment(int id, String name, String email, String text, Message parent) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.text = text;
		this.parent = parent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

}
