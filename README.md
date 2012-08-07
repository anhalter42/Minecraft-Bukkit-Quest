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
              - type: loadLevel
                index: 0
          - type: enterRegion
            from: 10,1,10
            to:   20,1,20
            Actions
              - type: loadLevel
                index: 1
                