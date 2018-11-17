package setting;

public class UnknownOperatingSystem implements OperatingSystem {
    @Override
    public String getFileSeparator() {
        return "/";
    }
}
