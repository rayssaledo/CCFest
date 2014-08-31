package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class Registro extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<Usuario> registroForm = form(Usuario.class)
			.bindFromRequest();

	@Transactional
	public static Result show() {
		return okRegistro("");
	}

	@Transactional
	public static Result registrar() {

		Form<Usuario> form = registroForm.bindFromRequest();

		String nome = form.field("nome").value();
		String email = form.field("email").value();
		String senha = form.field("senha").value();

		Usuario user = null;

		try {
			user = new Usuario(nome, email, senha);
		} catch (Exception e) {
			return okRegistro(e.getMessage());
		}

		if (validate(email)) {
			return okRegistro("Email já está em uso");
		}

		Application.salvaObjeto(user);
		return redirect(routes.Login.show());

	}

	@Transactional
	public static Result okRegistro(String mensagem) {
		return ok(views.html.registro.render(registroForm, mensagem));
	}

	private static boolean validate(String email) {
		List<Usuario> u = dao.findByAttributeName("Usuario", "email", email);

		for (Usuario usuario : u) {
			if (usuario.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}
}
