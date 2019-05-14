package Classes;

import java.util.ArrayList;
import java.util.Iterator;

import DAO.CarrinhoDao;
import DAO.ItensDao;
import DAO.ProdutoDao;
import DAO.UsuarioDao;
import java.util.Vector;

public class Carrinho {
	
	private ArrayList<Itens> produtos; //Lista de produtos
	private int idCompra;
	private double total = 0; //Soma dos pre�os dos produtos
	private String cliente; //Vinculado � conex�o do Socket
        private String mensagens; //Para gravar mensagens que serão enviadas para os clientes

        public Carrinho (){
            // TODO Auto-generated constructor stub
        }
	public Carrinho(int idCompra, String cliente) {
		
		this.idCompra = idCompra;
		this.cliente = cliente;
		this.produtos = new ArrayList<>();
	}
        
        public void montarCarrinho(Carrinho c, String atributo, String dado){
            switch(atributo){
                case "cliente"    : c.setCliente(dado); break; //Usado somente na resposta do servidor
                case "idCompra"   : c.setIdCompra(Integer.parseInt(dado)); break;
                case "total"      : c.setTotal(Double.parseDouble(dado)) ; break;
                
            }
            
        }
        
	
	public void adicionanoCarrinho(int idCompra, String produto, double preco) {
		Itens p = new Itens(idCompra, produto, preco); //vincula o item ao carrinho do cliente
		this.produtos.add(p); //Adiciona o item ao carrinho
		this.total += p.getPreco(); //Soma total
                Vector<Produto> lista = new Vector<>();
                
                //Atualiza estoque
                ProdutoDao pd = new ProdutoDao();
                Produto p1 = new Produto();
                lista = pd.pesquisarProduto(produto);
                p1 = lista.get(0);
                
                if (p1.getEstoque() > 0){
                    p1.setEstoque(p1.getEstoque() - 1); //Diminui um produto no estoque
                    pd.alterarProduto(p1); //Atualiza no banco de dados
                
                     //CHAMAR A FUNÇÃO QUE ATUALIZA OS PRODUTOS PARA OS CLIENTES
                }else {
                    //grava mensagem que será enviada ao usuário
                    this.mensagens = "Produto indisponível.";
                }
                
                
	}

	public void excluidoCarrinho(Itens t) {
		
		this.produtos.remove(t); //Remove o item do carrinho
		this.total -= t.getPreco(); //Diminui total
                Vector<Produto> lista = new Vector<>();
                
                //Atualiza estoque
                ProdutoDao pd = new ProdutoDao();
                Produto p1 = new Produto();
                lista = pd.pesquisarProduto(t.getNome());
                p1 = lista.get(0);
                p1.setEstoque(p1.getEstoque() + 1); //Diminui um produto no estoque
                pd.alterarProduto(p1); //Atualiza no banco de dados
                
                //CHAMAR A FUNÇÃO QUE ATUALIZA OS PRODUTOS PARA OS CLIENTES
	}
	
	
	public void mostrarCompras(Carrinho idCompra) {
		
		CarrinhoDao cdao = new CarrinhoDao();
		ItensDao idao = new ItensDao();
		
		Itens item = new Itens();
		
		int id_Compra = cdao.pesquisarCarrinho(idCompra.getCliente()).getIdCompra();
		idCompra.setIdCompra(id_Compra);
		
		System.out.println("Compra:" +idCompra.getIdCompra());
		System.out.println("Cliente:" +idCompra.getCliente());
		System.out.println("Total:" +idCompra.getTotal());
		System.out.println("Itens:\n" );
		
		
		item.listaTodos(id_Compra, idao);
		
	}

	public ArrayList<Itens> getProdutos() {
		return produtos;
	}



	public void setProdutos(ArrayList<Itens> produtos) {
		this.produtos = produtos;
	}

	

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

        public String getMensagens() {
            return mensagens;
        }

        public void setMensagens(String mensagens) {
            this.mensagens = mensagens;
        }



	
	
	

}
