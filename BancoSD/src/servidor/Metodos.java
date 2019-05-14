package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

public interface Metodos extends Remote {
    
    //Métodos do servidor
   
    
    //usuário
    Conta login(String usuario, String senha) throws RemoteException;
    
    //métodos para acessar as lista de contas e usuarios do servidor e adicionar os usuarios e contas criados remotamente
    void adicionaUsuarioLista(Usuarios u)throws RemoteException;
    void adicionaContaLista(Conta c)throws RemoteException;
    int geraIDConta() throws RemoteException;
    
    //Conta
    Conta abrirConta ( String cpf, String nome, Date dtnasc, String end, String tel, String usuario, String senha) throws RemoteException;
    Conta buscaConta(int idConta)throws RemoteException;
    double consultarSaldo(int idConta) throws RemoteException;
    ArrayList<Transacao> consultarExtrato(int idConta) throws RemoteException;
    ArrayList<Investimento> consultarInvestimento(int idConta) throws RemoteException;
    void atualizaSaldo(int idConta) throws RemoteException;
    void sacar(int idConta, double valor) throws RemoteException;
    void depositar(int idConta, double valor) throws RemoteException;
    void transferir(int origem, int destino, double valor) throws RemoteException;
    void aplicarPoupança(int idConta) throws RemoteException;
    void resgatarPoupanca(int idConta) throws RemoteException;
    void aplicarRendaFixa(int idConta, double valor) throws RemoteException;
    void resgatarRendaFixa(int idConta, double valor) throws RemoteException;
    
}
