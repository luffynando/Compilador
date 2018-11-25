import java.util.LinkedList;

public class Semantico {
    public String nombrePrograma;
    public LinkedList<Variables> tabla;

    public Semantico() {
        tabla = new LinkedList<>();
    }

    public boolean add(Variables aux){
        if(tabla.isEmpty()){
            tabla.add(aux);
            return true;
        }else{
            for(Variables lista: tabla){
                if(lista.nombre.equals(aux.nombre)){
                    return false;
                }
            }
            tabla.add(aux);
        }
        return true;
    }

    public Variables getByName(String name){
        for(Variables lista: tabla){
            if(lista.nombre.equals(name)){
                return lista;
            }
        }
        return null;
    }

    public String intentaagregar(Variables aux){
        if(tabla.isEmpty()){
            tabla.add(aux);
            return "";
        }else{
            Variables match = getByName(aux.nombre);
            if(match != null){
                if(match.isConstante){
                    return "No se puede modificar una constante";
                }
                if(match.isArreglo){
                    return "No se puede modificar un arreglo";
                }
                tabla.set(tabla.indexOf(match),aux);
            }else{
                tabla.add(aux);
                return "";
            }
        }
        return "";
    }

    public String saltaArregloAdd(Variables aux){
        if(tabla.isEmpty()){
            tabla.add(aux);
            return "";
        }else{
            Variables match = getByName(aux.nombre);
            if(match != null){
                if(match.isConstante){
                    return "No se puede modificar una constante";
                }
                tabla.set(tabla.indexOf(match),aux);
            }else{
                tabla.add(aux);
                return "";
            }
        }
        return "";
    }

    public void printtabla(){
        for(Variables lista: tabla) {
            if(lista.isArreglo){
                System.out.println("Es Arreglo");
                System.out.println("Nombre: "+lista.nombre +" Tipo: "+lista.tipo);
                System.out.println("Values: "+lista.printvalues());
            }else{
                if(lista.isConstante){
                    System.out.println("Es constante");
                    System.out.println("Nombre: "+lista.nombre +" Tipo: "+lista.tipo +" Valor:" + lista.value);
                }else{
                    System.out.println("Es variable");
                    System.out.println("Nombre: "+lista.nombre +" Tipo: "+lista.tipo +" Valor:" + lista.value);
                }
            }
        }
    }
}
