package Servidor;

import java.io.* ;


//---------------------------------------------------------------
//Clase servidor que atiende a un Cliente para enviarle un archivo solicitado
//Recibe el nombre del archivo
//Envía el archivo solicitado al cliente
//Atiende a un unico cliente
//El archivo debe existir
//Comunicación UDP
//---------------------------------------------------------------

import java.net.* ;
import java.util.Random;
class Servidor 
{
	static final int PuertoCliente=10000;
	static final int PuertoServidor=10001;
	boolean acertado=false;
	
	public Servidor() 
	{
		
	}
	public static void main( String[] arg ) 
	{
		if(true)
		{
			try 
			{
				String resultado="";
	
				DatagramSocket skServidor = new DatagramSocket(PuertoServidor);
				
				String fichero="";
				String linea="";
				
				InetAddress maquina = InetAddress.getByName("localhost");
				
				byte cadena[]=new byte [1000];
				DatagramPacket mensajeRecibir = new DatagramPacket(cadena, cadena.length);
				DatagramPacket mensajeEnviar;
				
				skServidor.receive(mensajeRecibir);
				fichero=new String(mensajeRecibir.getData(),0,mensajeRecibir.getLength());
				System.out.println("El cliente quiere el fichero: "+fichero);
	
				FileInputStream fstream = new FileInputStream(fichero);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				linea=br.readLine();
				
				while (linea != null)
				{
					mensajeEnviar = new DatagramPacket(linea.getBytes(),linea.length(), maquina, PuertoCliente);
					skServidor.send(mensajeEnviar);
					linea=br.readLine();
				}
				in.close();
				
				mensajeEnviar = new DatagramPacket("EOF".getBytes(),"EOF".length(), maquina, PuertoCliente);
				skServidor.send(mensajeEnviar);
					
				skServidor.close();
				System.out.println("Conexion finalizada!");
			} 
			catch( Exception e ) 
			{
				System.out.println( e.getMessage() );
			}
		}
	}
}