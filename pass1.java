import java.io.*;
import java.util.*;

class Tuple {
    String mnemonic, mclass, opcode;
    int length;

    Tuple(String s1, String s2, String s3, String s4) {
        mnemonic = s1;
        mclass = s2;
        opcode = s3;
        length = Integer.parseInt(s4.trim());
    }
}

class pass1 {
    public static HashMap<String,Tuple> map = new HashMap<>();
    public static HashMap<String, Integer> registers = new HashMap<String, Integer>();
    public static Map<String,String> literals = new LinkedHashMap<String,String>();
    public static Map<String,String> symbols = new LinkedHashMap<String,String>();

    pass1()
    {
        registers.put("AREG", 1);
        registers.put("BREG", 2);
        registers.put("CREG", 3);
        registers.put("DREG", 4);
    }

    public void mapper()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("mot.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                map.put(tokens[0], new Tuple(tokens[0], tokens[1], tokens[2], tokens[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createOutputFile()
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", false))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToOutput(String content)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            writer.write(content+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInput()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt")))
        {
            String line;
            int counter = 0, symbol_counter = 0, literal_counter = 0;
            String output = "";
            while((line = br.readLine()) != null)
            {
                String[] tokens = line.split("\\s+");
                if(map.containsKey(tokens[1]))
                {
                    Tuple tuple = map.get(tokens[1]);
                    String constant = "";

                    if(tuple.mclass.equals("AD"))
                    {
                        try{
                        if(tokens[2]!=null)
                        {
                            counter = Integer.parseInt(tokens[2]);
                            constant = " (C,"+counter+")";
                        }
                        if(tuple.mnemonic.equals("05"))
                        {
                            for (Map.Entry<String, String> entry : literals.entrySet()) {
                                String key = entry.getKey();
                                String value = entry.getValue();
                                System.out.println("Key: " + key + ", Value: " + value);
                                if(value.equals(""))
                                {
                                    output = counter+" "+key;
                                    writeToOutput(output);
                                    literals.put(output, constant);
                                }

                                counter++;
                            }
                        }
                    }catch (Exception ex)
                    {}
                        output = counter+"  ("+tuple.mclass+","+tuple.opcode+")"+constant;
                        writeToOutput(output);
                    }

                    if(tuple.mclass.equals("IS"))
                    {
                        String[] register = tokens[2].split(",");
                        int reg=0;
                        try{
                            reg = registers.get(register[0]);
                        }catch(Exception e)
                        {

                        }
                        if(register[1].contains("="))
                        {
                            output = counter+"  ("+tuple.mclass+","+tuple.opcode+") "+reg+" (L,"+literal_counter+")";
                            literals.put(register[1],""+counter);
                            writeToOutput(output);
                            literal_counter++;
                        }
                        else
                        {
                            output = counter+"  ("+tuple.mclass+","+tuple.opcode+") "+reg+" (S,"+symbol_counter+")";
                            symbols.put(register[1],""+counter);
                            writeToOutput(output);
                            symbol_counter++;
                        }
                    }

                    if(tuple.mclass.equals("DL"))
                    {
                        output = counter+"  ("+tuple.mclass+","+tuple.opcode+") ("+tokens[2]+")";
                        if (symbols.containsKey(tokens[0])) {
                            String prevValue = symbols.get(tokens[0]);
                            String newValue = prevValue + " " + tokens[2];
                            symbols.put(tokens[0], newValue);
                            writeToOutput(output);
                        } else {
                            System.out.println("Symbol not found '"+tokens[0]+"'");
                            counter--;
                        }
                    }
                }
                else
                {
                    System.out.println("Unknown mnemonic '"+tokens[1]+"'");
                }
                counter++;

            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeHashMapToFile(Map<String, String> hashMap, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) 
    {
        pass1 p = new pass1();
        p.mapper();
        p.createOutputFile();
        p.readInput();
        p.writeHashMapToFile(symbols,"symbols.txt");
        p.writeHashMapToFile(literals, "literals.txt");
    }


}
