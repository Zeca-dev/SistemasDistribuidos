package Classes;

import java.util.Iterator;

import DAO.ItensDao;
import DAO.TipoDao;

public class Itens {
	
	private int idCompra;
	private String Nome;
	private double preco;

	public Itens() {
		// TODO Auto-generated constructor stub
	}
	public Itens(int idCompra, String produto, double preco) {
		
		this.idCompra = idCompra;
		this.Nome = produto;
		this.preco = preco;
	}
        
        public void montarItem(Itens i, String atributo, String dado){
            switch(atributo){
                case "idCompra" : i.setIdCompra(Integer.parseInt(dado)); break;
                case "nome"     : i.setNome(dado); break;
                case "preço"    : i.setPreco(Integer.parseInt(dado)); break;
                
            }
            
        }
	
        public void mostraItens() {
		

		System.out.println("ID:" + this.getIdCompra());
		System.out.println("Nome:" + this.getNome());
		System.out.println("Preço:" + this.getPreco());
		
	}
	
	public static void listaTodos(int idCompra, ItensDao idao) {
		Iterator it = idao.listartItens(idCompra).iterator(); 
		Itens hs;

		while (it.hasNext()) {
			hs =  (Itens) it.next();
			hs.mostraItens();
			
		}
	}

	public int getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double precco) {
		this.preco = preco;
	}


		

}
