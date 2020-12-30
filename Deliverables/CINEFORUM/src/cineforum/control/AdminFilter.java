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
 * Restringe l'accesso ai non autorizzati alle sezioni dell'admin
 *
 */
@WebFilter(urlPatterns = {"/admin/*", "/GestioneFilm", "/GestioneRichieste", "/GestioneUtenti" })
public class AdminFilter implements Filter {

    public AdminFilter() {

    }


	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Utente user = (Utente) session.getAttribute("user");

		if (user == null || !user.getRuolo().equals("admin")) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(resp.encodeURL( request.getServletContext().getContextPath() +"/homepage.jsp"));
			return;
		}
		
		
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
