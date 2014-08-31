package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity
public class Evento {

	@Id
	@GeneratedValue
	private long id;

	@Required
	@MaxLength(value = 40)
	private String titulo;

	@Required
	@MaxLength(value = 450)
	@Column(name = "CONTENT", length = 450)
	private String descricao;

	@Temporal(value = TemporalType.DATE)
	@Required
	private Date data;

	@OneToMany
	private List<Usuario> participantes;

	@ElementCollection
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private List<Tema> temas = new ArrayList<Tema>();

	public Evento() {
		participantes = new ArrayList<Usuario>();
	}

	public Evento(String titulo, String descricao, Date data, List<Tema> temas)
			throws Exception {
		this();
		isSetTitulo(titulo);
		isSetDescricao(descricao);
		isSetData(data);
		isSetTemas(temas);
	}

	public void adicionaParticipante(Usuario usuario) throws Exception {
		if (usuario == null) {
			throw new Exception("Não pode adicionar usuario nulo");
		}
		if (participantes.contains(usuario)) {
			throw new Exception("Já está participando do evento");
		}
		participantes.add(usuario);
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public long getId() {
		return id;
	}

	public Integer getTotalDeParticipantes() {
		return participantes.size();
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTitulo(String titulo) throws Exception {
		isSetTitulo(titulo);
	}

	public void setDescricao(String descricao) throws Exception {
		isSetDescricao(descricao);
	}

	public void setData(Date data) throws Exception {
		isSetData(data);
	}

	public void setTemas(List<Tema> temas) throws Exception {
		isSetTemas(temas);
	}

	private void isSetTitulo(String titulo) throws Exception {
		if (titulo == null) {
			throw new Exception("Título não pode ser nulo");
		}
		if (titulo.trim().equals("")) {
			throw new Exception("Título não pode ser vazio");
		}
		if (titulo.length() > 40) {
			throw new Exception("Título não pode ter mais de 40 caracteres");
		}
		this.titulo = titulo;
	}

	private void isSetDescricao(String descricao) throws Exception {
		if (descricao == null) {
			throw new Exception("Descrição não pode ser nula");
		}
		if (descricao.trim().equals("")) {
			throw new Exception("Descrição não pode ser vazia");
		}
		if (descricao.length() > 450) {
			throw new Exception("Descrição longa");
		}
		this.descricao = descricao;
	}

	private void isSetData(Date data) throws Exception {
		if (data == null) {
			throw new Exception("Data não pode ser nula");
		}
		if (data.compareTo(new Date()) < 0) {
			throw new Exception("Data inválida");
		}
		this.data = data;
	}

	private void isSetTemas(List<Tema> temas) throws Exception {
		if (temas == null) {
			throw new Exception("Lista de temas não pode ser nula");
		}
		if (temas.size() == 0) {
			throw new Exception("Não foi adicionado nenhum tema");
		}
		this.temas = temas;
	}

}
