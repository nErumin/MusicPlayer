package setting;

public class Windows implements OperatingSystem {
    @Override
    public String getFileSeparator() {
        return "\\";
    }
}
