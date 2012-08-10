Minecraft-Bukkit-Quest
======================

    Quest
      name:
      Scenes
        - scene1
          Triggers
            - trigger1
              Actions
                - action1
            - trigger2
              Actions
                - action1
                - action2
        - scene2
          Triggers
            - trigger1
              Actions
                - action1
                
    name: TestQuest
    startscene: start
    Scenes
      - name: start
        Triggers
          - type: startScene
            Actions
              - type: loadFrame
                index: 0
          - type: playerEnterRegion
            from: 10,1,10
            to:   20,1,20
            Actions
              - type: loadFrame
                index: 1

Trigger
=======

initQuest
startQuest
initScene
startScene
playerEnterRegion <x>,<y>,<z> [<x>,<y>,<z>]
playerLeaveRegion <x>,<y>,<z> [<x>,<y>,<z>]
timerFinished <name>

Actions
=======

loadFrame index [full|mixed|reverse] [<x>,<y>,<z>]
fillRegion <Material> <Data> <x>,<y>,<z> [<x>,<y>,<z>]
startTimer <name> <ticks>
stopTimer <name>
continueTimer <name>
toggleLever <x>,<y>,<z>
activateLever <x>,<y>,<z>
deactivateLever <x>,<y>,<z>
