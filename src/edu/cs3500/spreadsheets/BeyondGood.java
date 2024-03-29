package edu.cs3500.spreadsheets;

import edu.cs3500.spreadsheets.controller.AdapterMVCController;
import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.controller.SpreadsheetMVCController;
import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.ViewCreator;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.provider.controller.IController;
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
      if ((args[0].equals("-gui") || args[0].equals("-edit")
              || args[0].equals("-provider")) && args.length == 1) {
        //System.out.println("making blank gui");
        File file = new File("newSpreadsheet.txt");
        Readable fileReader;
        WorksheetBuilder<IWriteWorkSheetModel> builder = new WorkSheetModel.SheetBuilder();
        fileReader = new FileReader(file);
        WorksheetReader.read(builder, fileReader);
        IWriteWorkSheetModel model = WorksheetReader.read(builder, fileReader);

        if (!args[0].equals("-provider")) {
          IView guiView = args[0].equals("-gui")
              ? ViewCreator.create(ViewCreator.ViewType.GUI, model)
              : ViewCreator.create(ViewCreator.ViewType.EDITGUI, model);
          if (args[0].equals("-edit")) {
            SpreadsheetController controller = new SpreadsheetMVCController(model);
            controller.setView(guiView);
          }
          guiView.render();
          guiView.makeVisible();
        } else {
          // Factory and view return our interfaces,
          // need controller, view, and model adapter classes.
          IView view = ViewCreator.create(ViewCreator.ViewType.EDITGUI, model);
          IController controller = new AdapterMVCController(model, view);
          controller.run();
        }


      } else if (validArgs(args)) {
        File file = new File(args[1]);
        // Use WorksheetReader.read() to build model
        Readable fileReader = new FileReader(file);
        WorksheetBuilder<IWriteWorkSheetModel> builder = new WorkSheetModel.SheetBuilder();
        IWriteWorkSheetModel model = WorksheetReader.read(builder, fileReader);
        if (args.length == 4) {
          if (args[2].equals("-eval") && !model.hasErrors()) {
            System.out.println(model.evaluateCell(args[3]));
          } else if (args[2].equals("-save")) {
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
          IView guiView = args[2].equals("-gui")
                  ? ViewCreator.create(ViewCreator.ViewType.GUI, model)
                  : ViewCreator.create(ViewCreator.ViewType.EDITGUI, model);

          SpreadsheetMVCController controller = new SpreadsheetMVCController(model);
          controller.setView(guiView);
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
            !(args[0].equals("-in") || args[0].equals("-gui")
                    || args[0].equals("-edit") || args[0].equals("-provider")) ||
            !(args[2].equals("-save") || args[2].equals("-eval") || (args[2].equals("-edit")) ||
                    args[2].equals("-gui") || args[2].equals("-provider"))) {
      System.out.println("Null or incorrect number of arguments");
      return false;
    }
    return true;
  }
}
