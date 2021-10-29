package hw12.family.exceptions;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(){
        System.out.println("Field must not be empty!  Repeat the input");
    }
}
