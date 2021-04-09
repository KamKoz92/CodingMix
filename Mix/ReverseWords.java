import java.util.ArrayList;
import java.util.List;

public class ReverseWords {
    public static void main(String[] args) {
        String testString = " the sky is blue ";
        List<String> words = new ArrayList<String>();
        System.out.println(testString);
        int sIndex = 0;
        int eIndex = 0;

        for(int i = 0; i < testString.length(); i++) {
            char temp = testString.charAt(i);
            if(temp == ' ') {
                eIndex = i;
                words.add(testString.substring(sIndex, eIndex));
                sIndex = i+1;
            }
        }
        words.add(testString.substring(sIndex, testString.length()));

        for(int i = words.size()-1; i >= 0 ; i--) {
            System.out.print(words.get(i));
            System.out.print(' ');
        }
    }
}
