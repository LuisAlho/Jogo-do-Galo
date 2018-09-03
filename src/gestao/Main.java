package gestao;

public class Main {

    public static void main(String [] args) {
        
        if (args.length == 0){
            System.out.println("Invalid arguments...");
            argumentsHelp();
            System.exit(0);
        }

        Gestao gestao = new Gestao();
        gestao.start();
    }
    
    public static void argumentsHelp(){
        System.out.println("Need to give some arguments...");
    }

}