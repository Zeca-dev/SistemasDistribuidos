package Classes;

import java.util.Iterator;

import DAO.UsuarioDao;

public class Usuario {
	
	private int id;
	private String tipo; //C - Cliente e F - Funcionário
	private String nome; //Vincular o nome do cliente a sua conexão do Socket

	public Usuario(int id, String tipo, String nome) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
	}
	
public void mostraUsuario() {
		
		System.out.println("ID:" + this.getId());
		System.out.println("Tipo:" + this.getTipo());
		System.out.println("Nome:" + this.getNome());
		
	}
	
	public static void listarTodos(UsuarioDao udao) {
		Iterator it = udao.listarUsuarios().iterator(); 
		Usuario hs;

		while (it.hasNext()) {
			hs =  (Usuario) it.next();
			hs.mostraUsuario();
			
		}
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	

}
