import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class ih_rmatrix {

	  public static void main(String[] args) throws IOException {
	        //creating variables to store in arguments, and setting them to default values
	        //inputfile and outputfile are set to default values
	        String inputfile = "ih_input.txt";
	        String outputfile= "ih_output.txt";
	        //checking if any argyment was passed
	        if (args.length != 0) {
	            //for every argument, check for -u,-p,-h
	        	for (int i = 0; i < args.length; i++)
	        		if (args[i].equals("-i")) {
	        			inputfile=args[i + 1];
	                    System.out.println("input recieved");
	        		}
	                else if (args[i].equals("o")) {
		                //-p will alow next arg to be portnum
		                outputfile =args[i + 1];
			                System.out.println("output recieved");
	                }
	        }	
	        //counting the number of lines in the file
	        BufferedReader br = new BufferedReader(new FileReader(new File("./"+inputfile)));
	        String strLine;
	        String delim = null;
	        int count = 0;
	        while ((strLine = br.readLine()) != null)   {
	          count++;
	        }
	        System.out.println(count+" is the number of lines"+delim+"l");
			
			
	        //buffered read to read in the file
	        BufferedReader in = new BufferedReader(new FileReader(new File("./"+inputfile)));
	        String st;
	        //2d array to hold the graph
	        String[][]vertices=new String[count][3];
	        //reading in the graph into a two dimensional array
	        try {
	        	int linenum=0;
				while ((st = in.readLine()) != null) {
				  System.out.println(st);
				  StringTokenizer linetoken=new StringTokenizer(st);
				  for(int i=0; i<vertices[0].length;i++)
				  {
					  vertices[linenum][i]=linetoken.nextToken();
				  }
				  linenum++;
				}
			} catch (IOException e) {
				System.out.println("FILE NOT IN CORRECT FORMAT");
				System.exit(1);
			}
	        
	       
		       

		   
	}

}
