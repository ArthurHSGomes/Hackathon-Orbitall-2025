package br.com.orbitall.channels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.orbitall.channels.repositories")
public class ChannelsApplication {

	public static void main(String[] args) {

        SpringApplication.run(ChannelsApplication.class, args);
	}

}
