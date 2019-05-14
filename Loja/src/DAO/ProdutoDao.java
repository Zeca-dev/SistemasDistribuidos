package DAO;

import org.sqlite.*;

import java.sql.*;
import java.util.Vector;

import java.util.Iterator;


import Classes.*;


public class ProdutoDao {
	Conexao dbCon;
	Produto produto;

	public ProdutoDao() {
	try {
		this.dbCon = new Conexao();
		


	}
	catch (Exception e) {
		e.printStackTrace();
	}  
	}
	
	//Produtos
	public void adicionarProduto(Produto produto) {		  
		try {		  
			
		  
			this.dbCon.getStm().executeUpdate("INSERT INTO Produto (tipo, nome, preco, estoque) VALUES ('"
				+ produto.getTipo() + "','"
				+ produto.getNome() + "','"
				+ produto.getPreco() +"','" 
				+ produto.getEstoque() + "')");
                        
                        //CHAMAR A FUNÇÃO QUE ATUALIZA OS PRODUTOS PARA OS CLIENTES
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterarProduto(Produto produto) {		  
		try {		  
			
		  
			this.dbCon.getStm().executeUpdate("UPDATE Produto set tipo = '"+ produto.getTipo() + "' , nome = '"+ produto.getNome()+ "'"
					+ ", preco = '"+ produto.getPreco() + "' , estoque = '"+ produto.getEstoque() + "' WHERE cod = '"+ produto.getCod() + "'");
					
                        
                        //CHAMAR A FUNÇÃO QUE ATUALIZA OS PRODUTOS PARA OS CLIENTES
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void apagarProduto(String nome) {		  
		try {		  
			
			this.dbCon.getStm().executeUpdate("DELETE FROM Produto WHERE nome = '" + nome+"'");
                        
                        //CHAMAR A FUNÇÃO QUE ATUALIZA OS PRODUTOS PARA OS CLIENTES
		}
		catch (SQLException e) {
			e.printStackTrace();  
		}  
	}
	
		public Vector<Produto> listartProdutos() { //Lista todos os produtos
		Vector<Produto> lista = new Vector<>();  
		ResultSet rs;

		try {
			rs = this.dbCon.getStm().executeQuery("SELECT * FROM Produto ORDER BY nome ASC");

			while (rs.next()) {
				lista.add(new Produto( rs.getInt("cod"), rs.getString("tipo"), rs.getString("nome"), rs.getDouble("preco"),  rs.getInt("estoque")));
			}

			rs.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
		
		
		public Vector<Produto> pesquisarProduto(int cod) { //Pesquisa produto pelo codigo
                    Vector<Produto> lista = new Vector<>();
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM Produto WHERE cod = "+cod+" ORDER BY nome ASC"); //Cria a consulta

                                while (rs.next()){
                                    lista.add( new Produto( rs.getInt("cod"), rs.getString("tipo"), rs.getString("nome"), rs.getDouble("preco"),  rs.getInt("estoque")));
                                }

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return lista;
		}
		
		
		public Vector<Produto> pesquisarProduto(String nome) { //Pesquisa produto pelo nome
                    Vector<Produto> lista = new Vector<>();
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM Produto WHERE nome like '%"+nome+"%' ORDER BY nome ASC"); //Cria a consulta
                                
                              
                                while (rs.next()){
                                    lista.add( new Produto( rs.getInt("cod"), rs.getString("tipo"), rs.getString("nome"), rs.getDouble("preco"),  rs.getInt("estoque")));
                                }

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return lista;
		}


		//Carrinho - Compra
		public void efetuarCompra(Carrinho compra) {
			//PEGAR OS DADOS DA COMPRA E GRAVAR NAS TABELAS DE COMPRA E DE ITENS SEPARADAMENTE
		}


	
	

}
