import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TestImpl {
    public static void main(String[] args) {
        HelloImpl h1 = new HelloImpl(), h2 = h1;
        h1.message = "Salut";

    }
}

interface Hello {
    String sayHello() throws IOException;
}
class Pet{
    int age ;
    public Pet(int age) { this.age =age;}
    public int getAge (){ return this.age;}
}
class HelloImpl implements Hello {
    static String message = "Hello";

    public String sayHello() {
return null;
    }
}