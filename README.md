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
                  type: generateBlocks
                  from: 10,3,10
                  to: 40,20,40
                  generator: 
                    type: Lobster
                    wallThickness: 1
                    ceilingThickness: 2
                    corridorHeight: 3
                    corridorWidth: 2
                    baseMaterial: "121"     
                    placeLadders: true 
                    placeTorches: true 
                    chanceForUpDown: 5 
                    ceilingMaterials:
                      -
                        material: GLOWSTONE
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
    * Lobster
      - corridorWidth: <width> // def = 1
      - corridorHeight: <height> // def = 2
      - borderThickness: <thickness> // def = 1
      - wallThickness: <thickness> // def = 1
      - ceilingThickness: <thickness> // def = 1
      - UpDownUseCorridorWidth: <true|false> // def = false
      - baseMaterial: <materialname>|<materialid> // def = SMOOTH_BRICK
      - baseMaterialData: <rawmetadata> // def = 3
      - placeTorches: <true|false> // def = true
      - placeLadders: <true|false> // def = true
      - placeChests: <true|false> // def = true
      - chanceForUpDown: <0..100> // def = 50 means half so often use up down in maze
      - chanceForTorches: <0..100> // def = 50
      - chanceForChests: <0..100> // def = 50
      - wallMaterials: // materials for building walls, array of materialnames or array of mat
      - floorMaterials: // materials for building floors, array of materialnames or array of mat
      - ceilingMaterials: // materials for building ceiling, array of materialnames or array of mat
      - chestItems: // items for chests, array of itemnames or array of chestitem
      