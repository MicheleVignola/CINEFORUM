package cineforum.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cineforum.model.Lista;
import cineforum.model.ListaDAO;
import cineforum.model.Utente;
import cineforum.model.UtenteDAO;

/**
 * Gestisce le operazioni relative all'account di un utente
 *
 */
@WebServlet("/InfoAccount")
public class InfoAccountControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InfoAccountControl() {
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
		Utente user = (Utente) session.getAttribute("user");
		UtenteDAO userOp = new UtenteDAO();
		ListaDAO listaOp = new ListaDAO();
		String action = request.getParameter("action");
		String passwordAttuale = null;
		String nuovaPassword = null;
		String username = null;
		
		if(action != null && action.equals("editPassword")) {
			passwordAttuale = encryptPassword(request.getParameter("passwordAttuale"));
			nuovaPassword = encryptPassword(request.getParameter("nuovaPassword"));
			
			//controllo se la password inserita nel campo "password attuale" è diversa da quella dell'utente
			if(!userOp.retrieveByKey(user.getUsername()).getPassword().equals(passwordAttuale)) {
				session.setAttribute("msg", "La password attuale non coincide");
				response.sendRedirect(response.encodeURL("user/infoaccount.jsp"));
				return;
			}
			
			//controllo se la password inserita nel campo "nuova password" è diversa da quella dell'utente
			if(userOp.retrieveByKey(user.getUsername()).getPassword().equals(nuovaPassword)) {
				session.setAttribute("msg", "La nuova password deve essere diversa da quella attuale");
				response.sendRedirect(response.encodeURL("user/infoaccount.jsp"));
				return;
			}
			
			//se la password attuale coincide e la nuova password è diversa procedo con la modifica della password
			user.setPassword(nuovaPassword);
			userOp.update(user);
			session.setAttribute("user", userOp.retrieveByKey(user.getUsername()));
			
			session.setAttribute("msg", "Password cambiata con successo");
			response.sendRedirect(response.encodeURL("user/infoaccount.jsp"));
			
		}
		
		if(action != null && action.equals("deleteList")) {
			username = request.getParameter("username");
			ArrayList<Lista> list = listaOp.retrieveAll(ListaDAO.BY_UTENTE, username);
			clearList(list, listaOp);
			
			session.setAttribute("msgList", "Lista cancellata con successo");
			response.sendRedirect(response.encodeURL("user/infoaccount.jsp"));
		}
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

	private static void clearList(ArrayList<Lista> list, ListaDAO listaOp) {
		Iterator<Lista> iterator = list.iterator();
		Lista element = null;
		
		while(iterator.hasNext()) {
			element = iterator.next();
			listaOp.delete(element);
		}
	}
}
