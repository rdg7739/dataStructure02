package proj2;

public class WrongNodeException extends Exception {
	public WrongNodeException(){
		super("Wrong Node Exception");
	}
	public WrongNodeException(String msg){
		super(msg);
	}
}
