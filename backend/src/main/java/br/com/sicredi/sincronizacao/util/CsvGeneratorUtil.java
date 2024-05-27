package br.com.sicredi.sincronizacao.util;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.sicredi.sincronizacao.dto.ContaDTO;

@Component
public class CsvGeneratorUtil {
	
 private static final String CSV_HEADER = "agencia,conta,saldo,status\n";

 public String generateCsv(List<ContaDTO> contas) {
    StringBuilder csvContent = new StringBuilder();
    csvContent.append(CSV_HEADER);

    for (ContaDTO conta : contas) {
        csvContent.append(conta.agencia()).append(",")
                  .append(conta.conta()).append(",")
                  .append(conta.saldo()).append(",")
                  .append(conta.status()).append("\n");
    }

    return csvContent.toString();
 }
}
