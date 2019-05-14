/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import DAO.ProdutoDao;
import com.sun.corba.se.impl.naming.cosnaming.InterOperableNamingImpl;
import java.awt.Event;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//Para uso dos sockets
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JTable;



/**
 *
 * @author jcarl
 */
public class FormLoja extends javax.swing.JFrame {

    /**
     * Creates new form FormLoja
     */
  
    private int modo = 1; //1 - Gravvação e 2 - Edição

    private String ipGroup = "225.7.8.9";
    private int portaGroup = 55555;
    
    private double totalCarrinho = 0;
    private int qtCarrinho = 0;
    
    private double totalProdutos = 0;
    private int qtEstoque = 0;
    
    private String idCompra;
    
    private DefaultTableModel mdProduto;
    private DefaultTableModel mdCarrinnho;
    
    //Socket
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;
    private MulticastSocket ms;
    private byte recvData[];
    private DatagramPacket recvPack;
    
    private String mensagemRecebida;
    
    
    public FormLoja() {
        initComponents();
         setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //conectar("localhost", 5000); //Conecta passando ip e porta
        this.mdProduto = (DefaultTableModel) jTableProdutos.getModel();  
        this.mdCarrinnho = (DefaultTableModel) jTableCarrinho.getModel();
        
         // criando uma DatagramPacket para recebimento
	this.recvData = new byte[1024];
        this.recvPack = new DatagramPacket(this.recvData, this.recvData.length);
        
        //Verifica atualiações de produtos continuamente 
        int delay = 5000;   // delay de 5 seg.
        int interval = 1000;  // intervalo de 1 seg.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    buscaAtualizacao();
                }
            }, delay, interval);
        
               
    }
    
    public void buscaAtualizacao(){
            
        try {
            
         
         this.ms.receive(this.recvPack);

         if (new String(this.recvPack.getData()).length() > 0){
             System.out.println("Mensagem Multicast recebida: "+new String(this.recvPack.getData()));
             
             if (jTableProdutos.getRowCount() > 0){
                JOptionPane.showMessageDialog(null,"Temos atualizações de produtos. Sua lista será atualizada");
                     atualizaProdutos();

                
             }
         }
        
        // this.ms.close();
         
        } catch (Exception e) {

        }
      
    }
    
    public void limpaTabela(int tam, int tab){
        if (tam <0){return;}
        this.qtEstoque = 0; //zera valores exibidos na tela
        this.qtCarrinho = 0;
        this.totalProdutos = 0;
        this.totalCarrinho = 0; 
        for (int i=0 ; i < tam; i++){ //Apaga todas as linhas da tabela passada
            if (tab == 1){
                this.mdProduto.removeRow(0);
            }
            if (tab == 2){
                this.mdCarrinnho.removeRow(0);
            }
        }
        
    }
    
    public void atualizaProdutos(){
        //Mostra produtos disponíveis
      //   mdProduto = (DefaultTableModel) jTableProdutos.getModel();
         
         //Limpa o jtable para inserir a lista atualizada       
         limpaTabela(this.mdProduto.getRowCount(), 1);
         
         
         Produto p = new Produto(); //Cria produto
         enviaMensagem("Atualiza");
         receberMensagem();
         //System.out.println("Mensgem recebida "+this.mensagemRecebida);
         int tamLista = Integer.parseInt(this.mensagemRecebida);
         
         for(int i=0; i< tamLista; i++){
            enviaMensagem(""); receberMensagem(); p.setCod(Integer.parseInt(this.mensagemRecebida)); //Código
            enviaMensagem(""); receberMensagem(); p.setTipo(this.mensagemRecebida); //Tipo
            enviaMensagem(""); receberMensagem(); p.setNome(this.mensagemRecebida); //Nome
            enviaMensagem(""); receberMensagem(); p.setPreco(Double.parseDouble(this.mensagemRecebida)); //Preço
            enviaMensagem(""); receberMensagem(); p.setEstoque(Integer.parseInt(this.mensagemRecebida)); //Estoque
            enviaMensagem(""); //Finaliza a troca de mensagens
         
              //Atualiza totais na tela
            Object[] dados = {p.getCod(), p.getTipo(), p.getNome(), Double.toString(p.getPreco()), Integer.toString(p.getEstoque())};
                    
            mdProduto.addRow(dados);
               
            this.qtEstoque = this.qtEstoque + p.getEstoque();
            jLabelTotal.setText(Integer.toString(this.qtEstoque));

            this.totalProdutos = this.totalProdutos + (p.getPreco() * p.getEstoque());
            jLabeltotalProd.setText(Double.toString(this.totalProdutos)); 
            jLabelLinhas.setText(Integer.toString(jTableProdutos.getRowCount()));
         }
    }
    
    public void preencheTabela(int tamLista){
        Produto p = new Produto(); //Cria produto
                       
         for(int i=0; i< tamLista; i++){
            enviaMensagem(""); receberMensagem(); p.setCod(Integer.parseInt(this.mensagemRecebida)); //Código
            enviaMensagem(""); receberMensagem(); p.setTipo(this.mensagemRecebida); //Tipo
            enviaMensagem(""); receberMensagem(); p.setNome(this.mensagemRecebida); //Nome
            enviaMensagem(""); receberMensagem(); p.setPreco(Double.parseDouble(this.mensagemRecebida)); //Preço
            enviaMensagem(""); receberMensagem(); p.setEstoque(Integer.parseInt(this.mensagemRecebida)); //Estoque
            enviaMensagem(""); //Finaliza a troca de mensagens
         
              //Atualiza totais na tela
            Object[] dados = {p.getCod(), p.getTipo(), p.getNome(), Double.toString(p.getPreco()), Integer.toString(p.getEstoque())};
                    
            mdProduto.addRow(dados);
               
            this.qtEstoque = this.qtEstoque + p.getEstoque();
            jLabelTotal.setText(Integer.toString(this.qtEstoque));

            this.totalProdutos = this.totalProdutos + (p.getPreco() * p.getEstoque());
            
            jLabeltotalProd.setText(Double.toString(this.totalProdutos)); 
            jLabelLinhas.setText(Integer.toString(jTableProdutos.getRowCount()));
         }
    }
    
  
    public boolean conectar(String ip, int porta){
        try {
                      
            this.cliente = new Socket(ip, porta);
            // Cria canal para receber dados 
            this.in = new DataInputStream(this.cliente.getInputStream()); 

		// Cria canal para enviar dados 
	   this.out = new DataOutputStream(this.cliente.getOutputStream()); 
           
           //Parte usada para o Multicast
           this.ms = new MulticastSocket(this.portaGroup); 
           // se juntando ao grupo 
            this.ms.joinGroup(InetAddress.getByName(this.ipGroup)); //Se junta ao grupo do servidor    
                    
            JOptionPane.showMessageDialog(rootPane, "Conexão estabelecida com sucesso.");

           return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Falha ao conectar ao servidor: "+jTextFieldIpServidor.getText());
            return false;
        }
    }
    
    public void desconectar(){
        try {
            
            //Fecha os canais de entrada e saída. 
            this.in.close(); 

            this.out.close(); 
            
            //Fecha socket
            this.cliente.close();
            
            //Para o Multicast
          
            this.ms.leaveGroup(InetAddress.getByName(this.ipGroup));
            this.ms.close();

            
        } catch (Exception e) {
        }
    }
    
    public void enviaMensagem(String mensagem){
        try {   
              out.writeUTF(mensagem ); //Envia string
           
        } catch (Exception e) {
        }
    }
    
    public void receberMensagem(){
        try {
             this.mensagemRecebida = in.readUTF();
    
            } catch (Exception e) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProdutos = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCarrinho = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextFieldPesquisa = new javax.swing.JTextField();
        jButtonPesquisar = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButtonGravar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jTextFieldNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jTextFieldPreco = new javax.swing.JTextField();
        jTextFieldEstoque = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButtonConectar = new javax.swing.JButton();
        jTextFieldPorta = new javax.swing.JTextField();
        jButtonComprar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldIpServidor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelTotalCarrinho = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelQuant = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabeltotalProd = new javax.swing.JLabel();
        jLabelLinhas = new javax.swing.JLabel();
        jButtonAdicionarItem = new javax.swing.JButton();
        jButtonremoverItem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistemas Distribuídos - Loja  - Alunos: Ítalo Resende e José Carlos");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTableProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod", "Tipo", "Nome", "Preço", "Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProdutos.setEnabled(false);
        jTableProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableProdutosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableProdutos);

        jTableCarrinho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Tipo", "Nome", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCarrinho.setEnabled(false);
        jScrollPane4.setViewportView(jTableCarrinho);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Código");
        jRadioButton1.setEnabled(false);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Nome");
        jRadioButton2.setEnabled(false);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jTextFieldPesquisa.setEnabled(false);
        jTextFieldPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPesquisaActionPerformed(evt);
            }
        });

        jButtonPesquisar.setText("Pesquisar");
        jButtonPesquisar.setEnabled(false);
        jButtonPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPesquisarActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Funcionário?");
        jCheckBox1.setEnabled(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButtonGravar.setText("Gravar");
        jButtonGravar.setEnabled(false);
        jButtonGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGravarActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jTextFieldNome.setEnabled(false);

        jLabel4.setText("Tipo");

        jLabel5.setText("Nome");

        jLabel6.setText("Preço");

        jLabel7.setText("Estoque");

        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alimentos", "Móveis", "Telefonia" }));
        jComboBoxTipo.setEnabled(false);
        jComboBoxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoActionPerformed(evt);
            }
        });

        jTextFieldPreco.setEnabled(false);

        jTextFieldEstoque.setEnabled(false);

        jLabel11.setText("Porta");

        jButtonConectar.setText("Conectar");
        jButtonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConectarActionPerformed(evt);
            }
        });

        jTextFieldPorta.setText("5000");

        jButtonComprar.setText("Finalizar Compra");
        jButtonComprar.setEnabled(false);
        jButtonComprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComprarActionPerformed(evt);
            }
        });

        jLabel10.setText("IP Servidor");

        jTextFieldIpServidor.setText("localhost");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel5)
                                .addGap(332, 332, 332)
                                .addComponent(jLabel6)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(199, 199, 199)
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2))))
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jButtonGravar)
                        .addGap(168, 168, 168)
                        .addComponent(jButtonExcluir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 385, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonConectar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonComprar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldIpServidor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonPesquisar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGravar)
                    .addComponent(jButtonExcluir))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPesquisar)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIpServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonConectar)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonComprar)
                        .addGap(34, 34, 34))))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Produtos:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Carrinho         R$ :");

        jLabelTotalCarrinho.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelTotalCarrinho.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Estoque total: ");

        jLabelTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelTotal.setText("0");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Itens: ");

        jLabelQuant.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelQuant.setText("0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("R$ ");

        jLabeltotalProd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabeltotalProd.setText("0");

        jLabelLinhas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLinhas.setText("0");

        jButtonAdicionarItem.setText("Adicionar >>>");
        jButtonAdicionarItem.setEnabled(false);
        jButtonAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdicionarItemActionPerformed(evt);
            }
        });

        jButtonremoverItem.setText("<<< Remover");
        jButtonremoverItem.setEnabled(false);
        jButtonremoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonremoverItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAdicionarItem)
                                    .addComponent(jButtonremoverItem)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelLinhas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabeltotalProd))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelTotal)))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelQuant))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabelTotalCarrinho)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabelLinhas)
                            .addComponent(jLabel9)
                            .addComponent(jLabeltotalProd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabelTotal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabelTotalCarrinho))
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabelQuant))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jButtonAdicionarItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonremoverItem)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jTextFieldPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPesquisaActionPerformed

   
    
    private void jButtonAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdicionarItemActionPerformed
        // Adiciona produtos no carrinho de compra
        
      // mdCarrinnho = (DefaultTableModel) jTableCarrinho.getModel();
       
       //Se não tiver linha selecionada sai
       if (jTableProdutos.getSelectedRow()== -1){ 
           return;   
       }
       
       //Se a quantidade for 0 não tem estoque e não adicina a compra
       if (jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 4).toString().equals("0") ){
           JOptionPane.showMessageDialog(rootPane, "Produto indisponível. Estoque esgotdado!");
           return;
       }
       
       if (mdCarrinnho.getRowCount() == 0){ //Se não tiver nenhum produto adicionado cria o carrinho
            //Adiciona o carrinho
             enviaMensagem("AdicionarCarrinho");receberMensagem();
             enviaMensagem(this.cliente.getLocalSocketAddress().toString());receberMensagem();
             enviaMensagem("0");receberMensagem();
             enviaMensagem("0"); receberMensagem();
             

            this.idCompra = this.mensagemRecebida;
       }
       
       //Pega os dados dos produtos que serão gravados no servidor
       int cod = Integer.parseInt(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 0).toString()); //Pega o produto da linha selecionada na coluna 0
       String tipo = jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 1).toString(); //pega o tipo
       String nome = jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 2).toString(); //pega o nome
       double preco = Double.parseDouble(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 3).toString()); //pega o preço
       int estoque = Integer.parseInt(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 4).toString()); //pega o estoque
       
       //Incluir produto no carrinho  
       
        enviaMensagem("AdicionarItem");receberMensagem();
        enviaMensagem(Integer.toString(cod));receberMensagem(); //envia cod do produto
        enviaMensagem(this.idCompra);receberMensagem(); //envia id da compra
        
        //Atualiza o valor da compra no banco
        enviaMensagem("AlterarCarrinho"); receberMensagem();
        enviaMensagem(this.cliente.getLocalSocketAddress().toString());receberMensagem(); //envia nome do cliente
               
       
       Object[] dados = {cod, tipo, nome, preco, estoque};
       mdCarrinnho.addRow(dados);
       
       //Atualiza totais na tela
       double total = 0;
       for (int i=0; i < mdCarrinnho.getRowCount(); i++){
           total = total + Double.parseDouble(jTableCarrinho.getValueAt(i, 3).toString());
       }
       
       jLabelQuant.setText(Integer.toString(mdCarrinnho.getRowCount()));
       jLabelTotalCarrinho.setText(Double.toString(total));
       
        enviaMensagem(jLabelTotalCarrinho.getText()); receberMensagem(); //envia o valor total do carrinho
 
       
       //Envia solicitação de atualização da lista de produtos
        enviaMensagem("AtualizeTodos"); receberMensagem();
       
       
               
    }//GEN-LAST:event_jButtonAdicionarItemActionPerformed

    private void jButtonGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGravarActionPerformed

        //Envia solicitação de para gravar ou alterar produto no banco
        if (this.modo == 1){
             enviaMensagem("AdicionarProduto"); receberMensagem(); //Gravação
             enviaMensagem("1"); receberMensagem(); //Tanto faz o codigo
        } else if (this.modo == 2){
            enviaMensagem("AlterarProduto"); receberMensagem(); //Alteração
            enviaMensagem(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 0).toString()); receberMensagem(); //Codigo vem do jtableprodutos
            this.modo = 1; //Coloca em modo de gravação novamente
        }
        
        enviaMensagem(jComboBoxTipo.getSelectedItem().toString()); receberMensagem();
        enviaMensagem(jTextFieldNome.getText()); receberMensagem();
        enviaMensagem(jTextFieldPreco.getText()); receberMensagem();
        enviaMensagem(jTextFieldEstoque.getText()); receberMensagem();
        
        //Envia solicitação de atualização da lista de produtos
        enviaMensagem("AtualizeTodos");  receberMensagem();
        
        //Limpa as entradas

        jTextFieldNome.setText("");
        jTextFieldPreco.setText("");
        jTextFieldEstoque.setText("");
        jComboBoxTipo.setSelectedIndex(0);
    }//GEN-LAST:event_jButtonGravarActionPerformed

    private void jComboBoxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTipoActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    public void habilitaCampos (boolean habilita){
        if (habilita){
            jButtonGravar.setEnabled(true);
            jButtonExcluir.setEnabled(true);
            jComboBoxTipo.setEnabled(true);
            jTextFieldNome.setEnabled(true);
            jTextFieldPreco.setEnabled(true);
            jTextFieldEstoque.setEnabled(true);

            //Limpa as entradas

            jTextFieldNome.setText("");
            jTextFieldPreco.setText("");
            jTextFieldEstoque.setText("");
            jComboBoxTipo.setSelectedIndex(0);
            
        } else{
             jButtonGravar.setEnabled(false);
             jButtonExcluir.setEnabled(false);
             jComboBoxTipo.setEnabled(false);
             jTextFieldNome.setEnabled(false);
             jTextFieldPreco.setEnabled(false);
             jTextFieldEstoque.setEnabled(false);
            
        }
        
    }
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // Se for funcionário pede login
        String senha = "master";
        if (jCheckBox1.isSelected()){
            String entrada = JOptionPane.showInputDialog("Digite a senha de acesso");
            if (entrada == null ){
                jCheckBox1.setSelected(false);
                return;
            }
            if(entrada.equals(senha)){
                habilitaCampos(true);
                
            }else
            {
                JOptionPane.showMessageDialog(rootPane, "Senha incorreta");
                habilitaCampos(false);
                jCheckBox1.setSelected(false);
            }
        }else{//Desmarca
            habilitaCampos(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed
    
    
    
    private void jButtonPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPesquisarActionPerformed
            if (jTextFieldPesquisa.getText().length() > 0){
            if(jRadioButton1.isSelected()){
                try {
                    int num = Integer.parseInt(jTextFieldPesquisa.getText());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Código inválido.");
                    jTextFieldPesquisa.grabFocus();
                    return;
                }
            }
        }
            
           limpaTabela(jTableProdutos.getRowCount(), 1); //Limpa os dados antes de cada consulta
           
           if (jRadioButton1.isSelected()){ //Pesquisa por código
             if (jTextFieldPesquisa.getText().equals("")){ //pesquisar tdos
                  // Atualiza Produtos
                    atualizaProdutos();  
                   return;
               }
              
                enviaMensagem("PesquisaProduto"); receberMensagem();
                enviaMensagem("cod"); receberMensagem();
                enviaMensagem(jTextFieldPesquisa.getText()); receberMensagem();
              
       
                if (this.mensagemRecebida.equals("Produto não encontrado")){
                    JOptionPane.showMessageDialog(rootPane, this.mensagemRecebida);
                    enviaMensagem("");
                    return;
                }
               
                 preencheTabela(Integer.parseInt(this.mensagemRecebida));
                  enviaMensagem(""); //Finaliza a troca de mensagens
              
               
              
           }else if(jRadioButton2.isSelected()){ //Pesquisa por nome
             
               if (jTextFieldPesquisa.getText().equals("")){ //pesquisar tdos
                  // Atualiza Produtos
                    atualizaProdutos();  
                   return;
               }
              
                enviaMensagem("PesquisaProduto"); receberMensagem();
                enviaMensagem("nome"); receberMensagem();
                enviaMensagem(jTextFieldPesquisa.getText()); receberMensagem();
              
       
                if (this.mensagemRecebida.equals("Produto não encontrado")){
                    JOptionPane.showMessageDialog(rootPane, this.mensagemRecebida);
                    enviaMensagem("");
                    return;
                }
               
                 preencheTabela(Integer.parseInt(this.mensagemRecebida));
                  enviaMensagem(""); //Finaliza a troca de mensagens
                 
           }
           
         
         
         

    }//GEN-LAST:event_jButtonPesquisarActionPerformed

    private void jButtonremoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonremoverItemActionPerformed
        // TODO add your handling code here:
        //Remove a linha selecionada
        //mdCarrinnho = (DefaultTableModel) jTableCarrinho.getModel();
        
         //Se não selecionar nada saia
        if (jTableCarrinho.getSelectedRow()== -1){ 
            return;   
        }
       
       //Atualiza totais na tela
       double total = Double.parseDouble(jLabelTotalCarrinho.getText());
      
       total = total - Double.parseDouble(jTableCarrinho.getValueAt(jTableCarrinho.getSelectedRow(), 3).toString());
        
        //Exclui item do carrinho
        enviaMensagem("ExcluirItem"); receberMensagem();
        enviaMensagem(this.idCompra); receberMensagem(); //envia o id da compra
        enviaMensagem(jTableCarrinho.getValueAt(jTableCarrinho.getSelectedRow(), 0).toString()); //envia o cod do produto
        
        receberMensagem();
        enviaMensagem(Double.toString(total)); receberMensagem(); //envia o total do carrinho
        
        this.mdCarrinnho.removeRow(jTableCarrinho.getSelectedRow());
        
       jLabelQuant.setText(Integer.toString(this.mdCarrinnho.getRowCount()));
       jLabelTotalCarrinho.setText(Double.toString(total));
        
      
       
       if (jTableCarrinho.getRowCount() == 0){
           this.totalCarrinho = 0;
           this.qtCarrinho = 0;
           
           //Exclui Carrinho
           enviaMensagem("ExcluirCarrinho"); receberMensagem();
           enviaMensagem(this.idCompra); receberMensagem(); //envia id da compra
           
           
           
       }
       
        
        //Envia solicitação de atualização da lista de produtos
        enviaMensagem("AtualizeTodos"); receberMensagem();
       
    }//GEN-LAST:event_jButtonremoverItemActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        // TODO add your handling code here:
          if (jTableProdutos.getSelectedRow()== -1){ 
           return;   
       }
       
       String nome = jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 2).toString(); //Pega o produto da linha selecionada na coluna 0
      
       
       
        enviaMensagem("ApagarProduto"); receberMensagem();
        enviaMensagem(nome); receberMensagem();
        
        
        //Envia solicitação de atualização da lista de produtos
        enviaMensagem("AtualizeTodos"); receberMensagem();
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarActionPerformed
        // Conecta ao servidor
        boolean con = conectar(jTextFieldIpServidor.getText(), Integer.parseInt(jTextFieldPorta.getText())); //Conecta passando ip e porta
        if (con){
            //Habilita campos da tela
            jCheckBox1.setEnabled(true);
            jRadioButton1.setEnabled(true);
            jRadioButton2.setEnabled(true);
            jButtonPesquisar.setEnabled(true);
            jButtonAdicionarItem.setEnabled(true);
            jButtonremoverItem.setEnabled(true);
            jButtonComprar.setEnabled(true);
            jTextFieldPesquisa.setEnabled(true);
            jTableProdutos.setEnabled(true);
            jTableCarrinho.setEnabled(true);
            
            jTextFieldIpServidor.setEnabled(false);
            jTextFieldPorta.setEnabled(false);
            jButtonConectar.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_jButtonConectarActionPerformed

    private void jButtonComprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComprarActionPerformed
        // TODO add your handling code here:
        
         //Se não selecionar nada saia
        if (this.mdCarrinnho.getRowCount() == 0){ 
            return;   
        }
        
        int resp = JOptionPane.showConfirmDialog(rootPane, "Deseja finalizar a compra?");
        
        if (resp == JOptionPane.OK_OPTION){
            
        
            String mensagem="";

            for (int i=0; i < mdCarrinnho.getRowCount() ; i++){
                mensagem = mensagem + "\n" +jTableCarrinho.getValueAt(i, 0) + " - " +jTableCarrinho.getValueAt(i, 1)+""
                        + " - " +jTableCarrinho.getValueAt(i, 2)+ " - " +jTableCarrinho.getValueAt(i, 3);

            }

            JOptionPane.showMessageDialog(rootPane, "Compra: "+this.idCompra +"  R$" +this.jLabelTotalCarrinho.getText()+"\n"+ mensagem);

            limpaTabela(this.mdCarrinnho.getRowCount(), 2);
            this.totalCarrinho = 0;
            this.qtCarrinho = 0;
            jLabelQuant.setText("0");
            jLabelTotalCarrinho.setText("0");
            
        }
        
    }//GEN-LAST:event_jButtonComprarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //Encerra a conexão com o servidor 
        if (JOptionPane.showConfirmDialog(null,"Deseja sair")==JOptionPane.OK_OPTION){
          
            desconectar();
            System.exit(0);
            
        }
    }//GEN-LAST:event_formWindowClosing

    private void jTableProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableProdutosMouseClicked
        // TODO add your handling code here:
        if (jCheckBox1.isSelected()){ //Executa for Funcionário
            if (evt.getClickCount() == 2){
                this.modo = 2; //Alteração
              jComboBoxTipo.setSelectedItem(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 1).toString());

              jTextFieldNome.setText(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 2).toString());
              jTextFieldPreco.setText(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 3).toString());
              jTextFieldEstoque.setText(jTableProdutos.getValueAt(jTableProdutos.getSelectedRow(), 4).toString());

          }  
        }
        
    }//GEN-LAST:event_jTableProdutosMouseClicked

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
            java.util.logging.Logger.getLogger(FormLoja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLoja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLoja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLoja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLoja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAdicionarItem;
    private javax.swing.JButton jButtonComprar;
    private javax.swing.JButton jButtonConectar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonGravar;
    private javax.swing.JButton jButtonPesquisar;
    private javax.swing.JButton jButtonremoverItem;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLinhas;
    private javax.swing.JLabel jLabelQuant;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalCarrinho;
    private javax.swing.JLabel jLabeltotalProd;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableCarrinho;
    private javax.swing.JTable jTableProdutos;
    private javax.swing.JTextField jTextFieldEstoque;
    private javax.swing.JTextField jTextFieldIpServidor;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldPesquisa;
    private javax.swing.JTextField jTextFieldPorta;
    private javax.swing.JTextField jTextFieldPreco;
    // End of variables declaration//GEN-END:variables
}
