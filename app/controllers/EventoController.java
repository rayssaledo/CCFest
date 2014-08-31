package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Evento;
import models.EventoComparator;
import models.Tema;
import models.Usuario;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoController extends Controller {

	private final static Form<Evento> EVENTO_FORM = form(Evento.class);
	private final static Form<Usuario> participanteForm = form(Usuario.class);

	@Transactional
	public static Result eventosPorTema(int id) throws Exception {

		List<Evento> todosEventos = Application.getDao().findAllByClassName(
				"Evento");

		List<Evento> eventosRequeridos = new ArrayList<Evento>();

		for (Evento ev : todosEventos) {
			if (ev.getTemas().contains(Tema.values()[(int) id])) {
				eventosRequeridos.add(ev);
			}
		}

		Collections.sort(eventosRequeridos, new EventoComparator());

		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		try {
			json = mapper.writeValueAsString(eventosRequeridos);
		} catch (Exception e) {
			return badRequest();
		}

		return ok(json);
	}

	@Transactional
	public static Result novo() throws Exception {
		Form<Evento> eventoFormRequest = EVENTO_FORM.bindFromRequest();

		if (EVENTO_FORM.hasErrors()) {
			return badRequest();
		} else {
			Evento novoEvento = eventoFormRequest.get();
			Application.salvaObjeto(novoEvento);
			return redirect(controllers.routes.Application.index());
		}
	}

	@Transactional
	public static Result participar(long id) throws Exception {
		Form<Usuario> participanteFormRequest = participanteForm
				.bindFromRequest();

		if (participanteForm.hasErrors()) {
			return badRequest();
		} else {
			Evento evento = Application.getDao().findByEntityId(Evento.class,
					id);
			Usuario novoParticipante = participanteFormRequest.get();
			evento.adicionaParticipante(novoParticipante);
			Application.salvaObjeto(evento);

			return redirect(controllers.routes.Application.index());
		}
	}
}
