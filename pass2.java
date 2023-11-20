import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class pass2 {
    public static ArrayList<String> symbols = new ArrayList<>();
    public static ArrayList<String> literals = new ArrayList<>();

    public void getTables() {
        try (BufferedReader br = new BufferedReader(new FileReader("symbols.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokenize = line.split("\\s+");
                symbols.add(tokenize[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("literals.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokenize = line.split("\\s+");
                literals.add(tokenize[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String output = "";
                line = line.replaceAll("\\(", "");  // Escape ( with \\
                line = line.replaceAll("\\)", "");  // Escape ) with \\
                String[] tokenize = line.split("\\s+");
                String[] first = tokenize[1].split(",");
                
                if (first[0].equals("IS")) {
                    String[] second = tokenize[3].split(",");
                    if(second[0].equals("S"))
                        output = tokenize[0]+" "+first[1] + " " + tokenize[2] + " " + symbols.get(Integer.parseInt(second[1]));
                    else if(second[0].equals("L"))
                        output = tokenize[0]+" "+first[1] + " " + tokenize[2] + " " + literals.get(Integer.parseInt(second[1]));
                    else
                        System.out.println("Problem in reading table values");
                    System.out.println(output);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        pass2 p2 = new pass2();
        p2.getTables();
        p2.readInput();
    }
}
