package autos;

//importamos las librerias que necesitamos
//agregamos este import para la conexion con la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
//importamos la biblioteca p@ara poder ingrar datos por pantalla
import java.util.Scanner;


public class auto {
    //armamos lka estrucutra pricipal para eliminar el error de que no hay clase principal
    //main principak
    public static void main(String []args){
        System.out.println("Prueba de ingreso de datos y conexion a la base de datos");
        
        
        //CREAMOS UN MENU para el ingreso de datos por pantalla
        int opc=0;
        
        //Creamos las variables para el inhreso de datos por pantalla
        String patenteNumero;
        boolean patenteActiva;
        String marca;
        String categoria;
        String color;
        //utilizamos scanner crando una variable sc del tipo scanner para las opciones
        Scanner sc=new Scanner(System.in);
         
        //hacer mientras opc sea distinto de 5 y cuando sea 5 termina la ejecucion del progema.
        do{
            //con el do hacemos si o si que se ejecute minimo una vez el codigo minetras  opc sea menor que 0 "o" mayor a 6
            do{
                System.out.println("----------------------------------------");
                System.out.println("MENU PRINCIPAL");
                System.out.println("----------------------------------------");
                System.out.println("1 - Ver Datos");
                System.out.println("2 - Ingresar");
                System.out.println("3 - Modificar (estado False)");
                System.out.println("4 - Eliminar");
                System.out.println("5 - Salir");
                System.out.println("");
                System.out.println("Ingrese un opcion: ");
                
                //opc toma el valor capturado
                opc=sc.nextInt();
                
            } while (opc<0 || opc>6);
            
            switch(opc){
                case 1:
                    consultar();
                    break;
                case 2:
                    System.out.println("COMPLETE LOS DATOS: ");
                    
                    System.out.println("Patente: ");
                    patenteNumero=sc.next();
                    
                    patenteActiva=true;

                    System.out.println("Marca: ");
                    marca=sc.next();
                    
                    System.out.println("Categoria: ");
                    categoria=sc.next();
                    
                    System.out.println("Color: ");
                    color=sc.next();
                    
                    //estos datos ingresado por main se los voy a mandar por argumento
                    //estoy enviando argumentos a la funcion insertar esta debe recibir los mismo
                    insertar(patenteNumero,patenteActiva,marca,categoria,color);
                    System.out.println("----------------------------------------");                    
                    System.out.println("Registro Agregado");
                    System.out.println("----------------------------------------");
                    break;
                case 3:
                    System.out.println("----------------------------------------");
                    System.out.println("INHABILITAR PATENTE");
                    System.out.println("----------------------------------------");
                    System.out.println("Ingrese el Numero de Patente:");
                    patenteNumero=sc.next();
                    //le envio el valor de patenteNumero, no la variable!
                    actualizar(patenteNumero);
                    break;
                case 4:
                    System.out.println("----------------------------------------");
                    System.out.println("ELIMINAR PATENTE");
                    System.out.println("----------------------------------------");
                    System.out.println("Ingrese el Numero de Patente:");
                    patenteNumero=sc.next();                    
                    eliminar(patenteNumero);
                    break;
                case 5:
                    System.out.println("----------------------------------------");
                    System.out.println("Gracias por utilizar la app");
                    System.out.println("----------------------------------------");

                    break;
            }
        } while(opc!=5);                
    }

    //este metodo funcion conecta la base de datos devolviendo un valor de conexion
    private static Connection conectarBaseDeDatos() throws SQLException {
        //sin definir conexion como variable null no funcionaria el return! Solucion!
        Connection conexion=null;

        //en el caso de error utilizaremos el try and cacht para capturar el error en el caso.
        try{
            //soliicitamos los drivers de conexion a la ide maria db de maeven!
            String driver="org.mariadb.jdbc.Driver";
            //definimos la ubicacion de donde esta la base de datos utilizando los drivers y el local host mas el puerto por default
            String url="jdbc:mariadb://localhost:3306/javaBD";
            String usuario="root";
            String clave="administrador";
            //realizamos la conexion
            conexion=DriverManager.getConnection(url,usuario,clave);
        }
        catch (SQLException e) {
            //captura cualquier exception sql que ocurra
            e.printStackTrace();
        }
        return conexion;
    } 
    
    //METODO DE CONSULTA
    private static void consultar(){
        try{
            //conectamos a la bd por medio de la funcion creada
            Connection conexion=conectarBaseDeDatos();
            
            //utilizamos la sintaxis de SQL para seleccionar toda o parte de los campos que queremos mostrar
            String sql="select * from autofamiliar";
        
            //sTATEMENT : para poder mostrar los datos solicitados con la consulta sql
            Statement querry=conexion.createStatement();
            //nos devuelve un resultado y donde le tengo que pasar a executequerry el string sql (variable que cree)
            //para una consulta
            ResultSet resultado=querry.executeQuery(sql); 
            
            
            //IMPRIMIMOS : mientras tengA resultados
            System.out.println("----------------------------------------");
            System.out.println("INICIO DE CONSULTA");
            System.out.println("----------------------------------------");
            while(resultado.next()){
                //se mostrara cada dato de la tabla
                System.out.println("Numero de Patente: "+resultado.getString("patenteNumero"));
                System.out.println("Patente Activa: "+resultado.getBoolean("patenteActiva"));
                System.out.println("Marca: "+resultado.getString("marca"));
                System.out.println("Catgoria: "+resultado.getString("categoria"));
                System.out.println("Color: "+resultado.getString("color"));
                System.out.println("----------------------------------------");

            }
            System.out.println("----------------------------------------");
            System.out.println("FIN DE CONSULTA");
            System.out.println("----------------------------------------");

        }
        
        catch(SQLException ex){
            //captura cualquier exception sql que ocurra
            ex.printStackTrace();        
        }
       
    }
    
    //METODO PARA INSETAR
    //recibo los datos del scanner y cro variables para poder trabajr con esos datos
    public static void insertar(String pat,boolean patAc, String mar, String cat, String col){
        try{
            //conectamos a la bd por medio de la funcion creada
            //me devuelve un string de conexion 
            Connection conexion=conectarBaseDeDatos();
            
            //utilizamos la sintaxis de SQL para seleccionar toda o parte de los campos que queremos mostrar
            //VALUE -> palabra reservada. Pasamos a Harcoder -> metemos datos desde el codigo! 
            String sql="INSERT INTO autofamiliar(patenteNumero,patenteActiva,marca,categoria,color)"
                    +"VALUES('"+pat+"',"+patAc+",'"+mar+"','"+cat+"','"+col+"')";
           
            //sTATEMENT : para poder mostrar los datos solicitados con la consulta sql
            Statement querry=conexion.createStatement();
           
            //para insertar. Vamos a tomar l qyerry y ejecutar el sql de la linea 88!
            querry.execute(sql);
            
        }
        
        catch(SQLException ex){
            //captura cualquier exception sql que ocurra
            ex.printStackTrace();        
        }
        
    }
    
    //ACTUALIZA EL ESTADO DE PATENTE
    public static void actualizar(String patenteNumero){
        try{
                //conectamos a la bd por medio de la funcion creada
                Connection conexion=conectarBaseDeDatos();
                //seteamos donde decimos que  de 0 pase a 1!
                String sql = "UPDATE autofamiliar SET patenteActiva=0 WHERE patenteNumero='"+patenteNumero+"'";
                Statement querry=conexion.createStatement();
                querry.execute(sql);
                System.out.println("----------------------------------------");
                System.out.println("Patente inhabilitada!");
                System.out.println("----------------------------------------");                  
            }

            catch(SQLException ex){
                //captura cualquier exception sql que ocurra
                ex.printStackTrace();            
            }
            
            //ESTE EXCEPTION VA A ATRAPAR CUALQUIER ERROR QUE OCURRA
            catch(Exception ex){
                    //captura cualquier exception sql que ocurra
                    //getMessage muestra en un mensaje de cualquier error que ocurra
                    ex.getMessage();            
            }
    }
    
    
    //METODO PARA ELIMINAR
    public static void eliminar(String patenteNumero){
        
        //utilizamos scanner crando una variable sc del tipo scanner para las opciones
        Scanner sc=new Scanner(System.in);
        String opc="";
        System.out.println("Desea eliminar este registro? S/N");
        //capturo el ingreso del dato e opc
        opc=sc.next();
        //lo trasnformo siempre a mayucula indemendientemente de como lo ingresa el usuario!
        opc=opc.toUpperCase();
        
        try{
                //conectamos a la bd por medio de la funcion creada
                //me devuelve un string de conexion 
                Connection conexion=conectarBaseDeDatos();

                //utilizamos la sintaxis de SQL para seleccionar toda o parte de los campos que queremos mostrar
                //VALUE -> palabra reservada. Pasamos a Harcoder -> metemos datos desde el codigo! 
                String sql = "DELETE FROM autofamiliar WHERE patenteNumero=('"+patenteNumero+"')";

                //sTATEMENT : para poder mostrar los datos solicitados con la consulta sql
                Statement querry=conexion.createStatement();

                if (opc.equals("S")){
                //para insertar. Vamos a tomar l qyerry y ejecutar el sql de la linea 88!
                querry.execute(sql);
                //mensaje de eliminar
                System.out.println("----------------------------------------");
                System.out.println("Patente eliminada correctamente!");
                System.out.println("----------------------------------------");                  
                }
        }

            catch(SQLException ex){
                //captura cualquier exception sql que ocurra
                ex.printStackTrace();            
        }
    }
}
