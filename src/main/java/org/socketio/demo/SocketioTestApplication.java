package org.socketio.demo;

 import org.sanghan.repository.Person;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org/socketio/demo/domain/entity")
public class SocketioTestApplication {
	Person pr = new Person();
	public static void main(String[] args) {
		SpringApplication.run(SocketioTestApplication.class, args);
	}



}
