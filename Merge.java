import java.util.*;
import java.io.*;
public class Merge{
  /*sort the array from least to greatest value. This is a wrapper function*/
  public static void mergesort(int[]data){
    mergesortH(data, 0, data.length - 1);
  }

  private static void mergesortH(int[] data, int lo, int hi){ //helper function
    if (lo >= hi){ //went through all the indices
      return;
    }
    int mid = data.length/2;
    int leftLength = mid;
    int rightLength = data.length - mid;
    int[] L = new int[leftLength];
    int[] R = new int[rightLength];
    //copy over values
    for (int i = 0; i < L.length; i++){
      //left array
      L[i]=data[i];
    }
    for (int i = 0; i < R.length; i++){
      //right array
      R[i]=data[mid+i];
    }
    mergesortH(L, 0, L.length - 1); //split in half and mergesort left side
    mergesortH(R, 0, R.length - 1); //split in half and mergesort right side
    merge(data, L, R, mid); //merge them back together
  }
  /*
  Pseudocode:
  mergesort(data,lo,hi):
    if lo >= hi :
      return
    mergesort left side
    mergesort right side
    merge
  */
  //You can make arrays as temporary space if you wish! This is the easier method, but will be a bit slower.

  private static void merge(int[] data, int[] L, int[] R, int mid){
    // start with creating temp arrays
    /*int leftLength = mid - left + 1;
    int rightLength = right - mid;
    int[] L = new int[leftLength];
    int[] R = new int[rightLength];
    */
    //store indices
    int i = 0; //data index
    int l = 0; //left index
    int r = 0; //right index
    //loop thru the elements in one side and elements on the other and move it into the right place
    while(l < L.length && r < R.length){
      // if left is less than or equal to right
      if (L[l] <= R[r]){
        data[i] = L[l];
        l++;
      }
      // if right is less than left
      else{
        data[i] = R[r];
        r++;
      }
      i++;
    }
    //if left not done (length was greater than right)
    while(l < L.length){
      data[i] = L[l];
      i++;
      l++;
    }
    //if right not done (length greater than left)
    while(r < R.length){
      data[i] = R[r];
      i++;
      r++;
    }
  }
  //If you want to make it faster:
  //Have your mergesort take both the data and temp arrays. Mergesort the temp, and merge into the original!
  //e.g.
  //  private static void mergesort(int[]data, int[]temp, int lo, int hi){}
  //Pre-allocate a temp array in your mergesort wrapper method, and copy the data into it.
  public static void main(String[]args){
    //Mr. K's sort driver from last time
    System.out.println("Size\t\tMax Value\tmerge/builtin ratio ");
    int[]MAX_LIST = {1000000000,500,10};
    for(int MAX : MAX_LIST){
      for(int size = 31250; size < 2000001; size*=2){
        long qtime=0;
        long btime=0;
        for(int trial = 0 ; trial <=5; trial++){
          int []data1 = new int[size];
          int []data2 = new int[size];
          for(int i = 0; i < data1.length; i++){
            data1[i] = (int)(Math.random()*MAX);
            data2[i] = data1[i];
          }
          long t1,t2;
          t1 = System.currentTimeMillis();
          Merge.mergesort(data2);
          t2 = System.currentTimeMillis();
          qtime += t2 - t1;
          t1 = System.currentTimeMillis();
          Arrays.sort(data1);
          t2 = System.currentTimeMillis();
          btime+= t2 - t1;
          if(!Arrays.equals(data1,data2)){
            System.out.println("FAIL TO SORT!");
            System.exit(0);
          }
        }
        System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
      }
      System.out.println();
    }
  }
}
