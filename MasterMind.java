import java.util.*;
import java.io.*;
class MasterMind{
	public static void main(String[] args)throws IOException{
		System.out.println("***Welcome to MasterMind with digits***");
		GuessClass gc=new GuessClass();
		gc.startGame();
	}
}

class GuessClass{
	private ArrayList<String> genrtdNos=new ArrayList<String>();
	int choice;
	FeedbackGenerator result=new FeedbackGenerator();
	FeedbackGenerator fdbk=new FeedbackGenerator();
	public GuessClass(){
		numGen();
	}
	
	public void startGame(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Menu:-");
		System.out.println("1. About the Game");
		System.out.println("2. Get Familiar with Notations and How it runs");
		System.out.println("3. Start Game");
		System.out.println("Enter your Choice:");
		choice=sc.nextInt();
		switch(choice){
			case 1:	
					System.out.println("*********************************");
					System.out.println("MasterMind is a code breaking game for two players. One player becomes the codemaker, the other the codebreaker.");
					System.out.println("The codemaker chooses a pattern of four digits.Duplicates are not allowed. The code breaker tries to guess the pattern by placing the guess for the four digits.");
					System.out.print("Once placed, the code maker provides a feedback of whether the digits are placed correctly and how many of these are the correct digits. Once feedback is provided, another guess is made.");
					System.out.println("Guesses and feedback continue to alternate until the codebreaker guesses correctly.");
					System.out.println("*********************************");
					startGame();
					break;
			case 2:
					System.out.println("*********************************");
					System.out.println("We will be working with digital number 0-9. For each game, the program will guess an ordered four non-repeated digits with minimum number of guesses with feedback provided on how good the current guess is.");
					System.out.println("Suppose the second player kept a number 2365 in his/her mind that the program needs to guess, which is unknown to the program.");
					System.out.println("In the first round the program has no information, so it makes an initial guess, e.g., 1234. Then the FeedbackGenerator will provide a feedback 0A 2B, meaning that the program guessed two digits correctly but they are not in the correct position. So according to this information, in the second round the program will adjust the guess to “2135”, then the FeedbackGenerator will provide with a feedback “1A 2B”, meaning that the program guessed 1 number correctly and it is in the correct position, and guessed 2 other numbers correctly but they are not in the correct position. The Program will continue adjusting its guess with the feedback until it has guessed it correctly.");
					System.out.println("*********************************");
					startGame();
					break;
			case 3:
					guess();
					break;
			default:
					System.out.println("Invalid Choice!!! Try again");
					break;
		}
	}	
	void guess(){
		Scanner sc=new Scanner(System.in);		
		System.out.println("Enter a Four Non-repeated Digit Number, eg.1234,7895:");
		String usrNo=sc.nextLine();
		int cc=1;
		int count=1;
		String guess="";
		if(usrNo.length()>4){
			System.out.println("Just a Four Digit No. is allowed!");
			guess();
		}
		if(checkRepeat(usrNo)==false){
			System.out.println("This is not an accepted Four Digit number! Please try again with a different 			number");
			guess();
		}
		
		else{
		while(true){
			guess=numberGenerator();	
			if(guess.isEmpty()){
				System.out.println("Incorrect Input...Please try Again!!!");
				return;
			}
			
			if(scoreReturn(guess,result,usrNo)){
				System.out.println("Program: My Guess is: "+guess);
				System.out.println("Player 2: Smart Guy!!! That is the number");
				System.out.println("Congratulations!!!");
				System.out.println("Program: See I found It in "+cc+" guesses....");
				System.out.println("Program: Beat that Human!!!");
				System.out.println("*********************************");	
				return;
			}
			
			System.out.println("Guess no. "+count);
			System.out.println("Program: My Guess is: "+guess);
			System.out.println("Player 2: Nope...That's not the Number :p");
			System.out.println("Player 2: Here take the help of this Feedback: "+result.getA()+"A "+result.getB()+"B");
			System.out.println("");
			removeGenNos(guess,result);
			cc++;
			count++;
		}
	}
}
	
	String numberGenerator(){
		if(genrtdNos.isEmpty()){
			return "";
		}
		int index=generateRandomNumber(0,genrtdNos.size());
		String num=genrtdNos.get(index);
		return num;
	}
	boolean scoreReturn(String guess,FeedbackGenerator result,String usrNo){	
		scoreGenerator(guess,usrNo,result);
		return (result.getA()==4);
	}
	
	void scoreGenerator(String guess,String fdbk,FeedbackGenerator pr){
		int b=0,c=0;
		pr.setA(b);
		pr.setB(c);
		for (int x=0;x<4;x++){
			if(guess.charAt(x)==fdbk.charAt(x)){
				b++;
				pr.setA(b);
			}
			else{
				for(int y=0;y<4;y++){
					if(guess.charAt(x)==fdbk.charAt(y)){
						c++;
						pr.setB(c);
					}
				}
			}
		}
	}
	
	void numGen(){
		for(int x=1023;x<9877;x++){
			StringBuffer sb=new StringBuffer("");
			sb.append(x);
			if(checkRepeat(sb.toString())){
				genrtdNos.add(sb.toString());
			}
		}
	}
	
	boolean checkRepeat(String s){
		int i=0;
		if(s.charAt(0)=='0'){
			return false;
		}
		while(i<4){
			for(int j=i+1;j<4;j++){
				if(s.charAt(i)==(s.charAt(j)))
					return false;
			}
			i++;
		}
		return true;
	}
	
	void removeGenNos(String guess,FeedbackGenerator r){
		genrtdNos.remove(guess);
		Iterator<String> pi = genrtdNos.iterator();
		while(pi.hasNext()){
			if(checkRepeatIterations(guess,pi.next(),r)){
				pi.remove();
			}
		}		
				
	}
	
	boolean checkRepeatIterations(String guess,String ts,FeedbackGenerator result){
		scoreGenerator(guess,ts,fdbk);
		return(fdbk.getA()!=result.getA() || fdbk.getB()!=result.getB());
	}
	
	int generateRandomNumber(int min,int max){
		int range = (max - min);     
   		return (int)(Math.random() * range) + min;
	}
}
class FeedbackGenerator{
	int A;
	int B;
	public FeedbackGenerator()
	{}
	
	public FeedbackGenerator(int A,int B){
		this.A=A;
		this.B=B;
	}
	public int getA(){
		return A;
	}
	public int getB(){
		return B;
	}
	public void setA(int A){
		this.A=A;
	}
	public void setB(int B){
		this.B=B;
	}
}
