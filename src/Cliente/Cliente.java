package Cliente;
import java.io.*;
import java.net.*;
import java.util.Random;

//---------------------------------------------------------------
//El proceso Cliente solicita un archivo al Servidor
//Se pide el nombre con el que se guardará el archivo
//Se solicita el nombre del archivo a transferir
//Muestra el contenido del archivo por pantalla
//Guarda el archivo en la carpeta del proyecto con el nombre indicado
//Comunicación UDP
//---------------------------------------------------------------


class Cliente 
{
	static final String HOST = "localhost";
	static final int PuertoCliente=10000;
	static final int PuertoServidor=10001;
	boolean acertado=false;
	
	public Cliente() 
	{
		
	}
	public static void main(String[] arg) 
	{
		if(true)
		{
			try
			{
				String fichero="";
				String ficheroGuardar="";
				String linea="";
				
				
				DatagramSocket skCliente = new DatagramSocket( PuertoCliente );
				InetAddress maquina = InetAddress.getByName("localhost");
				
				byte cadena[]=new byte [1000];
				DatagramPacket mensajeRecibir = new DatagramPacket(cadena, cadena.length);
				DatagramPacket mensajeEnviar;

				BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
				
				System.out.print("Escriba el nombre del fichero que quiere guardar: ");
				ficheroGuardar=reader.readLine();
				
				FileOutputStream fstream = new FileOutputStream(ficheroGuardar);
				DataOutputStream in = new DataOutputStream(fstream);
				
				System.out.print("Escriba el nombre del fichero que quiere recibir: ");
				fichero=reader.readLine();
				
				mensajeEnviar = new DatagramPacket(fichero.getBytes(),fichero.length(), maquina, PuertoServidor);
				skCliente.send(mensajeEnviar);
				
				skCliente.receive(mensajeRecibir);
				linea=new String(mensajeRecibir.getData(),0,mensajeRecibir.getLength());
				
				while(!linea.equals("EOF"))
				{
					in.writeUTF(linea);
					System.out.println(linea);
					skCliente.receive(mensajeRecibir);
					linea=new String(mensajeRecibir.getData(),0,mensajeRecibir.getLength());
				}
				
				skCliente.close();
				System.out.println("Conexion terminada!");
			} 
			catch( Exception e ) 
			{
				System.out.println(e.getMessage());
			}
		}
	}
}