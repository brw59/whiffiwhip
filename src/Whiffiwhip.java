



/**
 * This class just calls the application class frontEnd, which will
 * run the program
 */
public class Whiffiwhip
{
   /**
    * DOCUMENT ME!
    *
    * @param args user input that should never exist
    *
    * @throws Exception case it has to
    */
   public static void main(String[] args)
      throws Exception
   {
      /* original code
         javafx.application.Application.
              launch(wiffiwhip.desktop.GUI.class, args);
       */
      javafx.application.Application.launch(wiffiwhip.service.FrontEnd.class,
         args);
   }
}
