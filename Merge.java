public class Merge{
  /*sort the array from least to greatest value. This is a wrapper function*/
  public static void mergesort(int[]data){
    margesortH(data, 0, data.length - 1);
  }

  private static void mergesortH(int[] data, int lo, int hi){ //helper function
    if (lo >= hi){ //went through all the indices
      return;
    }
    mergesort(data, lo, (lo + hi)/2); //split in half and mergesort left side
    mergesort(data, (lo + hi)/2+1, hi); //split in half and mergesort right side
    //merge
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

  //If you want to make it faster:
  //Have your mergesort take both the data and temp arrays. Mergesort the temp, and merge into the original!
  //e.g.
    private static void mergesort(int[]data, int[]temp, int lo, int hi){}
//Pre-allocate a temp array in your mergesort wrapper method, and copy the data into it.
}
