package DAO;

import org.sqlite.*;

import java.sql.*;
import java.util.Vector;

import java.util.Iterator;


import Classes.*;


public class TipoDao {
	Conexao dbCon;
	Tipo tipo;

	public TipoDao() {
	try {
		this.dbCon = new Conexao();
		


	}
	catch (Exception e) {
		e.printStackTrace();
	}  
	}
	

	public void adicionartipo(Tipo tipo) {		  
		try {		  
			
			 //CONSERTAR ESSA LINHA NAS DEMAIS CLASSES DAO
			this.dbCon.getStm().executeUpdate("INSERT INTO tipo (nome) VALUES ('"+tipo.getTipo()+"')");
			
				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterartipo(Tipo tipo) {		  
		try {		  
			
		 //CONSERTAR ESSA LINHA NAS DEMAIS CLASSES DAO
			this.dbCon.getStm().executeUpdate("UPDATE tipo set nome = '"+ tipo.getTipo() +"' WHERE id = '"+ tipo.getId() + "' ");
					
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void apagartipo(String nome) {		  
		try {		  
			 //CONSERTAR ESSA LINHA NAS DEMAIS CLASSES DAO
			this.dbCon.getStm().executeUpdate("DELETE FROM tipo WHERE nome = '" + nome+"'");
		}
		catch (SQLException e) {
			e.printStackTrace();  
		}  
	}
	
		public Vector<Tipo> listarttipos() { //Lista todos os tipos
		Vector<Tipo> lista = new Vector<>();  
		ResultSet rs;

		try {
			rs = this.dbCon.getStm().executeQuery("SELECT * FROM tipo ORDER BY nome ASC");

			while (rs.next()) {
				lista.add(new Tipo(rs.getInt("id"), rs.getString("nome")));
			}

			rs.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
		
		
		public Tipo pesquisarTipo(int cod) { //Pesquisa tipo pelo codigo
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM tipo WHERE id = "+cod+" ORDER BY nome ASC"); //Cria a consulta

				
				this.tipo = new Tipo(rs.getInt("id"), rs.getString("nome"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.tipo;
		}
		
		
		public Tipo pesquisartipo(String nome) { //Pesquisa tipo pelo nome
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM tipo WHERE nome like '"+nome+"%' ORDER BY nome ASC"); //Cria a consulta

				
				this.tipo = new Tipo(rs.getInt("id"), rs.getString("nome"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.tipo;
		}


		//Carrinho - Compra
		public void efetuarCompra(Carrinho compra) {
			//PEGAR OS DADOS DA COMPRA E GRAVAR NAS TABELAS DE COMPRA E DE ITENS SEPARADAMENTE
		}


	
	

}
