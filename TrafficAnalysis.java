import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TrafficAnalysis {





    static String[] filename = {"traceA.csv" , "traceB.csv"};


	public static void main(String argv[]) throws Exception {

        for(String file : filename) {
            //Read the file
            BufferedReader bf = new BufferedReader(new FileReader(new File(file)));
            //Write to destination file
            PrintWriter pr = new PrintWriter("Respostas_GrupoXX.xls","utf-8");
            //Helper 
            String line;
			//unique srcPorts
			ArrayList<String> uniqueSrcPorts = new ArrayList<>();
			ArrayList<String> uniqueKnownSrcPorts = new ArrayList<>();
			//Conections attempts
			ArrayList<String> srcIPFlagSyn = new ArrayList<>();
            //Counter for Q1
            int ipv4Counter = 0,
                ipv6Counter = 0,
				counter = 0,
				tpcConnections = 0;

			float totalTime = 0;

            //Ignore csv header
            bf.readLine();
            while((line = bf.readLine()) != null) {
				counter++;
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

				if(!uniqueSrcPorts.contains(srcPort) && protocol.equals("HTTP") || protocol.equals("FTP") || protocol.equals("DNS") || protocol.equals("UDP"))
						uniqueKnownSrcPorts.add(srcPort);
				if(!uniqueSrcPorts.contains(srcPort))
					uniqueSrcPorts.add(srcPort);
				if(flags.equals("SYN"))
					srcIPFlagSyn.add(srcIP);
				if(protocol.equals("TCP"))
					tpcConnections++;
				
				

				totalTime += relTime;

            }

			String mostRepeatedIPOnConexions = 	srcIPFlagSyn.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting())).entrySet().stream().max(Comparator.comparing(Entry::getValue)).get().getKey();

			pr.println("Q1 - IPV4: " + ipv4Counter + ", IPV6: " + ipv6Counter);
			pr.println("Q2 - Time: " +totalTime + "Numero de pacotes: " +counter);
			pr.println("Q3 - Source ports unicas: " + uniqueSrcPorts.toString() + " Correspondentes a servicos de rede conhecidos: " + uniqueKnownSrcPorts );
			pr.println("Q6 - Tentativas de conexão: " + srcIPFlagSyn.size() + " IP que chamou mais: " + mostRepeatedIPOnConexions );
			pr.println("Q7 - TCP Connections: " +tpcConnections);





        }
	}




	private static boolean isIPv4(String address) {
        String[] addressArr = address.split("\\.");
		// ver se sao 4 numeros
		if (addressArr.length != 4) {
			return false;
		}
		for (int i = 0; i < addressArr.length; i++) {
			for (int j = 0; j < addressArr[i].length(); j++) {
				if (!Character.isDigit(addressArr[i].charAt(j))) {
					return false;
				}
			}
			// ver se estao entre 0 e 255
			int numberToCheck = Integer.parseInt(addressArr[i]);
			if (!(numberToCheck < 256 && numberToCheck > -1)) {
				return false;
			}
		}
		return true;
		
	}

	private static boolean isIPv6(String address) {
        // address = "0000:0000:ffff:4444:3333:0000::";
		String[] addressArr = address.split("\\:");
		for (int i = 0; i < addressArr.length; i++) {
			for (int j = 0; j < addressArr[i].length(); j++) {
				if (Character.digit(addressArr[i].charAt(j), 16) == -1) {
					return false;
				}
			}
			// ver se estao entre 0 e 255
			int numberToCheck = Integer.parseInt(addressArr[i], 16);
			if (!(numberToCheck < Math.pow(16, 4) && numberToCheck > -1)) {
				return false;
			}
		}
		return true;
		
	}
}