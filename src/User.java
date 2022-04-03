import java.util.ArrayList;

public class User {
   static ArrayList<String> aadhaars = new ArrayList<String>();
   String name;
   String gender;
   String aadhaar;

   public String getName() {
      return name;
   }

   public String getGender() {
      return gender;
   }

   public String getAadhaar() {
      return aadhaar;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setGender(String gender) {
      this.gender = gender;
   }

   public void setAadhaar(String aadhaar) {
      this.aadhaar = aadhaar;
   }

   public static boolean isAadhaar(String str) {
      // 先保证12位，全数字
      if (!str.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
         return false;
      }
      int num1 = Integer.parseInt(str.substring(0, 4));
      int num2 = Integer.parseInt(str.substring(4, 8));
      int num3 = Integer.parseInt(str.substring(8, 12));
      if (num1 < 1 || num1 > 1237)
         return false;
      if (num2 < 20 || num2 > 460)
         return false;
      if (num3 / 10 > 100 || num3 % 10 > 2)
         return false;
      return true;
   }

   public static boolean isGender(String str) {
      if (str.matches("[MFO]")) {
         return true;
      }
      return false;
   }

   public static boolean isName(String str) {
      if (str.matches("[a-zA-Z_]+")) {
         return true;
      }
      return false;
   }

   public static boolean registerAadhaar(String aadhaar) {
      for (String i : aadhaars) {
         if (i.equals(aadhaar))
            return false;
      }
      aadhaars.add(aadhaar);
      return true;
   }

   @Override
   public String toString() {
      System.out.println("Name:" + name);
      System.out.println("Sex:" + gender);
      System.out.println("Aadhaar:" + aadhaar);
      return "Name:" + name + "\n" + "Sex:" + gender + "\n" + "Aadhaar:" + aadhaar;
   }
}
