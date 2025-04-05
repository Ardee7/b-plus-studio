package com.api.b_plus_studio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class BPlusStudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BPlusStudioApplication.class, args);
	}

}
