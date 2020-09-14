public class Case01_Singleton_1 {

    private static final Case01_Singleton_1 INSTANCE = new Case01_Singleton_1();

    private Case01_Singleton_1(){

    }

    public static Case01_Singleton_1 getInstance() {
        return INSTANCE;
    }

    public static void main (String[] args){
        Case01_Singleton_1 t1 = Case01_Singleton_1.getInstance();
        Case01_Singleton_1 t2 = Case01_Singleton_1.getInstance();
        System.out.println(t1 == t2);
    }
}
