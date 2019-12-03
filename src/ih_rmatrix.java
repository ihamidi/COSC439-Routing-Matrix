import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
			
	        //arraylist to keep track of varibale names
	        ArrayList<String>verName= new ArrayList<String>();
			
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
	        System.out.println(verName.size());
	        Collections.sort(verName);
			int[][]matrix=createMatrix(vertices, verName.size(), verName);
			String [][]routes=new String[matrix.length][matrix.length];
			for(int i=0;i<matrix.length;i++)
			{
				routes[i]=ShortestPath(i,matrix,verName);
			}
			for(int i=0;i<routes.length;i++)
			{
				for(int j=0;j<routes.length;j++)
				{
					System.out.print(routes[j][i]+" ");
				}
				System.out.println();
			}
			
			
	  }
	  
	  /*
		 * ShortestPath
		 * Method takes a source vertex, the madjacency matrix, and a file name to output to
		 * Runs Djikstras algorithm on the matrix and writes to file
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
				C[i]=true;
				cntrue++;
				D[i]=matrix[s][i];		
				P[i]=""+s;
			}
			//finding the smallest value in D, marking it in C
			while(cntrue>=0) {
				int min=100000;
				int index=0;
				for(int i=0;i<D.length;i++)
				{
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
//			for(int i=0;i<D.length;i++)
//				if(i!=s)
//					P[i]=P[i]+"-"+i;
			
			
			for(int i=0;i<D.length;i++)
				if(i!=s) {
					System.out.println(verNames.get(Integer.parseInt(P[i]))+","+D[i]);
					P[i]=verNames.get(Integer.parseInt(P[i]))+","+D[i];
				}
				else {
					System.out.println("-");
					P[i]="-";

				}
			//returns the vertical of each column in matrix
			return P;
//			//points a file writer to output file with same name +"out"
//			FileWriter fw = new FileWriter(new File("src/"+name+"out"));
//			fw.write("Shortest Path from "+source+":\n");
//			//writing each matrix value to the file
//			for(int i = 0; i < D.length; ++i)
//			{
//				if(!(D[i]==10000000))
//					fw.write("["+D[i]+"]"+" "+P[i]);
//				else
//					fw.write("No path to Vertex "+(i+1)+" from source");
//			    fw.write("\n");
//			}
//			fw.close();
		}

	
	  public static int[][] createMatrix(String[][]vertices,int size, ArrayList<String>verNames)
	  {
		  int[][]matrix= new int[size][size];
		  for(int i=0;i<vertices.length;i++)
		  {
			  matrix[verNames.indexOf(vertices[i][0])]
					  [verNames.indexOf(vertices[i][1])]=Integer.parseInt(vertices[i][2]);
			  matrix[verNames.indexOf(vertices[i][1])]
					  [verNames.indexOf(vertices[i][0])]=Integer.parseInt(vertices[i][2]);
		  }
		  
		  
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
		  for(int i=0;i<matrix.length;i++) {
			  for(int j=0;j<matrix.length;j++)
				  System.out.print(matrix[i][j]+" ");
			  System.out.println();
		  }
		return matrix;
	  }

}
