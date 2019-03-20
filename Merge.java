import java.util.*;
import java.io.*;
public class Merge{
  /**Insertion Sort of an int array.
  *Upon completion, the elements of the array will be in increasing order
  *@param ary the elements to be sorted.
  */
  public static void insertionSort(int[] ary){
    int nowVal; //Keep track of current index value
    int insertIndex; //Keep track of where to insert the value
    for (int i = 1;i<ary.length;++i){
      //Loop through ary.length times (n)
      nowVal = ary[i];
      insertIndex = i;
      while (insertIndex > 0 && nowVal < ary[insertIndex -1]){
        //Shift the values if index is not 0 and nowVal is smaller than value before
        ary[insertIndex] = ary[insertIndex - 1];
        insertIndex--;
      }
      ary[insertIndex]=nowVal; //Insert the value at the index
    }
  }

  /*sort the array from least to greatest value. This is a wrapper function*/
  public static void mergesort(int[]data){
    mergesortH(data, 0, data.length - 1);
  }

  private static void mergesortH(int[] data, int lo, int hi){ //helper function
    if (lo >= hi){ //went through all the indices
      return;
    }
    int mid = data.length/2;
    //int leftLength = mid;
    //int rightLength = data.length - mid;
    int[] L = new int[mid];
    int[] R = new int[data.length - mid];
    //copy over values
    for (int i = 0; i < L.length; i++){
      //left array
      L[i]=data[i];
    }
    for (int i = 0; i < R.length; i++){
      //right array
      R[i]=data[mid+i];
    }
    if (L.length < 75){
      insertionSort(L);
    }
    else{
      mergesortH(L, 0, L.length - 1);
    }
    if (R.length < 75){
      insertionSort(R);
    }
    else{
      mergesortH(R, 0, R.length - 1);
    }
    //mergesortH(L, 0, L.length - 1); //split in half and mergesort left side
    //mergesortH(R, 0, R.length - 1); //split in half and mergesort right side
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
  /* //sample times without optimizing
  Size            Max Value       merge/builtin ratio
31250           1000000000      0.32608695652173914
62500           1000000000      2.3636363636363638
125000          1000000000      2.1956521739130435
250000          1000000000      2.073394495412844
500000          1000000000      1.902542372881356
1000000         1000000000      2.1839323467230445
2000000         1000000000      2.1594202898550723

31250           500     17.0
62500           500     3.9166666666666665
125000          500     3.074074074074074
250000          500     5.323529411764706
500000          500     3.1926605504587156
1000000         500     2.896694214876033
2000000         500     3.537712895377129

31250           10      29.0
62500           10      5.666666666666667
125000          10      6.230769230769231
250000          10      7.705882352941177
500000          10      6.613636363636363
1000000         10      7.170731707317073
2000000         10      7.841059602649007

//sample times with optimizing (100 boundary)
Size            Max Value       merge/builtin ratio
31250           1000000000      1.6521739130434783
62500           1000000000      3.25
125000          1000000000      2.129032258064516
250000          1000000000      1.1503267973856208
500000          1000000000      1.24
1000000         1000000000      1.5314685314685315
2000000         1000000000      1.462474645030426

31250           500     0.9375
62500           500     1.16
125000          500     2.607142857142857
250000          500     1.3424657534246576
500000          500     1.7565217391304349
1000000         500     2.391959798994975
2000000         500     2.2653061224489797

31250           10      4.0
62500           10      0.0
125000          10      4.0
250000          10      3.619047619047619
500000          10      5.703703703703703
1000000         10      5.968253968253968
2000000         10      5.221311475409836

//sample times with optimizing (90 boundary)
Size            Max Value       merge/builtin ratio
31250           1000000000      0.8095238095238095
62500           1000000000      0.5161290322580645
125000          1000000000      0.8103448275862069
250000          1000000000      0.9383561643835616
500000          1000000000      1.0576271186440678
1000000         1000000000      1.585480093676815
2000000         1000000000      1.4858695652173912

31250           500     0.2857142857142857
62500           500     0.5161290322580645
125000          500     3.769230769230769
250000          500     1.2692307692307692
500000          500     1.487603305785124
1000000         500     2.3300970873786406
2000000         500     2.2224719101123593

31250           10      NaN
62500           10      Infinity
125000          10      4.444444444444445
250000          10      34.666666666666664
500000          10      4.0256410256410255
1000000         10      4.625
2000000         10      4.16875

//sample times with optimizing (70 boundary)
Size            Max Value       merge/builtin ratio
31250           1000000000      0.4603174603174603
62500           1000000000      0.46875
125000          1000000000      1.309090909090909
250000          1000000000      1.0555555555555556
500000          1000000000      1.1904761904761905
1000000         1000000000      1.4780701754385965
2000000         1000000000      1.4930777422790202

31250           500     0.18518518518518517
62500           500     0.5161290322580645
125000          500     1.9666666666666666
250000          500     3.186046511627907
500000          500     1.7661290322580645
1000000         500     2.0047846889952154
2000000         500     2.3433179723502304

31250           10      NaN
62500           10      13.0
125000          10      0.9473684210526315
250000          10      12.875
500000          10      4.909090909090909
1000000         10      4.097560975609756
2000000         10      3.7005988023952097

  */
}
