package com.itdr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itdr.mappers")//扫包
public class Rlg2Application {

	public static void main(String[] args) {
		SpringApplication.run(Rlg2Application.class, args);
	}

}
