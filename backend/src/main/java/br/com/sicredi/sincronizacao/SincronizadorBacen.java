package br.com.sicredi.sincronizacao;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import br.com.sicredi.sincronizacao.service.SincronizacaoService;

@SpringBootApplication
public class SincronizadorBacen {

	public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = SpringApplication.run(SincronizadorBacen.class, args);
		SincronizacaoService service = applicationContext.getBean(SincronizacaoService.class);
	    service.syncAccounts(args[0]);
	}

}
