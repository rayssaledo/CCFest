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
import views.html.registro;

public class Registro extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	static Form<Usuario> registroForm = form(Usuario.class).bindFromRequest();

	@Transactional
    public static Result show() {
        return ok(registro.render(registroForm));
    }
    
	@Transactional
	public static Result registrar() {
		
		Usuario u = registroForm.bindFromRequest().get();
    	
		if (registroForm.hasErrors()) {
			flash("fail", "Email já está em uso");
            return badRequest(registro.render(registroForm));
        } else if(validate(u.getEmail())){
        	flash("fail", "Email já está em uso");
            return badRequest(registro.render(registroForm));	
        } else {
        	dao.persist(u);
        	dao.merge(u);
        	dao.flush();
            return redirect(routes.Login.show());
        }
    }
	
	private static boolean validate(String email) {
		List<Usuario> u = dao.findByAttributeName("Usuario", "email", email);
		if (u == null || u.isEmpty()) {
			return false;
		} return true;
	}

}
