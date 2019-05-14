package DAO;
import java.util.Iterator;




import Classes.*;

public class Driver {

	public Driver() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ProdutoDao pd = new ProdutoDao();	
			UsuarioDao ud = new UsuarioDao();
			TipoDao tp = new TipoDao();
			CarrinhoDao cd = new CarrinhoDao();
			Produto p10 = new Produto(0, "Alimentos", "Teste de produto q", 250.0, 5);
			
                        pd.adicionarProduto(p10);
			//Tipo t = new Tipo(5, "Laser Alterado");
			//tp.apagartipo(t.getTipo());
			
			
			//p3.mostraProduto();
			
			//Produto p1 = new Produto(14, "Alimentos", "Teste de inser��o", 250.00, 120);
			//pd.apagarProduto(p1.getNome());
			//p1.mostraProduto();
			
			//Lista todos os produtos
			//Produto.listaTodos(pd);
			
			//Usuario user = new Usuario(0, "C", "Cliente 32");
			//ud.apagarusuario(user.getNome());
			
			//Pesquisa 1 Usu�rio  por c�digo
			Usuario u = ud.pesquisarUsuario(1);
			
			
			
			
			
                        //carrinho.comprar(carrinho);
			
		
                        
                        
			
			//u.mostraUsuario();
			
			//Listar usu�rios
			//Usuario.listarTodos(ud);
			
			//Pesquisa 1 Tipo  por c�digo
			//Tipo t = tp.pesquisarTipo(1);
			//t.mostraTipo();
			
			//Listar Tipos
			//Tipo.listaTodos(tp);
			

		}
		catch (Exception e) {
			e.printStackTrace();
		}  
	}

	
	
	

	

}
