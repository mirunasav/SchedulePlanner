import java.util.HashMap;
import java.util.Map;

public class sth {
    public static void main(String[] args) {
        String[] strings = new String[]{"a","ab","abc","acbd","abc","a"};
        Map<String, Integer> map = new HashMap<>();
        for(String string : strings){
            if(map.containsKey(string)){
                var value = map.get(string);
                map.replace(string, ++value);
                continue;
            }
            map.put(string,1);
        }

        for(var Key : map.keySet()){
            System.out.println("Key: " + Key+ ", Value : " + map.get(Key)) ;
        }
    }
}
