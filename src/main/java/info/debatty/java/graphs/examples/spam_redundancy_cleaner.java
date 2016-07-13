package info.debatty.java.graphs.examples;


import info.debatty.java.graphs.*;
import info.debatty.java.graphs.build.Brute;
import info.debatty.java.stringsimilarity.JaroWinkler;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ExecutionException;

;


/**

 *
 *
 *
 *
 */
public class spam_redundancy_cleaner {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String path_in = "spam-subject-200K.txt";
        String path_out = "spam-subject-200K_unique.txt";
        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        try {
            File file = new File(path_in);
            FileReader fr = new FileReader(file);
            BufferedReader dati = new BufferedReader(fr);
            String nextLine;
            // Leggo una riga per volta e memorizzo il suo contenuto in un oggetto riga
            int input_size = 0;
            while ((nextLine = dati.readLine()) != null) {
                if (!nextLine.isEmpty()) {
                ht.put(nextLine, 1);
                input_size++; }
            }
            System.out.println(input_size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Set<String> key_list = ht.keySet();
            System.out.println(key_list.size());
            File file_w = new File(path_out);
            FileWriter fw = new FileWriter(file_w);
            BufferedWriter data_w = new BufferedWriter(fw);
            int i=0;
            int data_size = key_list.size();
            for (String s : key_list) {


                data_w.write(s);
                if (i!=data_size-1) {data_w.write("\n");}
                i++;
            }
            data_w.flush();
            data_w.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


