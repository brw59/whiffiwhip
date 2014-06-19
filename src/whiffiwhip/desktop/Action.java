package wiffiwhip.desktop;

import java.lang.reflect.*;

import java.util.*;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.control.*;

/**
 * A class for C.L.A.W.-style wiring of actions to components.
 */
public class Action
   implements EventHandler<ActionEvent>
{
   /**
    * Holds the keyed singleton instances.
    */
   private static Map<String, Action> cInstances;

   static
   {
      cInstances = new HashMap<String, Action>();
   }

   /**
    * Holds the action name.
    */
   private String mName;

   /**
    * Holds the method name.
    */
   private String mMethodName;

   /**
    * Holds the method to be invoked.
    */
   private Method mMethod;

   /**
    * Holds the invokee of the method (the object the method is invoked on).
    */
   private Object mMethodInvokee;

   /**
    * Constructs a new Action instance.
    *
    * Sets the method via reflection on the method name.
    *
    * For this to work, the method named must have a void return type
    * and take no arguments. Ideally, the method name could be overridden
    * in the constructor call. For now, it defaults to the camelCase
    * transformation of the action name (e.g., "Start Thread" -->
    * "startThread").
    *
    * @param pName the action name.
    *
    * @param pMethodInvokee the method invokee.
    */
   private Action(String pName, Object pMethodInvokee)
   {
      mName = pName;
      mMethodName = (mName.substring(0, 1).toLowerCase() +
                     mName.substring(1, mName.length()).replace(" ", ""));
      mMethodInvokee = pMethodInvokee;
      try
      {
         mMethod = mMethodInvokee.getClass().getMethod(mMethodName);
      }
      catch (Exception e)
      {
      }
   }

   /**
    * The keyed singleton lookup method.
    *
    * @param pKey the lookup key.
    *
    * @param pMethodInvokee the method invokee.
    */
   public static Action get(String pKey, Object pMethodInvokee)
   {
      String actionName = pKey;
      pKey = pKey + ":" + pMethodInvokee.getClass().getName();
      Action action = cInstances.get(pKey);
      if (action == null)
      {
         action = new Action(actionName, pMethodInvokee);
         cInstances.put(pKey, action);
      }
      return action;  
   }

   /**
    *  Gets a string representation of the Action instance.
    *
    * @return a string representation of the Action instance.
    */
   public String toString()
   {
      return ("Action[" + mName + " invokes " +
              mMethodName + " on " + mMethodInvokee + "]");
   }
   
   /**
    * Shows all Actions.
    */
   public static void showAll()
   {
      for (String key : cInstances.keySet())
      {
         System.out.println(key + " <---> " + cInstances.get(key));
      }
   }

   /**
    * Handles the action event.
    *
    * @param pEvent the action event.
    */
   public void handle(ActionEvent pEvent)
   {
      perform(pEvent.getSource());
   }

   /**
    * Generic perform method for all Action instances.
    *
    * Performs the action of invoking the method on the invoke.
    *
    * @param pTarget the target of the action (ignored for now).
    */
   public void perform(Object pTarget)
   {
      if ((mMethod != null) && (mMethodInvokee != null))
      {
         try
         {
            mMethod.invoke(mMethodInvokee);
         }
         catch (Exception e)
         {
            System.err.println(e);
         }
      }
      else
      {
         System.out.println(pTarget);
      }
   }
}

