public class AnalysisException extends Exception{
    private int line;
    private int column;
    public AnalysisException(String message) {
        super(message);
    }

    public AnalysisException(int line, int column) {
        super("Error");
        this.line = line;
        this.column = column;
    }

    public AnalysisException(String message, int line, int column) {
        super(message);
        this.line = line;
        this.column = column;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " at line: " + line + ", col: " + column;
    }
}
