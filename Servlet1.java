package Servlet1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	private String publicKey = null;
	private String sign = null;
	private String originalString = null;
	boolean result = false;
	
	public Servlet1() {
		// TODO Auto-generated constructor stub
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("static-access")
	public boolean verify(byte[] sign, byte[] pubkey) {

		KeyFactory keyFact = null;
		Signature sig = null;
		boolean isVerified = false;
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubkey);
		
		try {
			keyFact = KeyFactory.getInstance("RSA");
			PublicKey generatedPublicKey = keyFact.generatePublic(x509KeySpec);
			sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(generatedPublicKey);
			sig.update(originalString.getBytes());
			isVerified = sig.verify(sign);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isVerified;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = request.getReader();
			String line = null;
			while((line = reader.readLine()) != null){
				buffer.append(line);
			}
			String receivedstring = new String(buffer);
			
			System.out.println(receivedstring);
			String[] combinedString = receivedstring.split(" ");
			sign = combinedString[0];
			publicKey = combinedString[1];
			originalString = combinedString[2];
			
			System.out.println("Sign:- "+sign);
			System.out.println("Public key:- "+publicKey);
			System.out.println("Original String:- "+originalString);

			byte[] signBytes = Base64.decodeBase64(sign);
			byte[] publicKeyBytes = Base64.decodeBase64(publicKey);
			result = verify(signBytes, publicKeyBytes);
			if (result) {
				System.out.println("Signature successfully validated");
			} else {
				System.out.println("error in validating signature");
			}

			OutputStreamWriter writer = new OutputStreamWriter(
					response.getOutputStream());
			writer.write("signature successfully validated" + "\n");
			writer.flush();
			writer.close();

			response.setStatus(HttpServletResponse.SC_OK);
			// sin.close();
		} catch (IOException e) {
			response.getWriter().println(e);
		}
	}

}
