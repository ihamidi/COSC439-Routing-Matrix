import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
/**
 * Project 4 COSC 439
 * ROUTING MATRIX USING DJIKSTRAS ALGORITHM
 * @author Izhak Hamid, E01533340
 *
 */

public class ih_rmatrix {

	  public static void main(String[] args) throws IOException {
	        //creating variables to store in arguments, and setting them to default values
	        //inputfile and outputfile are set to default values
	        String inputfile = "ih_input.txt";
	        String outputfile= "ih_output.txt";
	        //checking if any argument was passed
	        if (args.length != 0) {
	            //for every argument, check for -i and -o for file names
	        	for (int i = 0; i < args.length; i++)
	        		if (args[i].equals("-i")) {
	        			inputfile=args[i + 1];
	                    System.out.println("input recieved");
	        		}
	                else if (args[i].equals("-o")) {
		                outputfile =args[i + 1];
			                System.out.println("output recieved");
	                }
	        }	
	        //counting the number of lines in the file
	        BufferedReader br = new BufferedReader(new FileReader(new File("./"+inputfile)));
	        int count = 0;
	        while ((br.readLine()) != null)   {
	          count++;
	        }
	        //arraylist to keep track of variable names
	        ArrayList<String>verName= new ArrayList<String>();
			
	        //buffered read to read in the file
	        BufferedReader in = new BufferedReader(new FileReader(new File("./"+inputfile)));
	        //filewriter to write to output file
			FileWriter fw = new FileWriter(new File("./"+outputfile));

	        
	        String st;
	        //2d array to hold the graph
	        String[][]vertices=new String[count][3];
	        //reading in the graph into a two dimensional array
	        try {
	        	int linenum=0;
	        	//reading the input file
				while ((st = in.readLine()) != null) {
					//tokenizing string to get the vertices
					//can be seperated by tabs or white space
				  StringTokenizer linetoken=new StringTokenizer(st);
				  for(int i=0; i<vertices[0].length;i++)
				  {
					  vertices[linenum][i]=linetoken.nextToken();
	
				  }
				  //adding the vertices into an arraylist for later usage if they are unique and new
				  if(!verName.contains(vertices[linenum][0]))
				  {
					  verName.add(vertices[linenum][0]);
				  }
				  if(!verName.contains(vertices[linenum][1]))
				  {
					  verName.add(vertices[linenum][1]);
				  }
				  linenum++;
				}
			} catch (IOException e) {
				System.out.println("FILE NOT IN CORRECT FORMAT");
				System.exit(1);
			}
	        //sorting vertices array
	        Collections.sort(verName);
	        //creates a matrix representation of weighted graph in vertices[][]
			int[][]matrix=createMatrix(vertices, verName.size(), verName);
			//creating a matrix to store the values returned by djikstra
			String [][]routes=new String[matrix.length][matrix.length];
			//running djikstras shortest path for each vertex
			for(int i=0;i<matrix.length;i++)
			{
				routes[i]=ShortestPath(i,matrix,verName);
			}
			
			
			//printing the routing matrix to Console, as well as system
			//printing the headers of the table
			String printString="";
			//padding each word to a length of 15 (text is left aligned)
			printString=String.format("%-" + 15 + "s", printString);
			fw.write(printString);
			System.out.print(printString);
			for(int i=0;i<verName.size();i++) {
				printString=String.format("%-" + 15 + "s", verName.get(i));  
				fw.write(printString);
				System.out.print(printString);
			}
		    fw.write("\n");
			System.out.println();
			
			//printing the actual contents of the table, such as distance and vertex
			for(int i=0;i<routes.length;i++)
			{
				printString=String.format("%-" + 15 + "s",  verName.get(i));
				fw.write(printString);
				System.out.print(printString);
				for(int j=0;j<routes.length;j++)
				{
					
					//
					//printing in transverse order because the first node of destination
					//is last node of source
					//
					
					printString=String.format("%-" + 15 + "s", routes[j][i]);  
					fw.write(printString);
					System.out.print(printString);
				}
			    fw.write("\n");
				System.out.println();
			}
			fw.close();	
	  }
	  
	  /*
		 * ShortestPath
		 * Method takes a source vertex, the adjacency matrix, and a file name to output to
		 * Runs Djikstras algorithm on the matrix and writes to file
		 *
		 * Created by Izhak Hamidi for COSC 314, Repurposed and refactored for COSC 439
		 *
		 */
		public static String[] ShortestPath(int source, int[][]matrix,ArrayList<String>verNames) throws IOException
		{
			int s=source;
			int cntrue=0;
			//three arrays to keep track of previous vertex,shortest distance, and marking the vertices
			int [] D= new int[matrix.length];
			boolean [] C= new boolean[matrix.length];
			String[] P= new String[matrix.length];
			//setting all the marked vertices to true
			//setting distance to the source's row of the matrix
			for(int i=0;i<matrix.length;i++)
			{
				//setting default values for each array to start djikstra
				C[i]=true;
				cntrue++;
				D[i]=matrix[s][i];		
				P[i]=""+s;
			}
			//finding the smallest value in D, marking it in C
			//running the algorithm for each vertex
			while(cntrue>=0) {
				//always resetting min to a large amount adn index to 0 for every vertex from source
				int min=100000;
				int index=0;
				for(int i=0;i<D.length;i++)
				{
					//if it is unmarked and value is less than min then it is marked and smallest value is chosen
					if(C[i]==true && D[i]<min)
					{
						min=D[i];
						index=i;
					}
				}
				//decrementing the true counter and marking the smallest value from C
				C[index]=false;
				cntrue--;
				//checking to see if going through any point is smaller than going to direct path
				for(int j=0;j<matrix.length;j++)
				{
					//if it is less, set the new distance to the vertex and Change value of P to vertex of the index
					if(min+matrix[index][j]<D[j]) {
						D[j]=min+matrix[index][j];
						P[j]=""+index;
					}
						
				}


				
			}

			//setting P so it shows up in the routing matrix properly
			for(int i=0;i<D.length;i++)
				if(i!=s) {
					P[i]=verNames.get(Integer.parseInt(P[i]))+","+D[i];
				}
				else {
					P[i]="-";

				}
			//returns the vertical of each column in matrix
			return P;

		}

		/**
		 * CREATES AN ADJANCENCY MATRIX REPRESENTING A GRAPH
		 * @param vertices THE GRAPH TO REPRESENT
		 * @param size SIZE OF MATRIX
		 * @param verNames VERTEX NAMES
		 * @return
		 */
	  public static int[][] createMatrix(String[][]vertices,int size, ArrayList<String>verNames)
	  {
		  int[][]matrix= new int[size][size];
		  //populating matrix with values of vertices
		  for(int i=0;i<vertices.length;i++)
		  {
			  //for indexof two vertices, assign weight to correponding
			  //row and column as well as its transpose
			  matrix[verNames.indexOf(vertices[i][0])]
					  [verNames.indexOf(vertices[i][1])]=Integer.parseInt(vertices[i][2]);
			  matrix[verNames.indexOf(vertices[i][1])]
					  [verNames.indexOf(vertices[i][0])]=Integer.parseInt(vertices[i][2]);
		  }
		  
		  //setting all unreachable paths to a very big number
			for(int i = 0; i < size; ++i)
			{
			    for(int j = 0; j < size; ++j)
			    {
				       //large number to represent no direct path
				       if((i!=j)&&matrix[i][j]==0)
			           {
		            	matrix[i][j]=10000000;
		               }
			    }
				    
			}
		return matrix;
	  }

}
