package ar.edu.unq.desapp.grupon022020.backenddesappapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendDesappApiApplicationTests {

	@Test
	void contextLoads() {
		//This is only for test coverage
		String [] args = new String[1];
		args[0] = "arg";
		BackendDesappApiApplication.main(args);
	}

}
