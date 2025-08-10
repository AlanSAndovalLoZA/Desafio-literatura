package com.alanSandoval.DesafioLiteratura;

import com.alanSandoval.DesafioLiteratura.Service.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DesafioLiteraturaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DesafioLiteraturaApplication.class, args);
		Principal principal = context.getBean(Principal.class);
		principal.mostrarMenu();
	}

}
