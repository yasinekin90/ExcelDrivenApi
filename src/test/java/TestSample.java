import java.io.IOException;
import java.util.ArrayList;

public class TestSample {
    public static void main(String[] args) {
        try {

            ArrayList add_profile = ReusableMethods.getData("Add Profile","testdata");

            System.out.println(add_profile.get(0));
            System.out.println(add_profile.get(1));
            System.out.println(add_profile.get(2));
            System.out.println(add_profile.get(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
