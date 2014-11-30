


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class cv {

	public static void main(String[] args) throws IOException {

		 String fileName = "./assets/lena.im";
		 int headerLength = 172;
		 int imageWidth = 512;
		 int imageHeight = 512;
		 int threshold = 128;

		 ArrayList<Integer> bytes = GetByteData(fileName);
		 ArrayList<Integer> binerized = Binarize(bytes,threshold,headerLength,imageWidth,imageHeight);
		 
		 ArrayList<Integer> origin = binerized;
		 ArrayList<Integer> result;
		 
		 result = thinning(origin,headerLength,imageWidth,imageHeight);
		 while(!identical(result,origin))
		 {
			 origin = copy(result);
			 result = thinning(origin,headerLength,imageWidth,imageHeight);
		 }
		 
		 WriteOut(result,"./assets/result.im");
	}
	
	public static ArrayList<Integer> thinning (ArrayList<Integer> origin,int headerLength, int width, int height)
	{
		ArrayList<Integer> results = copy(origin);
		for(int y = 0 ; y < height ; y ++)
		{
			for(int x = 0 ; x < width ; x++)
			{
				//A
				int WhiteCount = 0;
				
				if(GetValue(origin,x,y-1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y-1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y-1,headerLength,width,height) == 255)WhiteCount++;
				
				if(WhiteCount <2 || WhiteCount >6) continue;
				
				//B
				int ChangeCount = 0;
				if(GetValue(origin,x,y-1,headerLength,width,height) == 0 && GetValue(origin,x+1,y-1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y-1,headerLength,width,height) == 0 && GetValue(origin,x+1,y,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y,headerLength,width,height) == 0 && GetValue(origin,x+1,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y+1,headerLength,width,height) == 0 && GetValue(origin,x,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x,y+1,headerLength,width,height) == 0 && GetValue(origin,x-1,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y+1,headerLength,width,height) == 0 && GetValue(origin,x-1,y,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y,headerLength,width,height) == 0 && GetValue(origin,x-1,y-1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y-1,headerLength,width,height) == 0 && GetValue(origin,x,y-1,headerLength,width,height) == 255)ChangeCount ++;
				
				if(ChangeCount!=1) continue;
				
				//C
				if(GetValue(origin,x+1,y,headerLength,width,height)==255&&
				   GetValue(origin,x,y-1,headerLength,width,height)==255&&
				   GetValue(origin,x,y+1,headerLength,width,height)==255)
				   {		
						continue;
				   }
				
				//D
				if(GetValue(origin,x+1,y,headerLength,width,height)==255&&
						   GetValue(origin,x,y+1,headerLength,width,height)==255&&
						   GetValue(origin,x-1,y,headerLength,width,height)==255)
						   {		
								continue;
						   }
				
				results.set(headerLength + y * width + x, 0 );
			}
		}
		origin = copy(results);
		
		for(int y = 0 ; y < height ; y ++)
		{
			for(int x = 0 ; x < width ; x++)
			{
				//A
				int WhiteCount = 0;
				
				if(GetValue(origin,x,y-1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y-1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x+1,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y+1,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y,headerLength,width,height) == 255)WhiteCount++;
				if(GetValue(origin,x-1,y-1,headerLength,width,height) == 255)WhiteCount++;
				
				if(WhiteCount <2 || WhiteCount >6) continue;
				
				//B
				int ChangeCount = 0;
				if(GetValue(origin,x,y-1,headerLength,width,height) == 0 && GetValue(origin,x+1,y-1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y-1,headerLength,width,height) == 0 && GetValue(origin,x+1,y,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y,headerLength,width,height) == 0 && GetValue(origin,x+1,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x+1,y+1,headerLength,width,height) == 0 && GetValue(origin,x,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x,y+1,headerLength,width,height) == 0 && GetValue(origin,x-1,y+1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y+1,headerLength,width,height) == 0 && GetValue(origin,x-1,y,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y,headerLength,width,height) == 0 && GetValue(origin,x-1,y-1,headerLength,width,height) == 255)ChangeCount ++;
				if(GetValue(origin,x-1,y-1,headerLength,width,height) == 0 && GetValue(origin,x,y-1,headerLength,width,height) == 255)ChangeCount ++;
				
				if(ChangeCount!=1) continue;
				
				//C
				if(GetValue(origin,x-1,y,headerLength,width,height)==255&&
				   GetValue(origin,x,y-1,headerLength,width,height)==255&&
				   GetValue(origin,x+1,y,headerLength,width,height)==255)
				   {		
						continue;
				   }
				
				//D
				if(GetValue(origin,x-1,y,headerLength,width,height)==255&&
						   GetValue(origin,x,y+1,headerLength,width,height)==255&&
						   GetValue(origin,x,y-1,headerLength,width,height)==255)
						   {		
								continue;
						   }
				
				results.set(headerLength + y * width + x, 0 );
						   
			}
		}
		return results;
		
	}
	
	public static int GetValue(ArrayList<Integer> origin,int x,int y,int headerLength, int width, int height)
	{
		if(x<0||x>=width)return 0;
		if(y<0||y>=height)return 0;
		
		return origin.get(headerLength + y*width + x); 
		
	}
	
	public static boolean identical(ArrayList<Integer> origin,ArrayList<Integer> target)
	{
		if(origin==null)return false;
		if(target==null)return false;
		
		
		boolean identical = true;
		
		for(int i = 0 ; i < origin.size() ; i++)
		{
			if(origin.get(i) != target.get(i))identical = false;
		}
		
		return identical;
	}
	
	
	public static ArrayList<Integer> copy(ArrayList<Integer> origin)
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		for(int i = 0 ; i < origin.size() ; i++)
		{
			results.add(origin.get(i));
		}
		
		return results;
	}

	public static ArrayList<Integer> InitWhite(ArrayList<Integer> origin,int headerLength, int width, int height)
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		for(int i = 0 ; i < headerLength ; i++)
		{
			results.add(origin.get(i));
		}
		
		for(int i = 0 ; i < width ; i ++)
		{
			for(int j = 0 ; j<height ; j++)
			{
				results.add(0);
			}
		}
		
		return results;
	}
	
	public static ArrayList<Integer> Binarize(ArrayList<Integer> origin,int threshold,int headerLength, int width, int height)
	{
		ArrayList<Integer> results = InitWhite(origin,headerLength,width,height);
		for(int i = 0 ; i < width ; i ++)
		{
			for(int j = 0 ; j<height ; j++)
			{
				int index = headerLength+width*i+j;
				int originData = origin.get(index);
				
				if(originData < threshold)originData = 0;
				else originData = 255;
				results.set(index, originData);
			}
		}
		return results;
		
	}
	
	public static ArrayList<Integer> GetByteData(String fileName) throws IOException
	{
		 File f = new File(fileName);
		 ArrayList<Integer> bytes = new ArrayList<Integer>();
		
		
		 FileInputStream in = null;	
		 in = new FileInputStream(fileName);
		 
		 int c;
		 while ((c = in.read()) != -1) {
			 bytes.add(c);
        }
		 
		 return bytes;
	}
	
	public static void WriteOut(char[][] data,String name) throws IOException
	{
		PrintWriter out = new PrintWriter(name);
		
		for(int y = 0 ; y < data.length;y++)
		{
			for(int x = 0; x<data.length;x++)
			{
				out.print(data[y][x]);
			}
			
			out.println();
		}
		out.close();
		
	}
	
	public static void WriteOut(ArrayList<Integer> data,String name) throws IOException
	{
		File f = new File(name);
		if(f.exists())f.delete();
		FileOutputStream out = null;
		out = new FileOutputStream(name);
		
		for(int i : data)
		{
			out.write((byte)i);
		}
		
		out.flush();
		out.close();
	}
	

}
