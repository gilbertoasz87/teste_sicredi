package br.com.sicredi.sincronizacao.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import br.com.sicredi.sincronizacao.dto.ContaDTO;

@Service
public class BancoCentralService {

  private static final int MIN_AGENCIA_LENGTH = 4;

  private static final int MIN_CONTA_LENGTH = 7;

  /**
   * Realiza o envio da conta para atualização junto ao Banco Central
   * @param contaDTO dados da conta a ser atualizada
   * @return <b>true</b> se a conta foi atualizada ou <b>false</b> caso contrário.
   */
  public boolean atualizaConta(ContaDTO contaDTO) {
	  return validarConta(contaDTO);
  }

  private boolean validarConta(ContaDTO contaDTO) {
    return !StringUtils.isAnyBlank(contaDTO.agencia(), contaDTO.conta())
       && validarTamanhos(contaDTO.agencia(), contaDTO.conta());
  }

  private boolean validarTamanhos(String agencia, String conta) {
    return agencia.length() >= MIN_AGENCIA_LENGTH && conta.length() >= MIN_CONTA_LENGTH;
  }
}
