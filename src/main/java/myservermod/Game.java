package myservermod;



import com.youthdigital.servermod.game.*;

public class Game extends TeamData {
  
  /* VARIBLES */
  public static int minutesLeft;
  public static int secondsLeft;
  
  
  public void setTimer() {
    minutesLeft = 5;
    secondsLeft = 0;
  }
  
  
  public Game(ChatColors teamColor, String teamNameDisplay) {
    super("All Players", teamColor, teamNameDisplay);
    setTimer();
  }
  
  public static void setupGameRules() {
    
    /* SERVER PROPERTIES */
    
    // access
    GameInfo.setServerDescription("An Awesome Minecraft Server By Nicholas N");
    GameInfo.addToWhitelist("bluemario8, Luigicatpower, samsungrocks10, lacheer01");
    GameInfo.addToOPs("bluemario8, Luigicatpower, lacheer01, samsungrocks10");
    
    // game rules
    GameInfo.disableBlockBreaking();
    GameInfo.addAllowedBreakableBlock(Blocks.diamond_ore);
    GameInfo.addMaxItemsHeldLimit(Items.diamond, 5);
    GameInfo.disableMobSpawning();    
    GameInfo.setTime(6000);
    GameInfo.setDifficulty(3);
    GameInfo.isRaining(false);
    GameInfo.setPVP(true);
    GameInfo.setTeamPVP(false);
    
    // teams
    Team blueTeam = new Team("blueTeam", "Blue Team", ChatColors.BLUE);
    Team redTeam = new Team("redTeam", "Red Team", ChatColors.RED);
    GameInfo.addTeam(blueTeam);
    GameInfo.addTeam(redTeam);
    
  }
  
  @Override
  public void onUpdate() {
    
  /* MOB SPAWNING */
    
    // zombie
    if (Conditions.secondsGoneBy(2)) {
      Actions.spawnSpecialEntityInRange("zombieSpawn", 5, EntityZombie.class, 1,
                                       Items.iron_axe, Enchantment.knockback, 3,
                                       Items.chainmail_helmet,
                                       Items.chainmail_chestplate,
                                       Items.golden_leggings,
                                       Items.chainmail_boots,
                                       Potion.invisibility, 10000,
                                       Potion.fireResistance, 10000,
                                       SharedMonsterAttributes.maxHealth, 5,
                                       SharedMonsterAttributes.movementSpeed, 0.3);
    }
    
    // skeleton
    if (Conditions.secondsGoneBy(2)) {
      Actions.spawnSpecialEntityInRange("skeletonSpawn", 5, EntitySkeleton.class, 1,
                                       Items.bow, Enchantment.punch, 1,
                                       Items.leather_helmet,
                                       SharedMonsterAttributes.maxHealth, 1);
    }
    
    // blaze
    if (Conditions.secondsGoneBy(15)) {
      Actions.spawnSpecialEntityInRange("blazeSpawn", 10, EntityBlaze.class, 1,
                                       SharedMonsterAttributes.maxHealth, 20,
                                       Potion.invisibility, 10000);
    }
    
    // creeper
    if (Conditions.secondsGoneBy(15)) {
      Actions.spawnSpecialEntityInRange("creeperSpawn", 5, EntityCreeper.class, 1,
                                       SharedMonsterAttributes.movementSpeed, 0.3,
                                       SharedMonsterAttributes.maxHealth, 10,
                                       Potion.jump, 10000);
    }
    
    
/* Chests */
    
    Random random = new Random();
    int fillRate = random.nextInt(5) + 3;
    
    String[] chestArray = { "chest1", "chest2", "chest3", "chest4" };
    Item[] itemArray = { Items.golden_apple, Items.stone_sword, Items.diamond_pickaxe, Items.milk_bucket, Items.iron_helmet };
    
    int chestNumber = random.nextInt(chestArray.length);
    int itemNumber = random.nextInt(itemArray.length);
    
    if(Conditions.secondsGoneBy(fillRate) && Conditions.getChestCount(chestArray[chestNumber]) < 3) {
      Actions.fillChest(chestArray[chestNumber], itemArray[itemNumber], 1);
    }
  
    
 /* TIMER */ 
    
    if (GameManager.isGameActive()) {
      if (Conditions.secondsGoneBy(1)) {
        if (secondsLeft > 0) {
        secondsLeft--;
        } else {
          minutesLeft--;
          secondsLeft = 59;
        }  
      }
    }
    
    if (secondsLeft == 0 && minutesLeft == 0) {
      GameManager.triggerGameOver();
    }
    
    
/* ORE RESTORE */ 
    
    if (Conditions.secondsGoneBy(60)) {
      Actions.loadBlocks("ores");
      Actions.saveBlocks("ores", 15, Blocks.diamond_ore);
    }
    
    
  }
  
  @Override
  public void onResetGameToLobby() {
    
    setTimer();
    Actions.loadBlocks("ores");    
  }
  
  @Override
  public void onStartGame() {
    
    Actions.saveBlocks("ores", 15, Blocks.diamond_ore);
    
  }
  
}