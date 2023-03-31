# a3-dicegames

**Name** : Darahaas Yajamanyam \
**BITS ID** : 2020A7PS1396G \
**Email ID** : f20201386@goa.bits-pilani.ac.in

## Description

The software gives users the option to click on a Dice to generate a random number (out of 1 to 6). If the number is 6, the user's wallet is boosted by 5 coins. There is a button that leads users to a different area where they can play a game and place bets. They choose four dice because they believe that number to be the most similar. If they have that many dice with the same outcome, the wager is multiplied by that many to raise the number of coins. They lose the doubled sum if they don't receive that many identical dice.

## Tasks

- **Task 1** \- I then replicated the portrait view in landscape after implementing the model as discussed in class. Once the UIs were similar, it was time to make sure that data remained persistent when layouts changed. I used the viewModel as demonstrated in the lectures in order to do this challenge. After that, I implemented the "two or more" button using intents to make it possible to switch from one activity to another.
- **Task 2** \- I went through task 1's stages again. Here, I started by reading the TwoOrMoreViewModel test cases that were provided. I was able to implement my code after reading them, and I then ran individual test cases to gauge my progress. I went on to job 3 after being satisfied with my code and having all test cases pass.
- **Task 3** \- I started out by designing the layout for portrait mode. I made this layout functional in the code and started by sending data from the wallet view activity through the intent. Verified that intents were working.
- **Task 4** \- I created a functional back button by following the instructions on the slides, allowing the data to be transported between two different activities.
- **Task 5** \- I followed the same steps of creating an intent to create the new info activity.
- **Task 6** \- After being satisfied with the working of my app in portrait mode, I created the equivalent layouts for landscape mode and began to check for persistence.

## Testing

- Did not test during development, wrote tests after fixing multiple UI bugs and persist issues.

## Accessibility

- I used the accessibility scanner to evaluate my app's usability for users who are visually impaired, and I followed its recommendations. Initially, it was recommended that in order to improve visibility, the contrast between the foreground and background on the button die may be enhanced. It suggested changing the "zeroes" because many of them were coincidental. It was unable to do this since it would have made the interface less readable and more convoluted.

## Completion Time

- It took me around 48 hours to complete all the tasks from the starter template.

## Difficulty Level

- 8/10
