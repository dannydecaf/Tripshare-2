# Tripshare-2

Updated version of tripShare. Incorporating MVVM model, and extensive Firebase functionality.

# Functionality

The end user is presented with a login screen at the start, whereby they can use their Google login or alternative standard email credentials.

A user may update their image in-app. The image used is based on the user's Google account profile pic (if they have one). Or else a standard picture.

The created users will show in the Firebase console once added.

The app is based Mainly on the latter labs of DonationX, but refactored to suit to my previous V1 tripShare app and its' overall strucutre.

The user can add Roadtrips via the Roadtrip Fragment add the name of the trip, description, highlights and lowlights of the trip. As well as give it a rating of 0-5 in half-steps.

This will display as a 'Roadtrip Card', within the 'Roadtrip List' fragment.

The Roadtrips that show up upon sign-in are unique to the user (if they had logged in previous and added some). However, there is a read-only toggle button where all Roadtrips from a network of Roadtrippers are show in read-only mode. The logged in user may only amend or delete his/her own added Roadtrips. Deletion is facilitated using the Swipe functionality.

# UX/DX

MVVM from DonationX labs was used as a starting point in building out the framework of the app.

Unlike V1, a Nav Bar is used which allows the user to update their pic, sign out or redirect to the relevant roadtrip list.

I had initially attempted MVP from the Placemark labs, but was unsuccessful in incorporating it as efficiently as I'd have hoped.

# UX/DX

While structurally different, tripShare V2 incorporates the over-arching idea, utilising the relevant rubric for Assignment 2 moreso than the previous features.

As mentioned, I hoped to use Placemark lab codes in building from the ground up, but used the DonationX labs. This still worked very well, but had a fair bit of refactoring to do to get it close enough to tripShare V1 in structure.

I encountered many difficulties in the labs prior to the Christmas period, which drastically halted my progress on this assignment.

However, I am quite satisfied with what I have achieved in the short time frame and the learning curves I encountered before Christmas.

This time around, I had no such issues that I had when initially doing the labs. Overall, everything works well.

Time was a factor though, and if I had found myself so short on time in the end, I would have liked to endeavour the Excellent band in terms of Rubric.

I had to sacrifice Map/Location functionality, and slotting in a 'Destination' model as a sub-model to 'Roadtrip'. I had also started Dark Mode but alas, I left myself too tight on time.

# UML Diagram

![file2](https://user-images.githubusercontent.com/76448325/211430855-35d470db-878a-4820-bd64-7213033d2fbc.png)

# References

Adding Dark Mode - https://www.youtube.com/watch?v=fM6BGgmXYpA
Creating UML Diagram in Intellij Ultimate - https://www.youtube.com/watch?v=66ArYQUFLdM
