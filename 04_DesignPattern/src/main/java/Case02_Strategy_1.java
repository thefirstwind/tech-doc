import java.util.Arrays;

public class Case02_Strategy_1 {

    public static void main(String[] args){
        Cat[] a = {new Cat(3,3), new Cat(3,5), new Cat(5,7)};
        Sorter sorter = new Sorter();
        sorter.sort(a);
        System.out.println(Arrays.toString(a));

    }

    static class Cat implements Comparable<Cat>{
        int weight, height;

        public Cat(int weight, int height){
            this.weight = weight;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "weight=" + weight +
                    ", height=" + height +
                    '}';
        }

        @Override
        public int compareTo(Cat c) {
            if(this.weight < c.weight) return -1;
            else if(this.weight > c.weight) return 1;
            else return 0;
        }

    }
    static class Sorter{
        public static void sort(Comparable[] arr){
            for( int i =0; i < arr.length ; i++){
                int minPos = i;
                for(int j=i+1; j< arr.length; j++){
//                    minPos = arr[j] < arr[minPos] ? j : minPos;
                    minPos = arr[j].compareTo(arr[minPos]) == -1 ? j : minPos;

                }
                swap(arr, i , minPos);
            }
        }
        public static void swap(Comparable[] arr, int i , int j){
            Comparable temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }


}
