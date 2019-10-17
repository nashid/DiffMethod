import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        String url;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the specific url of project or choose from these options \n" +
                "1) My test (https://github.com/amaembo/streamex.git) \n" +
                "2) StreamX (https://github.com/EhsanMashhadi/test.git) \n" +
                "3) Gson (https://github.com/google/gson)");

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
            default: {
                url = inputUrl;
            }
        }

        System.out.println("Enter mName of clone folder? Press D for default");
        String cloneFolder = scanner.next();

        if (cloneFolder.equals("D")) {
            cloneFolder = "test-clone";
        }

        System.out.println("Enter mName of the output file? Press enter D for default");
        String outputFile = scanner.next();

        if (outputFile.equals("D")) {
            outputFile = "test-output";
        }

        MainClass mainClass = new MainClass();
        mainClass.fetchRemote(url, cloneFolder, outputFile + ".csv");
    }

    private void fetchRemote(String url, String cloneFolder, String outputFile) {
        new CustomStudy(url, cloneFolder, outputFile).execute();
    }
}
