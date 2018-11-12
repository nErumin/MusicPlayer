import io.sentry.Sentry;

public class Main {
    public static void main(String[] args) {
        final String sentryDSN =
            "https://e917f0b7f74a47cd92685dd89b0bcecd@sentry.io/1321363";
        Sentry.init(sentryDSN);

        try {
            System.out.println("Hello, world!");
        } catch (Exception exception) {
            Sentry.capture(exception);
        }
    }
}
