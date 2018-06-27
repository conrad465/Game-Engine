## Interfaces.md

### Game Engine

* For Player
    * Start/Stop game cycle
    * Pause game cycle
    * Set game objects/initial values
    * Notify engine of keyboard inputs
    * Access to locations and states player and other objects when game starts
* For Authoring Environment
    * List of rules


### Authoring Environment
* **Game Authoring Environment Internal API**: 
    * The Game Authoring Enviroment must have the ability to read in a file. This is a saved .XML file that represents a serialized `GameAuthorState`.  This will be handled by a `load()` method.
    * The Game Authoring Environment must also have the ability to save to a file. This will be a .XML file that will represent a serialized `GameAuthorState`. We intend to use the xStream serialization toolkit. This will be handled through a `save()` method.
    * The Game Authoring Enviroment has to be able to create new instances of Game Objects. These game objects will be created by their individual constructors but adding them to the `GameAuthorState` will be handled by  a method `appendGameAuthorState` 
    * The Game Authoring Environment needs the ability to remove Game Ojects from the `GameAuthorState`. This will have to be handled by a method `removeGameAuthorState()`
    * The two functions above `appendGameAuthorState()` and `removeGameAuthorState()` will rely on a seperate API that will be used by components in the `gameView` and the `ComponentView` that handles objects being dragged and dropped. We will called this our `DragAndDropDynamicYoutility` or `DADDY`
    * The Game Authoring Environment needs the ability to edit currently instantiated Game Objects. Finding and altering these objects will be handled by a method `editGameAuthorState()`.
    * The Game Authoring Enviroment needs the capability to create new levels within itself. This will be handled by a method `appendLevelGameAuthorState()` and can be removed by `removeLevelGameAuthorState()`.
    * The Game Authroing Enviroment needs the ability to create a new `GameAuthorState`. This will be handled through the method `newGameAuthorState()`.
* **Game Authoring Environment external API**: The only public methods contained within the authoring environment will be for the Data team to access the current state of the game through the `GameState` object. 
### Data 
* external 
    * load game made by the author sends a gamestate object to the authoring environment/ player
    * save game made by the author
    * load game mid play passes a game state object to the player
    * save game mid play
    * these could be condensed into one load and one save if the design is flexible enough 
* internal 
    * game state decoder - takes a game state object read in by the caller to load and organizes the containing classes to be read by the file writer
    * file writer takes organized data from the decoder and serialized them to a data file in xml format using XStream 
    * file reader takes the informatoin and forms it into the gams state object to be passed to the callee