package Classes;

import java.util.Iterator;

import DAO.TipoDao;

public class Tipo {
	
	private int id;
	private String tipo;

	public Tipo(int id, String tipo) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.tipo = tipo;
	}
	
public void mostraTipo() {
		

		System.out.println("ID:" + this.getId());
		System.out.println("Nome:" + this.getTipo());
		
	}
	
	public static void listaTodos(TipoDao pdao) {
		Iterator it = pdao.listarttipos().iterator(); 
		Tipo hs;

		while (it.hasNext()) {
			hs =  (Tipo) it.next();
			hs.mostraTipo();
			
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

	

}
