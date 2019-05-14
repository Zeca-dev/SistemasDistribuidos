/*
 guarda as transações das contas (saques, depósitos e transações)
 */
package servidor;

import java.io.Serializable;
import java.text.*;
import java.util.*;

/**
 *
 * @author jcarl
 */
public class Transacao implements Serializable{
    //dados da transacao
    private int idConta;
    private char debito_credito; //D - débito   C - Crédito
    private int tipo; //1 - Saque   2 - Depósito 3 - Transferência
    private Date data;
    private double valor;
    private String historico;
    private double saldoA; //saldo anterior à transação
    private double saldoD; //saldo após a transação
    
    public Transacao(){
        //construtor vazio
    }
    
    public Transacao(int idconta, char dc, int tipo, double valor, String hist, double saldo){
        DecimalFormat df = new DecimalFormat("0.##"); //Para formatar os valores float      
        
        this.idConta = idconta;
        this.debito_credito = dc;
        this.tipo = tipo;
       
        this.data = new Date();
       
        this.valor = Double.parseDouble(df.format(valor).replace(',', '.'));
        //this.valor = valor;
        
        this.historico = hist;
        this.saldoA = Double.parseDouble(df.format(saldo).replace(',', '.'));
        
        if (dc == 'D' || dc == 'd'){
            this.saldoD = Double.parseDouble(df.format(saldo - valor).replace(',', '.'));
        }else if (dc == 'C' || dc == 'c'){
           this.saldoD = Double.parseDouble(df.format(saldo + valor).replace(',', '.')); 
        }
        
    }

    
    public char getDebito_credito() {
        return debito_credito;
    }

    public void setDebito_credito(char debito_credito) {
        this.debito_credito = debito_credito;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public double getSaldoA() {
        return saldoA;
    }

    public void setSaldoA(double saldoA) {
        this.saldoA = saldoA;
    }

    public double getSaldoD() {
        return saldoD;
    }

    public void setSaldoD(double saldoD) {
        this.saldoD = saldoD;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
   
    
    
}
