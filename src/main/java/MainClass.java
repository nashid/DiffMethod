import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        String url;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the specific url or choose from these options: \n\n" +
                "1) My test (https://github.com/amaembo/streamex.git) \n" +
                "2) StreamX (https://github.com/EhsanMashhadi/test.git) \n" +
                "3) Gson (https://github.com/google/gson)) \n" +
                "4) Retrofit (https://github.com/square/retrofit)");

        String inputUrl = scanner.next();

        switch (inputUrl) {

            case "1": {
                url = "https://github.com/EhsanMashhadi/test.git";
                break;
            }
            case "2": {
                url = "https://github.com/amaembo/streamex.git";
                break;
            }
            case "3": {
                url = "https://github.com/google/gson)";
                break;
            }
            case "4": {
                url = "https://github.com/square/retrofit";
                break;
            }
            default: {
                url = inputUrl;
            }
        }

        System.out.println("Enter name of clone folder? Press D for default");
        String cloneFolder = scanner.next();

        if (cloneFolder.equals("D")) {
            cloneFolder = "clone";
        }

        System.out.println("Enter name of the output file? Press D for default");
        String outputFile = scanner.next();

        if (outputFile.equals("D")) {
            outputFile = "result";
        }

        MainClass mainClass = new MainClass();
        mainClass.fetchRemote(url, cloneFolder, outputFile + ".csv");

    }

    private void fetchRemote(String url, String cloneFolder, String outputFile) {
        new CustomStudy(url, cloneFolder, outputFile).execute();
    }
}
