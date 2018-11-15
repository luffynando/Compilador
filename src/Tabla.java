import java.util.ArrayList;

public class Tabla {
    public ArrayList<String> nombre;
    public ArrayList<String> tipo= new ArrayList<>();
    public ArrayList<String> lexema;
    public ArrayList<Integer> tam;

    public Tabla() {
        nombre=new  ArrayList<>();
        tipo= new ArrayList<>();
        lexema=new  ArrayList<>();
        tam= new ArrayList<>();
    }


    public void printTable() {
        System.out.print( "\ntipo ");
        for(int i=0;i<tipo.size();i++) {
            System.out.print(tipo.get(i)+"  |  " );
        }
        System.out.print( "\nnombre: ");
        for(int i=0;i<nombre.size();i++) {
            System.out.print( nombre.get(i) +"  |  " );
        }
        System.out.print( "\nlexema: ");
        for(int i=0;i<lexema.size();i++) {
            System.out.print(lexema.get(i)+"  |  " );
        }
        System.out.print( "\ntamaño: ");
        for(int i=0;i<tam.size();i++) {
            System.out.print(tam.get(i)+"  |  " );
        }
        System.out.println("\n----------------");
    }


    public boolean AgregaIden(String iden) {
        //System.out.println("variable a agregar: "+iden);
        if(nombre.contains(iden)){
            return false;
        }
        nombre.add(iden);
        return true;
    }


    public boolean AgregaLexemaArreglo(String lexema, int index) {
        boolean band=false;
        if(index==0) {
            this.lexema.add(lexema+" ");
            return true;
        }
        if(index==1) {
            if(this.tipo.get(nombre.size()-1).equals("arreglo entero") ) {
                Integer.parseInt(lexema);
                this.lexema.set(this.nombre.size()-1," "+this.lexema.get(this.nombre.size()-1).concat(lexema+" ") );
                return true;
            }
            if(this.tipo.get(nombre.size()-1).equals("arreglo caracter") ) {
                try {
                    Integer.parseInt(lexema);
                }catch(Exception e) {
                    band=true;
                    System.out.println("exepcion");
                    //String aux= lexema.replaceAll("'", "");
                    this.lexema.set(this.nombre.size()-1," "+this.lexema.get(this.nombre.size()-1).concat(lexema+" ") );
                }
                if(band==false) {
                    return false;
                }

            }
            return true;
        }
        return false;
    }

    public String getLexemaByIden(String iden){
        int aux = nombre.indexOf(iden);
        if(aux != -1){
            return lexema.get(aux);
        }
        return new String();
    }

    public int getTambyName(String iden) {
        return nombre.indexOf(iden);
    }


}

