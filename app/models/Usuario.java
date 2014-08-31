package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String nome;
	private String email;
	private String senha;

	public Usuario() {
	}

	public Usuario(String nome, String email, String senha) throws Exception {
		isNomeValido(nome);
		isEmailValido(email);
		isSenhaValida(senha);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws Exception {
		isEmailValido(email);
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws Exception {
		isSenhaValida(senha);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws Exception {
		isNomeValido(nome);
	}

	private void isNomeValido(String nome) throws Exception {
		if (nome == null) {
			throw new Exception("nome nao pode ser nulo");
		}
		if (nome.trim().equals("")) {
			throw new Exception("nome nao pode ser vazio");
		}
		if (!(nome.matches("[A-Za-zÇ-ú\\s]*+"))) {
			throw new Exception("nome so pode conter caracteres");
		}
		this.nome = nome;
	}

	private void isSenhaValida(String senha) throws Exception {
		if (senha == null) {
			throw new Exception("senha nao pode ser nula");
		}
		if (senha.trim().equals("")) {
			throw new Exception("senha nao pode ser vazia");
		}
		if (senha.length() < 4) {
			throw new Exception("senha nao pode ter tamanho menor que 4");
		}
		this.senha = senha;
	}

	private void isEmailValido(String email) throws Exception {
		if (email == null) {
			throw new Exception("email nao pode ser nulo");
		}
		if (email.trim().equals("")) {
			throw new Exception("email nao pode ser vazio");
		}
		if (!(email
				.matches("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$"))) {
			throw new Exception("dominio do email eh invalido");
		}
		this.email = email;
	}
}
