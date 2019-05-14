package DAO;

import org.sqlite.*;

import java.sql.*;
import java.util.Vector;

import java.util.Iterator;


import Classes.*;


public class UsuarioDao {
	Conexao dbCon;
	Usuario usuario;

	public UsuarioDao() {
	try {
		this.dbCon = new Conexao();
		


	}
	catch (Exception e) {
		e.printStackTrace();
	}  
	}
	
	
	public void adicionarUsuario(Usuario usuario) {		  
		try {		  
			
		  
			this.dbCon.getStm().executeUpdate("INSERT INTO usuario  (tipo, nome)VALUES ('"
				+ usuario.getTipo() + "','"
				+ usuario.getNome() + "')");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void alterarUsuario(Usuario usuario) {		  
		try {		  
			
		  
			this.dbCon.getStm().executeUpdate("UPDATE usuario set tipo = '"+ usuario.getTipo() + "' , nome = '"+ usuario.getNome()+ "'"
					 + "WHERE id = '"+ usuario.getId() + "'");
					
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void apagarusuario(String nome) {		  
		try {		  
			
			this.dbCon.getStm().executeUpdate("DELETE FROM usuario WHERE nome = '" + nome+"'");
		}
		catch (SQLException e) {
			e.printStackTrace();  
		}  
	}
	
		public Vector<Usuario> listarUsuarios() { //Lista todos os usuarios
		Vector<Usuario> lista = new Vector<>();  
		ResultSet rs;

		try {
			rs = this.dbCon.getStm().executeQuery("SELECT * FROM usuario ORDER BY nome ASC");

			while (rs.next()) {
				lista.add(new Usuario( rs.getInt("id"), rs.getString("tipo"), rs.getString("nome")));
			}

			rs.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}
		
		
		public Usuario pesquisarUsuario(int cod) { //Pesquisa usuario pelo codigo
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM usuario WHERE id = "+cod+" ORDER BY nome ASC"); //Cria a consulta

				
				this.usuario = new Usuario( rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.usuario;
		}
		
		
		public Usuario pesquisarUsuario(String nome) { //Pesquisa usuario pelo nome
			ResultSet rs;

			try {
				rs = this.dbCon.getStm().executeQuery("SELECT * FROM usuario WHERE nome ='"+nome+"' ORDER BY nome ASC"); //Cria a consulta

				
				this.usuario = new Usuario( rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"));
				

				rs.close();

			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			return this.usuario;
		}


		//Carrinho - Compra
		public void efetuarCompra(Carrinho compra) {
			//PEGAR OS DADOS DA COMPRA E GRAVAR NAS TABELAS DE COMPRA E DE ITENS SEPARADAMENTE
		}


	
	

}
