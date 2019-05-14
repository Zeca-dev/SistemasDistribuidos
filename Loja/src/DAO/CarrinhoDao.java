package DAO;

import org.sqlite.*;

import java.sql.*;
import java.util.Vector;

import java.util.Iterator;


import Classes.*;


public class CarrinhoDao {
	Conexao dbCon;
	Carrinho carrinho;

	public CarrinhoDao() {
	try {
		this.dbCon = new Conexao();
		


	}
	catch (Exception e) {
		e.printStackTrace();
	}  
	}
	
	//Carrinho
	public void adicionarCarrinho(Carrinho carrinho) {		  
		try {		  
			
		 
			this.dbCon.getStm().executeUpdate("INSERT INTO Carrinho (cliente) VALUES ('"+ carrinho.getCliente() +"')");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void alterarrCarrinho(Carrinho carrinho) {		  
		try {		  
			
		 
			this.dbCon.getStm().executeUpdate("UPDATE Carrinho set total_compra = '"+ carrinho.getTotal() +"' WHERE id_compra = '"+ carrinho.getIdCompra() + "' ");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	 
	
	public void apagarCarrinho(int idcompra) {		  
		try {		  
			
			this.dbCon.getStm().executeUpdate("DELETE FROM Carrinho WHERE id_compra = '" + idcompra+"'"); //Apaga compra
			this.dbCon.getStm().executeUpdate("DELETE FROM Itens WHERE id = '" + idcompra+"'"); //Apaga itens da compra
		}
		catch (SQLException e) {
			e.printStackTrace();  
		}  
	}
	
		public Vector<Carrinho> listartCompra() { //Lista todos os produtos
		Vector<Carrinho> lista = new Vector<>();  
		ResultSet rs;

		try {
			rs = this.dbCon.getStm().executeQuery("SELECT * FROM Carrinho ORDER BY id_compra ASC");

			while (rs.next()) {
				lista.add(new Carrinho( rs.getInt("id_compra"),  rs.getString("cliente")));
			}

			rs.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
		
		
		public Carrinho pesquisarCarrinho(int cod) { //Pesquisa Compra pelo codigo
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM Carrinho WHERE id_compra = '"+cod+"' ORDER BY id_compra ASC"); //Cria a consulta

				
				this.carrinho = new Carrinho(  rs.getInt("id_compra"),  rs.getString("cliente"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.carrinho;
		}
		
		
		public Carrinho pesquisarCarrinho(String cliente) { //Pesquisa Compras por cliente
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT id_compra, cliente FROM Carrinho WHERE cliente = '"+cliente+"' ORDER BY id_compra DESC"); //Cria a consulta

				
				this.carrinho = new Carrinho(  rs.getInt("id_compra"),  rs.getString("cliente"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.carrinho;
		}

	

}
