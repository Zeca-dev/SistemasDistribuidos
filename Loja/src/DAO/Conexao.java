package DAO;

import org.sqlite.*;
import java.sql.*;
import java.util.Vector;

import Classes.Carrinho;
import Classes.Produto;

public class Conexao {

	private Connection conn;
	private Statement stm;

	public Conexao() throws SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		this.conn = DriverManager.getConnection("jdbc:sqlite:db/Loja.db");
		this.stm = this.conn.createStatement();
	}

		public void setConn(Connection conn) {
		this.conn = conn;
	}


	public Statement getStm() {
		return stm;
	}


	public void setStm(Statement stm) {
		this.stm = stm;
	}
	
	
	

}
