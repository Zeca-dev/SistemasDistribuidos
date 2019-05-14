package servidor;

import cliente.Cliente;
import java.io.Serializable;
import java.security.Provider;
import java.util.*;


/*
 * A conta do cliente mantem os seus dados de cadastro e suas trasnsações e investimentos associados
 */

/**
 *
 * @author jcarl
 */
public class Conta implements Serializable{
    //guarda os dados da conta
    
    private double saldo = 0;
    
    private int idConta; 
    private String CPF;
    private String Nome;
    private Date dtNasc;
    private String end;
    private String Telefone;
    
    private Usuarios usuario = new Usuarios();
    
    //guarda as transações das contas
    private ArrayList<Transacao> transacoes = new ArrayList<>();
    
    //guarda os investimentos das contas
    private ArrayList<Investimento> investimentos = new ArrayList<>();
    
    public Conta (){
        //construtor vazio
    }
    
    public Conta (int id, String cpf, String nome, Date dtnasc, String end, String tel, String usuario, String senha){
        this.idConta = id;
        this.CPF = cpf;
        this.Nome = nome;
        this.dtNasc = dtnasc;
        this.end = end;
        this.Telefone = tel;
        
        //cada conta criada deve ter um usuário vinculado
        Usuarios u = new Usuarios();
        u.setNome(usuario);
        u.setSenha(senha);
        u.setC(this); //atribui a conta criada para o usuario criador
        this.usuario = u;
        
        Cliente c = new Cliente();
        c.adicionaUsuarioLista(u); //adiciona na lista do servidor
       
        
        
        
        //Aplica em Poupança
        this.aplicarPoupança();
        
       
        
        c.adicionaContaLista(this); //adiciona na lista do servidor   
        
    }
    
    
    //Abrir conta
    public static Conta abrirConta ( String cpf, String nome, Date dtnasc, String end, String tel, String usuario, String senha){
        
        //número da próxima conta
        Cliente c = new Cliente();
        int id = c.geraIDConta();
        Conta conta = new Conta (id, cpf,  nome, dtnasc, end, tel, usuario, senha);
        return conta;
    }
       
    //métodos para transações 
    /*
    Transações que alteram o saldo das contas devem verificar se tem investimentos e atualizar os seus valores
    dependendo do caso
    
    */
    
    //consultar saldo
    public double consultarSaldo(int idConta){
        return Servidor.localizaConta(1, Integer.toString(idConta)).getSaldo();
    }
    
    //consultar Extrato
    public ArrayList<Transacao> consultarExtrato(int idConta){
        return Servidor.localizaConta(1, Integer.toString(idConta)).getTransacoes();    
    }
    
    //consultar Extrato
    public ArrayList<Investimento> consultarInvestimento(int idConta){
        return Servidor.localizaConta(1, Integer.toString(idConta)).getInvestimentos();    
    }
    
    //atualiza saldo
    public void atualizaSaldo(int idConta){
        ArrayList<Transacao> extrato = consultarExtrato(idConta); //consulta o extrato
        if (extrato.size() > 0){
            Conta c = Servidor.localizaConta(1, Integer.toString(idConta));
           c.setSaldo( extrato.get(extrato.size() -1 ).getSaldoD());
        }   
    }
    
    //realiza saque
    public void sacar(double valor){
        if(valor <= this.getSaldo()){          
            Transacao t = new Transacao(this.getIdConta(), 'D', 1, valor, "Saque", this.saldo);
            this.getTransacoes().add(t);
            this.atualizaSaldo(this.idConta); 
            
            //Atualizar os valores dos investimentos de poupança 
            this.aplicarPoupança();
        }
        
    }
    
    //realizar depósito
    public void depositar(double valor){        
        Transacao t = new Transacao(this.getIdConta(), 'C', 2, valor, "Depósito", this.saldo);
        this.getTransacoes().add(t);
        this.atualizaSaldo(this.idConta);    
        
        //Atualizar os valores dos investimentos de poupança
        this.aplicarPoupança();     
    }
    
    //realizar transferência
    public void transferir(Conta origem, Conta destino, double valor){
        if(valor <= origem.getSaldo()){
            Transacao orig = new Transacao(origem.getIdConta(), 'D', 3, valor, "Transferencia para a conta "+destino.getIdConta(), origem.getSaldo());
            origem.getTransacoes().add(orig);

            Transacao dest = new Transacao(destino.getIdConta(), 'C', 3, valor, "Transferência recebida da conta "+origem.getIdConta(), destino.getSaldo());
            destino.getTransacoes().add(dest);

            //atualiza os saldos das contas
            origem.atualizaSaldo(origem.getIdConta());
            destino.atualizaSaldo(destino.getIdConta());
            
            //Atualizar os valores dos investimentos de poupança
            origem.aplicarPoupança(); destino.aplicarPoupança();
        
        }
        
    }
    
    //Aplicação em poupança
    public void aplicarPoupança(){
        Investimento.aplicarValor(this, 1, this.getSaldo());
    }
    
    //Resgate em poupança
    public void resgatarPoupanca(){
        Investimento.resgatarInvestimento(this, 1, this.getSaldo());
    }
    
    //Aplicar em fundo de Renda Fixa
    public void aplicarRendaFixa(double valor){
        Investimento.aplicarValor(this, 2, valor);
    }
    
    //Resgar fundo de Renda Fixa
    public void resgatarRendaFixa(double valor){
        Investimento.resgatarInvestimento(this, 2, valor);
    }
    //fim métodos para transações

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String Telefone) {
        this.Telefone = Telefone;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(ArrayList<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public ArrayList<Investimento> getInvestimentos() {
        return investimentos;
    }

    public void setInvestimentos(ArrayList<Investimento> investimentos) {
        this.investimentos = investimentos;
    }

    

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
    
}
