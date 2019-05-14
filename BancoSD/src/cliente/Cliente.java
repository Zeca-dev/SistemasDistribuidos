package cliente;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import servidor.Conta;
import servidor.Investimento;

import servidor.Metodos;
import servidor.Servidor;
import servidor.Transacao;
import servidor.Usuarios;

public class Cliente  {
    public int idConta;
    public String host = "localhost"; //para trocar o host com mais facilidade no cliente
    
    //Para uso remoto
    private Registry registry;
    private Metodos stub;
    
    
    //Construtor
    public Cliente(){
        try {
             this.registry = LocateRegistry.getRegistry(this.host, 20001); 
             this.stub = (Metodos) registry.lookup("Banco");
        } catch (Exception e) {
        }
       

    }

    //Logar
    public  boolean logar(String usuario, String senha) {
        try {
           
            Conta c = new Conta();
            c = stub.login(usuario, senha);
            if (c != null ){
                int conta = stub.login(usuario, senha).getIdConta();
                if (conta > 0){
                    setIdConta(conta);
                    return true;
                }else{
                     return false;
                }             
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
         return false;  
        }
    
    //Adiciona conta na lista
    public void adicionaContaLista(Conta c){
        try {  
            stub.adicionaContaLista(c);
        } catch (Exception e) {
            
        }
    }
    
    //Adiciona usuario na lista
     public void adicionaUsuarioLista(Usuarios u){
        try {
              stub.adicionaUsuarioLista(u);
        } catch (Exception e) {
            
        }
    }
    
     //gera o id da proxima cotna
     public int geraIDConta()
     {
         try {
            return stub.geraIDConta();
            
        } catch (Exception e) {
          return 0;   
        }
     }

     
    //Abrir conta
    public Conta abrirConta ( String cpf, String nome, Date dtnasc, String end, String tel, String usuario, String senha){
        try {
             return stub.abrirConta(cpf, nome, dtnasc, end, tel, usuario, senha);
            
        } catch (Exception e) {
            return null;
        }
        
    }
    //método para consultar o saldo da conta
    public double consultaSaldo(int idConta){
       try {
            return stub.consultarSaldo(idConta);
            
        } catch (Exception e) {
            return 0.0;
        }
         
    }
    
    //Método para consultar Extrato
    
    //busca uma conta - Retorn numero ou nome
    public  String buscaConta(int idConta, int retorno){
       try {
          
            switch (retorno){
                case 1:return Integer.toString(stub.buscaConta(idConta).getIdConta());
                case 2: return stub.buscaConta(idConta).getNome();
                default: return null;
                }
        
            
        } catch (Exception e) {
            return null;
        }
         
    }
    
    public void sacar(int idConta, double valor){
        
        try {
           stub.sacar(idConta, valor);
            
        } catch (Exception e) {         
            
        }
        
    }
    
    public void depositar(int idConta, double valor){

        try {
            
            stub.depositar(idConta, valor);
            
        } catch (Exception e) {         
            
        }
        
    }
    
    public void transferir( int origem, int destino, double valor){       
        
         try {
            stub.transferir(origem,destino ,valor);
            
        } catch (Exception e) {         
            
        }
        
    }
    
    //consulta extrato
    public ArrayList<Transacao> consultaExtrato(int idConta){
       try {
             return stub.consultarExtrato(idConta);
            
        } catch (Exception e) {
            return null;
        }
         
    }

    //consulta investimentos
    public ArrayList<Investimento> consultaInvestimentos(int idConta){
       try {
             return stub.buscaConta(idConta).getInvestimentos();
            
        } catch (Exception e) {
            return null;
        }
         
    }
    
   
    
    //método para realizar investimento
    public void aplicarPoupanca(int idConta){
         try {
             stub.aplicarPoupança(idConta);
            
        } catch (Exception e) {         
            
        }
     
    }
    
    //método para realizar investimento
    public void aplicarRendaFixa(int idConta, double valor){
         try {
            stub.aplicarRendaFixa(idConta, valor);
            
        } catch (Exception e) {         
            
        }
     
    }
     //método para realizar investimento
    public void resgatarPoupanca(int idCont){
         try {
             stub.resgatarPoupanca(idConta);
            
        } catch (Exception e) {         
            
        }
     
    }
    
    //método para resgatar investimento
    public void resgatarRendaFixa(int idConta, double valor){
         try {
             stub.resgatarRendaFixa(idConta, valor);
            
        } catch (Exception e) {         
            
        }
     
    }
    
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }


}
