package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.IWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;

import java.io.*;

/**
 * The main class for our program.
 */
public class BeyondGood {

    /**
     * The main entry point.
     *
     * @param args any command-line arguments
     */
    public static void main(String[] args) {
    /*
      TODO: For now, look in the args array to obtain a filename and a cell name,
      - read the file and build a model from it, 
      - evaluate all the cells, and
      - report any errors, or print the evaluated value of the requested cell.
    */

        if (validArgs(args)) {
            File file = new File(args[1]);
            // Use WorksheetReader.read() to build model
            try {
                Readable fileReader = new FileReader(file);
                WorksheetBuilder<IWorkSheetModel> builder = new WorkSheetModel.SheetBuilder();
                IWorkSheetModel model = WorksheetReader.read(builder, fileReader);
                System.out.println("model Made");
                if(!model.hasErrors()) {
                    if(args.length == 4) {
                        System.out.println("4");
                        if(args[2].equals("-eval") && !model.hasErrors()) {
                            model.evaluateIndCell(args[3]);
                        } else if(args[2].equals("-save")) {
                            System.out.println("saved");
                            File outputFile = new File(args[3]);
                            outputFile.createNewFile();
                            PrintWriter writer = new PrintWriter(outputFile);
                            IView textView = new SpreadsheetTextualView(model, writer);
                            textView.render();
                            writer.close();
                        }
                    } else if(args.length == 3) {

                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("No file provided");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Validates input arguments
    private static boolean validArgs(String[] args) {
        if (args == null || args.length > 4 ||
                !(args[0].equals("-in") || args[0].equals("-gui")) ||
                !(args[2].equals("-save") || args[2].equals("-eval") ||
                        args[2].equals("-gui"))) {
            System.out.println("Null or incorrect number of arguments");
            return false;
        }
        return true;
    }
}
