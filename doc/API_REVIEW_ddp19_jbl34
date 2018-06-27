API Review Questions
===
ddp19, jbl34

### Flexibility
* **Dylan**: We want the programmer to be able to easily add new components in the engine, without having to also add them in the authoring environment - by setting up editable properties at runtime using reflection, we ensure DRY code. 
* **Julia**: By creating our user interface using a modular design, the programmer can easily add and remove buttons and toolbars without modifying more than one line of code. 

### Other parts of the project
* **Dylan**: Our API interacts directly with the Data team, because they have to be able to serialize our `GameState` object when the user wants to save. Our primary public-facing API method deals with the Data team having access to that object. We also need access to the engine, in order to determine what methods are allowed at runtime. 
* **Julia**: Our project depends on Game Data to save projets to JSON, and depends heavily on the game engine for all Game Object and Level objects. When new objects are created, the process involves a direct link to classes in the Game Engine. 

### Errors
* **Dylan**: We need to communicate with the engine team about erroneous values that could the user could enter into certain `Component` field. We will ideally display them graphically so that the user can know exactly how to fix their mistakes. We will use a `ComponentValueException` class to 
* **Julia**: Users inputting invalid x and y values for object location (i.e. out of bounds, letters) will generate an error. We will handle this by showing an error message to the user to inform them that the values they inputted were invalid.



