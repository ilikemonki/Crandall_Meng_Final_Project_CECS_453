# Crandall_Meng_Final_Project_CECS_453

CECS453\
Mobile Application Development\
Final Project

Authors: Josue Crandall and Meng Cha\
CECS 453 Mobile Apps\
Date: 5/15/20

Title: Image Reader

--Project Design--
  Image Reader will recognize characters in an image and print the text out. The app can then convert the text to speech. Images can be taken from the user's gallery or any other image-based applications.
  Image reader also has a real-time scanning camera which will print text to the screen during camera viewing.
  All other features are to satsify the project requirements for CECS 453 Mobile Apps Dev.
  
--Contribution--
  Josue Crandall is the back-end developer and Meng Cha is he front-end developer. Josue created the login, sign-up, database, and all functions/features containing user's accounts.
  Meng created the main features that includes images, image-to-text, text-to-speech, and camera functions.
  
--Problems--
  There are some problems with the Google Vision API's CameraSource not being able to set it's orientation. This results in the real-time camera to not fixed itself when the the phone is flipped.
  Another problem was converting the bytes array into Bitmap and then converting Bitmap into Uri. Converting Bitmap to Uri saves the image in the user's gallery everytime. We would have like to not save it everytime.
  But, user's can overcome this by using the default phone's camera from the image source (bottom left-most button).
