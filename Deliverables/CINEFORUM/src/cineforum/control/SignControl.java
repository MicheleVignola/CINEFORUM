package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

/**
 * 
 * Gestisce la registrazione degli utenti
 *
 */

@WebServlet("/SignControl")
public class SignControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SignControl() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strCheck = null;
		HttpSession session = request.getSession();
		Utente user;
		UtenteDAO userOp = new UtenteDAO();
		
		if((strCheck = checkForm(request)) != null) {
			session.setAttribute("errorSign", strCheck);
			response.sendRedirect("signup.jsp");
			return;
		}
		
		//verifico se l'utente è già presente nel database
		if(userOp.retrieveByKey(request.getParameter("user")) != null) {
			session.setAttribute("error", "Utente già presente");
			response.sendRedirect("signup.jsp");
			return;
		}
		
		//verifico se l'email inserita è già occupata
		if(!(userOp.retrieveAll(UtenteDAO.BY_EMAIL, request.getParameter("email")).isEmpty())) {
			session.setAttribute("error", "Email già presente");
			response.sendRedirect("signup.jsp");
			return;
		}
		
		//verifico se le due password inserite corrispondono
		if(!request.getParameter("password").equals(request.getParameter("ripetiPassword"))) {
			session.setAttribute("error", "Le password non corrispondono");
			response.sendRedirect("signup.jsp");
			return;
		}
		
		user = new Utente();
		user.setUsername(request.getParameter("user"));
		user.setPassword(encryptPassword(request.getParameter("password")));
		user.setEmail(request.getParameter("email"));
		user.setRuolo("registered");
		userOp.save(user);
		
		response.sendRedirect("homepage.jsp");
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
	 * Verifica se i parametri inviati dall'utente tramite il form rispettano criteri
	 * @param request la request contenente i parametri del form da controllare
	 * @return messaggio di errore, altrimenti null
	 */
	private String checkForm(HttpServletRequest request) {
		String user = request.getParameter("user");
		String pass = request.getParameter("password");
		String email = request.getParameter("email");
		if (user == null || user.trim().length() < 5 || user.length() > 20 )
			return "L'username deve essere lungo almeno 5 caratteri e meno di 20";
		if (pass == null || pass.trim().length() < 8 || pass.trim().length() > 20)
			return "La password deve essere lunga almeno 4 caratteri e meno di 20";
		
		if (email == null || email.trim().length() > 50)
			return "E-mail non valida";
		
		return null;
		
	}

	
}
