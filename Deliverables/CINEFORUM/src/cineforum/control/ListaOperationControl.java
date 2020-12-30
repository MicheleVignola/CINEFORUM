package cineforum.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.Lista;
import cineforum.model.ListaDAO;

/**
 * Gestisce le operazioni di inserimento, modifica e eliminazione di un film nella lista di un utente
 * 
 */
@WebServlet("/ListaOperation")
public class ListaOperationControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ListaOperationControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		return;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		Lista bean = null;
		ListaDAO listaOp = null;
		String requestPage = request.getHeader("referer");
		
		if (action != null) {
			bean = new Lista();
			listaOp = new ListaDAO();
			
			bean.setCodiceFilm(Integer.parseInt(request.getParameter("codiceFilm")));
			bean.setUsername(request.getParameter("username"));
			bean.setCategoria(request.getParameter("categoria"));
			
			switch(action) {
				case "insert":
					bean.setVoto(Short.parseShort(request.getParameter("voto")));
					listaOp.save(bean);
					break;
				case "edit":
					bean.setVoto(Short.parseShort(request.getParameter("voto")));
					listaOp.update(bean);
					break;
				case "remove":
					listaOp.delete(bean);
					break;
			}
		}
		
		response.sendRedirect(response.encodeURL(requestPage));
	}

}
