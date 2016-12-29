import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class Driver {
	//calculates and returns cosine similarity between 2 users in consideration
	private static double cosineSimilarity(int[][] userXProduct,int user1,int user2)
	{
		int sumUser1User1=0,sumUser2User2=0,sumUser1User2=0;
		for(int prodNo=0;prodNo<1682;prodNo++)
		{
			int user1Prod=userXProduct[user1][prodNo];
			int user2Prod=userXProduct[user2][prodNo];
			sumUser1User1+=user1Prod*user1Prod;
			sumUser2User2+=user2Prod*user2Prod;
			sumUser1User2+=user1Prod*user2Prod;
		}
		int denominator=sumUser1User1*sumUser2User2;
		if(denominator>0)
			return (sumUser1User2/Math.sqrt(denominator));
		else
			return 0.0;
	}
	
	public static void main(String[] args) {

		BufferedReader br=null;
		BufferedWriter bw=null;
		int user=943,product=1682;
		int[][] userXProduct=new int[user][product];	//utility matrix
		double[][] userXUser=new double[user][user];	//matrix to store similarity amon users 
		int[] userMeanRating=new int[user];				//mean/average user rating
		try {
			br=new BufferedReader(new FileReader("train_all_txt.txt"));

			String entry;
			//fill the utility matrix
			while((entry = br.readLine()) != null)
			{
				String[] line=entry.split("[ |\t]+");
				int userID,itemID,rating;
				userID=Integer.parseInt(line[0]);
				itemID=Integer.parseInt(line[1]);
				rating=Integer.parseInt(line[2]);
				
				userXProduct[userID-1][itemID-1]=rating;
			}
			br.close();
			
			//fill average user rating
			for(int userNo=0;userNo<user;userNo++)
			{
				
				int sumOfRated=0,noOfRated=0;
				
				for(int prod=0;prod<product;prod++)
				{
					if(userXProduct[userNo][prod]>0)
					{
						sumOfRated+=userXProduct[userNo][prod];
						noOfRated++;
					}
				}
				if(noOfRated>0)
					userMeanRating[userNo]=sumOfRated/noOfRated;
			}
			
			//calculate user similarity
			for(int user1=0;user1<user;user1++)
				for(int user2=0;user2<user;user2++)
					if(user1<user2)
						userXUser[user1][user2]=cosineSimilarity(userXProduct,user1,user2);
					else
						userXUser[user1][user2]=userXUser[user2][user1];

					
			for(int userNo=0;userNo<user;userNo++)
			{
				for(int prodNo=0;prodNo<product;prodNo++)
				{
					//perform prediction for all user and products that are not rated
					if(userXProduct[userNo][prodNo]==0)
					{
						double num=0.0,den=0.0,prediction=0.0;
						for(int i=0;i<user;i++)
						{
							if(userXProduct[i][prodNo]!=0 && userNo!=i)
							{
								num+=(userXProduct[i][prodNo]-userMeanRating[i])*userXUser[userNo][i];
								den+=userXUser[userNo][i];
							}
						}
						if(den!=0)
							prediction=(userMeanRating[userNo]+(num/den));
						userXProduct[userNo][prodNo]=(int)Math.round(prediction);
						//Cold start problem handled
						if(userXProduct[userNo][prodNo]<=0)
							userXProduct[userNo][prodNo]=1;
						else if(userXProduct[userNo][prodNo]>5)
							userXProduct[userNo][prodNo]=5;
					}
				}
			}
			
			//store the result in output file "result.txt"
			File out=new File("result.txt");
			bw=new BufferedWriter(new FileWriter(out));
			for(int i=0;i<943;i++)
			{
				for(int j=0;j<1682;j++)
				{
					String line=(i+1)+" "+(j+1)+" "+userXProduct[i][j];
					bw.write(line);
					bw.write('\n');
				}
			}		
			bw.close();
		} catch (FileNotFoundException e) {
			System.err.println("INPUT FILE NOT FOUND");
			System.exit(0);
		} catch (Exception e) {
			System.err.println("ERROR");
			System.exit(0);
		}
		finally
		{
		}
	}
}
