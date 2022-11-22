import java.io.*;
import java.util.*;

public class TrafficAnalysis {





    String[] filename = ["traceA.csv","traceB.csv"]


	public static void main(String argv[]) throws Exception {

        for(String file : filename) {
            //Read the file
            BufferedReader bf = new BufferedReader(new FileReader(new File(file)));
            //Write to destination file
            PrintWriter pr = new PrintWriter("Respostas_GrupoXX.xls","utf-8");
            //Helper 
            String line;
            //Counter for Q1
            int ipv4Counter = 0,
                ipv6Counter = 0;

            //Ignore csv header
            bf.readLine();
            while((line = bf.readLine()) != null) {
                //Tokenize string 
                StringTokenizer tokens      = new StringTokenizer(line, ",");
                //Divide values beetween vars
                int             numOp       = Integer.parseInt(tokens.nextToken().replace("\"",""));
                float           relTime     = Float.parseFloat(tokens.nextToken().replace("\"",""));
                String          srcIP       = tokens.nextToken().replace("\"",""),
                                destIP      = tokens.nextToken().replace("\"",""),
                                srcPort     = tokens.nextToken().replace("\"",""),
                                destPort    = tokens.nextToken().replace("\"",""),
                                protocol    = tokens.nextToken().replace("\"","");
                long            length      = Integer.parseInt(tokens.nextToken().replace("\"",""));
                String          flags       = tokens.nextToken().replace("\"","");

                //Counters to anwser the questions

                //Q1 - What is the number of packets that have an IPv4 sender and receiver? and those that have sender and receiver IPv6? How many have sender and receiver hostnames?
                if(isIPv4(srcIP) && isIPv4(destIP)) 
                    ipv4Counter++;
                if(isIPv6(srcIP) && isIPv6(destIP)) 
                    ipv6Counter++;

            }




        }
	}// FIM DA MAIN

	private static boolean isIPv4(String address) {
		
	}

	private static boolean isIPv6(String address) {
		
	}
}// FIM CLASSE