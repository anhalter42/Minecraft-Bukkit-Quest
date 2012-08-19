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
                  type: setLever
                  pos: PortalLever
                  mode: on

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
    * fillBlocks
      - material: <MaterialName|MaterialID>
      - data: <Data>
      - from: <x>,<y>,<z>
      - [to: <x>,<y>,<z>]
    * generateBlocks
      - generator: <GeneratorName>
      - from: <x>,<y>,<z>
      - [to: <x>,<y>,<z>]
    * startTimer
      - name: <name>
      - ticks: <ticks>
    * stopTimer
      - name: <name>
      - reset: <true|false>
    * continueTimer
      - name: <name>
    * setLever
      - pos: <x>,<y>,<z>
      - [mode: <toggle|on|off>]

    Generators
    ==========
    
    for generateBlocks
    * Randomizer