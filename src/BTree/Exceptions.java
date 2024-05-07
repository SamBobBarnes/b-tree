package BTree;

public class Exceptions
{

    public static class HowDidYouGetHereException extends Exception
    {
        public HowDidYouGetHereException() {
            super("How did you get here?");
        }
    }
}
