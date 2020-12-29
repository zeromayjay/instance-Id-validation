package com.example.restservice4;

public class Service {

	private final long id;
	private final String name;

	public Service(long id, String name) {
		this.id = id;
		this.name= name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}