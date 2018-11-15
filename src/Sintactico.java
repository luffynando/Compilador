import java.util.EnumMap;

public class Sintactico {
    private AFDVault lexico;
    private Tokens token;
    private String preanalisis;

    public Sintactico(AFDVault lexico) {
        this.lexico = lexico;
    }

    public void Iniciar() throws ParserException {
        token = lexico.getToken();
        A();
        if (!token.token.equals("FinFichero")) {
            if (token.token.equals("PR")){
                throw new ParserException(Error(token.token+": "+token.secuencia, "fin de fichero", token.pos));
            }
            throw new ParserException(Error(token.token, "fin de fichero", token.pos));
        }
    }

    public void A() throws ParserException {
        Emparejar("programa");
        Emparejar("identificador");
        B();
        Emparejar("inicio");
        C();
        Emparejar("fin");
    }

    public void B() throws ParserException {
        if (token.token.equals("PR")) {
            if (token.secuencia.equals("constantes")) {
                Emparejar("constantes");
                Cons();
                D();
            }
            if (token.secuencia.equals("arreglos")) {
                Emparejar("arreglos");
                Arreg();
            }
        }
    }

    public void C() throws ParserException {
        if (token.token.equals("PR")){
            switch (token.secuencia){
                case "escribe":
                        J();
                    break;
                case "para":
                        K();
                    break;
                case "si":
                        L();
                    break;
                case "lee":
                        M();
                    break;
                default:
                    //vacio
            }
        }else if(token.token.equals("identificador")){
            N();
        }else{
            //vacio
        }
    }


    //Aqui se debe hacer la funcion escribe :3
    public void J()throws ParserException{
        Emparejar("escribe");
        Emparejar("parA");
        U();
        Emparejar("parC");
        Emparejar("punto y coma");

        C();
    }

    public void U()throws ParserException{
        Q();
    }

    public void Q()throws ParserException{
        if(token.token.equals("identificador")){
            Emparejar("identificador");
            T();
        }else {
            Valor();
        }
    }

    public void T()throws ParserException{
        if (token.token.equals("punto")){
            Emparejar("punto");
            Emparejar("length");
        }else if(token.token.equals("CuadA")) {
            Emparejar("CuadA");
            Lim();
            P();
            Emparejar("CuadC");
        }else{
            //nada
        }
    }

    public void Lim()throws ParserException{
        if(token.token.equals("entero")){
            Emparejar("entero");
        }else if(token.token.equals("identificador")){
            Emparejar("identificador");
            T();
        }else{
            throw new ParserException(Error(token.token, "entero o identificador", token.pos));
        }
    }

    public void K()throws ParserException{
        Emparejar("para");
        Emparejar("identificador");
        Emparejar("asignacion");
        Lim();
        Emparejar("hasta");
        Lim();
        Emparejar("paso");
        Indec();
        Emparejar("hacer");
        C();
        Emparejar("fin");
        C();

    }

    public void Indec()throws ParserException{
        if(token.token.equals("suma")){
            Emparejar("suma");
            Lim();
        }else if(token.token.equals("resta")){
            Emparejar("resta");
            Lim();
        }else{
            throw new ParserException(Error(token.token, "+ o -", token.pos));
        }
    }

    public void L()throws ParserException{
        Emparejar("si");
        Emparejar("parA");
        Q();
        Comp();
        Q();
        Emparejar("parC");
        Emparejar("entonces");
        C();
        O();
        Emparejar("fin");
        C();

    }

    public void Comp()throws ParserException{
        switch (token.token){
            case "menor":
                Emparejar("menor");
                break;
            case "menor igual":
                Emparejar("menor igual");
                break;
            case "mayor":
                Emparejar("mayor");
                break;
            case "mayor igual":
                Emparejar("mayor igual");
                break;
            case "igual a":
                Emparejar("igual a");
                break;
            case "diferente":
                Emparejar("diferente");
                break;
            default:
                throw new ParserException(Error(token.token, "simbolo relacional o de igualdad", token.pos));
        }
    }

    public void O()throws ParserException{
        if(token.secuencia.equals("sino")){
            Emparejar("sino");
            C();
        }
    }

    public void M()throws ParserException{
        Emparejar("lee");
        Emparejar("parA");
        Emparejar("identificador");
        Emparejar("parC");
        Emparejar("punto y coma");
        C();
    }

    public void N()throws ParserException{
        Emparejar("identificador");
        V();
        Emparejar("asignacion");
        if (token.token.equals("caracter")){
            Emparejar("caracter");
        }else {
            Lim();
            P();
        }
        Emparejar("punto y coma");
        C();
    }

    public void V()throws ParserException{
        if (token.token.equals("CuadA")){
            Emparejar("CuadA");
            Lim();
            Emparejar("CuadC");
        }
    }

    public void P()throws ParserException{
        if(token.token.equals("PR")){
            if(token.secuencia.equals("mod")){
                Emparejar("mod");
                Lim();
            }
        }else{
            switch (token.token){
                case "suma":
                        Emparejar("suma");
                        Lim();
                    break;
                case "resta":
                        Emparejar("resta");
                        Lim();
                    break;
                case "division":
                        Emparejar("division");
                        Lim();
                    break;
                case "multiplicacion":
                        Emparejar("multiplicacion");
                        Lim();
                    break;
                default:
               //nada
            }
        }
    }

    public void Cons()throws ParserException{
        Emparejar("identificador");
        Emparejar("asignacion");
        Valor();
        F();
    }

    public void Arreg()throws ParserException{
        Emparejar("identificador");
        Emparejar("asignacion");
        Emparejar("corchA");
        G();
        Emparejar("corchC");
        H();
    }

    public void D()throws ParserException{
        if(token.token.equals("PR")){
            if(token.secuencia.equals("arreglos")){
               Emparejar("arreglos");
               Arreg();
            }
        }
    }

    public void H()throws ParserException{
        if(token.token.equals("identificador")){
            Emparejar("identificador");
            Emparejar("asignacion");
            Emparejar("corchA");
            G();
            Emparejar("corchC");
            H();
        }
    }

    public void G()throws ParserException{
        Valor();
        I();
    }

    public void I()throws ParserException{
        if(token.token.equals("coma")) {
            Emparejar("coma");
            Valor();
            I();
        }
    }

    public void F()throws ParserException{
        if(token.token.equals("identificador")){
            Emparejar("identificador");
            Emparejar("asignacion");
            Valor();
            F();
        }
    }

    public void Valor()throws ParserException{
        switch (token.token){
            case "entero":
                    Emparejar("entero");
                break;
            case "caracter":
                    Emparejar("caracter");
                break;
                default:
                    throw new ParserException(Error(token.token,"entero o caracter",token.pos));
        }
    }

    public void Emparejar(String tok)throws ParserException{
        if (token.token.equals("PR")){
            if (!token.secuencia.equals(tok)){
                throw new ParserException(Error(token.token+": "+token.secuencia,tok,token.pos));
            }
            switch (tok){
                case "programa":
                    token = lexico.getToken();
                    break;
                case "inicio":
                    token = lexico.getToken();
                    break;
                case "fin":
                    token = lexico.getToken();
                    break;
                case "constantes":
                    token = lexico.getToken();
                    break;
                case "arreglos":
                    token = lexico.getToken();
                    break;
                case "para":
                    token = lexico.getToken();
                    break;
                case "hasta":
                    token = lexico.getToken();
                    break;
                case "paso":
                    token = lexico.getToken();
                    break;
                case "hacer":
                    token = lexico.getToken();
                    break;
                case "si":
                    token = lexico.getToken();
                    break;
                case "entonces":
                    token = lexico.getToken();
                    break;
                case "sino":
                    token = lexico.getToken();
                    break;
                case "escribe":
                    token = lexico.getToken();
                    break;
                case "lee":
                    token = lexico.getToken();
                    break;
                case "mod":
                    token = lexico.getToken();
                    break;
                case "length":
                    token = lexico.getToken();
                    break;
                default:
                    throw new ParserException(Error(token.token+": "+token.secuencia,tok,token.pos));
            }
        }else{
            if(token.token.equals(tok)){
                token = lexico.getToken();
            }else{
                throw new ParserException(Error(token.token,tok,token.pos));
            }
        }
    }



    public String Error(String lexema, String esperado,int linea){
        return "Error Sintactico: Encontrado lexema "+lexema+" se esperaba "+ esperado+ " en linea: "+String.valueOf(linea);
    }

    public static String ErrorSemantico(String lexema, String esperado,int linea){
        return "Error Semantico: "+lexema+" "+esperado+" en linea "+String.valueOf(linea);
    }

    public void funcionEscribe(String cadena) {
        Interfaz.textSalida.append("\n funcion escribe dice: "+ cadena);
    }


}
