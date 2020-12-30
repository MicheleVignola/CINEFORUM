package cineforum.control;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.Utente;

/**
 * 
 * Filtro per impedire l'accesso ad alcune pagine, riservate a chi è registrato, a coloro che non sono loggati
 *
 */
@WebFilter(urlPatterns = { "/user/*","/CommentoFilm", "/InfoAccount", "/ListaOperation", "/RichiestaFilm", "/LoutCheck" })
public class UserFilter implements Filter {


    public UserFilter() {

    }


	public void destroy() {

	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Utente user = (Utente) session.getAttribute("user");
		
		if (user == null) {
			HttpServletResponse resp = (HttpServletResponse) response;
			session.setAttribute("error", "Devi essere loggato per accedere a questa pagina");
			resp.sendRedirect(resp.encodeURL("login.jsp"));
		}
		
		
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
