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

import org.json.JSONObject;

import cineforum.model.Utente;

@WebServlet("/CheckPasswordAdmin")
public class CheckPasswordAdminControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckPasswordAdminControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		JSONObject jsonResponse = new JSONObject(); //json da inviare nella response
		HttpSession session = request.getSession();		
		Utente admin = (Utente) session.getAttribute("user");
		String passwordInserita = encryptPassword(jsonObj.getString("password"));

		if(passwordInserita.equals(admin.getPassword()))
			jsonResponse.put("esito", true);
		else
			jsonResponse.put("esito", false);
			
		response.setContentType("application/json");
		response.getWriter().write(jsonResponse.toString());
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
}
