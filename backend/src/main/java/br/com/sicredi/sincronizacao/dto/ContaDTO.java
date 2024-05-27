package br.com.sicredi.sincronizacao.dto;

public record ContaDTO(String agencia, String conta, Double saldo, boolean status) {
    
	public ContaDTO withStatus(boolean status) {
        return new ContaDTO(agencia(), conta(), saldo(), status);
    }

}
