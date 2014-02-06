package proj2;

public class UnderflowException extends Exception {
	public UnderflowException(){
		super("Underflow Exception");
	}
	public UnderflowException(String msg){
		super(msg);
	}
}
