package br.com.cursojsf;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "pessoaBean")
public class PessoaBean {
	
	private String nome;
	private String sobrenome;
	private String nomeCompleto;
	
	public String mostrarNomeCompleto() {
		nomeCompleto = nome + " " +sobrenome;
		return ""; //Define pro JSF que irá ficar na mesma página
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
}
