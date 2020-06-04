package com.example.hitachi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.hitachi.mapper")
@SpringBootApplication
public class HitachiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HitachiApplication.class, args);
	}

}
