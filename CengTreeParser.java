import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CengTreeParser
{
    public static ArrayList<CengVideo> parseVideosFromFile(String filename)
    {
        ArrayList<CengVideo> videoList = new ArrayList<CengVideo>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengVideos
        Scanner read = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\|");
                videoList.add(new CengVideo(Integer.parseInt(split[0]), split[1], split[2], split[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the video, and call CengVideoRunner.addVideo(newlyCreatedVideo).
        // 3) search : Parse the key, and call CengVideoRunner.searchVideo(parsedKey).
        // 4) print : Print the whole tree, call CengVideoRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.

        Scanner userInput = new Scanner(System.in);
        String input;
        while(true) {
            input = userInput.nextLine();
            String[] parsed = input.split("\\|");
            if (parsed[0].equals("quit")){
                break;
            }
            else if (parsed[0].equals("print"))
                CengVideoRunner.printTree();
            else if (parsed[0].equals("search"))
                CengVideoRunner.searchVideo(Integer.parseInt(parsed[1]));
            else if (parsed[0].equals("add"))
                CengVideoRunner.addVideo(new CengVideo(Integer.parseInt(parsed[1]),parsed[2],parsed[3],parsed[4]));
            else
                System.out.println("There are 4 available commands: quit, add, search, print");
        }
    }
}
