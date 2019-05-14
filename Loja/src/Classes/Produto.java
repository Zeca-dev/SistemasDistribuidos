package Classes;

import java.util.Iterator;

import DAO.ProdutoDao;

public class Produto {
	
	private int cod;
	private String tipo;
	private String nome;
	private double preco;
	private int estoque;
	
        public Produto(){
            // TODO Auto-generated constructor stub
        }

	public Produto(int cod, String tipo, String nome, Double preco, int qtde) {
		
		this.cod = cod;
		this.tipo = tipo;
		this.nome = nome;
		this.preco = preco;
		this.estoque = qtde;
		
	}
        
        public void montarProduto(Produto p, String atributo, String dado){
            switch(atributo){
                case "cod"    : p.setCod(cod); break; //Usado somente na resposta do servidor
                case "tipo"   : p.setTipo(dado); break;
                case "nome"   : p.setNome(dado); break;
                case "preço"  : p.setPreco(Double.parseDouble(dado)); break;
                case "estoque": p.setEstoque(Integer.parseInt(dado)); break;
            }
            
        }
        
	public void mostraProduto() {
		
		System.out.println("Cod:" + this.getCod());
		System.out.println("Tipo:" + this.getTipo());
		System.out.println("Nome:" + this.getNome());
		System.out.println("Pre�o:" + this.getPreco());
		System.out.println("Estoque:" + this.getEstoque());
	}
	
	public static void listaTodos(ProdutoDao pdao) {
		Iterator it = pdao.listartProdutos().iterator(); 
		Produto hs;

		while (it.hasNext()) {
			hs =  (Produto) it.next();
			hs.mostraProduto();
			
		}
	}

	public int getCod() {
		return cod;
	}


	public void setCod(int cod) {
		this.cod = cod;
	}





	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public double getPreco() {
		return preco;
	}


	public void setPreco(double preco) {
		this.preco = preco;
	}


	public int getEstoque() {
		return estoque;
	}


	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	
	

}
