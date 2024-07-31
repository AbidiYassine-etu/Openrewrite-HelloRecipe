package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public String hello() {
		return "Hello, from com.example.demo.MyClass";
	}

}



class MyClass{
	public String hello() {
		return "Hello, from com.example.demo.MyClass";
	}

	public String hello() {
		return "Hello, from com.example.demo.MyClass";
	}

	public String hello() {
		return "Hello, from com.example.demo.MyClass";
	} }