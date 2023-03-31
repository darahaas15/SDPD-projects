# A2- Persist UI
## a. Details
**Name** : Darahaas Yajamanyam \
**BITS ID** : 2020A7PS1386G \
**Email ID** : f20201386@goa.bits-pilani.ac.in 

## b. Description
Dice Games is an implementation of the WalletActivity discussed in class, to explain lifecycle methods in Android. The user is given the ability to toss a die, and they earn 10 coins every time it lands on a 6. The app also follows W3C guidlines, enabling people with various disabilities to use it. 
 Further modifications have been made as requested by the assignment:
- If the user rolls a six, you still get 5 coins.
- If the user rolls two sixes in a row, you earn 10 coins.
- But if the user rolls any other number twice in a row, you lose 5 coins.

**Known bugs** : Even though they are not app breaking, there are a few issues. The text elements shift around slightly depending on the length of the number they are bound to, and this looks slightly unsightly. My app's landscape layout looks slightly different as compared to the mentioned layout in Prof Swaroop's slides.

## c. Tasks
- For **task (1)**, I referred mostly to the Activity Lifecycle slides provided on swaroopjoshi.in and implemented the methods in accordance to what was discussed in class.
- For **task (2)**, I have referred to Android's toast documentation and created my own method which takes in two parameters: 
  - The message to be displayed by the toast.
  - The interval for which the toast notification is active.
  - ``` public void showToastMessage(String text, int duration){
    final Toast toast = Toast.makeText(WalletActivity.this, text, Toast.LENGTH_SHORT);
    toast.show();
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        toast.cancel();
      }
    }, duration);}
  
- **For task (3)**, I have added two new methods to my ViewModel, singleSixes() and totalRolls(), which increment whenever the Die value rolls the WIN_VALUE.

-  **For task (4)**, I have added two more methods to my ViewModel, doubleSixes() and doubleOthers(), which check for the following condition:
 - ```if (mDie.value() == WIN_VALUE && mPreviousValue == WIN_VALUE) { doubleSixCount++; mBalance += BONUS;}```
    
 - ```if (mDie.value() != WIN_VALUE && mPreviousValue != WIN_VALUE && mDie.value() == mPreviousValue) { doubleOtherCount++; mBalance -= INCREMENT;}```
    
## d. Testing
- Monkey testing caused the app to crash a few times.
 - <img width="978" alt="Screenshot 2022-09-28 at 11 38 21 PM" src="https://user-images.githubusercontent.com/63366288/192856798-744ddb44-3b47-4a29-b466-00e96b150e67.png">
- Test cases: 
 - Wrote test cases using mock objects (In WalletTest.java)

## e. Accessibility prompts
- I have faced a few issues while using my app blindfolded, in the starting, when all the values (coins, number of sixes rolled, double sixes, double others etc.) are all 0. So while talkback is reading out the values, it gets a bit confusing to maintain context of which value it is talking about.
Other than that, I could use my app without any major problems.

- ImageButton gives a missing Content Description error, can be made accessible by adding ```android:clickable = "true"```, as well as adding a button description.

A few of the accessibility promps that accessibility scanner showed:
- <img width="1440" alt="image" src="https://user-images.githubusercontent.com/63366288/195898059-31fb09f3-f5f5-4e06-9a03-7ab8efd28fd3.png">
 - This accessibility error asked to be ignored.
- <img width="1440" alt="image" src="https://user-images.githubusercontent.com/63366288/195898308-81a3bf4f-a65b-457f-a5d3-d853397a58dc.png">
 - Did not change the hardcoded value of the button, as it was not required.


## f. Time taken for completion
- I have taken around *3-4 hours* to finish this assignment from scratch.

## g. Difficulty rating of the assignment
- **Difficulty rating** : 4/10
- I feel that this was a moderately easy assignment, as most of the tasks given in the assignment were discussed in the slides/in-class activities. The assignment gave me a great hands-on experience with activity lifecycle methods.
