import java.util.ArrayList;

public class Recursion {
    public void printAllSubSequence(){
        System.out.println("1. Print All Sub Sequence ");
        String str= "ahbgdc";
        System.out.println("Qns :  ahbgdc \n:Ans  :");
        ArrayList<Character> ds  = new ArrayList<>();
        recToPrintSubSequence(0, str, ds);
    }

    private void recToPrintSubSequence(int i, String str, ArrayList<Character> ds) {
        if(i == str.length()){
            System.out.println(ds);
            return;
        }
        recToPrintSubSequence(i+1, str, ds);
        ds.add(str.charAt(i));
        recToPrintSubSequence(i+1, str, ds);
        ds.remove(ds.size()-1);

    }



}
