package proj2;

import java.io.*;
import java.util.*;


public class Proj2<AnyType> {

	public Proj2(String[] args){
		Scanner input;
		try {
			if(args.length != 2){
				throw new UnderflowException("Not enough argument");
			}
			input = new Scanner(new FileInputStream(args[0]));
			BinarySearchTree<Integer> hoo = new BinarySearchTree<Integer>();
			while(input.hasNext()){
				try {
					int foo = input.nextInt();
					//System.out.println("Insert: " + foo);
					hoo.insert(foo);
				} catch (WrongNodeException e) {
					System.out.println(e.toString());
				}
			}
			input = new Scanner(new FileInputStream(args[1]));
			while(input.hasNext()){
				try{
					String command = input.next().toUpperCase();
					int foo= 0;
					if(input.hasNextInt())
						foo = input.nextInt();
					if(command.equals("PRINT")){
						System.out.println("Level: " + foo);
						System.out.println(hoo.toString(foo));
					}
					else if(command.equals("RANK")){
						System.out.println("Rank: " + foo);
						System.out.println(hoo.rank(foo));
					}
					else if(command.equals("NTH")){
						System.out.println("Nth: " + foo);
						System.out.println(hoo.nthElement(foo).toString());
					}else if(command.equals("REMOVE")){
						System.out.println("Remove: " + foo);
						hoo.remove(foo);
					}
					else if(command.equals("PRINTTREE")){
						System.out.println("Print tree");
						System.out.println(hoo.printTree());
					}
					else if(command.equals("MEDIAN")){
						System.out.println("Median: ");
						System.out.println(hoo.median().toString());
					}
					else if(command.equals("PERFECT")){
						System.out.println("Perfect");
						System.out.println(hoo.isPerfect());
					}
					else if(command.equals("COMPLETE")){
						System.out.println("Complete");
						System.out.println(hoo.isComplete());
					}else{
						System.out.println(command + " is not a correct command");
					}
				}catch (UnderflowException e) {
					System.out.println(e.toString());
				}catch (WrongNodeException e) {
					System.out.println(e.toString());
				}
			}
		}catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (UnderflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		new Proj2(args);
	}

}
