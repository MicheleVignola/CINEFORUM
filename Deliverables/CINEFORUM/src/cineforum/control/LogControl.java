package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * Gestisce il Log-In degli utenti
 *
 */
@WebServlet("/LogControl")
public class LogControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LogControl() {
        super();
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String strCheck = null;
		Utente user = null;
		UtenteDAO userOp = new UtenteDAO();
		
		if((strCheck = checkForm(request)) != null) {
			session.setAttribute("error", strCheck);
			response.sendRedirect(response.encodeURL("login.jsp"));
			return;
		}
		
		if((user = userOp.retrieveByKey(request.getParameter("user"))) != null) {
			if (user.getPassword().equals(encryptPassword(request.getParameter("password")))) {
				session.setAttribute("user", user);
				response.sendRedirect(response.encodeURL("homepage.jsp"));
				return;
			}
		}
			session.setAttribute("error", "Username o password non validi");
			response.sendRedirect(response.encodeURL("login.jsp"));
				

	}
	
	/**
	 * Cripta la password utilizzando SHA-512
	 * @param password la passworda da criptare
	 * @return password criptata
	 */
	private static String encryptPassword(String password) 
    { 
        try { 
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 
  
            byte[] messageDigest = md.digest(password.getBytes()); 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        } 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
	/**
	 * Verifica se i parametri inseriti dall'utente rispettano certi criteri	
	 * @param request la request contenente i parametri 
	 * @return messaggio di errore, altrimenti null
	 */
	private String checkForm(HttpServletRequest request) {
		String user = request.getParameter("user");
		String pass = request.getParameter("password");
		
		if (user == null || user.trim().length() < 4 || user.length() > 20)
			return "L'username deve essere lungo almeno 4 caratteri e meno di 20";
		if (user == null || pass.trim().length() < 8 || pass.trim().length() > 20)
			return "La password deve essere lungo almeno 8 caratteri e meno di 20";
		
		return null;
	}

}
