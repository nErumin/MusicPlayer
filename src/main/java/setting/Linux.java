package setting;

public class Linux implements OperatingSystem {
    @Override
    public String getFileSeparator() {
        return "/";
    }
}
