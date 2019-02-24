/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.utils;

import java.io.*;
import java.util.*;
import javax.swing.*;

import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseFileConnect;


/**
 * @author Omar R. A. - BAF Consulting S.C / Para Grupo Gigante "Super Precio"
 *         Clase que contiene los metodos necesarios para la gestion del archivo
 *         encriptado que contiene la información para conectarse a un sistema
 *         SAP.
 */
public class ArchLogg {

/**
 * Metodo main de esta clase, el cual nos permite mandar ejecutar el metodo CreaArch
 * de esta misma clase, para la creación del archivo enctriptado.
 * @param args No se esta ocupando 
 */
	public static void main(String args[]) {
		CreaArch();
	}

	/**
	 * Metodo para crear un archivo encriptado que contenga toda la información
	 * necesaria para firmarse en un sistema SAP.
	 * 
	 * @param pClient
	 *            Mandante
	 * @param pHost
	 *            Nombre del servidor SAP
	 * @param pLangu
	 *            Lenguaje en el que se va a firmar
	 * @param pSysNum
	 *            Numero de sistema (SAP)
	 * @param pUsr
	 *            Usuario (SAP)
	 * @param pPass
	 *            Contraseña del usuario (SAP)
	 * @return String Estatus de la creación del archivo ("OK" se creo
	 *         correctamente).
	 */
	private static String creLogg(String pClient, String pHost, String pLangu,
			String pSysNum, String pUsr, String pPass) {

		my_pro.setProperty("Client", Encript.Encripta(pClient, gKey));
		my_pro.setProperty("Host", Encript.Encripta(pHost, gKey));
		my_pro.setProperty("Langu", Encript.Encripta(pLangu, gKey));
		my_pro.setProperty("SysNum", Encript.Encripta(pSysNum, gKey));
		my_pro.setProperty("User", Encript.Encripta(pUsr, gKey));
		my_pro.setProperty("Pass", Encript.Encripta(pPass, gKey));

		File fLogg = new File(gFile);
		try {
			FileOutputStream fos = new FileOutputStream(fLogg, false);
			my_pro.store(fos, "Arch. de conf. a SAP");
			fos.close();
			return "OK";
		} catch (Exception e) {
			return "Error " + e.toString();
		}
	}

	/**
	 * Metodo para leer el archivo encriptado, desencriparlo almacenar en un
	 * objeto statico de la clase SapSystem la información necesaria para la
	 * conexion a un sistema SAP.
	 * 
	 * @return String Status final del proceso de lectura y desencriptación del
	 *         archivo ("OK" todo bien)
	 */
	public static String leeLogg() {// String leeLogg() {

		try {
			//my_pro.load(new FileInputStream(gFile));
			//System.out.println(System.getProperty("java.class.path"));			
			CheckFileConnection archivoIS = new CheckFileConnection();
			ResponseFileConnect resp = new ResponseFileConnect();
			resp=archivoIS.getFileConnection();
			
			if(resp.getMensaje().equals("SUCCESS"))
			{
				my_pro.load(archivoIS.getFileConnection().getFileConnect());
				ArchLogg.setSClient(Encript.Desencripta(my_pro
						.getProperty("Client"), gKey));
				ArchLogg.setSServer(Encript.Desencripta(my_pro.getProperty("Host"),
						gKey));
				ArchLogg.setSLangu(Encript.Desencripta(sLangu = my_pro
						.getProperty("Langu"), gKey));
				ArchLogg.setSNum(Encript.Desencripta(sNum = my_pro
						.getProperty("SysNum"), gKey));
				ArchLogg.setSUser(Encript.Desencripta(sUser = my_pro
						.getProperty("User"), gKey));
				ArchLogg.setSPass(Encript.Desencripta(sPass = my_pro
						.getProperty("Pass"), gKey));
	
				system.setClient(ArchLogg.getSClient());
				system.setHost(ArchLogg.getSServer());
				system.setLanguage(ArchLogg.getSLangu());
				system.setSystemNumber(ArchLogg.getSNum());
				system.setUser(ArchLogg.getSUser());
				system.setPassword(ArchLogg.getSPass());
				
				return "OK";
		    }
			else
			{
				return "NO";
			}

		} catch (Exception e) {
			//e.printStackTrace();
			return "NO";
			
		}
	}
	
	

/**
 * Metodo con interfaz grafica que permite la creacición del archivo encriptado, el cual
 * contendra toda la información necesaria para establecer la conexion a un sistema SAP
 * esto es establecido en la información que es solicitada en este metodo.	
 */
	public static void CreaArch() {

		String pHost = JOptionPane.showInputDialog("Nombre del servidor SAP");
		String pSysNum = JOptionPane.showInputDialog("Numero de Sistema SAP (2 caracteres num.)");
		String pClient = JOptionPane.showInputDialog("Mandate del sistema SAP (3 caracteres num)");
		String pLangu = JOptionPane
				.showInputDialog("Lenguaje con el que se va aconect. a SAP (Exmp: ES o EN)");
		String pUser = JOptionPane.showInputDialog("Usuario SAP");
		String pPass = JOptionPane
				.showInputDialog("Contraseña del usuario SAP");

		if (pHost.equals("") || pClient.equals("") || pSysNum.equals("")
				|| pLangu.equals("") || pUser.equals("") || pPass.equals("")) {
			JOptionPane.showMessageDialog(null, "Es necesario completar todos los campos",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else {

			String pStatus = ArchLogg.creLogg(pClient, pHost, pLangu, pSysNum,
					pUser, pPass);
			if (pStatus.equals("OK")) {
				JOptionPane.showMessageDialog(null,
						"Se creo exitosamente el archivo" + gFile, "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, pStatus, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	/**
	 * @return the sClient Mandante al que se quiere firmar en el sistema SAP
	 */
	private static String getSClient() {
		return sClient;
	}

	/**
	 * @param client
	 *            Mandante al que se quiere firmar en el sistema SAP
	 */
	private static void setSClient(String client) {
		sClient = client;
	}

	/**
	 * @return the sServer Nombre del servidor SAP
	 */
	private static String getSServer() {
		return sServer;
	}

	/**
	 * @param server
	 *            Nombre del servidor SAP
	 */
	private static void setSServer(String server) {
		sServer = server;
	}

	/**
	 * @return the sLangu El lenguaje con el que se desea entrar al sistema SAP
	 */
	private static String getSLangu() {
		return sLangu;
	}

	/**
	 * @param langu
	 *            El lenguaje con el que se desea entrar al sistema SAP
	 */
	private static void setSLangu(String langu) {
		sLangu = langu;
	}

	/**
	 * @return the sNum Numero de Sistema SAP al que se desea conectar
	 */
	private static String getSNum() {
		return sNum;
	}

	/**
	 * @param num
	 *            Numero de Sistema SAP al que se desea conectar
	 */
	private static void setSNum(String num) {
		sNum = num;
	}

	/**
	 * @return Usuario con el que se accedera al sistema SAP
	 */
	private static String getSUser() {
		return sUser;
	}

	/**
	 * @param user
	 *            Usuario con el que se accedera al sistema SAP
	 */
	private static void setSUser(String user) {
		sUser = user;
	}

	/**
	 * @return the sPass Contraseña del usuario con el que se accedera al
	 *         sistema SAP
	 */
	private static String getSPass() {
		return sPass;
	}

	/**
	 * @param pass
	 *            Contraseña del usuario con el que se accedera al sistema SAP
	 */
	private static void setSPass(String pass) {
		sPass = pass;
	}

	/**
	 * @return the SapSystem Objeto de la clase SapSystem el cual contiene la
	 *         información necesaria para la conexion al servidor SAP
	 *         especificado en el archivo plano
	 */
	public static SapSystem getSapSystem() {
		return system;
	}

	private static String gKey = "$P$@92Oo9";
	private static Properties my_pro = new Properties();
	private static String gFile = "./src/mx/com/grupogigante/archivo/login.conf";
	private static String gFile2 = "login.conf";

	private static String sClient = new String();
	private static String sServer = new String();
	private static String sLangu = new String();
	private static String sNum = new String();
	private static String sUser = new String();
	private static String sPass = new String();
	private static SapSystem system = new SapSystem();
}
