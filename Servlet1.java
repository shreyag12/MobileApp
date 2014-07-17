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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String receivedstring = null;
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
			sig.update(receivedstring.getBytes());
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
			
			BufferedReader br=new BufferedReader(request.getReader());
			Gson gson=new Gson();
			MyEntity obj=gson.fromJson(br, MyEntity.class);
			 receivedstring=obj.getStr();
			 System.out.println(receivedstring);
			byte[]signBytes=obj.getSignature();
			System.out.println(new String(signBytes));
			byte[]publicKeyBytes=obj.getPublickeybytes();
			System.out.println(new String(publicKeyBytes));
			result = verify(signBytes, publicKeyBytes);

			OutputStreamWriter writer = new OutputStreamWriter(
					response.getOutputStream());
			if (result) {
				System.out.println("Signature successfully validated");
				writer.write("signature successfully validated" + "\n");
			} else {
				System.out.println("error in validating signature");
				writer.write("error in validating signature" + "\n");
			}

			
			writer.flush();
			writer.close();

			response.setStatus(HttpServletResponse.SC_OK);
		} catch (IOException e) {
			response.getWriter().println(e);
		}
	}

}
