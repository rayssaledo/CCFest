package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import models.Evento;
import models.Usuario;
import models.Tema;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	private static boolean criouEventosFake = false;
	private static GenericDAO dao = new GenericDAOImpl();
	private static final int DATA_SETE = 7, DATA_TRES = 3, DATA_UM = 1,
			DATA_DOZE = 12, DATA_DEZESSETE = 17, DATA_CINCO = 5,
			DATA_VINTE_UM = 21, DATA_QUINZE = 15, DATA_OITO = 8, TRES = 3;

	@Transactional
	public static Result index() throws Exception {
		if (session().get("user") == null) {
			return redirect(routes.Login.show());
		}
		if (!criouEventosFake) {
			List<Evento> eventos = criarEventosFakes();
			criarParticipacoesFake(eventos);

			criouEventosFake = true;
		}
		Usuario user = new Usuario();
		if (dao.findByAttributeName("Usuario", "email", session().get("user"))
				.size() > 0) {
			if (dao.findByAttributeName("Usuario", "email",
					session().get("user")).get(0) != null) {
				user = (Usuario) dao.findByAttributeName("Usuario", "email",
						session().get("user")).get(0);
			}
		}
		return ok(views.html.index.render(user));
	}

	public static GenericDAO getDao() {
		return dao;
	}

	private static List<Evento> criarEventosFakes() throws Exception {
		try {
			List<Evento> eventos = new ArrayList<Evento>();
			Evento evento;
			Calendar calendar;

			List<Tema> temas = new ArrayList<Tema>();

			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_SETE);

			evento = new Evento(
					"Python na mente e coração",
					"Neste evento iremos debater e propor soluções para novas releases.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.ARDUINO);
			temas.add(Tema.ELETRONICA);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento(
					"Luta de robôs",
					"Traga seu robô feito em arduino e traga para competir com outros.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, DATA_UM);

			evento = new Evento(
					"IV Olímpiadas de programação da UFCG",
					"Traga sua equipe e venha competir nessa maratona de programação.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);
			temas.add(Tema.PROGRAMACAO);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DOZE);

			evento = new Evento(
					"II Encontro para programadores de Python",
					"O encontro contará com a participação de um de seus fundadores, inúmeras palestras e maratonas. Não percam!!",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			calendar.add(Calendar.DAY_OF_WEEK, DATA_TRES);

			evento = new Evento(
					"III Semana da Computação Verde",
					"Exiba sua proposta para uma computação mais verde e concorra a diversos prêmios",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.WEB);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_DEZESSETE);

			evento = new Evento(
					"Web em foco",
					"Este evento contará com a participação de um dos fundadores da Web, e juntos iremos compartilhar diversas dicas e boas práticas nessa vasta área.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_CINCO);

			evento = new Evento(
					"Minicurso Arduino",
					"Evento destinado a alunos de LOAC, caso sobre vagas iremos disponibilizar em breve",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.ELETRONICA);
			temas.add(Tema.ARDUINO);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_VINTE_UM);

			evento = new Evento(
					"Curto circuito",
					"Evento sobre circuitos eletrônicos, venha dar curto em seus circuitos também!!",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.DESAFIOS);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_QUINZE);

			evento = new Evento(
					"VI Encontro de Docentes de CC",
					"Evento para debatermos propostas e soluções para os problemas enfrentados pelos alunos de CC.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			temas = new ArrayList<Tema>();
			temas.add(Tema.PROGRAMACAO);
			temas.add(Tema.DESAFIOS);

			calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, DATA_OITO);

			evento = new Evento(
					"Café com Java",
					"Curso destinado apenas a alunos cursando a disciplina LP2.",
					calendar.getTime(), temas);
			eventos.add(evento);
			salvaObjeto(evento);

			return eventos;
		} catch (Exception e) {
			return null;
		}
	}

	private static void criarParticipacoesFake(List<Evento> eventos) {
		Random rnd = new Random();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			usuarios.add(new Usuario("Joaquina", "joaquinha@mail.com", "123456"));
			usuarios.add(new Usuario("Tomas Turbando", "tomas@mail.com",
					"123456"));
			usuarios.add(new Usuario("Alberto Leça", "alberto_leca@mail.com",
					"123456"));
			usuarios.add(new Usuario("Belmifer Linares",
					"belmifer_linares@mail.com", "123456"));

			usuarios.add(new Usuario("Belo", "belo_gatixnho10@mail.com",
					"123456"));
			usuarios.add(new Usuario("Célia Rúa", "celia_rua@mail.com",
					"123456"));
			usuarios.add(new Usuario("Deolindo Castello Branco",
					"deolindo_castello@mail.com", "123456"));
			usuarios.add(new Usuario("Doroteia Pasos",
					"doroteia_passos@mail.com", "123456"));

			usuarios.add(new Usuario("Eugénio Palhares",
					"eugenio_palhares@mail.com", "123456"));
			usuarios.add(new Usuario("Fausto Furtado",
					"fausto_furtado@mail.com", "123456"));
			usuarios.add(new Usuario("Filipa Leiria", "filipa_leiria@mail.com",
					"123456"));
			usuarios.add(new Usuario("Leonilde Figueiredo",
					"leonilde_figueiredo@mail.com", "123456"));
			usuarios.add(new Usuario("Pascoal Caldeira",
					"pascoal_caldeira@mail.com", "123456"));
			usuarios.add(new Usuario("Paula Lousado", "paula_lousado@mail.com",
					"123456"));
			usuarios.add(new Usuario("Quitério Galindo",
					"quiterio_galindo@mail.com", "123456"));
			usuarios.add(new Usuario("Paula Lousado", "paula_lousado@mail.com",
					"123456"));
			usuarios.add(new Usuario("Rosa Varejão", "rosa_varejao@mail.com",
					"123456"));
			usuarios.add(new Usuario("Sonia Gabeira", "sonia_gabeira@mail.com",
					"123456"));
			usuarios.add(new Usuario("Érico Albuquerque",
					"erico_albuquerque@mail.com", "123456"));
			usuarios.add(new Usuario("Tairine Reis", "tairine_reis@mail.com",
					"123456"));

			for (Usuario usuario : usuarios) {
				salvaObjeto(usuario);
			}

			for (Usuario usuario : usuarios) {
				eventos.get(rnd.nextInt(TRES)).adicionaParticipante(usuario);
			}
		} catch (Exception e) {
		}
		for (Evento evento : eventos) {
			salvaObjeto(evento);
		}
	}

	@Transactional
	protected static void salvaObjeto(Object obj) {
		dao.persist(obj);
		dao.merge(obj);
		dao.flush();
	}

}
