import java.util.Arrays;
import java.util.Scanner;

public class Test {

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      while (true) {
         String command = in.nextLine();
         if (command.equals("QUIT")) {
            break;
         }
         String[] words = command.split(" +");
         String option = words[0];
         String arg[] = Arrays.copyOfRange(words, 1, words.length);
         if (option.equals("addUser")) {
            addUser(arg);
         }
      }
      in.close();
      System.out.println("----- Good Bye! -----");
      return;
   }

   public static int addUser(String str[]) {
      if (str.length != 3) {
         System.out.println("Arguments illegal");
         return 1;
      }
      String name = str[0], gender = str[1], aadhaar = str[2];
      if (!User.isName(name)) {
         System.out.println("Name illegal");
         return 1;
      }
      if (!User.isGender(gender)) {
         System.out.println("Sex illegal");
         return 1;
      }
      if (!User.isAadhaar(aadhaar)) {
         System.out.println("Aadhaar number illegal");
         return 1;
      }
      if (!User.registerAadhaar(aadhaar)) {
         System.out.println("Aadhaar number exist");
         return 1;
      }
      User user = new User();
      user.setName(name);
      user.setGender(gender);
      user.setAadhaar(aadhaar);
      user.toString();
      return 0;
   }
}