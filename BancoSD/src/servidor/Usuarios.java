/*
 guarda os usuários para simular login
 */
package servidor;

import java.io.Serializable;

/**
 *
 * @author jcarl
 */
public class Usuarios implements Serializable{
    private String nome;
    private String senha;
    private boolean logado = false;
    private Conta c = null;

    public Usuarios(){
        //construtor vazio
    }
    
    //para verificações de login consultar a variavel logado
    public Conta logar(String usuario, String senha){

        for (Usuarios u: Servidor.usuarios ){          
            if (u.getNome().equals(usuario) && u.getSenha().equals(senha)){                 
                u.setLogado(true);
                u.setC( Servidor.localizaConta(4, usuario));               
              return u.getC();
            }
        } 
        return null;
    }

    
    
    //Gets e Sets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public Conta getC() {
        return c;
    }

    public void setC(Conta c) {
        this.c = c;
    }

   
    
    
    
}
