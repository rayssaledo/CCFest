package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.db.jpa.Transactional;

public class Login extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<Usuario> loginForm = form(Usuario.class)
			.bindFromRequest();

	@Transactional
	public static Result show() {
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
		return okLogin("");
	}

	@Transactional
	public static Result logout() {
		session().clear();
		return show();
	}

	@Transactional
	public static Result authenticate() {

		Form<Usuario> form = loginForm.bindFromRequest();
		
		String email = form.field("email").value();
		String senha = form.field("senha").value();


		if (isDadosInvalidos(email, senha)) {
			return okLogin("Email ou senha inválidos");
		} else {
			Usuario user = (Usuario) dao.findByAttributeName("Usuario",
					"email", email).get(0);
			session().clear();
			session("user", user.getEmail());
			return redirect(routes.Application.index());
		}
	}

	private static boolean isDadosInvalidos(String email, String senha) {
		return loginForm.hasErrors() || !validate(email, senha);
	}

	@Transactional
	public static Result okLogin(String mensagem) {
		return ok(views.html.login.render(loginForm, mensagem));
	}

	private static boolean validate(String email, String senha) {
		List<Usuario> u = dao.findByAttributeName("Usuario", "email", email);
		if (u == null || u.isEmpty()) {
			return false;
		}
		if (!u.get(0).getSenha().equals(senha)) {
			return false;
		}
		return true;
	}
}