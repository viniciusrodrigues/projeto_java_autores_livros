
package banco;

//import com.mysql.jdbc.MySQLConnection;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import objetos.Autores;
import objetos.Livros;

public class Banco 
{
    Autores autores = new Autores();
    Livros livros = new Livros();
    
	// CONEXÃO COM O BANCO		
	String dbUrl = "jdbc:mysql://localhost/biblioteca";				
	Connection con = null;
                                		
		// IMPLEMENTAÇÃO DE CONEXÃO AO BANCO								
			public Connection Conectar()
			{
				try {
				Class.forName("com.mysql.jdbc.Driver");
				con =  (Connection)DriverManager. getConnection(dbUrl, "root", "");			
				return con;											
				} catch (Exception e) {
				return null;
				}
			}                       
                               
                        // INSERIR AUTOR
			public	void inserirAutores(Autores autores) throws SQLException
			{
				try
				{	
				con = Conectar();
				String sqlStmt = "INSERT INTO autores(nome, data_nascimento)"
                                                +"VALUES(?,?)";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setString(1, autores.getNome());				
                                stmt.setString(2, autores.getData_nascimento());
                                
				stmt.execute();	
                                
                                JOptionPane.showMessageDialog(null,"Inserção efetuada com sucesso!"); 
					
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 						
                                
				} finally {
                                    con.close();
                                }
			}
                       
                        public int acharID(Autores autores)
                        {
                            int id = 0;
                            try
                            {
                                con = Conectar();
                                Statement stmt =con.createStatement();					
                                String sqlStmt = "SELECT id_autor FROM autores WHERE nome = '"+autores.getNome()+"' AND data_nascimento = '"+autores.getData_nascimento()+"';";
                                        
                                ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                rSet.next();
                                id = rSet.getInt("id_autor");
                            } 
                                
                            catch(Exception e){

                                JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	

                            }
                            
                            return id;
                        }
                        
                        // ALTERAR AUTOR                        
                        public void alterarAutores(Autores autores)
			{
				try
				{	
				con = Conectar();													
				String sqlStmt = "UPDATE autores SET nome = ?, "
                                                 + "data_nascimento = ?"
                                                 + "WHERE id_autor = ? ;";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setString(1, autores.getNome());				
                                stmt.setString(2, autores.getData_nascimento());
                                stmt.setInt(3, autores.getId());
                                
				int linha = stmt.executeUpdate();	
                                
                                JOptionPane.showMessageDialog(null,"Alteração efetuada com sucesso!\n" + linha + " linha afetada!" ); 
			
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
			}	
			
                        // EXCLUIR AUTOR
			public void excluirAutores(int id)
			{
				try
				{
                                    con = Conectar();
                                    String sqlStmt = "DELETE FROM autores WHERE id_autor = ?;";
                                    PreparedStatement stmt =con.prepareStatement(sqlStmt);					
                                    stmt.setInt(1, id);                                  
                                    int linha = stmt.executeUpdate();

                                     JOptionPane.showMessageDialog(null,"Exclusão efetuada com sucesso!\n" + linha + " linha afetada!"); 

				}
                                
                                catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                                }
                        
                        
                        
                        
                        
                        // LISTAR AUTORES CADASTRADOS
			public JTable mostrarAutores()
			{
                            JTable tabelaAutores = new JTable();
				try
				{
                                con = Conectar();
				Statement stmt =con.createStatement();					
				String sqlStmt =  "SELECT "
                                                 +"       nome, "
                                                 +"       data_nascimento"
                                                 +"   FROM "
                                                 +"       autores "
                                                 +"   ORDER BY "
                                                 +"           nome ASC";
				ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                
                                DefaultTableModel model = (DefaultTableModel) tabelaAutores.getModel(); 
                                
                                
                                while (rSet.next()) 
                                {
                                    String nome, data_nascimento;
                                    nome = rSet.getString("nome");
                                    data_nascimento = rSet.getString("data_nascimento");
                                    model.addRow(new String[] {nome, data_nascimento} );
                                }
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                                
                                return tabelaAutores;
                          }
                        
                        /*
                          // INSERIR CONTAS
			public	void inserirContas(Contas contas, String nomeCliente) throws SQLException
			{
				try
				{	
				con = Conectar();

				Statement stmtDescobrirID = con.createStatement();					
				String sqlStmtDescobrirID = "SELECT "
                                                 +"       id_cliente"
                                                 +"   FROM "
                                                 +"       clientes"
                                                 +"WHERE nome = "+nomeCliente;
                                
				ResultSet rSet = stmtDescobrirID.executeQuery(sqlStmtDescobrirID); 
                                
                                int ID = rSet.getInt("id_cliente");                     
                                
				String sqlStmt = "INSERT INTO contas(id_cliente, digito)"
                                        + "       VALUES(?,?)";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setInt(1, ID);				
                                stmt.setInt(2, contas.getDigito());
                                
				stmt.execute();	
                                
                                JOptionPane.showMessageDialog(null,"Inserção efetuada com sucesso!"); 
					
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 						
                                
				} finally {
                                    con.close();
                                }
			}
                        
                        // ALTERAR CONTAS                        
                        public void alterarContas(Contas contas, int id_contas, String nomeCliente)
			{
				try
				{	
				con = Conectar();			
                                
                                Statement stmtDescobrirID = con.createStatement();					
				String sqlStmtDescobrirID = "SELECT "
                                                 +"       id_cliente"
                                                 +"   FROM "
                                                 +"       clientes"
                                                 +"WHERE nome = "+nomeCliente;
                                
				ResultSet rSet = stmtDescobrirID.executeQuery(sqlStmtDescobrirID); 
                                
                                int ID = rSet.getInt("id_cliente");
                               
				String sqlStmt = "UPDATE contas SET id_cliente = ?, "
                                                 + "digito = ?,"
                                                 + "WHERE id_contas = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setInt(1, ID);				
                                stmt.setInt(2, contas.getDigito());
                                stmt.setInt(3,id_contas);
                                
				int linha = stmt.executeUpdate();	
                                
                                JOptionPane.showMessageDialog(null,"Alteração efetuada com sucesso!\n" + linha + " linha afetada!" ); 
			
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
			}	
			
                        // EXCLUIR CONTAS
			public void excluirContas(int id_contas)
			{
				try
				{                         				
                                con = Conectar();
				String sqlStmt = "DELETE FROM contas WHERE id_contas = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);					
				stmt.setInt(1,id_contas);	
				int linha = stmt.executeUpdate();
                                
                                 JOptionPane.showMessageDialog(null,"Exclusão efetuada com sucesso!\n" + linha + " linha afetada!"); 
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                                }
                        
                        // LISTAR CONTAS CADASTRADOS
			public void mostrarContas()
			{
				try
				{
                                con = Conectar();
				Statement stmt =con.createStatement();					
				String sqlStmt = "SELECT "
                                                 +"       cl.nome, "
                                                 +"       co.numero,"
                                                 +"       co.digito"
                                                 +"   FROM "
                                                 +"       contas co"
                                                 +"       INNER JOIN clientes cl"
                                                 +"       ON co.id_cliente = cl.id_cliente"
                                                 +"   ORDER BY "
                                                 +"           nome ASC";
				ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                
                                String registros = "";                     
                                
                                while (rSet.next()) 
                                {
                                    registros += "----------------Contas----------------\n"+
                                            "Nome:     "+rSet.getString("cl.nome")+"\n"+
                                            "Número da Conta:     "+rSet.getString("co.numero")+"\n"+
                                            "Dígito:     "+rSet.getString("co.digito")+"\n";
                                }
                                
                                JOptionPane.showMessageDialog(null, registros);
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                          }              */
                        
                        
                        
                        
                        
                        
                        
                        
                    // INSERIR LIVRO
			public void inserirLivros(Livros livros, int id_autor) throws SQLException
			{
				try
				{	
				con = Conectar();
				String sqlStmt = "INSERT INTO livros(id_autor, sinopse, titulo)"
                                        + "       VALUES(?,?,?)";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setInt(1, id_autor);				
                                stmt.setString(2, livros.getSinopse());
                                stmt.setString(3, livros.getTitulo());
                                
				stmt.execute();	
                                
                                JOptionPane.showMessageDialog(null,"Inserção efetuada com sucesso!"); 
					
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 						
                                
				} finally {
                                    con.close();
                                }
			}
                        
                        public void acharIdAutor(String autor)
                        {
                            try
				{
                                con = Conectar();
				Statement stmt =con.createStatement();					
				String sqlStmt =  "SELECT "
                                                 +"       id_autor "
                                                 +"   FROM "
                                                 +"       autores "
                                                 +"   WHERE "
                                                 +"       nome = ?";
                                
                                
				ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                
                                String registros = "";                     
                                
                                while (rSet.next()) 
                                {
                                    registros += "----------------Cliente----------------\n"+
                                            "Nome:     "+rSet.getString("nome")+"\n"+
                                            "CPF:     "+rSet.getString("cpf")+"\n";
                                }
                                
                                JOptionPane.showMessageDialog(null, registros);
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                        }
                        
                        // ALTERAR CLIENTE                        
                        /*public void alterarClientes(Cliente cliente, int id_cliente)
			{
				try
				{	
				con = Conectar();													
				String sqlStmt = "UPDATE clientes SET nome = ?, "
                                                 + "cpf = ?,"
                                                 + "WHERE id_cliente = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setString(1,cliente.getNome());				
                                stmt.setString(2,cliente.getCpf());
                                stmt.setInt(3,id_cliente);
                                
				int linha = stmt.executeUpdate();	
                                
                                JOptionPane.showMessageDialog(null,"Alteração efetuada com sucesso!\n" + linha + " linha afetada!" ); 
			
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
			}	
			
                        // EXCLUIR CLIENTE
			public void excluirClientes(int id_cliente)
			{
				try
				{                         				
                                con = Conectar();
				String sqlStmt = "DELETE FROM clientes WHERE id_cliente = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);					
				stmt.setInt(1,id_cliente);	
				int linha = stmt.executeUpdate();
                                
                                 JOptionPane.showMessageDialog(null,"Exclusão efetuada com sucesso!\n" + linha + " linha afetada!"); 
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                                }
                        
                        // LISTAR CLIENTES CADASTRADOS
			public void mostrarClientes()
			{
				try
				{
                                con = Conectar();
				Statement stmt =con.createStatement();					
				String sqlStmt = "SELECT "
                                                 +"       nome, "
                                                 +"       cpf, "
                                                 +"   FROM "
                                                 +"       clientes "
                                                 +"   ORDER BY "
                                                 +"           modelo ASC";
				ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                
                                String registros = "";                     
                                
                                while (rSet.next()) 
                                {
                                    registros += "----------------Cliente----------------\n"+
                                            "Nome:     "+rSet.getString("nome")+"\n"+
                                            "CPF:     "+rSet.getString("cpf")+"\n";
                                }
                                
                                JOptionPane.showMessageDialog(null, registros);
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                          }
                        
                          // INSERIR CONTAS
			public	void inserirContas(Contas contas, String nomeCliente) throws SQLException
			{
				try
				{	
				con = Conectar();

				Statement stmtDescobrirID = con.createStatement();					
				String sqlStmtDescobrirID = "SELECT "
                                                 +"       id_cliente"
                                                 +"   FROM "
                                                 +"       clientes"
                                                 +"WHERE nome = "+nomeCliente;
                                
				ResultSet rSet = stmtDescobrirID.executeQuery(sqlStmtDescobrirID); 
                                
                                int ID = rSet.getInt("id_cliente");                     
                                
				String sqlStmt = "INSERT INTO contas(id_cliente, digito)"
                                        + "       VALUES(?,?)";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setInt(1, ID);				
                                stmt.setInt(2, contas.getDigito());
                                
				stmt.execute();	
                                
                                JOptionPane.showMessageDialog(null,"Inserção efetuada com sucesso!"); 
					
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 						
                                
				} finally {
                                    con.close();
                                }
			}
                        
                        // ALTERAR CONTAS                        
                        public void alterarContas(Contas contas, int id_contas, String nomeCliente)
			{
				try
				{	
				con = Conectar();			
                                
                                Statement stmtDescobrirID = con.createStatement();					
				String sqlStmtDescobrirID = "SELECT "
                                                 +"       id_cliente"
                                                 +"   FROM "
                                                 +"       clientes"
                                                 +"WHERE nome = "+nomeCliente;
                                
				ResultSet rSet = stmtDescobrirID.executeQuery(sqlStmtDescobrirID); 
                                
                                int ID = rSet.getInt("id_cliente");
                               
				String sqlStmt = "UPDATE contas SET id_cliente = ?, "
                                                 + "digito = ?,"
                                                 + "WHERE id_contas = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);	
				stmt.setInt(1, ID);				
                                stmt.setInt(2, contas.getDigito());
                                stmt.setInt(3,id_contas);
                                
				int linha = stmt.executeUpdate();	
                                
                                JOptionPane.showMessageDialog(null,"Alteração efetuada com sucesso!\n" + linha + " linha afetada!" ); 
			
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
			}	
			
                        // EXCLUIR CONTAS
			public void excluirContas(int id_contas)
			{
				try
				{                         				
                                con = Conectar();
				String sqlStmt = "DELETE FROM contas WHERE id_contas = ? ";
				PreparedStatement stmt =con.prepareStatement(sqlStmt);					
				stmt.setInt(1,id_contas);	
				int linha = stmt.executeUpdate();
                                
                                 JOptionPane.showMessageDialog(null,"Exclusão efetuada com sucesso!\n" + linha + " linha afetada!"); 
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                                }
                        
                        // LISTAR CONTAS CADASTRADOS
			public void mostrarContas()
			{
				try
				{
                                con = Conectar();
				Statement stmt =con.createStatement();					
				String sqlStmt = "SELECT "
                                                 +"       cl.nome, "
                                                 +"       co.numero,"
                                                 +"       co.digito"
                                                 +"   FROM "
                                                 +"       contas co"
                                                 +"       INNER JOIN clientes cl"
                                                 +"       ON co.id_cliente = cl.id_cliente"
                                                 +"   ORDER BY "
                                                 +"           nome ASC";
				ResultSet rSet = stmt.executeQuery(sqlStmt); 
                                
                                String registros = "";                     
                                
                                while (rSet.next()) 
                                {
                                    registros += "----------------Contas----------------\n"+
                                            "Nome:     "+rSet.getString("cl.nome")+"\n"+
                                            "Número da Conta:     "+rSet.getString("co.numero")+"\n"+
                                            "Dígito:     "+rSet.getString("co.digito")+"\n";
                                }
                                
                                JOptionPane.showMessageDialog(null, registros);
                                
				} catch(Exception e){
                                    
				JOptionPane.showMessageDialog(null,"Erro -> "+e.getMessage().toString()); 	
                                
				}
                          }              */      
        	                       
}
