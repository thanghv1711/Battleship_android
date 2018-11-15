package fu.prm391.example.configure;

public class Common {

    public static int getPositionOnOneDimensionArray(int row, int col, int numberOfRows, int weightValue) {
        return row * numberOfRows + col + weightValue;
    }
}
