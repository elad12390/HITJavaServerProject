package main.java.com.hit.string.matching;
import main.java.com.hit.string.matching.abstracts.AbstractMatcher;

public class KmpSearch extends AbstractMatcher{
    private int [] lpsArray(String pat, int [] lps){
        int patLen=pat.length();
        int lenLongestPs=0;
        int i=1;
        lps[0]=0;
        while(i < patLen) {
            if(pat.charAt(i) == pat.charAt(patLen)){
                patLen++;
                lps[i]=patLen;
                i++;
            }
            else {
                if(patLen!=0) {
                  patLen=lps[patLen-1];
                }
                lps[i]=patLen;
                i++;
            }
        }
        return lps;
    }
    @Override
    public Integer match(String searchWord, String input) {
        Integer edgeCases = super.match(searchWord, input);
        if (edgeCases != null) return edgeCases;
        int S=searchWord.length();
        int N=input.length();
        int [] lps = new int[S];
        lpsArray(searchWord,lps);
        int j,i=0;
        while(i< N){
            if (searchWord.charAt(j) == input.charAt(i)) {
                j++;
                i++;
            }
            if (j==S){
                return (i-j);
            }
            else if(j!=0){
                j=lps[j-1];
            }
        }



        return -1;
    }
}
