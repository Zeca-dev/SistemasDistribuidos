package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.*;
import java.util.ArrayList;
import java.util.*;


public class Servidor implements Metodos{
   
    public static ArrayList<Usuarios> usuarios = new ArrayList<>();  //lista com os nomes de usuários e senhas para simular o login
    
    public static ArrayList<Conta> contas = new ArrayList<>(); //lista de contas

    //Pesquisa a conta por tipo de consulta - tipos (1 - idConta 2 - CPF, 3 - Nome do Titular e 4 - nome de usuario
    public static Conta localizaConta(int tipo, String dado){
     Conta c = null;
     
     switch (tipo){
        case 1:{
            for(Conta i: contas){
                if  (Integer.parseInt(dado) == i.getIdConta()){
                    c = i;     
                }
            }
            break;
        }
        case 2:{
            for(Conta i: contas){
                if  (i.getCPF().equals(dado)){
                    c = i;     
                }
            }
            break;
        }
        case 3: {
            for(Conta i: contas){
                if  (i.getNome().equals(dado)){
                    c = i;     
                }
            }
            break;
        }
        case 4:{
            for(Conta i: contas){
                if  (i.getUsuario().getNome().equals(dado)){
                    c = i;     
                }
            }
            break;
        }
        default: {break;}

     }
     
     return c;   
    }
	
    public static void main(String[] args) {

            try {
                    //criar objeto servidor
                    Servidor server = new Servidor();
               
                    Metodos stub = (Metodos) UnicastRemoteObject.exportObject(server, 0);
                    
                  

                    LocateRegistry.createRegistry(20001);  

                    Registry registry = LocateRegistry.getRegistry(20001);
                    

                    /* O método bind é então chamado no stub do registro para vincular 
                     * o stub do objeto remoto ao nome "Hello" no registro.*/
                    
                    registry.rebind("Banco", stub);         

                    System.out.println("Servidor pronto:");

            } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
            }
    }

    //Métodos Remotos
    @Override
    public Conta login(String usuario, String senha) throws RemoteException{
        Usuarios u = new Usuarios();
        return u.logar(usuario, senha);
    }
    @Override
    public void adicionaUsuarioLista(Usuarios u)throws RemoteException{
        Servidor.usuarios.add(u);
    }
    
    @Override
    public void adicionaContaLista(Conta c)throws RemoteException{
       Servidor.contas.add(c);
    }
    
    @Override
    public int geraIDConta() throws RemoteException{
         return Servidor.contas.size()+1; 
        
    }
    
    @Override
    public Conta abrirConta ( String cpf, String nome, Date dtnasc, String end, String tel, String usuario, String senha) throws RemoteException {    
       return Conta.abrirConta( cpf,  nome, dtnasc, end, tel, usuario, senha);    
    }
    @Override
    public  Conta buscaConta(int idConta)throws RemoteException{
        Conta c = new Conta();
        return localizaConta(1, Integer.toString(idConta));
    }
    
    @Override
    public double consultarSaldo(int idConta) throws RemoteException{
        Conta c = new Conta();
        return c.consultarSaldo(idConta);
    }
    
    @Override
    public ArrayList<Transacao> consultarExtrato(int idConta)throws RemoteException{
        Conta c = new Conta();
        return c.consultarExtrato(idConta);
    }
    
    @Override
    public ArrayList<Investimento> consultarInvestimento(int idConta)throws RemoteException{
        Conta c = new Conta();
        return c.consultarInvestimento(idConta);
    }

    @Override
    public void atualizaSaldo(int idConta) throws RemoteException{
        Conta c = new Conta();
        c.atualizaSaldo(idConta);
           
    }
    
    @Override
    public void sacar(int idConta, double valor) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.sacar(valor);
    }
    
    @Override
    public void depositar(int idConta, double valor) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.depositar(valor);
    }
    
    @Override
    public void transferir(int origem, int destino, double valor) throws RemoteException{
        Conta c1 = new Conta();
        Conta c2 = new Conta();
        c1 = localizaConta(1, Integer.toString(origem));
        c2 = localizaConta(1, Integer.toString(destino));
        c1.transferir(c1, c2, valor);
    }
    
    @Override
    public void aplicarPoupança(int idConta) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.aplicarPoupança();
    }
    
    @Override
    public void resgatarPoupanca(int idConta) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.resgatarPoupanca();
    }
    
    @Override
    public void aplicarRendaFixa(int idConta, double valor) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.aplicarRendaFixa(valor);
    }
    @Override
    public void resgatarRendaFixa(int idConta, double valor) throws RemoteException{
        Conta c = new Conta();
        c = localizaConta(1, Integer.toString(idConta));
        c.resgatarRendaFixa(valor);
    }
    
    
}
