import java.io.*;
import java.util.*;

class arguments
{
    String name;
    String value;
    arguments(String a,String b)
    {
        name = a;
        value = b;
    }
}

class pass1 {
    HashMap<String, Integer> MNT = new LinkedHashMap<String, Integer>();
    ArrayList<String> MDT = new ArrayList<String>();
    ArrayList<arguments> ALA = new ArrayList<arguments>();
    ArrayList<String> Output = new ArrayList<String>();
    int mdt_counter = 0;

    public void readInput() {
        try (BufferedReader br = new BufferedReader(new FileReader("macroInput.txt"))) {
            String line;
            boolean macro = false;
            while ((line = br.readLine()) != null) {
                String[] tokenize = line.split("\\s+");
                if (tokenize[0].equals("MACRO")) {
                    macro = true;
                    MNT.put(tokenize[1], ++mdt_counter);
                    
                    String[] arguments = tokenize[2].split(",");
                    ALA.add(new arguments(tokenize[1],""));

                    for (int i = 0; i < arguments.length; i++) {

                        if (arguments[i].contains("=")) {
                            String[] values = arguments[i].split("=");
                            System.out.println(values[0] + " " + values[1]);
                            ALA.add(new arguments(values[0], values[1]));
                        } else {
                            System.out.println(arguments[i]);
                            ALA.add(new arguments(arguments[i], ""));
                        }
                    }
                    ALA.add(new arguments("END", ""));
                    
                }
                if (tokenize[0].equals("MEND")) {
                    macro = false;
                    MDT.add(mdt_counter + " " + line);
                }


                if (macro) {
                    
                    MDT.add(mdt_counter + " " + line.replaceAll("MACRO ", ""));
                    mdt_counter++;
                }
                else
                {
                    if(!line.contains("MEND"))
                        Output.add(line);
                    
                }

            }
        } catch (Exception e) {
        }
    }

    public void putALA() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter("ALA.txt"))) {
            for (int i=0;i<ALA.size();i++) {
                arguments a = ALA.get(i);
                br.write(a.name + " " + a.value + "\n");
            }
        } catch (Exception e) {
        }
        try (BufferedWriter br = new BufferedWriter(new FileWriter("MNT.txt"))) {
            for (Map.Entry<String, Integer> entry : MNT.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();

                br.write(key + " " + value+"\n");

            }
        } catch (Exception e) {
        }

        try (BufferedWriter br = new BufferedWriter(new FileWriter("MDT.txt"))) {
            for (int i=0;i<MDT.size();i++) {
                br.write(MDT.get(i) + "\n");
            }
        } catch (Exception e) {
        }

        try (BufferedWriter br = new BufferedWriter(new FileWriter("macroOutput.txt"))) {
            for (int i=0;i<Output.size();i++) {
                br.write(Output.get(i) + "\n");
            }
        } catch (Exception e) {
        }
        
    }

    public static void main(String[] args) {
        pass1 p = new pass1();
        p.readInput();
        p.putALA();
    }
}