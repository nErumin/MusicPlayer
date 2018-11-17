package setting;

public final class Environment {
    private static volatile Environment instance;
    private OperatingSystem os;

    public static Environment getInstance() {
        if (instance == null) {
            synchronized (Environment.class) {
                if (instance == null) {
                    instance = new Environment(createOperatingSystem());
                }
            }
        }

        return instance;
    }

    private static OperatingSystem createOperatingSystem() {
        String operatingSystemName = System.getProperty("os.name");
        if (operatingSystemName.startsWith("Win")) {
            return new Windows();
        } else if (operatingSystemName.startsWith("Mac")) {
            return new Mac();
        } else if (operatingSystemName.startsWith("Linux")) {
            return new Linux();
        } else {
            return new UnknownOperatingSystem();
        }
    }

    private Environment(OperatingSystem os) {
        this.os = os;
    }

    public String combinePath(String path, String attachingPath) {
        if (path == null || attachingPath == null) {
            throw new NullPointerException("The provided path is null.");
        }

        return purifyPath(path) + os.getFileSeparator() + attachingPath;
    }

    private String purifyPath(String path) {
        if (path == null) {
            throw new NullPointerException("The provided path is null.");
        }

        String purifiedPath = path;

        if (purifiedPath.length() > 0) {
            String lastCharacter = path.substring(path.length() - 1);
            if (lastCharacter.equals(os.getFileSeparator())) {
                purifiedPath = path.substring(0, path.length() - 2);
            }
        }

        return purifiedPath;
    }
}
