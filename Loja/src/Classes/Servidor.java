package Classes;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


import java.util.Vector;

import Classes.*;
import DAO.*;
import java.util.ArrayList;


public class Servidor implements Runnable{
    
    private String ipGroup = "225.7.8.9";
    private int portaGroup = 55555;
    
    private Socket socketCliente;
    private  DataInputStream in;
    private DataOutputStream out;
    
    private MulticastSocket ms;
    private byte sendData[];
    private DatagramPacket sendPack ;
       
    public static int cont = 0;
    
    
    

    public Servidor(Socket cliente){
        this.socketCliente = cliente;
        
        try {
          //Cria um canal para receber dados. 
        this.in = new DataInputStream(this.socketCliente.getInputStream()); 

        //Cria um canal para enviar dados. 
         this.out = new DataOutputStream(this.socketCliente.getOutputStream()); 
         
         
     
         
        } catch (Exception e) {
        }
        
    }
    
    public void desconectar(){
        try {
            this.in.close();
            this.out.close();
            
            this.socketCliente.close();
            this.ms.close();
            
            System.out.println("Conexao "+ this.socketCliente.getInetAddress().getHostAddress() +"/" 
        		+this.socketCliente.getInetAddress().getHostName());
           // cont--;  
        
        } catch (Exception e) {
        }
    }
    
    public void enviaMulticast(String mensagem){
        try {
        
            this.ms = new MulticastSocket(this.portaGroup); 
            this.sendData = new byte[1024];
            this.sendData = mensagem.getBytes(); //Pega a mensagem que será enviada
            this.sendPack = new DatagramPacket(this.sendData, this.sendData.length, InetAddress.getByName(this.ipGroup), this.portaGroup); 
            this.ms.send(this.sendPack);
            this.ms.close();
            
        } catch (Exception e) {
        }
   
    }
    
    public void enviaMensagem(String mensagem){
        try {   
                  this.out.writeUTF(mensagem ); //Envia string
           
        } catch (Exception e) {
        }
    }
    
    public String receberMensagem(){ //Processa a mensagem recebida e retorna 
        
        String s = "";
        try {
             s = this.in.readUTF(); //Recebe a mensagem
             
        } catch (Exception e) {
        }
       return s; 
    }
    
    //MÉTODOS PARA PROCESSAR AS REQUISIÇÕES DOS CLIENTES
    public Vector<Produto> pesquisarProduto(String tipo, String dado){ //Pesquisa produto com parametros
        Vector<Produto> lista = new Vector<>(); 
        Produto p = new Produto();
        ProdutoDao pd = new ProdutoDao();
        
        
        switch (tipo){
            case "cod": {
                lista = pd.pesquisarProduto(Integer.parseInt(dado));
                if(lista.size() == 0){
                    enviaMensagem("Produto não encontrado");receberMensagem();
                }else{
                    //manda tamanho da lista
                    enviaMensagem(Integer.toString(lista.size())); receberMensagem();
        
                    for(int i=0; i< lista.size(); i++){
                        //Envia cinco mensagens para cada produto da lista
                        enviaMensagem(Integer.toString(lista.get(i).getCod())); receberMensagem(); //Código
                        enviaMensagem(lista.get(i).getTipo()); receberMensagem(); //Tipo
                        enviaMensagem(lista.get(i).getNome()); receberMensagem(); //Nome
                        enviaMensagem(Double.toString(lista.get(i).getPreco())); receberMensagem(); //Preço
                        enviaMensagem(Integer.toString(lista.get(i).getEstoque())); receberMensagem(); //Estoque
                    }
                }
                break;
            }
            case "nome": {
                lista = pd.pesquisarProduto(dado); 
                if(lista.size() == 0){
                    enviaMensagem("Produto não encontrado");receberMensagem();
                }else{
                    //manda tamanho da lista
                    enviaMensagem(Integer.toString(lista.size())); receberMensagem();
        
                    for(int i=0; i< lista.size(); i++){
                        //Envia cinco mensagens para cada produto da lista
                        enviaMensagem(Integer.toString(lista.get(i).getCod())); receberMensagem(); //Código
                        enviaMensagem(lista.get(i).getTipo()); receberMensagem(); //Tipo
                        enviaMensagem(lista.get(i).getNome()); receberMensagem(); //Nome
                        enviaMensagem(Double.toString(lista.get(i).getPreco())); receberMensagem(); //Preço
                        enviaMensagem(Integer.toString(lista.get(i).getEstoque())); receberMensagem(); //Estoque
                    }
                }
                break;
            }
        }
        
    return lista;
        
    }

    
   
    //Pesquisa produuto sem parametros - retorna uma lista
    public Vector<Produto> pesquisaProduto(){
        Vector<Produto> lista = new Vector<>();  
        ProdutoDao pd = new ProdutoDao();
        lista = pd.listartProdutos();
        
        return lista;
    }
    
    //Envia lista de produtos para o cliente
    public void enviaListaProdutos(){
       Vector<Produto> lista = new Vector<>();
    
       lista = pesquisaProduto();
       //manda tamanho da lista
        enviaMensagem(Integer.toString(lista.size())); receberMensagem();
        
       for(int i=0; i< lista.size(); i++){
           //Envia cinco mensagens para cada produto da lista
           enviaMensagem(Integer.toString(lista.get(i).getCod())); receberMensagem(); //Código
           enviaMensagem(lista.get(i).getTipo()); receberMensagem(); //Tipo
           enviaMensagem(lista.get(i).getNome()); receberMensagem(); //Nome
           enviaMensagem(Double.toString(lista.get(i).getPreco())); receberMensagem(); //Preço
           enviaMensagem(Integer.toString(lista.get(i).getEstoque())); receberMensagem(); //Estoque
       }
    }
    
    
    //Pesquisar carrinho por cliente
    public Carrinho pesquisaCarrinho(String cliente){
        Carrinho c = new Carrinho();
        CarrinhoDao cd = new CarrinhoDao();
        
        c = cd.pesquisarCarrinho(cliente);
        
        return c;
    }
    
     //Pesquisar itens por compra
    public Vector<Itens> pesquisaItens(String idCompra){
        Vector<Itens> lista = new Vector<>();  
        ItensDao id = new ItensDao();
        lista = id.listartItens(Integer.parseInt(idCompra));
        
        return lista;
    }
    
    //Adicionar produto
    public void adicionarProduto(){
                
        Produto p = new Produto();
        ProdutoDao pd = new ProdutoDao();
        Vector<Produto> lista = new Vector<>();
        try { //Pega as mensagens vindas do cliente
                       
            p.montarProduto(p, "cod", receberMensagem()); enviaMensagem(""); 
            p.montarProduto(p, "tipo", receberMensagem()); enviaMensagem(""); 
            p.montarProduto(p, "nome", receberMensagem()); enviaMensagem(""); 
            p.montarProduto(p, "preço", receberMensagem()); enviaMensagem(""); 
            p.montarProduto(p, "estoque", receberMensagem());
            
            pd.adicionarProduto(p); //Salva o produto no banco
            
            lista = pd.pesquisarProduto(p.getNome()); //Pega o produto criado no banco para saber o código dele para mandar o produto atualizado
            
            enviaMensagem(""); 
            
            //CHAMAR MÉTODO PARA ATUALIZA A LISTA DE PRODUTOS DOS CLIENTES (MULTICAST)
            
        } catch (Exception e) {
        }
                            
    }
    
    //Alterar produto
    public void alterarProduto(){
        Produto p = new Produto();
        ProdutoDao pd = new ProdutoDao();
        Vector<Produto> lista = new Vector<>();
        
        enviaMensagem("");  //Destrava o cliente para mandar os dados do produto
         
        //pesquisa produto e preenche com os dados passados para alteração
        lista = pd.pesquisarProduto(Integer.parseInt(receberMensagem())); enviaMensagem("");
        p = lista.get(0);
        
        p.setTipo(receberMensagem()); enviaMensagem(""); //tipo
        p.setNome(receberMensagem()); enviaMensagem(""); //nome
        p.setPreco(Double.parseDouble(receberMensagem())); enviaMensagem(""); //preço
        p.setEstoque(Integer.parseInt(receberMensagem())); enviaMensagem(""); //estoque
        
        //grava alteração no banco
        pd.alterarProduto(p);
            
        //MANDAR A LISTA DE PRODUTOS ATUALIZADOS
        
    }
    
    //Excluir produto
    public void excluirProduto(){
        ProdutoDao pd = new ProdutoDao();
        enviaMensagem("");  //Destrava o cliente para mandar os dados do produto
         
        //recebe o nome, e manda excluir do banco
        pd.apagarProduto(receberMensagem()); enviaMensagem("");
            
        //MANDAR A LISTA DE PRODUTOS ATUALIZADOS
        
    }
    
    //Adicionar Carrinho
    public void adicionarCarrinho(){
                
        Carrinho c = new Carrinho(); 
        CarrinhoDao cd = new CarrinhoDao();
        try { //Pega as mensagens vindas do cliente
            this.out.writeUTF("");  //Destrava o cliente para mandar os dados
            c.montarCarrinho(c, "cliente", receberMensagem()); enviaMensagem(""); 
            c.montarCarrinho(c, "idCompra", receberMensagem()); enviaMensagem(""); 
            c.montarCarrinho(c, "total", receberMensagem());
            
            cd.adicionarCarrinho(c); //Salva carrinho no banco
            
            c = cd.pesquisarCarrinho(c.getCliente()); //Pega a compra criada no banco para saber o código
            
           //Retorna para o cliente o id da compra
            enviaMensagem(Integer.toString(c.getIdCompra()));
            
        } catch (Exception e) {
        }
    }
    
    //Alterar Carrinho
    public void alterarCarrinho(){
                
        Carrinho c = new Carrinho(); 
        CarrinhoDao cd = new CarrinhoDao();
        try { //Pega as mensagens vindas do cliente
            enviaMensagem("");  //Destrava o cliente para mandar os dados
            
            c = cd.pesquisarCarrinho(receberMensagem()); enviaMensagem(""); //Pega a ultima compra do cliente
            c.setTotal(Double.parseDouble(receberMensagem())); enviaMensagem(""); //Altera o valor da compra
            
            cd.alterarrCarrinho(c); //Grava alteração no banco 
           
            
        } catch (Exception e) {
        }
    }
    
    public void excluirCompra(){
      
        CarrinhoDao cd = new CarrinhoDao();
        try { //Pega as mensagens vindas do cliente
            enviaMensagem("");  //Destrava o cliente para mandar os dados
                  
            cd.apagarCarrinho(Integer.parseInt(receberMensagem())); enviaMensagem("");//Ecluir compra do banco 
           
                       
        } catch (Exception e) {
        }
        
    }
    
    //Adicionar itens de compra
    public void adicionarItem(){
        Vector<Produto> lista = new Vector<>();
        enviaMensagem("");  //Destrava o cliente para mandar os dados
        
        Produto p = new Produto();
        ProdutoDao  pd = new ProdutoDao();
        Carrinho c = new Carrinho();
        CarrinhoDao cd = new CarrinhoDao();
        
        ItensDao id = new ItensDao();
        
        lista = pd.pesquisarProduto(Integer.parseInt(receberMensagem())); //Pesquisa produto
        p = lista.get(0); //pega o produto pesquisado
        enviaMensagem("");
        
        //Adiciona item
        id.adicionarItem(Integer.parseInt(receberMensagem()), p.getNome(), p.getPreco());
        
        //Atualiza estoque do produto
        p.setEstoque(p.getEstoque() - 1); //diminui estoque
        pd.alterarProduto(p); //Salva alteração no banco   
        
        enviaMensagem(""); //Encerra mesnsagens
        
    }
    
    //Excluir itens de compra
    public void excluirItem(){
        enviaMensagem("");  //Destrava o cliente para mandar os dados
        
        Produto p = new Produto();
        ProdutoDao  pd = new ProdutoDao();
        Carrinho c = new Carrinho();
        CarrinhoDao cd = new CarrinhoDao();
        Vector<Produto> lista = new Vector<>();
        
        ItensDao id = new ItensDao();
        
        c = cd.pesquisarCarrinho(Integer.parseInt(receberMensagem())); enviaMensagem(""); //pesquisa carrinho
        lista = pd.pesquisarProduto(Integer.parseInt(receberMensagem())); //pesquisa produto
        p = lista.get(0); //pega o produto pesquisado
        
        enviaMensagem("");
        
        //Excluir item
        id.apagaritem(p.getNome());
        
        //Atualiza estoque do produto
        p.setEstoque(p.getEstoque() + 1); //aumenta estoque
        pd.alterarProduto(p); //Salva alteração no banco     
        
        c.setTotal(Double.parseDouble(receberMensagem())); enviaMensagem("");
        cd.alterarrCarrinho(c);
        
    }
    
    //FIM MÉTODOS PARA PROCESSAR AS REQUISIÇÕES DOS CLIENTES

    /* A classe Thread, que foi instancia no servidor, implementa Runnable.
       Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'.
    */
    public void run(){
        System.out.println("Conexao "+Servidor.cont+" com o cliente " + this.socketCliente.getInetAddress().getHostAddress() +"/" 
        		+this.socketCliente.getInetAddress().getHostName());
       
        
        //Solicitações dos clientes
         
        while (this.socketCliente.isConnected()){ //Enquanto a conexão estiver ativa ficando aguardando mensagens do cliente
            try {
                String mensagem = receberMensagem();
                switch(mensagem){ //Verificar todos os casos de mensagems e tratá-las, conforme definido
                    //PRODUTOS
                    case "Atualiza": {
                        enviaListaProdutos();
                        
                        break;
                    } 
                    //Produto
                    case "AdicionarProduto": {
                        enviaMensagem(""); //Destrava cliente
                        //recebe tipo, nome, preço e estoque
                        adicionarProduto();
                        break;
                    }
                    case "AlterarProduto": {
                        //recebe tipo, nome, preço e estoque
                        alterarProduto();
                        break;
                    }
                    case "ApagarProduto": {
                        //recebe nome do produto
                        excluirProduto();                 
                        break;
                    }
                    case"AtualizeTodos": {
                        
                        enviaMulticast("AtualizeTodos");
                        System.out.println("Mensagem Multicast: "+new String (this.sendPack.getData()));
                        
                        enviaMensagem("");
                        break;
                    }
                    case "PesquisaProduto": {
                        String tipo; String dado;
                        enviaMensagem(""); //Destrava cliente
                        
                        tipo = receberMensagem(); enviaMensagem("");
                        dado = receberMensagem(); 
                        pesquisarProduto(tipo, dado);
                        break;
                    }
                   
                    //CARRINHO
                    case "AdicionarCarrinho": {
                        //recebe o nome do cliente da compra
                        adicionarCarrinho();
                        
                        break;
                    }
                    case "AlterarCarrinho": {
                        //Esse método é usado apenas para atualizar o valor total da compra
                        //Recebe o nome do cliente e o valor total da compra
                        alterarCarrinho();                        
                        break;
                    }
                    case "ExcluirCarrinho": {
                        //Desiste da compra e exclui todos os itens ligados a ela
                        //Recebe o id da compra
                        excluirCompra();
                        break;
                    }
                    //ITENS DE COMPRA
                    case "AdicionarItem": {
                        //recebe o id do produto e o id da compra
                        adicionarItem();
                        break;
                    }
                    case "ExcluirItem": {
                        //recebe o id do produto e o id da compra
                        excluirItem();
                        break;
                    }
                   
                   
                   
                }
                
                   
            } catch (Exception e) {
                e.printStackTrace();
                
            }
            
        }
        System.out.println("Conexao "+ this.socketCliente.getInetAddress().getHostAddress() +"/" 
        		+this.socketCliente.getInetAddress().getHostName());
        

    }
    

}
