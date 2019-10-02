package br.com.cursojsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.facelets.FaceletContext;

import org.apache.jasper.tagplugins.jstl.core.Url;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> dao = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();

	public String salvar() {
		pessoa = dao.merge(pessoa);
		this.carregarPessoas();

		mostrarMsg("Cadastrado com Sucesso!");

		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public String limpar() {
		pessoa = new Pessoa();
		return "";
	}

	public String remover() {
		dao.deletePorId(pessoa);
		pessoa = new Pessoa();

		this.carregarPessoas();

		mostrarMsg("Removido com Sucesso!");

		return "";
	}

	public String logar() {

		Pessoa p = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		if (p != null) {// Achou o Usuário

			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("pessoaLogado", p);

			return "primeiraPagina.jsf";

		}

		return "index.jsf";
	}

	public boolean permiteAcesso(String acesso) {

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa p = (Pessoa) externalContext.getSessionMap().get("pessoaLogado");

		return p.getPerfilUser().equals(acesso);

	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = dao.getListEntity(Pessoa.class);
	}

	private void mostrarMsg(String msg) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public void pesquisaCep( AjaxBehaviorEvent event) {
		
		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection conn = url.openConnection();
			InputStream fluxoCep = conn.getInputStream();
			BufferedReader br = new BufferedReader( new  InputStreamReader(fluxoCep, "UTF-8"));
			
			String cep = "";
			StringBuffer jsonCep = new StringBuffer();
			
			while( (cep = br.readLine()) != null ) {
				jsonCep.append(cep);
			}
			
			System.out.println(jsonCep);
			
			
		} catch( Exception ex ) {
			
			ex.printStackTrace();
			mostrarMsg("Erro ao consultar o CEP");
			
		}
			
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
}
