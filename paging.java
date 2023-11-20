import java.util.*;
import java.io.*;

class paging {
    ArrayList<Integer> pages = new ArrayList<>();

    int size = 0;
    int n = 0;
    int page = 0;

    public void getInput() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the size : ");
            size = Integer.parseInt(br.readLine());

            System.out.println("Enter the number of pages : ");
            n = Integer.parseInt(br.readLine());

            System.out.println("Enter the pages : ");
            for (int i = 0; i < n; i++) {
                pages.add(Integer.parseInt(br.readLine()));
            }

            lru();

        } catch (Exception e) {
        }
    }

    public void fcfs() {
        ArrayList<Integer> memory = new ArrayList<>();
        int pageFaults = 0, pageHits = 0;
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (memory.contains(pages.get(i))) {
                pageHits++;
            } else if (memory.size() < size) {
                memory.add(pages.get(i));
                pageFaults++;
            } else {
                memory.set(k, pages.get(i));
                pageFaults++;
                k++;
            }
            k = k % (size);

            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.get(j) + " ");
            }
            System.out.println();
        }

        System.out.println("Page Faults : " + pageFaults);
        System.out.println("Page Hits : " + pageHits);
    }

    public void optimal() {
        ArrayList<Integer> memory = new ArrayList<>();
        int pageFaults = 0, pageHits = 0;
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (memory.contains(pages.get(i))) {
                pageHits++;
            } else if (memory.size() < size) {
                memory.add(pages.get(i));
                pageFaults++;
            } else {
                int max = 0, x;
                for (int j = 0; j < memory.size(); j++) {
                    for (x = i+1; x < pages.size(); x++) {
                        if (memory.get(j) == pages.get(x)) {
                            break;

                        } else if (pages.size() == (x + 1)) {
                            max = pages.get(x);
                            k = j;
                        }
                    }
                    if (x > max) {
                        max = x;
                        k = j;
                    }
                }
                memory.set(k, pages.get(i));
                pageFaults++;
            }

            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.get(j) + " ");
            }
            System.out.println();
        }

        System.out.println("Page Faults : " + pageFaults);
        System.out.println("Page Hits : " + pageHits);
    }

    void lru()
    {
        ArrayList<Integer> memory = new ArrayList<>();
        HashMap<Integer,Integer> map = new LinkedHashMap<>();
        int pageFaults = 0, pageHits = 0;
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (memory.contains(pages.get(i))) {
                pageHits++;
            } else if (memory.size() < size) {
                memory.add(pages.get(i));
                pageFaults++;
            } else {
                map.clear();
                for(int j=i;j>0;j--)
                {
                    if(memory.contains(pages.get(j)))
                    {
                        map.put(pages.get(j), j);
                        System.out.println(pages.get(j)+" size "+map.size());
                        if(map.size()==size){
                            k = memory.indexOf(pages.get(j));
                            break;
                        }
                    }
                }
                memory.set(k, pages.get(i));
                pageFaults++;
            }
            for (int j = 0; j < memory.size(); j++) {
                System.out.print(memory.get(j) + " ");
            }
            System.out.println();
        }

        System.out.println("Page Faults : " + pageFaults);
        System.out.println("Page Hits : " + pageHits);
    }

    public static void main(String[] args) {
        paging f = new paging();
        f.getInput();
    }
}