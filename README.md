Minecraft-Bukkit-Quest
======================

    quest:
      name: TestQuest
      startscene: start
      markers:
        -
          name: PortalLever
          pos: 15,2,15
      scenes:
        -
          name: start
          triggers:
            -
              type: SceneInitialized
              actions:
                -
                  type: loadFrame
                  index: 0
            -
              type: PlayerEnteredRegion
              from: 10,1,10
              to:   20,1,20
              actions:
                -
                  type: startScene
                  name: level1
        -
          name: level1
          triggers:
            -
              type: SceneInitialized
              actions:
                -
                  type: loadFrame
                  index: 1
            -
              type: PlayerEnteredRegion
              from: 10,1,10
              to:   20,1,20
              actions:
                -
                  type: activateLever
                  pos: PortalLever

Trigger
=======
* SceneInitialized
* PlayerEnteredRegion
  - from: <x>,<y>,<z>
  - [to: <x>,<y>,<z>]
  
* TimerLapsed
  - name: <name>

Actions
=======

* startScene
  - name: <name>
* loadFrame
  - index: <index>
  - [mode: full|mixed|reverse]
  - [pos: <x>,<y>,<z>]
    fillRegion <Material> <Data> <x>,<y>,<z> [<x>,<y>,<z>]
    generateRegion <Generator> <x>,<y>,<z> [<x>,<y>,<z>]
    startTimer <name> <ticks>
    stopTimer <name>
    continueTimer <name>
    toggleLever <x>,<y>,<z>
    activateLever <x>,<y>,<z>
    deactivateLever <x>,<y>,<z>
