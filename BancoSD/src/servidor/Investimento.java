/*
 guarda os investimentos das contas
 */
package servidor;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javafx.util.converter.LocalDateTimeStringConverter;



/**
 *
 * @author jcarl
 */
public class Investimento implements Serializable{     
       
    private LocalDate dtBase; //Data base do investimento

    private int tipo; //1 - Poupança  2 - Renda Fixa
    private double txP = (0.5 / 100); //Taxa mensal de rendimento da poupança
    private double txRF = (1.5 /100); //Taxa mensal de rendimento da Renda Fixa
    private double valor; //valor do investimento
    private double V3 = 0; //Valor para 3 meses
    private double V6 = 0; //Valor para 6 meses
    private double V12 = 0; //Valor para 12 meses
    
    private Conta c; //Conta vinculada ao investimento
    
    
    public Investimento(){
        //construtor vazio
    }
    
    public Investimento(int tipo, Conta c){
        this.dtBase = LocalDate.now();
        this.tipo = tipo;
        this.c = c;   
    }
    
    //Aplicar (ao aplicar o valor será dimminuído do saldo da conta quando se tratar de Renda Fixa)
    public static void aplicarValor(Conta c, int tipo, double valor){
        DecimalFormat df = new DecimalFormat("0.##"); //Para formatar os valores float
        //Antes de aplicar corrige o saldo de poupança e exclui o investimento anterior
        c.resgatarPoupanca();
     
                
        Investimento inv = new Investimento(tipo, c);
        switch (tipo){ //1 - Poupança   2 - Renda Fixa
            case 1: {
                
                inv.setTipo(tipo);
                inv.setC(c);
                inv.setValor(valor); //Investe o saldo total da conta
                
                //Calcula as simulações para 3,6 e 12 meses
                inv.setV3( valor + (valor * inv.getTxP()) * 3 );
                inv.setV6( valor + (valor * inv.getTxP()) * 6);
                inv.setV12( valor + (valor * inv.getTxP()) * 12);
                
                break;
            }
            
            case 2: {
                if(valor <= c.getSaldo()){
                    inv.setTipo(tipo);
                    inv.setC(c);
                
                    inv.setValor(valor);
                    
                    //Cada investimento retira o valor da conta (bloqueia)
                    Transacao t = new Transacao(c.getIdConta(), 'D', 2, inv.getValor(), "Aplicação em Fundos de Renda Fixa", c.getSaldo());
                    c.getTransacoes().add(t);
                    c.atualizaSaldo(c.getIdConta());

                    //Calcula as simulações para 3,6 e 12 meses
                    inv.setV3( inv.getValor() + (inv.getValor() * inv.getTxRF()) * 3 );
                    inv.setV6( inv.getValor() + (inv.getValor() * inv.getTxRF()) * 6);
                    inv.setV12( inv.getValor() + (inv.getValor() * inv.getTxRF()) * 12);

                    //Se tiver poupança, atualiza os saldos investiidos
                    for(Investimento i: c.getInvestimentos()){
                        if(i.getTipo() == 1){
                            i.setValor(c.getSaldo());
                            i.setV3(c.getSaldo() + (c.getSaldo()* inv.getTxP()) * 3);
                            i.setV6(c.getSaldo() + (c.getSaldo() * inv.getTxP()) * 6);
                            i.setV12(c.getSaldo() + (c.getSaldo() * inv.getTxP()) * 12);
                        }
                    }
                
                }
                break;
            }
        }
        if (inv.getTipo()==1 && inv.getValor() == 0){
             c.getInvestimentos().add(inv);
        }else if (inv.getTipo() == 2){
            c.getInvestimentos().add(inv);
        }
            
        
        
    }
    
    //Resgatar investimento 
    //Se for Poupança calcula os juros e atualiza o saldo da conta
    //Se for Renda Fixa calcula os juros e incorpora ao saldo da conta
    public static void resgatarInvestimento(Conta c, int tipo, double valor){
                 
        switch (tipo){ //1 - Poupança   2 - Renda Fixa
            case 1: {
                for (Investimento inv : c.getInvestimentos()){
                    if(inv.getTipo() == 1){
                        //LocalDate dt = LocalDate.now();
                        LocalDate dt = LocalDate.parse("2018-10-05");
                        LocalDate dtbase = inv.getDtBase();
                        long prazo = ChronoUnit.DAYS.between(inv.getDtBase(), dt); //Calcula dias entre as dastas
                                
                        //Atualiza saldos de Poupança
                        Transacao t = new Transacao(c.getIdConta(), 'C', 2, (c.getSaldo() * inv.getTxP())/30 * prazo, "Correção de Poupança", c.getSaldo() );
                        c.getTransacoes().add(t);
                        c.atualizaSaldo(c.getIdConta());

                        //inv.setDtBase(dt); //atualiza a data base para a data atual
                        
                        inv.setValor(c.getSaldo()); //Investe o novo saldo total da conta

                        //Calcula as simulações para 3,6 e 12 meses
                        inv.setV3( valor + (valor * inv.getTxP()) * 3 );
                        inv.setV6( valor + (valor * inv.getTxP()) * 6);
                        inv.setV12( valor + (valor * inv.getTxP()) * 12);  
                    }
                }
                
                
                break;
            }
            
            case 2: {
               for (Investimento inv : c.getInvestimentos()){
                    if( (inv.getTipo() == 2) && (inv.getValor() == valor)){
                       //LocalDate dt = LocalDate.now();
                        LocalDate dt = LocalDate.parse("2018-10-05");
                        LocalDate dtbase = inv.getDtBase();
                        long prazo = ChronoUnit.DAYS.between(inv.getDtBase(), dt); //Calcula dias entre as dastas

                        inv.setValor(inv.getValor() + (inv.getValor() * inv.getTxRF() /30 * prazo)); //atualiza o valor da Renda Fixa
                        
                        //Atualiza saldos de Poupança
                        Transacao t = new Transacao(c.getIdConta(), 'C', 2, inv.getValor(), "Resgate Corrigido - Renda Fixa ", c.getSaldo() );
                        c.getTransacoes().add(t);
                        c.atualizaSaldo(c.getIdConta());
                        
                        //Exclui o investimento da lista de investimentos
                        c.getInvestimentos().remove(inv);                       

                        //Se tiver poupança, atualiza os saldos investiidos
                        for(Investimento i: c.getInvestimentos()){
                            if(i.getTipo() == 1){
                                
                                i.setValor(c.getSaldo());
                                i.setV3(c.getSaldo() + (c.getSaldo()* inv.getTxP()) * 3);
                                i.setV6(c.getSaldo() + (c.getSaldo() * inv.getTxP()) * 6);
                                i.setV12(c.getSaldo() + (c.getSaldo() * inv.getTxP()) * 12);
                    
                            }
                        }
                        
                    }
               }
                
                
                break;
            }
        }
        
    }

    public LocalDate getDtBase() {
        return dtBase;
    }

    public void setDtBase(LocalDate dtBase) {
        this.dtBase = dtBase;
    }

    

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getTxP() {
        return txP;
    }

    public void setTxP(double txP) {
        this.txP = txP;
    }

    public double getTxRF() {
        return txRF;
    }

    public void setTxRF(double txRF) {
        this.txRF = txRF;
    }

    public double getV3() {
        return V3;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
  
    public void setV3(double V3) {
        this.V3 = V3;
    }

    public double getV6() {
        return V6;
    }

    public void setV6(double V6) {
        this.V6 = V6;
    }

    public double getV12() {
        return V12;
    }

    public void setV12(double V12) {
        this.V12 = V12;
    }

    

    public Conta getC() {
        return c;
    }

    public void setC(Conta c) {
        this.c = c;
    }
    
    
}
