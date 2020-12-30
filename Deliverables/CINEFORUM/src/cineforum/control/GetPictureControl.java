package cineforum.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cineforum.model.Film;
import cineforum.model.FilmDAO;

/**
 * Produce response contenente l'immagine del film da mostrare sulla pagina nell'element img
 *
 */
@WebServlet("/GetPicture")
public class GetPictureControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetPictureControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer codice = Integer.parseInt(request.getParameter("film"));
		FilmDAO filmOp = new FilmDAO();
		Film film = filmOp.retrieveByKey(codice);
		byte[] bt = film.getImmagine();
		
		if (bt != null) {
			ServletOutputStream out = response.getOutputStream();
			out.write(bt);
			response.setContentType("image/png");
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
