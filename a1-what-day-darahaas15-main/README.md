# A1- What Day App 
## a. Details
**Name** : Darahaas Yajamanyam \
**BITS ID** : 2020A7PS1386G \
**Email ID** : f20201386@goa.bits-pilani.ac.in 

## b. Description
- What Day is a simple android app that takes in a date as input from the user and gives an output of what day of the week that date was. It also validates the given input, and shows a series of errors (such as invalid dates, invalid input data types, etc.). The app also follows W3C guidlines, enabling people with various disabilities to use it. 
- **Known bugs** : Even though they are not app breaking, there are a few issues. Pressing the enter button on the keypad that shows up to input switches to the next input and then just hovers over the "Check" button, instead of actually pressing it. This behaviour also causes the inbuilt keypad to switch from taking only numerical inputs to its default state, where pressing the enter button again will trigger the function attached to the "Check" button. We weren't asked to modify this behavior for the assignment so I did not make an attempt to fix it.
- Accessibility scanner shows one common error: To increase the font of the the segment shown below, but we have been told that we aren't required to change it.
  - <img width="121" alt="image" src="https://user-images.githubusercontent.com/63366288/192845795-8ab2e465-89c0-4d75-aa90-59a473019910.png">


## c. Tasks
- For **task (1)**, I referred directly to the documentation for the Calendar Object (provided in the assignment PDF), instantiated a Calender Object and set the date to the parameters of the initialise() method. The most important part of this task was the calendar.setlenient() method call, which throws an exception when invalid dates are entered.
- For **task (2)**, I have attached the required method (DateModel.getMessage()) to the Check button and have taken the text from the inputs as parameters for the DateModal.initialize() method.
- For task (3), I have added the required accessibility features (As suggested by Android Studio) and used the accessibility scanner on my phone to make sure the app is accessible enough.

## d. Testing
- I have tested my application for all kinds of errors, not just after implementing my modal but all throughout the development process (Example below of me trying different ways to catch and resolve errors), and have tried using different specialised tests with the intent of trying to break my code. My app has crashed on many of these instances due to unresolved exceptions.
  - <img width="842" alt="image" src="https://user-images.githubusercontent.com/63366288/192827384-3d356a47-e016-4c6f-a021-127f7cebf585.png">
- Tried to run monkey testing to stress test the application but was unable to get it working on my package.
  - <img width="978" alt="Screenshot 2022-09-28 at 11 38 21 PM" src="https://user-images.githubusercontent.com/63366288/192856798-744ddb44-3b47-4a29-b466-00e96b150e67.png">

## e. Time taken for completion
- I have taken around *3-4 hours* to finish this assignment from scratch.

## f. Difficulty rating of the assignment
- **Difficulty rating** : 3/10
- I feel that this was a fairly easy assignment, but also has a lot of learning to offer as it has given me a hands on experience with using external APIs, learning the process of writing rigorous tests and handling various types of Exceptions. My difficulty rating is on the lower side because most of the code has already been provided in the starting template.

## g. Additional tasks
- I have made the message prompt more verbose, along with appropriate tense (like *was* or *will be*), and pushed the changes to a new branch **"additional"**.
- Once cool feature I think would make it a bit easier for the user if they could have a UI calender instead of having to manually type out the input fields. I know that this approach can have some concerns with respect to accessibility, but it would be a useful feature for most.
