package Classes;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RodaServidor {

	public static void main(String[] args) throws Exception{     

        //Cria um socket na porta 12345
        ServerSocket socketServidor = new ServerSocket(5000);
        System.out.println("Servidor rodando na porta "+socketServidor.getLocalPort());
        System.out.println("HostAddress = "+ InetAddress.getLocalHost().getHostAddress());
        System.out.println("HostName = "+ InetAddress.getLocalHost().getHostName());

        /* Aguarda alguém se conectar. A execução do servidor
         fica bloqueada na chamada do método accept da classe
         ServerSocket. Quando alguém se conectar ao servidor, o
         método desbloqueia e retorna com um objeto da classe
         Socket, que é uma porta da comunicação. */
        
        System.out.println("Aguardando conexão do cliente...");   

        while (true) {
          Socket cliente = socketServidor.accept();
          // Cria uma thread do servidor para tratar a conexão
          Servidor servidor = new Servidor(cliente);
          Thread t = new Thread(servidor);
          // Inicia a thread para o cliente conectado
          Servidor.cont++;
          t.start();
          
        }
        
    }


}
