/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Italo
 */
public class Menu extends javax.swing.JFrame {

    private DefaultTableModel mdDados;
    private DefaultTableModel mdInvestimento;
    private DefaultTableModel mdPoupanca;
    public static int idConta;
    public static String nome;
    
    public Menu() {
        initComponents();
        this.setLocationRelativeTo(null); //para ficar no centro da tela
        lbConta.setText("Conta: "+Integer.toString(idConta)+"  Titular: "+nome);
        
        //tabela
        this.mdDados = (DefaultTableModel) TabelaExtrato.getModel(); 
        this.mdPoupanca = (DefaultTableModel) TabelaPoupanca.getModel(); 
        this.mdInvestimento = (DefaultTableModel) TabelaInvestimentos.getModel(); 
        
        limpaTabela(mdInvestimento);
        limpaTabela(mdPoupanca);
        preencherTabelaInvestimento(idConta);
      

    }
   
    //Usado antes de preenccher a tabela novamente com dados novos
    public void limpaTabela(DefaultTableModel md){
        if (md.getRowCount() <0){return;}
        
        while(md.getRowCount() > 0){ //Apaga todas as linhas da tabela passada
             md.removeRow(0);   
        }
        
    }
    
    //Preenche a tabela com os dados do extrato (para qualquer transação iremos exibir o extrato)
    public void preencherTabelaExtrato(int idConta){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Para formatar a data na tabela
       
        int i=0;
        Cliente c = new Cliente(); //para poder usar o método do servidor

        while (i < c.consultaExtrato(idConta).size()){

         Object[] dados = {sdf.format(c.consultaExtrato(idConta).get(i).getData()), 
             c.consultaExtrato(idConta).get(i).getSaldoA(),
             c.consultaExtrato(idConta).get(i).getDebito_credito(),
             c.consultaExtrato(idConta).get(i).getValor(),
             c.consultaExtrato(idConta).get(i).getHistorico(),
             c.consultaExtrato(idConta).get(i).getSaldoD(),

         };

         if(c.consultaExtrato(idConta).get(i).getValor() > 0){
                   mdDados.addRow(dados);   
                }
         i++;
        }
            
    }
    
    //preenche tabela investimento
    public void preencherTabelaInvestimento(int idConta){
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //Para formatar a data na tabela
        try {
            DecimalFormat df = new DecimalFormat("0.##"); //Para formatar os valores float   
        int i=0;
        Cliente c = new Cliente(); //para poder usar o método do servidor

        while (i < c.consultaInvestimentos(idConta).size()){
     
            if (c.consultaInvestimentos(idConta).get(i).getTipo() == 1){ //Poupança
                Object[] poup = {Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getValor()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV3()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV6()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV12()).replace(',', '.'))
                };
                    
                if(c.consultaInvestimentos(idConta).get(i).getValor() > 0){
                    mdPoupanca.addRow(poup); 
                }
                    
                
            }
                
                if (c.consultaInvestimentos(idConta).get(i).getTipo() == 2){ //Renda fixa
                Object[] inv = {c.consultaInvestimentos(idConta).get(i).getDtBase(), 
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getValor()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV3()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV6()).replace(',', '.')),
                Double.parseDouble(df.format(c.consultaInvestimentos(idConta).get(i).getV12()).replace(',', '.'))
               
                };
                
                if(c.consultaInvestimentos(idConta).get(i).getValor() > 0){
                   mdInvestimento.addRow(inv);   
                }
                

            }
            i++;
        }
       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro");
        }
            
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        saldo = new javax.swing.JButton();
        extrato = new javax.swing.JButton();
        realizar = new javax.swing.JButton();
        resgatar = new javax.swing.JButton();
        saque = new javax.swing.JButton();
        deposito = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldConta = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldValor = new javax.swing.JTextField();
        confirma = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        lbConta = new javax.swing.JLabel();
        cancela = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaExtrato = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaPoupanca = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaInvestimentos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        transferencia = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SD - Ítalo e José Carlos");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("BANCO SD");

        jSeparator1.setBackground(new java.awt.Color(0, 153, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Consultar");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Investir");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Movimentar");

        saldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/TUTORIAL.jpg"))); // NOI18N
        saldo.setText("Saldo");
        saldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saldoActionPerformed(evt);
            }
        });

        extrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/REPORT.jpg"))); // NOI18N
        extrato.setText("Extrato");
        extrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extratoActionPerformed(evt);
            }
        });

        realizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/GRPHBAR.jpg"))); // NOI18N
        realizar.setText("Realizar");
        realizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarActionPerformed(evt);
            }
        });

        resgatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ARROW2D.jpg"))); // NOI18N
        resgatar.setText("Resgatar");
        resgatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resgatarActionPerformed(evt);
            }
        });

        saque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ARROW1D.jpg"))); // NOI18N
        saque.setText("Saque");
        saque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saqueActionPerformed(evt);
            }
        });

        deposito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ARROW1U.jpg"))); // NOI18N
        deposito.setText("Depósito");
        deposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Dados de tranferencias");

        jLabel6.setText("Conta:");

        jTextFieldConta.setEnabled(false);
        jTextFieldConta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldContaFocusLost(evt);
            }
        });

        jLabel7.setText("Nome:");

        jTextFieldNome.setEnabled(false);

        jLabel8.setText("Valor:");

        jTextFieldValor.setEnabled(false);

        confirma.setText("Confirma");
        confirma.setEnabled(false);
        confirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmaActionPerformed(evt);
            }
        });

        lbConta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        cancela.setText("Cancelar");
        cancela.setEnabled(false);
        cancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelaActionPerformed(evt);
            }
        });

        TabelaExtrato.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Saldo Anterior", "Tipo", "Valor", "Histórico", "Saldo Atual"
            }
        ));
        jScrollPane3.setViewportView(TabelaExtrato);

        TabelaPoupanca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Valor", "3", "6", "12"
            }
        ));
        jScrollPane2.setViewportView(TabelaPoupanca);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cifrao_120x120.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, Short.MAX_VALUE)
        );

        jLabel9.setText("Poupança - Meses");

        jLabel12.setText(" ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TabelaInvestimentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Valor", "3 meses", "6 meses", "12 mses"
            }
        ));
        jScrollPane1.setViewportView(TabelaInvestimentos);

        jLabel11.setText("Renda Fixa");

        transferencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ARROW2R.jpg"))); // NOI18N
        transferencia.setText("Transferência");
        transferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(195, 195, 195)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(292, 292, 292)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11)
                    .addComponent(transferencia))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Extrato de Movimentações");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel2)
                .addGap(289, 289, 289)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(121, 121, 121))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(573, 573, 573)
                .addComponent(jSeparator3))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator4)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jSeparator5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldConta, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancela))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(saldo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(extrato)
                                .addGap(108, 108, 108)
                                .addComponent(realizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resgatar)
                                .addGap(75, 75, 75)
                                .addComponent(saque)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deposito))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 14, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(jLabel13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbConta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lbConta))
                .addGap(11, 11, 11)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(realizar)
                        .addComponent(saque)
                        .addComponent(deposito)
                        .addComponent(resgatar))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saldo)
                        .addComponent(extrato)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jTextFieldValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(confirma)
                        .addComponent(cancela)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelaActionPerformed
        // desabiilita os campos
        jTextFieldConta.setText("");
        jTextFieldValor.setText("");
        jTextFieldNome.setText("");

        jTextFieldConta.setEnabled(false);
        jTextFieldValor.setEnabled(false);
        confirma.setEnabled(false);
        cancela.setEnabled(false);
    }//GEN-LAST:event_cancelaActionPerformed

    private void jTextFieldContaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldContaFocusLost
        // Consulta a conta e pega o nome
        try {
            Cliente c = new Cliente();
            String nome = c.buscaConta(Integer.parseInt(jTextFieldConta.getText()), 2);
            if (nome == null){
                JOptionPane.showMessageDialog(null, "Conta não encontrada!");
                jTextFieldConta.requestFocus();
                return;
            }

            if (jTextFieldConta.getText().equals(Integer.toString(this.idConta))){ //Não pode transferir para si mesmo
                JOptionPane.showMessageDialog(null, "Conta de Destino não pode ser igual a Origem!");
                jTextFieldConta.requestFocus();
                return;
            }



            jTextFieldNome.setText(nome);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Conta de Destino inválida!"); 
          jTextFieldConta.requestFocus();
        }
        
    }//GEN-LAST:event_jTextFieldContaFocusLost

    private void transferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferenciaActionPerformed
        // Habilita campos para transaferência
        jTextFieldConta.setEnabled(true);
        jTextFieldValor.setEnabled(true);
        confirma.setEnabled(true);
        cancela.setEnabled(true);

    }//GEN-LAST:event_transferenciaActionPerformed

    private void extratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extratoActionPerformed
        // Limpa a tela - Tabela
        limpaTabela(this.mdDados);

        //Preenche com os dados
        preencherTabelaExtrato(this.idConta);

    }//GEN-LAST:event_extratoActionPerformed

    private void saldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saldoActionPerformed
        // consulta saldo e exibe janela com o resultado
        Cliente c = new Cliente(); //para poder usar o método do servidor
        double saldo = c.consultaSaldo(this.idConta);
        double investimentos = 0;
        double Disponiveltotal = 0;
        
        //Pega os valores investidos
        int i=0;
        while (i < c.consultaInvestimentos(idConta).size()){
            if (c.consultaInvestimentos(idConta).get(i).getTipo() == 2){ //Se for renda fixa
                 investimentos = investimentos + c.consultaInvestimentos(idConta).get(i).getValor();
            }
            i++;
        }
        
        Disponiveltotal = saldo + investimentos;
        
        //exibe tela
        JOptionPane.showMessageDialog(null, "Banco SD \nConta: "+this.idConta+"\n\n"
            +"Saldo disponível para saque: "+saldo+"\n"
            +"Saldo de Investimentos: "+investimentos+"\n"
            +"Saldo Disponível Total: "+Disponiveltotal );
        
    }//GEN-LAST:event_saldoActionPerformed

    private void realizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarActionPerformed
        // Aplica em renda fixa
        Cliente c = new Cliente(); //para usar os metodos do servidor
        try {
           int t = TabelaInvestimentos.getRowCount();
           
           double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor que deseja investir").replace(',', '.')); 
           c.aplicarRendaFixa(this.idConta, valor);
          
           
          //atualiza extrato
            limpaTabela(mdDados);
            limpaTabela(mdPoupanca);
            limpaTabela(mdInvestimento);
            
            preencherTabelaExtrato(this.idConta);
            preencherTabelaInvestimento(this.idConta);
            
            
            if(TabelaInvestimentos.getRowCount() == t){
                JOptionPane.showMessageDialog(null, "Valor do saldo insuficiente para esse investimento!");
            }
            
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor inválido\n");
            return;
        }   
        
    }//GEN-LAST:event_realizarActionPerformed

    private void resgatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resgatarActionPerformed
        // resgata investimento de renda fixa
        Cliente c = new Cliente(); //para usar os metodos do servidor
        try {
           int t = TabelaInvestimentos.getRowCount();
           double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor que deseja resgatar").replace(',', '.')); 
           c.resgatarRendaFixa(this.idConta, valor);
         
           
           //atualiza tabelas
            limpaTabela(mdDados);
            limpaTabela(mdPoupanca);
            limpaTabela(mdInvestimento);
           
            preencherTabelaExtrato(this.idConta);
            preencherTabelaInvestimento(this.idConta);
            
             if(TabelaInvestimentos.getRowCount() == t){
                JOptionPane.showMessageDialog(null, "Não há investimento para o valor informado!");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor inválido\n");
            return;
        }   
    }//GEN-LAST:event_resgatarActionPerformed

    private void saqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saqueActionPerformed
        // sacar      
        try {
           int t = TabelaExtrato.getRowCount(); 
           Cliente c = new Cliente(); //para acessar os métodos do servidor
           double val = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor que deseja sacar").replace(',', '.'));
        
           c.sacar(this.idConta, val);
          
           
            
            //atualiza
            limpaTabela(mdDados);
            limpaTabela(mdPoupanca);
            limpaTabela(mdInvestimento);
          
            preencherTabelaExtrato(this.idConta);
            preencherTabelaInvestimento(this.idConta);
            
            if(TabelaExtrato.getRowCount() == t){
                JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor inválido\n");
            return;
        }
    }//GEN-LAST:event_saqueActionPerformed

    private void depositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositoActionPerformed
        // depositar
        
        try {
            Cliente c = new Cliente(); //para acessar os métodos do servidor
            double dep = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor que deseja depositar").replace(',', '.'));
        
           c.depositar(this.idConta, dep);
           
            
            //atualiza 
            limpaTabela(mdDados);
            limpaTabela(mdPoupanca);
            limpaTabela(mdInvestimento);
          
            preencherTabelaExtrato(this.idConta);
            preencherTabelaInvestimento(this.idConta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor inválido\n");
            return;
        }
  
    }//GEN-LAST:event_depositoActionPerformed

    private void confirmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmaActionPerformed
        // transferir

        try {
            int t = TabelaExtrato.getRowCount(); 
           Cliente c = new Cliente(); //para acessar os métodos do servidor
           int destino = Integer.parseInt(jTextFieldConta.getText());
           double valor = Double.parseDouble(jTextFieldValor.getText());  
           c.transferir(this.idConta, destino,valor);          
            
            //atualiza 
            limpaTabela(mdDados);
            limpaTabela(mdPoupanca);
            limpaTabela(mdInvestimento);
          
            preencherTabelaExtrato(this.idConta);
            preencherTabelaInvestimento(this.idConta);
            
            // desabiilita os campos
            jTextFieldConta.setText("");
            jTextFieldValor.setText("");
            jTextFieldNome.setText("");

            jTextFieldConta.setEnabled(false);
            jTextFieldValor.setEnabled(false);
            confirma.setEnabled(false);
            cancela.setEnabled(false);
            
            if(TabelaExtrato.getRowCount() == t){
                JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor inválido\n");
            return;
        }
    }//GEN-LAST:event_confirmaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaExtrato;
    private javax.swing.JTable TabelaInvestimentos;
    private javax.swing.JTable TabelaPoupanca;
    private javax.swing.JButton cancela;
    private javax.swing.JButton confirma;
    private javax.swing.JButton deposito;
    private javax.swing.JButton extrato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextFieldConta;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldValor;
    private javax.swing.JLabel lbConta;
    private javax.swing.JButton realizar;
    private javax.swing.JButton resgatar;
    private javax.swing.JButton saldo;
    private javax.swing.JButton saque;
    private javax.swing.JButton transferencia;
    // End of variables declaration//GEN-END:variables
}
