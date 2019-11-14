package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.ViewCreator;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.IView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;


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
    try {
      if (args[0].equals("-gui") && args.length == 1) {
        //System.out.println("making blank gui");
        File file = new File("newSpreadsheet.txt");
        file.createNewFile();
        Readable fileReader = new FileReader(file);
        WorksheetBuilder<IWriteWorkSheetModel> builder = new WorkSheetModel.SheetBuilder();
        fileReader = new FileReader(file);
        IWriteWorkSheetModel model = WorksheetReader.read(builder, fileReader);
        model = WorksheetReader.read(builder, fileReader);
        IView guiView = ViewCreator.create(ViewCreator.ViewType.GUI, model);
        guiView.render();
        guiView.makeVisible();
      } else if (validArgs(args)) {
        File file = new File(args[1]);
        // Use WorksheetReader.read() to build model
        Readable fileReader = new FileReader(file);
        WorksheetBuilder<IWriteWorkSheetModel> builder = new WorkSheetModel.SheetBuilder();
        IWriteWorkSheetModel model = WorksheetReader.read(builder, fileReader);
        if (args.length == 4) {
          if (args[2].equals("-eval") && !model.hasErrors()) {
            System.out.println(model.evaluateCell(args[3]));
          } else if (args[2].equals("-save") && !model.hasErrors()) {
            File outputFile = new File(args[3]);
            outputFile.createNewFile();
            PrintWriter writer = new PrintWriter(outputFile);
            IView textView = ViewCreator.create(ViewCreator.ViewType.TEXT, model, writer);
            textView.render();
            writer.close();
          }
          // GUI called
        } else if (args.length == 3) {
          //System.out.println("making gui");
          IView guiView = ViewCreator.create(ViewCreator.ViewType.GUI, model);
          guiView.render();
          guiView.makeVisible();
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("No file provided");
    } catch (IOException e) {
      System.out.println("IOexception occured.");
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
