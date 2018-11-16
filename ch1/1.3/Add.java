// https://www.tutorialspoint.com/jdb/jdb_quick_guide.htm
// http://pages.cs.wisc.edu/~horwitz/jdb/jdb.html
// help
// stop in <Class.Method>
// stop at <Class:Line>
// clear <Class.Method>
// clear <Class:Line>
// run
// list
// list <Line>
// list <Method>
// step
// step up
// next
// print VAR
// dump ?
// locals
// cont
// exit or quit
public class Add {
   public int addition(int x, int y)
   {
      int z = x + y;
      return z;
   }

   public static void main(String[] args) {
      int a = 5, b = 6;
      Add ob = new Add();
      int c = ob.addition(a, b);
      System.out.println("Add: " + c);
   }
}
