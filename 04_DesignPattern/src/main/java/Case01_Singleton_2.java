public class Case01_Singleton_2 {

    private static volatile Case01_Singleton_2 INSTANCE;

    private Case01_Singleton_2(){
    }

    public Case01_Singleton_2 getINSTANCE(){

        if (INSTANCE == null){
            synchronized (Case01_Singleton_2.class){
                if(INSTANCE == null){
                    INSTANCE = new Case01_Singleton_2();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args){

        Case01_Singleton_2 t = new Case01_Singleton_2();
        for(int i=0; i<100; i++){

            new Thread(()->{
                System.out.println(t.getINSTANCE().hashCode());
            }).start();


        }
    }


}
