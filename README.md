# Requirements
* Android phone with version 8.0 and above.
* Permission to access location

# Menu
<img src="https://github.com/user-attachments/assets/88ef8d91-a5ed-45bf-97ed-df0dc005f65d" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/b4f1e2b4-516b-459b-ba13-d8293ec9da5f" alt="drawing" width="250"/>

* Select game movement mode: buttons or tilt
* Display records stored localy on the device
  
# Game Loop
In this game the player's spaceship needs to avoid asteroids coming towards it, while collecting coins in order to gain points.

If before the start of the game the player selected to use buttons for movement the game screen will look as follow:
<img src="https://github.com/user-attachments/assets/d0959c0e-5ac1-4d22-a05a-523a13cd9ffa" alt="drawing" width="250"/>

else the player's movement will be based on a tilt detection utility and the buttons will be invisible.
<img src="https://github.com/user-attachments/assets/05c63c87-3c77-4c8c-b1e3-794f77b26caf" alt="drawing" width="250"/>

If the player failed to avoid the asteroids 3 times the game will reset and will continue in an infinite loop,
until the player press the device return button. Also, each time the game resets the score is stored into a local cache, which will be exported into the device disk when exiting the game screen.

<img src="https://github.com/user-attachments/assets/25864c8f-8699-4cc6-b7df-ce653a48044d" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/396392fb-e81c-48d6-9aa3-e4219e815d1f" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/9dafbc80-3d6e-4911-b1dd-8afcaf561c0b" alt="drawing" width="250"/>

# Records Display
Here the player can view up to top 10 records, and with the press of a button the player can also see where in the world the record was made.

<img src="https://github.com/user-attachments/assets/ca73ce1f-4b90-4e6a-98a9-019afc4670ce" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/a285ea68-d547-4496-86fa-1a9232c04124" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/6e469240-1539-47f1-9d13-7bd3adceb714" alt="drawing" width="250"/>
<img src="https://github.com/user-attachments/assets/2c9ee2c4-4471-4d83-ba82-09ec9cae85a7" alt="drawing" width="250"/>
