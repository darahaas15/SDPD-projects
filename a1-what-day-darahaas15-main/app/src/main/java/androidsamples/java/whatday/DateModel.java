package androidsamples.java.whatday;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents the date to be set, whether it is a valid date, and the message with error status or the day of the week.
 */
public class DateModel {
  private static String message;

  public static void numberToDay (int day){
    switch(day)
    {
      case 1:
        message = "Sunday";
        break;
      case 2:
        message = "Monday";
        break;
      case 3:
        message = "Tuesday";
        break;
      case 4:
        message = "Wednesday";
        break;
      case 5:
        message = "Thursday";
        break;
      case 6:
        message = "Friday";
        break;
      case 7:
        message = "Saturday";
        break;
    }
  }


  /**
   * Initializes the {@link DateModel} with the given year, month, and date.
   * If the date is not valid, it sets the appropriate error message.
   * If it is valid, it sets the appropriate day of the week message.
   *
   * @param yearStr  a {@code String} representing the year, e.g., "1947"
   * @param monthStr a {@code String} representing the month, e.g., "8"
   * @param dateStr  a {@code String} representing the date,  e.g., "15"
   */
  public static void initialize(String yearStr, String monthStr, String dateStr) {
    int year, month, date;
    // Hint: use the Date class to check if the date is valid
    try{
     year = Integer.parseInt(yearStr);}
    catch (NumberFormatException e){
      message = "Enter values in a proper numeric format";
      return;
    }
    try{
     month = Integer.parseInt(monthStr);}
    catch (NumberFormatException e){
      message = "Enter values in a proper numeric format";
      return;
    }
    try{
      date = Integer.parseInt(dateStr);}
    catch (NumberFormatException e){
      message = "Enter values in a proper numeric format";
      return;
    }

    Calendar c = Calendar.getInstance();
    //Set lenient true
    c.setLenient(true);
    c.set(year, month-1, date);

    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

    String errorMessage = errorCheck(yearStr, monthStr, dateStr);

      if (errorMessage.equals("No error")) {
        numberToDay(dayOfWeek);
      }
      else{
        message = errorMessage;
      }


  }

  public static String errorCheck(String yearStr, String monthStr, String dateStr) {

    int year, month, date;
    // Hint: use the Date class to check if the date is valid
    try{
      year = Integer.parseInt(yearStr);}
    catch (NumberFormatException e){
      return  "Enter values in a proper numeric format";
    }
    try{
      month = Integer.parseInt(monthStr);}
    catch (NumberFormatException e){
      return  "Enter values in a proper numeric format";
    }
    try{
      date = Integer.parseInt(dateStr);}
    catch (NumberFormatException e){
      return  "Enter values in a proper numeric format";
    }

    if (month < 1 || month > 12) {
      return "Invalid month";
    }
    if (date < 1 || date > 31) {
      return "This month does not have " + date + " days";
    }
    if (month == 2 && date > 28) {
      if (year % 4 == 0) {
        if (date > 29) {
          return "February of " + year + " does not have " + date + " days";
        }
      } else {
        return "February of " + year + " does not have " + date + " days";
      }
    }
    if (month == 4 || month == 6 || month == 9 || month == 11) {
      if (date > 30) {
        return "This month does not have " + date + " days";
      }
    }
    //Check for proper numeric format
    if (yearStr.matches("[a-zA-Z]+") || monthStr.matches("[a-zA-Z]+") || dateStr.matches("[a-zA-Z]+")) {
      return "Enter values in a proper numeric format";
    }

    return "No error";
  }


  /**
   * Retrieves the message from the {@link DateModel}.
   * It can be an error message like "February of 2019 does not have 29 days"
   * or a success message like "Friday".
   *
   * @return the message from the model
   */
  @NonNull
  public static String getMessage() {
    // Get the message from DateModel
    return message;
  }
}
