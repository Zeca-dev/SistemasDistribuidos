package DAO;

import org.sqlite.*;

import java.sql.*;
import java.util.Vector;

import java.util.Iterator;


import Classes.*;


public class ItensDao {
	Conexao dbCon;
	Itens item;

	public ItensDao() {
	try {
		this.dbCon = new Conexao();
		
		

	}
	catch (Exception e) {
		e.printStackTrace();
	}  
	}
	
	//Itens
	public void adicionarItem(int idCompra, String produto, double preco) {		  
		try {		  
			
		  
			this.dbCon.getStm().executeUpdate("INSERT INTO Itens VALUES ('"
				+ idCompra + "','"
				+ produto + "','"
				+ preco + "')");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void apagaritem(String nome) {		  
		try {		  
			
			this.dbCon.getStm().executeUpdate("DELETE FROM Itens WHERE nome = '" + nome+"' ");
		}
		catch (SQLException e) {
			e.printStackTrace();  
		}  
	}
	
		public Vector<Itens> listartItens(int idCompra) { //Lista todos os itens
		Vector<Itens> lista = new Vector<>();  
		ResultSet rs;

		try {
			rs = this.dbCon.getStm().executeQuery("SELECT * FROM Itens WHERE id = '"+idCompra+"' ORDER BY id ASC");

			while (rs.next()) {
				lista.add(new Itens( rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco")));
			}

			rs.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
		
		
		public Itens pesquisarItem(int cod) { //Pesquisa itens pelo codigo
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM Itens WHERE id like '"+cod+"%' ORDER BY id ASC"); //Cria a consulta

				
				this.item = new Itens( rs.getInt("id"), rs.getString("nome"),  rs.getDouble("preco"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.item;
		}
		
		
		public Itens pesquisarItem(String nome) { //Pesquisa item pelo nome
			ResultSet rs;

			try {
				
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM Itens WHERE id like '"+nome+"%' ORDER BY nome ASC"); //Cria a consulta

				
				this.item = new Itens( rs.getInt("id"), rs.getString("nome"),  rs.getDouble("preco"));

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.item;
		}

	

}
